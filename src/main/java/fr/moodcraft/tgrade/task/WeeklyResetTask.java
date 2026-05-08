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

    @Override
    public void run() {

        TownGrade best =
                RankingManager.getBest();

        PayoutManager.payoutAll();

        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        Bukkit.broadcastMessage("§6✦ Fin de Saison Urbaine");
        Bukkit.broadcastMessage("");

        if (best != null) {

            double national =
                    NationalScoreCalculator.getFinalScore(
                            best.getTown()
                    );

            Bukkit.broadcastMessage("§7Ville dominante:");
            Bukkit.broadcastMessage(" §e👑 " + best.getTown());
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§7Prestige national:");
            Bukkit.broadcastMessage(" §e" + national + "§7/50");
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§7Classe urbaine:");
            Bukkit.broadcastMessage(" " + best.getRank());
            Bukkit.broadcastMessage("");
        }

        Bukkit.broadcastMessage("§a✦ Fonds Urbains Distribués");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§7Les villes validées ont reçu");
        Bukkit.broadcastMessage("§7leurs aides de développement.");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§7Archivage des projets et votes...");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        Bukkit.broadcastMessage("");

        for (TownGrade grade :
                GradeManager.getAll()) {

            grade.setArchitecture(0);
            grade.setStyle(0);
            grade.setActivite(0);
            grade.setBanque(0);
            grade.setRemarquable(0);
            grade.setRp(0);
            grade.setTaille(0);
            grade.setVotes(0);

            grade.setFinished(false);
            grade.setPayoutClaimed(false);

            GradeManager.save(grade);
        }

        SubmissionStorage.cleanup();

        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        Bukkit.broadcastMessage("§b✦ Nouvelle Semaine Urbaine");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§7Les projets précédents ont été");
        Bukkit.broadcastMessage("§7archivés par la commission.");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§7Les villes peuvent maintenant");
        Bukkit.broadcastMessage("§7déposer de nouveaux projets.");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§e▶ Votes et inspections réouverts");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        Bukkit.broadcastMessage("");
    }
}