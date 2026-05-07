package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.RankingManager;

import fr.moodcraft.tgrade.model.TownGrade;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ClassementGUI {

    //
    // 🏆 OPEN
    //

    public static void open(
            Player p
    ) {

        Inventory inv =
                Bukkit.createInventory(

                        null,

                        54,

                        "§8🏆 Classement National"
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

        glassMeta.setDisplayName(" ");

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
        // 🏆 TOP
        //

        List<TownGrade> top =
                RankingManager.getTop();

        int slot = 10;

        int pos = 1;

        for (TownGrade grade : top) {

            if (slot >= 44)
                break;

            //
            // 🥇 MATERIAL
            //

            Material mat =
                    switch (pos) {

                        case 1 ->
                                Material.NETHER_STAR;

                        case 2 ->
                                Material.DIAMOND;

                        case 3 ->
                                Material.EMERALD;

                        default ->
                                Material.GOLD_INGOT;
                    };

            //
            // 📦 ITEM
            //

            ItemStack item =
                    new ItemStack(mat);

            ItemMeta meta =
                    item.getItemMeta();

            meta.setDisplayName(

                    "§6#"
                            + pos
                            + " §b"
                            + grade.getTown()
            );

            meta.setLore(List.of(

                    "§8━━━━━━━━━━━━━━━━",

                    "§7Prestige:",
                    grade.getRank(),

                    "",

                    "§7Score:",
                    grade.getFormattedScore(),

                    "",

                    "§7Bourse:",
                    "§a"
                            + format(
                            grade.getPayout()
                    )
                            + "$",

                    "",

                    "§7Appréciation:",
                    grade.getAppreciation()
            ));

            item.setItemMeta(meta);

            inv.setItem(slot, item);

            //
            // ➡ NEXT
            //

            slot++;

            if (slot == 17)
                slot = 19;

            if (slot == 26)
                slot = 28;

            if (slot == 35)
                slot = 37;

            pos++;
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

                "§7Retour au menu",

                "§7principal."
        ));

        back.setItemMeta(backMeta);

        inv.setItem(49, back);

        //
        // 🚀 OPEN
        //

        p.openInventory(inv);
    }

    //
    // 💰 FORMAT
    //

    private static String format(
            int value
    ) {

        return String.format(
                "%,d",
                value
        ).replace(",", " ");
    }
}