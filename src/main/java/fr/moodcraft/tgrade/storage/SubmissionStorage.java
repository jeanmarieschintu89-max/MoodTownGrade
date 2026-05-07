package fr.moodcraft.tgrade.storage;

import fr.moodcraft.tgrade.Main;
import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownSubmission;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SubmissionStorage {

    //
    // 📂 FILE
    //

    private static File file;

    //
    // 📄 CONFIG
    //

    private static YamlConfiguration config;

    //
    // 🚀 INIT
    //

    public static void init() {

        file = new File(
                Main.get().getDataFolder(),
                "submissions.yml"
        );

        if (!file.exists()) {

            try {

                file.getParentFile().mkdirs();

                file.createNewFile();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        config =
                YamlConfiguration.loadConfiguration(file);
    }

    //
    // 💾 SAVE SUBMISSION
    //

    public static void save(TownSubmission sub) {

        String path =
                "submissions." + sub.getId();

        config.set(path + ".town",
                sub.getTown());

        config.set(path + ".build",
                sub.getBuildName());

        config.set(path + ".world",
                sub.getWorld());

        config.set(path + ".x",
                sub.getX());

        config.set(path + ".y",
                sub.getY());

        config.set(path + ".z",
                sub.getZ());

        config.set(path + ".player",
                sub.getSubmittedBy().toString());

        config.set(path + ".timestamp",
                sub.getTimestamp());

        config.set(path + ".status",
                sub.getStatus().name());

        saveFile();
    }

    //
    // ❌ DELETE
    //

    public static void delete(String id) {

        config.set(
                "submissions." + id,
                null
        );

        saveFile();
    }

    //
    // 🗑 CLEAR ALL
    //

    public static void clearAll() {

        //
        // ❌ DELETE SECTION
        //

        config.set(
                "submissions",
                null
        );

        //
        // 💾 SAVE
        //

        saveFile();
    }

    //
    // 📚 GET ALL
    //

    public static List<TownSubmission> getAll() {

        List<TownSubmission> list =
                new ArrayList<>();

        if (!config.contains("submissions")) {
            return list;
        }

        for (String id :
                config.getConfigurationSection("submissions")
                        .getKeys(false)) {

            String path =
                    "submissions." + id;

            TownSubmission sub =
                    new TownSubmission(

                            id,

                            config.getString(path + ".town"),

                            config.getString(path + ".build"),

                            config.getString(path + ".world"),

                            config.getInt(path + ".x"),

                            config.getInt(path + ".y"),

                            config.getInt(path + ".z"),

                            UUID.fromString(
                                    config.getString(path + ".player")
                            ),

                            config.getLong(path + ".timestamp"),

                            SubmissionStatus.valueOf(
                                    config.getString(path + ".status")
                            )
                    );

            list.add(sub);
        }

        return list;
    }

    //
    // 🏙 GET TOWN SUBMISSIONS
    //

    public static List<TownSubmission> getTown(String town) {

        List<TownSubmission> list =
                new ArrayList<>();

        for (TownSubmission sub : getAll()) {

            if (sub.getTown()
                    .equalsIgnoreCase(town)) {

                list.add(sub);
            }
        }

        return list;
    }

    //
    // 🔍 GET BY ID
    //

    public static TownSubmission get(String id) {

        for (TownSubmission sub : getAll()) {

            if (sub.getId()
                    .equalsIgnoreCase(id)) {

                return sub;
            }
        }

        return null;
    }

    //
    // 💾 SAVE FILE
    //

    private static void saveFile() {

        try {

            config.save(file);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}