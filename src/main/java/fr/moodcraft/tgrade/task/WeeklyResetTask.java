package fr.moodcraft.tgrade.task;

import fr.moodcraft.tgrade.manager.GradeManager;
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
                "§6🏛 Fin de semaine urbaine"
        );

        Bukkit.broadcastMessage("");

        //
        // 👑 BEST CITY
        //

        if (best != null) {

            Bukkit.broadcastMessage(
                    "§7Ville dominante:"
            );

            Bukkit.broadcastMessage(
                    "§e👑 "
                            + best.getTown()
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Prestige final:"
            );

            Bukkit.broadcastMessage(
                    best.getFormattedScore()
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    best.getRank()
            );

            Bukkit.broadcastMessage("");
        }

        Bukkit.broadcastMessage(
                "§7Les bourses municipales"
        );

        Bukkit.broadcastMessage(
                "§7ont été distribuées."
        );

        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(
                "§7Archivage des inspections..."
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
                "§b🏛 Nouvelle semaine urbaine"
        );

        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(
                "§7Les inspections précédentes"
        );

        Bukkit.broadcastMessage(
                "§7ont été archivées."
        );

        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(
                "§7Les villes peuvent déposer"
        );

        Bukkit.broadcastMessage(
                "§7de nouveaux projets urbains."
        );

        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(
                "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        );

        Bukkit.broadcastMessage("");
    }
}