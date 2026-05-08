package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.GradeManager;

import fr.moodcraft.tgrade.model.TownGrade;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

        //
        // 🌌 GLASS
        //

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

        //
        // 🧱 BORDERS
        //

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
                "§b✦ Centre d'Évaluation"
        );

        headerMeta.setLore(List.of(

                "§8━━━━━━━━━━━━━━━━",

                "§7Gestion des notes",

                "§7urbaines nationales.",

                "",

                "§7Villes inspectées: §b"
                        + GradeManager.getAll().size(),

                "",

                "§b▶ Commission active"
        ));

        header.setItemMeta(headerMeta);

        inv.setItem(4, header);

        //
        // 📚 LISTE
        //

        List<TownGrade> grades =
                new ArrayList<>(
                        GradeManager.getAll()
                );

        //
        // 🏆 TRI SCORE
        //

        grades.sort(
                Comparator.comparingInt(
                        TownGrade::getTotal
                ).reversed()
        );

        //
        // 📦 SLOT
        //

        int slot = 10;

        for (TownGrade grade : grades) {

            if (slot >= 44)
                break;

            Material mat;

            int total =
                    grade.getTotal();

            if (total >= 45) {

                mat = Material.NETHER_STAR;

            } else if (total >= 35) {

                mat = Material.BEACON;

            } else if (total >= 25) {

                mat = Material.EMERALD;

            } else if (total >= 15) {

                mat = Material.GOLD_INGOT;

            } else {

                mat = Material.IRON_INGOT;
            }

            ItemStack item =
                    new ItemStack(mat);

            ItemMeta meta =
                    item.getItemMeta();

            meta.setDisplayName(
                    "§f✦ §b" + grade.getTown()
            );

            meta.setLore(List.of(

                    "§8━━━━━━━━━━━━━━━━",

                    "§7Score global:",
                    " " + grade.getFormattedScore(),

                    "",

                    "§7Rang urbain:",
                    grade.getRank(),

                    "",

                    "§7Architecture: §e"
                            + grade.getArchitecture()
                            + "§7/10",

                    "§7Style: §e"
                            + grade.getStyle()
                            + "§7/6",

                    "§7Activité: §e"
                            + grade.getActivite()
                            + "§7/8",

                    "§7Banque: §e"
                            + grade.getBanque()
                            + "§7/4",

                    "§7Remarquable: §e"
                            + grade.getRemarquable()
                            + "§7/8",

                    "§7RP: §e"
                            + grade.getRp()
                            + "§7/6",

                    "§7Taille: §e"
                            + grade.getTaille()
                            + "§7/3",

                    "§7Votes: §e"
                            + grade.getVotes()
                            + "§7/5",

                    "",

                    "§7Statut:",

                    grade.isFinished()
                            ? "§a✔ Inspection validée"
                            : "§6⏳ Évaluation en cours",

                    "",

                    "§b▶ Cliquer pour modifier"
            ));

            item.setItemMeta(meta);

            inv.setItem(slot, item);

            //
            // ➡ NEXT SLOT
            //

            slot++;

            if (slot == 17)
                slot = 19;

            if (slot == 26)
                slot = 28;

            if (slot == 35)
                slot = 37;
        }

        //
        // ❌ AUCUNE VILLE
        //

        if (grades.isEmpty()) {

            ItemStack empty =
                    new ItemStack(
                            Material.BARRIER
                    );

            ItemMeta meta =
                    empty.getItemMeta();

            meta.setDisplayName(
                    "§c✖ Aucune évaluation"
            );

            meta.setLore(List.of(

                    "§8━━━━━━━━━━━━━━━━",

                    "§7Aucune ville n'a encore",

                    "§7été inspectée par",

                    "§7la commission nationale."
            ));

            empty.setItemMeta(meta);

            inv.setItem(22, empty);
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
                "§c⬅ Retour administratif"
        );

        backMeta.setLore(List.of(

                "§8━━━━━━━━━━━━━━━━",

                "§7Retour au centre",

                "§7national d'urbanisme."
        ));

        back.setItemMeta(backMeta);

        inv.setItem(49, back);

        //
        // 🚀 OPEN
        //

        p.openInventory(inv);
    }
}