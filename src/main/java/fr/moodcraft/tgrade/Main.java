package fr.moodcraft.tgrade;

import fr.moodcraft.tgrade.command.UrbanismeCommand;

import fr.moodcraft.tgrade.listener.GUIListener;
import fr.moodcraft.tgrade.listener.RateGUIListener;
import fr.moodcraft.tgrade.listener.ReviewGUIListener;
import fr.moodcraft.tgrade.listener.UrbanismeMainListener;

import fr.moodcraft.tgrade.manager.GradeManager;

import fr.moodcraft.tgrade.storage.SubmissionStorage;
import fr.moodcraft.tgrade.storage.GradeStorage;

import fr.moodcraft.tgrade.task.WeeklyResetTask;

import org.bukkit.Bukkit;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    //
    // 🌍 INSTANCE
    //

    private static Main instance;

    public static Main get() {

        return instance;
    }

    //
    // 🚀 ENABLE
    //

    @Override
    public void onEnable() {

        //
        // 📌 INSTANCE
        //

        instance = this;

        //
        // 📂 CONFIG
        //

        saveDefaultConfig();

        //
        // 🌍 TOWNY CHECK
        //

        if (Bukkit.getPluginManager()
                .getPlugin("Towny") == null) {

            getLogger().severe("");

            getLogger().severe(
                    "━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            getLogger().severe(
                    "Towny introuvable"
            );

            getLogger().severe(
                    "MoodTownGrade désactivé"
            );

            getLogger().severe(
                    "━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            getLogger().severe("");

            Bukkit.getPluginManager()
                    .disablePlugin(this);

            return;
        }

        //
        // 💾 STORAGE
        //

        SubmissionStorage.init();

        GradeStorage.init();

        //
        // 📚 LOAD GRADES
        //

        GradeManager.loadAll();

        //
        // 📜 COMMANDES
        //

        if (getCommand("urbanisme") != null) {

            getCommand("urbanisme")
                    .setExecutor(
                            new UrbanismeCommand()
                    );
        }

        //
        // 🎨 GUI LISTENERS
        //

        getServer()
                .getPluginManager()
                .registerEvents(
                        new GUIListener(),
                        this
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        new RateGUIListener(),
                        this
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        new ReviewGUIListener(),
                        this
                );

        getServer()
                .getPluginManager()
                .registerEvents(
                        new UrbanismeMainListener(),
                        this
                );

        //
        // ⏰ WEEKLY RESET
        //

        long week =
                20L * 60L * 60L * 24L * 7L;

        Bukkit.getScheduler()
                .runTaskTimer(

                        this,

                        new WeeklyResetTask(),

                        week,

                        week
                );

        //
        // 🌆 CONSOLE
        //

        getLogger().info("");

        getLogger().info(
                "━━━━━━━━━━━━━━━━━━━━━━━━━━"
        );

        getLogger().info(
                "✦ MoodTownGrade activé"
        );

        getLogger().info("");

        getLogger().info(
                "Commission urbaine chargée"
        );

        getLogger().info(
                "Système d'inspection actif"
        );

        getLogger().info(
                "Grades chargés: "
                        + GradeManager.getAll()
                        .size()
        );

        getLogger().info(
                "Towny détecté avec succès"
        );

        getLogger().info(
                "Reset hebdomadaire actif"
        );

        getLogger().info("");

        getLogger().info(
                "━━━━━━━━━━━━━━━━━━━━━━━━━━"
        );

        getLogger().info("");
    }

    //
    // 🔻 DISABLE
    //

    @Override
    public void onDisable() {

        //
        // 💾 SAVE ALL
        //

        GradeManager.getAll()
                .forEach(
                        GradeManager::save
                );

        //
        // 🧹 CACHE CLEAR
        //

        GradeManager.clearCache();

        //
        // 🌆 CONSOLE
        //

        getLogger().info("");

        getLogger().info(
                "━━━━━━━━━━━━━━━━━━━━━━━━━━"
        );

        getLogger().info(
                "MoodTownGrade arrêté"
        );

        getLogger().info(
                "Inspection urbaine désactivée"
        );

        getLogger().info(
                "Sauvegarde terminée"
        );

        getLogger().info(
                "━━━━━━━━━━━━━━━━━━━━━━━━━━"
        );

        getLogger().info("");
    }
}