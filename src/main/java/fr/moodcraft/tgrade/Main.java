package fr.moodcraft.tgrade;

import fr.moodcraft.tgrade.command.VilleCommand;

import fr.moodcraft.tgrade.listener.GUIListener;
import fr.moodcraft.tgrade.listener.RateGUIListener;
import fr.moodcraft.tgrade.listener.ReviewGUIListener;

import fr.moodcraft.tgrade.manager.GradeManager;

import fr.moodcraft.tgrade.storage.SubmissionStorage;
import fr.moodcraft.tgrade.storage.GradeStorage;

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

        if (getCommand("ville") != null) {

            getCommand("ville")
                    .setExecutor(
                            new VilleCommand()
                    );
        }

        //
        // 🎨 GUI LISTENER
        //

        getServer()
                .getPluginManager()
                .registerEvents(
                        new GUIListener(),
                        this
                );

        //
        // 📊 RATE GUI LISTENER
        //

        getServer()
                .getPluginManager()
                .registerEvents(
                        new RateGUIListener(),
                        this
                );

        //
        // 🏛️ REVIEW GUI LISTENER
        //

        getServer()
                .getPluginManager()
                .registerEvents(
                        new ReviewGUIListener(),
                        this
                );

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
                    "Towny introuvable."
            );

            getLogger().severe(
                    "MoodTownGrade désactivé."
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