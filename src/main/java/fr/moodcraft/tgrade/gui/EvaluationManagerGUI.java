package fr.moodcraft.tgrade.gui;

import fr.moodcraft.flag.api.MoodTownFlagAPI;
import fr.moodcraft.tgrade.manager.GradeManager;
import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownGrade;
import fr.moodcraft.tgrade.model.TownSubmission;
import fr.moodcraft.tgrade.storage.SubmissionStorage;

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
            "§6✦ §8Notation Admin §6✦";

    private static final int[] BORDER_SLOTS = {
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 17,
            18, 26,
            27, 35,
            36, 44,
            45, 46, 47, 48, 50, 51, 52, 53
    };

    private static final int[] TOWN_SLOTS = {
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34,
            37, 38, 39, 40, 41, 42, 43
    };

    public static void open(
            Player p
    ) {

        Inventory inv = Bukkit.createInventory(
                null,
                54,
                TITLE
        );

        fillBorders(inv);

        inv.setItem(
                4,
                item(
                        Material.ENCHANTED_BOOK,
                        "§6✦ §fNotation staff §6✦",
                        List.of(
                                "§7Choisis une ville",
                                "§7avec un projet validé.",
                                "",
                                "§8• §7Dossier urbain",
                                "§8• §7Note staff",
                                "§8• §7Classement hebdo",
                                "",
                                "§eCliquer sur une ville"
                        )
                )
        );

        List<String> towns =
                getApprovedTowns();

        if (towns.isEmpty()) {

            inv.setItem(
                    22,
                    item(
                            Material.BARRIER,
                            "§6✦ §fAucun dossier §6✦",
                            List.of(
                                    "§7Aucune ville n'a",
                                    "§7de projet validé.",
                                    "",
                                    "§8• §7Validation requise"
                            )
                    )
            );

            inv.setItem(
                    49,
                    backItem()
            );

            p.openInventory(inv);
            return;
        }

        int index = 0;

        for (String town : towns) {

            if (index >= TOWN_SLOTS.length) {
                break;
            }

            TownSubmission project =
                    getActiveProject(town);

            String projectName =
                    project == null
                            ? "Projet en cours"
                            : project.getBuildName();

            TownGrade grade =
                    GradeManager.get(town);

            String score =
                    grade == null
                            ? "§70/50"
                            : grade.getFormattedScore();

            ItemStack icon =
                    null;

            boolean hasFlag = false;

            try {
                icon = MoodTownFlagAPI.getTownShieldItem(town);
                hasFlag = icon != null;
            } catch (Throwable ignored) {
                icon = null;
            }

            if (icon == null) {
                icon = new ItemStack(Material.SHIELD);
            }

            ItemMeta meta =
                    icon.getItemMeta();

            if (meta != null) {

                meta.setDisplayName(
                        "§6✦ §f" + town + " §6✦"
                );

                List<String> lore =
                        new ArrayList<>();

                lore.add("§7Ville: §b" + town);
                lore.add("§7Projet: §e" + projectName);
                lore.add("§7Score: " + score);
                lore.add("");

                if (hasFlag) {
                    lore.add("§8• §7Blason enregistré");
                } else {
                    lore.add("§8• §7Blason non défini");
                }

                lore.add("§8• §7Inspection staff");
                lore.add("§8• §7Notation hebdo");
                lore.add("");
                lore.add("§eOuvrir le dossier");

                meta.setLore(lore);
                meta.addItemFlags(
                        ItemFlag.HIDE_ADDITIONAL_TOOLTIP,
                        ItemFlag.HIDE_ATTRIBUTES,
                        ItemFlag.HIDE_ENCHANTS
                );

                icon.setItemMeta(meta);
            }

            inv.setItem(
                    TOWN_SLOTS[index],
                    icon
            );

            index++;
        }

        inv.setItem(
                49,
                backItem()
        );

        p.openInventory(inv);
    }

    private static void fillBorders(
            Inventory inv
    ) {

        ItemStack glass =
                item(
                        Material.BLACK_STAINED_GLASS_PANE,
                        " ",
                        null
                );

        for (int slot : BORDER_SLOTS) {
            inv.setItem(slot, glass);
        }
    }

    private static ItemStack backItem() {

        return item(
                Material.ARROW,
                "§6✦ §fRetour §6✦",
                List.of(
                        "§7Retour au centre",
                        "§7administratif.",
                        "",
                        "§8• §7Commission Urbaine"
                )
        );
    }

    private static ItemStack item(
            Material material,
            String name,
            List<String> lore
    ) {

        ItemStack item =
                new ItemStack(material);

        ItemMeta meta =
                item.getItemMeta();

        if (meta != null) {

            meta.setDisplayName(name);

            if (lore != null) {
                meta.setLore(lore);
            }

            meta.addItemFlags(
                    ItemFlag.HIDE_ADDITIONAL_TOOLTIP,
                    ItemFlag.HIDE_ATTRIBUTES,
                    ItemFlag.HIDE_ENCHANTS
            );

            item.setItemMeta(meta);
        }

        return item;
    }

    private static List<String> getApprovedTowns() {

        Set<String> towns =
                new HashSet<>();

        for (TownSubmission submission : SubmissionStorage.getAll()) {

            if (submission.getStatus() != SubmissionStatus.APPROVED) {
                continue;
            }

            towns.add(
                    submission.getTown()
            );
        }

        List<String> sorted =
                new ArrayList<>(towns);

        sorted.sort(
                String.CASE_INSENSITIVE_ORDER
        );

        return sorted;
    }

    private static TownSubmission getActiveProject(
            String town
    ) {

        TownSubmission fallback =
                null;

        for (TownSubmission submission : SubmissionStorage.getAll()) {

            if (!submission.getTown().equalsIgnoreCase(town)) {
                continue;
            }

            if (submission.getStatus() == SubmissionStatus.APPROVED) {
                return submission;
            }

            fallback = submission;
        }

        return fallback;
    }
}
