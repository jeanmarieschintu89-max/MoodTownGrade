package fr.moodcraft.tgrade.storage;

import fr.moodcraft.tgrade.Main;

import fr.moodcraft.tgrade.model.CitizenVote;
import fr.moodcraft.tgrade.model.MayorVote;
import fr.moodcraft.tgrade.model.StaffVote;

import org.bukkit.Bukkit;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VoteStorage {

    //
    // 📂 FILES
    //

    private static File staffFile;
    private static File mayorFile;
    private static File citizenFile;

    //
    // 📄 CONFIGS
    //

    private static YamlConfiguration staffConfig;
    private static YamlConfiguration mayorConfig;
    private static YamlConfiguration citizenConfig;

    //
    // 🚀 INIT
    //

    public static void init() {

        staffFile = new File(
                Main.get().getDataFolder(),
                "staffvotes.yml"
        );

        mayorFile = new File(
                Main.get().getDataFolder(),
                "mayorvotes.yml"
        );

        citizenFile = new File(
                Main.get().getDataFolder(),
                "citizenvotes.yml"
        );

        create(staffFile);
        create(mayorFile);
        create(citizenFile);

        staffConfig =
                YamlConfiguration.loadConfiguration(
                        staffFile
                );

        mayorConfig =
                YamlConfiguration.loadConfiguration(
                        mayorFile
                );

        citizenConfig =
                YamlConfiguration.loadConfiguration(
                        citizenFile
                );
    }

    //
    // 📂 CREATE
    //

    private static void create(
            File file
    ) {

        if (file.exists()) {
            return;
        }

        try {

            file.getParentFile()
                    .mkdirs();

            file.createNewFile();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    //
    // 🏛️ SAVE STAFF
    //

    public static void saveStaffVote(
            StaffVote vote
    ) {

        if (vote == null
                || vote.getTown() == null
                || vote.getVoter() == null) {

            return;
        }

        String path =
                "votes."
                        + vote.getTown()
                        + "."
                        + vote.getVoter();

        staffConfig.set(path + ".architecture", vote.getArchitecture());
        staffConfig.set(path + ".style", vote.getStyle());
        staffConfig.set(path + ".activite", vote.getActivite());
        staffConfig.set(path + ".banque", vote.getBanque());
        staffConfig.set(path + ".remarquable", vote.getRemarquable());
        staffConfig.set(path + ".rp", vote.getRp());
        staffConfig.set(path + ".taille", vote.getTaille());
        staffConfig.set(path + ".votes", vote.getVotes());
        staffConfig.set(path + ".timestamp", vote.getTimestamp());

        save(staffConfig, staffFile);
    }

    //
    // 👑 SAVE MAYOR
    //

    public static void saveMayorVote(
            MayorVote vote
    ) {

        if (vote == null
                || vote.getTown() == null
                || vote.getVoter() == null) {

            return;
        }

        String path =
                "votes."
                        + vote.getTown()
                        + "."
                        + vote.getVoter();

        mayorConfig.set(path + ".beaute", vote.getBeaute());
        mayorConfig.set(path + ".ambiance", vote.getAmbiance());
        mayorConfig.set(path + ".activite", vote.getActivite());
        mayorConfig.set(path + ".originalite", vote.getOriginalite());
        mayorConfig.set(path + ".popularite", vote.getPopularite());
        mayorConfig.set(path + ".timestamp", vote.getTimestamp());

        save(mayorConfig, mayorFile);
    }

    //
    // 👥 SAVE CITIZEN
    //

    public static void saveCitizenVote(
            CitizenVote vote
    ) {

        if (vote == null
                || vote.getTown() == null
                || vote.getVoter() == null) {

            return;
        }

        String path =
                "votes."
                        + vote.getTown()
                        + "."
                        + vote.getVoter();

        citizenConfig.set(path + ".beaute", vote.getBeaute());
        citizenConfig.set(path + ".ambiance", vote.getAmbiance());
        citizenConfig.set(path + ".activite", vote.getActivite());
        citizenConfig.set(path + ".originalite", vote.getOriginalite());
        citizenConfig.set(path + ".popularite", vote.getPopularite());
        citizenConfig.set(path + ".timestamp", vote.getTimestamp());

        save(citizenConfig, citizenFile);
    }

    //
    // 🏛️ GET STAFF VOTES
    //

    public static List<StaffVote>
    getStaffVotes(
            String town
    ) {

        List<StaffVote> votes =
                new ArrayList<>();

        if (town == null
                || staffConfig == null) {

            return votes;
        }

        ConfigurationSection section =
                staffConfig.getConfigurationSection(
                        "votes." + town
                );

        if (section == null) {
            return votes;
        }

        for (String key :
                section.getKeys(false)) {

            try {

                String path =
                        "votes."
                                + town
                                + "."
                                + key;

                StaffVote vote =
                        new StaffVote(
                                UUID.fromString(key),
                                town
                        );

                vote.setArchitecture(staffConfig.getInt(path + ".architecture"));
                vote.setStyle(staffConfig.getInt(path + ".style"));
                vote.setActivite(staffConfig.getInt(path + ".activite"));
                vote.setBanque(staffConfig.getInt(path + ".banque"));
                vote.setRemarquable(staffConfig.getInt(path + ".remarquable"));
                vote.setRp(staffConfig.getInt(path + ".rp"));
                vote.setTaille(staffConfig.getInt(path + ".taille"));
                vote.setVotes(staffConfig.getInt(path + ".votes"));

                votes.add(vote);

            } catch (Exception ex) {

                Bukkit.getConsoleSender()
                        .sendMessage(
                                "§c[MoodTownGrade] Vote staff corrompu ignoré: "
                                        + town
                                        + "/"
                                        + key
                        );
            }
        }

        return votes;
    }

    //
    // 👑 GET MAYOR VOTES
    //

    public static List<MayorVote>
    getMayorVotes(
            String town
    ) {

        List<MayorVote> votes =
                new ArrayList<>();

        if (town == null
                || mayorConfig == null) {

            return votes;
        }

        ConfigurationSection section =
                mayorConfig.getConfigurationSection(
                        "votes." + town
                );

        if (section == null) {
            return votes;
        }

        for (String key :
                section.getKeys(false)) {

            try {

                String path =
                        "votes."
                                + town
                                + "."
                                + key;

                MayorVote vote =
                        new MayorVote(
                                UUID.fromString(key),
                                town
                        );

                vote.setBeaute(mayorConfig.getInt(path + ".beaute"));
                vote.setAmbiance(mayorConfig.getInt(path + ".ambiance"));
                vote.setActivite(mayorConfig.getInt(path + ".activite"));
                vote.setOriginalite(mayorConfig.getInt(path + ".originalite"));
                vote.setPopularite(mayorConfig.getInt(path + ".popularite"));

                votes.add(vote);

            } catch (Exception ex) {

                Bukkit.getConsoleSender()
                        .sendMessage(
                                "§c[MoodTownGrade] Vote maire corrompu ignoré: "
                                        + town
                                        + "/"
                                        + key
                        );
            }
        }

        return votes;
    }

    //
    // 👥 GET CITIZEN VOTES
    //

    public static List<CitizenVote>
    getCitizenVotes(
            String town
    ) {

        List<CitizenVote> votes =
                new ArrayList<>();

        if (town == null
                || citizenConfig == null) {

            return votes;
        }

        ConfigurationSection section =
                citizenConfig.getConfigurationSection(
                        "votes." + town
                );

        if (section == null) {
            return votes;
        }

        for (String key :
                section.getKeys(false)) {

            try {

                String path =
                        "votes."
                                + town
                                + "."
                                + key;

                CitizenVote vote =
                        new CitizenVote(
                                UUID.fromString(key),
                                town
                        );

                vote.setBeaute(citizenConfig.getInt(path + ".beaute"));
                vote.setAmbiance(citizenConfig.getInt(path + ".ambiance"));
                vote.setActivite(citizenConfig.getInt(path + ".activite"));
                vote.setOriginalite(citizenConfig.getInt(path + ".originalite"));
                vote.setPopularite(citizenConfig.getInt(path + ".popularite"));

                votes.add(vote);

            } catch (Exception ex) {

                Bukkit.getConsoleSender()
                        .sendMessage(
                                "§c[MoodTownGrade] Vote citoyen corrompu ignoré: "
                                        + town
                                        + "/"
                                        + key
                        );
            }
        }

        return votes;
    }

    //
    // 🧹 CLEAR ALL
    //

    public static void clearAll() {

        if (staffConfig != null) {

            staffConfig.set(
                    "votes",
                    null
            );

            save(
                    staffConfig,
                    staffFile
            );
        }

        if (mayorConfig != null) {

            mayorConfig.set(
                    "votes",
                    null
            );

            save(
                    mayorConfig,
                    mayorFile
            );
        }

        if (citizenConfig != null) {

            citizenConfig.set(
                    "votes",
                    null
            );

            save(
                    citizenConfig,
                    citizenFile
            );
        }
    }

    //
    // 💾 SAVE FILE
    //

    private static void save(
            YamlConfiguration config,
            File file
    ) {

        try {

            config.save(file);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}