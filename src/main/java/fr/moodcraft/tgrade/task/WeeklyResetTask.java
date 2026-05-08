package fr.moodcraft.tgrade.task;

import fr.moodcraft.tgrade.manager.GradeManager;
import fr.moodcraft.tgrade.manager.NationalScoreCalculator;
import fr.moodcraft.tgrade.manager.PayoutManager;
import fr.moodcraft.tgrade.manager.RankingManager;

import fr.moodcraft.tgrade.model.TownGrade;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Bukkit;

public class WeeklyResetTask
        implements Runnable {

    //
    // 🔄 RESET
    //

    @Override
    public void run() {

        //
        // 🏆 BEST CITY
        //

        TownGrade best =
                RankingManager.getBest();

        //
        // 💰 PAYOUTS
        //

        PayoutManager.payoutAll();

        //
        // 📢 FIN SAISON
        //

        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(
                "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        );

        Bukkit.broadcastMessage(
                "§6✦ Archives Nationales Urbaines"
        );

        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(
                "§7La semaine d'inspection urbaine"
        );

        Bukkit.broadcastMessage(
                "§7vient officiellement de se terminer."
        );

        Bukkit.broadcastMessage("");

        //
        // 👑 BEST CITY
        //

        if (best != null) {

            double national =
                    NationalScoreCalculator
                            .getFinalScore(
                                    best.getTown()
                            );

            double staff =
                    NationalScoreCalculator
                            .getStaffScore(
                                    best.getTown()
                            );

            double mayors =
                    NationalScoreCalculator
                            .getMayorScore(
                                    best.getTown()
                            );

            double citizens =
                    NationalScoreCalculator
                            .getCitizenScore(
                                    best.getTown()
                            );

            Bukkit.broadcastMessage(
                    "§6👑 Capitale dominante de la semaine"
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    " §e" + best.getTown()
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Prestige national:"
            );

            Bukkit.broadcastMessage(
                    " §e" + national + "§7/50"
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Réputation gouvernementale:"
            );

            Bukkit.broadcastMessage(
                    " §6🏛 Staff: §e" + staff
            );

            Bukkit.broadcastMessage(
                    " §6👑 Maires: §e" + mayors
            );

            Bukkit.broadcastMessage(
                    " §6👥 Citoyens: §e" + citizens
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Classe urbaine:"
            );

            Bukkit.broadcastMessage(
                    " " + best.getRank()
            );

            Bukkit.broadcastMessage("");
        }

        Bukkit.broadcastMessage(
                "§a✦ Fonds Nationaux Distribués"
        );

        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(
                "§7Les municipalités inspectées"
        );

        Bukkit.broadcastMessage(
                "§7ont reçu leurs subventions"
        );

        Bukkit.broadcastMessage(
                "§7de développement urbain."
        );

        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(
                "§7Archivage des inspections"
        );

        Bukkit.broadcastMessage(
                "§7et fermeture des registres..."
        );

        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(
                "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        );

        Bukkit.broadcastMessage("");

        //
        // 🧹 RESET NOTES
        //

        for (TownGrade grade :
                GradeManager.getAll()) {

            //
            // 📊 RESET NOTES
            //

            grade.setArchitecture(0);

            grade.setStyle(0);

            grade.setActivite(0);

            grade.setBanque(0);

            grade.setRemarquable(0);

            grade.setRp(0);

            grade.setTaille(0);

            grade.setVotes(0);

            //
            // 🔄 RESET
            //

            grade.setFinished(false);

            grade.setPayoutClaimed(false);

            //
            // 💾 SAVE
            //

            GradeManager.save(grade);
        }

        //
        // 🧹 CLEAN PROJECTS
        //

        SubmissionStorage.cleanup();

        //
        // 📢 NEW WEEK
        //

        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(
                "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        );

        Bukkit.broadcastMessage(
                "§b✦ Nouvelle Saison Urbaine"
        );

        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(
                "§7Les anciennes inspections"
        );

        Bukkit.broadcastMessage(
                "§7ont été archivées dans"
        );

        Bukkit.broadcastMessage(
                "§7les registres nationaux."
        );

        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(
                "§7Les villes peuvent désormais"
        );

        Bukkit.broadcastMessage(
                "§7déposer de nouveaux projets"
        );

        Bukkit.broadcastMessage(
                "§7et participer au prestige national."
        );

        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(
                "§e▶ Les votes citoyens et"
        );

        Bukkit.broadcastMessage(
                "§e  gouvernementaux sont réouverts"
        );

        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(
                "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        );

        Bukkit.broadcastMessage("");
    }
}