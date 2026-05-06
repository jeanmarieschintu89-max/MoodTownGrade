package fr.moodcraft.tgrade;

import fr.moodcraft.tgrade.command.VilleCommand;
import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    public static Main get() {
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        SubmissionStorage.init();

        getCommand("ville")
                .setExecutor(new VilleCommand());

        getLogger().info("MoodTownGrade chargé ✔");
    }
}