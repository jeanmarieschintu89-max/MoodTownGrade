package fr.moodcraft.tgrade;

import fr.moodcraft.tgrade.command.VilleCommand;
import fr.moodcraft.tgrade.listener.GUIListener;
import fr.moodcraft.tgrade.storage.SubmissionStorage;

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
                .setExecutor(new VilleCommand());

        //
        // ✅ CONSOLE
        //

        getLogger().info("MoodTownGrade chargé ✔");
    }

    //
    // 🔻 DISABLE
    //

    @Override
    public void onDisable() {

        getLogger().info("MoodTownGrade arrêté ✖");
    }
}