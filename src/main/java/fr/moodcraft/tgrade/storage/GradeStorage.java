package fr.moodcraft.tgrade.storage;

import fr.moodcraft.tgrade.Main;

import fr.moodcraft.tgrade.model.TownGrade;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class GradeStorage {

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
                "grades.yml"
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
    // 💾 SAVE
    //

    public static void save(TownGrade grade) {

        String path =
                "grades." + grade.getTown();

        config.set(
                path + ".architecture",
                grade.getArchitecture()
        );

        config.set(
                path + ".style",
                grade.getStyle()
        );

        config.set(
                path + ".activite",
                grade.getActivite()
        );

        config.set(
                path + ".banque",
                grade.getBanque()
        );

        config.set(
                path + ".remarquable",
                grade.getRemarquable()
        );

        config.set(
                path + ".rp",
                grade.getRp()
        );

        config.set(
                path + ".taille",
                grade.getTaille()
        );

        config.set(
                path + ".votes",
                grade.getVotes()
        );

        saveFile();
    }

    //
    // 📥 LOAD
    //

    public static TownGrade load(String town) {

        String path =
                "grades." + town;

        TownGrade grade =
                new TownGrade(town);

        grade.setArchitecture(
                config.getInt(
                        path + ".architecture"
                )
        );

        grade.setStyle(
                config.getInt(
                        path + ".style"
                )
        );

        grade.setActivite(
                config.getInt(
                        path + ".activite"
                )
        );

        grade.setBanque(
                config.getInt(
                        path + ".banque"
                )
        );

        grade.setRemarquable(
                config.getInt(
                        path + ".remarquable"
                )
        );

        grade.setRp(
                config.getInt(
                        path + ".rp"
                )
        );

        grade.setTaille(
                config.getInt(
                        path + ".taille"
                )
        );

        grade.setVotes(
                config.getInt(
                        path + ".votes"
                )
        );

        return grade;
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