package fr.moodcraft.tgrade.task;

import fr.moodcraft.tgrade.manager.GradeManager;

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
        // 🧹 RESET NOTES
        //

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

            //
            // 💾 SAVE
            //

            GradeManager.save(grade);
        }

        //
        // 🗑 DELETE PROJECTS
        //

        SubmissionStorage.clearAll();

        //
        // 📢 BROADCAST
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
                "§7Les inspections ont été archivées."
        );

        Bukkit.broadcastMessage(
                "§7Les nouveaux projets peuvent"
        );

        Bukkit.broadcastMessage(
                "§7désormais être déposés."
        );

        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(
                "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        );

        Bukkit.broadcastMessage("");
    }
}