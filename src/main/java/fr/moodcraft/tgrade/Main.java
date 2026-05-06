package fr.moodcraft.tgrade;

import fr.moodcraft.tgrade.command.VilleCommand;

import fr.moodcraft.tgrade.listener.GUIListener;
import fr.moodcraft.tgrade.listener.RateGUIListener;

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
        // ✅ CONSOLE
        //

        getLogger().info(
                "MoodTownGrade chargé ✔"
        );
    }

    //
    // 🔻 DISABLE
    //

    @Override
    public void onDisable() {

        getLogger().info(
                "MoodTownGrade arrêté ✖"
        );
    }
}