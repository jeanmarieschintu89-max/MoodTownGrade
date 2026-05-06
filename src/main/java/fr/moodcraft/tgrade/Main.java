package fr.moodcraft.tgrade;

import fr.moodcraft.tgrade.command.VilleCommand;

import fr.moodcraft.tgrade.listener.GUIListener;
import fr.moodcraft.tgrade.listener.RateGUIListener;
import fr.moodcraft.tgrade.listener.ReviewGUIListener;

import fr.moodcraft.tgrade.storage.SubmissionStorage;
import fr.moodcraft.tgrade.storage.GradeStorage;

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
        // 📜 COMMANDES
        //

        getCommand("ville")
                .setExecutor(
                        new VilleCommand()
                );

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
        // 🌆 CONSOLE
        //

        getLogger().info("");

        getLogger().info(
                "━━━━━━━━━━━━━━━━━━━━━━━━━━"
        );

        getLogger().info(
                "✦ MoodTownGrade activé"
        );

        getLogger().info(
                "Commission urbaine chargée."
        );

        getLogger().info(
                "Système d'inspection actif."
        );

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

        getLogger().info("");

        getLogger().info(
                "━━━━━━━━━━━━━━━━━━━━━━━━━━"
        );

        getLogger().info(
                "MoodTownGrade arrêté."
        );

        getLogger().info(
                "Inspection urbaine désactivée."
        );

        getLogger().info(
                "━━━━━━━━━━━━━━━━━━━━━━━━━━"
        );

        getLogger().info("");
    }
}