package fr.moodcraft.tgrade.task;

import fr.moodcraft.tgrade.manager.GradeManager;

import org.bukkit.Bukkit;

public class WeeklyResetTask implements Runnable {

    @Override
    public void run() {

        //
        // 🔄 RESET
        //

        GradeManager.resetWeek();

        //
        // 📢 ANNOUNCE
        //

        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(
                "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        );

        Bukkit.broadcastMessage(
                "§b🏛 Nouvelle saison urbaine"
        );

        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(
                "§7Les inspections municipales"
        );

        Bukkit.broadcastMessage(
                "§7ont été réinitialisées."
        );

        Bukkit.broadcastMessage("");

        Bukkit.broadcastMessage(
                "§7Les villes peuvent"
        );

        Bukkit.broadcastMessage(
                "§7soumettre de nouveaux projets."
        );

        Bukkit.broadcastMessage(
                "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        );

        Bukkit.broadcastMessage("");
    }
}