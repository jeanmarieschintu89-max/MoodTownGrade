package fr.moodcraft.tgrade.gui;

import fr.moodcraft.flag.api.MoodTownFlagAPI;
import fr.moodcraft.tgrade.manager.GradeManager;
import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownGrade;
import fr.moodcraft.tgrade.model.TownSubmission;
import fr.moodcraft.tgrade.storage.SubmissionStorage;
import fr.moodcraft.tgrade.util.MoodStyle;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EvaluationManagerGUI {

    public static final String TITLE =
            MoodStyle.EVALUATION_TITLE;

    private static final int[] BORDER_SLOTS = {
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 17,
            18, 26,
            27, 35,
            36, 44,
            45, 46, 47, 48, 50, 51, 52, 53
    };

    private static final int[] CONTENT_SLOTS = {
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34,
            37, 38, 39, 40, 41, 42, 43
    };

    public static void open(Player player) {

        Inventory inventory = Bukkit.createInventory(null, 54, TITLE);
        fill(inventory);

        List<String> towns = collectTowns();

        inventory.setItem(
                4,
                MoodStyle.item(
                        Material.NETHER_STAR,
                        "§6✦ §fNotation Admin §6✦",
                        List.of(
                                MoodStyle.detail("Villes avec dossiers urbains"),
                                MoodStyle.detail("Notation staff et suivi"),
                                MoodStyle.detail("Total : §e" + towns.size()),
                                "",
                                MoodStyle.info("Sélectionnez une ville")
                        ),
                        true
                )
        );

        int index = 0;

        for (String townName : towns) {
            if (index >= CONTENT_SLOTS.length) {
                break;
            }

            inventory.setItem(CONTENT_SLOTS[index], townItem(townName));
            index++;
        }

        if (towns.isEmpty()) {
            inventory.setItem(
                    22,
                    MoodStyle.item(
                            Material.BARRIER,
                            "§6✦ §fAucun dossier §6✦",
                            List.of(
                                    MoodStyle.detail("Aucune ville à noter"),
                                    MoodStyle.detail("Les projets apparaîtront ici")
                            ),
                            false
                    )
            );
        }

        inventory.setItem(
                49,
                MoodStyle.item(
                        Material.BARRIER,
                        "§6✦ §fRetour §6✦",
                        List.of(
                                MoodStyle.detail("Centre national"),
                                "",
                                MoodStyle.info("Retour")
                        ),
                        false
                )
        );

        player.openInventory(inventory);
    }

    private static List<String> collectTowns() {

        Set<String> names = new HashSet<>();

        for (TownGrade grade : GradeManager.getAllGrades()) {
            if (grade != null && grade.getTownName() != null && !grade.getTownName().isBlank()) {
                names.add(grade.getTownName());
            }
        }

        for (TownSubmission submission : SubmissionStorage.getAll()) {
            if (submission != null && submission.getTownName() != null && !submission.getTownName().isBlank()) {
                names.add(submission.getTownName());
            }
        }

        List<String> list = new ArrayList<>(names);
        list.sort(String.CASE_INSENSITIVE_ORDER);
        return list;
    }

    private static ItemStack townItem(String townName) {

        ItemStack base = MoodTownFlagAPI.getTownShieldItem(townName);

        if (base == null) {
            base = new ItemStack(Material.MAP);
        }

        ItemMeta meta = base.getItemMeta();

        if (meta == null) {
            return base;
        }

        int score = GradeManager.getGlobalScore(townName);
        int pending = 0;
        int approved = 0;

        for (TownSubmission submission : SubmissionStorage.getAll()) {
            if (submission == null || submission.getTownName() == null) {
                continue;
            }

            if (!submission.getTownName().equalsIgnoreCase(townName)) {
                continue;
            }

            if (submission.getStatus() == SubmissionStatus.PENDING) {
                pending++;
            }

            if (submission.getStatus() == SubmissionStatus.APPROVED) {
                approved++;
            }
        }

        meta.setDisplayName("§6✦ §f" + townName + " §6✦");
        meta.setLore(List.of(
                MoodStyle.detail("Score : §e" + score + "§8/§e100"),
                MoodStyle.detail("En attente : §e" + pending),
                MoodStyle.detail("Validés : §a" + approved),
                "",
                MoodStyle.info("Ouvrir le dossier")
        ));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        base.setItemMeta(meta);
        return base;
    }

    private static void fill(Inventory inventory) {
        ItemStack pane = MoodStyle.item(Material.BLACK_STAINED_GLASS_PANE, " ", List.of(), false);

        for (int slot : BORDER_SLOTS) {
            inventory.setItem(slot, pane);
        }
    }
}
