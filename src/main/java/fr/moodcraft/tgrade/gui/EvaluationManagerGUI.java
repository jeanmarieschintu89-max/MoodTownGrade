package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.NationalScoreCalculator;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EvaluationManagerGUI {

    //
    // ⭐ OPEN
    //

    public static void open(Player p) {

        Inventory inv =
                Bukkit.createInventory(

                        null,

                        54,

                        "§8✦ Évaluations Nationales"
                );

        ItemStack glass =
                new ItemStack(
                        Material.BLACK_STAINED_GLASS_PANE
                );

        ItemMeta glassMeta =
                glass.getItemMeta();

        glassMeta.setDisplayName(
                " "
        );

        glass.setItemMeta(glassMeta);

        int[] borders = {

                0,1,2,3,4,5,6,7,8,

                9,17,

                18,26,

                27,35,

                36,44,

                45,46,47,48,50,51,52,53
        };

        for (int slot : borders) {

            inv.setItem(slot, glass);
        }

        //
        // 🏛 HEADER
        //

        ItemStack header =
                new ItemStack(
                        Material.ENCHANTED_BOOK
                );

        ItemMeta headerMeta =
                header.getItemMeta();

        headerMeta.setDisplayName(
                "§6✦ Registre des Évaluations"
        );

        headerMeta.setLore(List.of(

                "§8----- §6Commission Urbaine §8-----",

                "§7Sélectionnez une ville",

                "§7pour ouvrir sa notation.",

                "",

                "§e▶ Évaluation nationale"
        ));

        header.setItemMeta(headerMeta);

        inv.setItem(4, header);

        //
        // 🏙 VILLES VALIDÉES
        //

        Set<String> towns =
                new HashSet<>();

        for (TownSubmission sub :
                SubmissionStorage.getAll()) {

            if (sub.getStatus()
                    != SubmissionStatus.APPROVED) {

                continue;
            }

            towns.add(
                    sub.getTown()
            );
        }

        List<String> sorted =
                new ArrayList<>(towns);

        sorted.sort(String::compareToIgnoreCase);

        //
        // ❌ EMPTY
        //

        if (sorted.isEmpty()) {

            ItemStack empty =
                    new ItemStack(
                            Material.BARRIER
                    );

            ItemMeta meta =
                    empty.getItemMeta();

            meta.setDisplayName(
                    "§c✖ Aucune ville évaluable"
            );

            meta.setLore(List.of(

                    "§8----- §6Registre National §8-----",

                    "§7Aucun projet validé",

                    "§7n'est disponible pour",

                    "§7une notation nationale.",

                    "",

                    "§e▶ Validez d'abord un projet"
            ));

            empty.setItemMeta(meta);

            inv.setItem(22, empty);
        }

        //
        // 📦 LISTE
        //

        int slot = 10;

        for (String town : sorted) {

            if (slot >= 44)
                break;

            double score =
                    NationalScoreCalculator
                            .getFinalScore(town);

            ItemStack item =
                    new ItemStack(
                            Material.BEACON
                    );

            ItemMeta meta =
                    item.getItemMeta();

            meta.setDisplayName(
                    "§f✦ §b" + town
            );

            meta.setLore(List.of(

                    "§8----- §6Dossier National §8-----",

                    "§7Ville: §b" + town,

                    "§7Note actuelle: §e"
                            + score
                            + "§7/50",

                    "",

                    "§7Ouvre directement le",

                    "§7menu de notation officiel.",

                    "",

                    "§e▶ Noter cette ville"
            ));

            item.setItemMeta(meta);

            inv.setItem(slot, item);

            slot++;

            if (slot == 17)
                slot = 19;

            if (slot == 26)
                slot = 28;

            if (slot == 35)
                slot = 37;
        }

        //
        // 🔙 RETOUR
        //

        ItemStack back =
                new ItemStack(
                        Material.ARROW
                );

        ItemMeta backMeta =
                back.getItemMeta();

        backMeta.setDisplayName(
                "§c⬅ Retour"
        );

        backMeta.setLore(List.of(

                "§8----- §6Centre National §8-----",

                "§7Retour au centre",

                "§7national d'urbanisme."
        ));

        back.setItemMeta(backMeta);

        inv.setItem(49, back);

        p.openInventory(inv);
    }
}