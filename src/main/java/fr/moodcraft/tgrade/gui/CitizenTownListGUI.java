package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.GradeManager;
import fr.moodcraft.tgrade.manager.NationalScoreCalculator;

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

public class CitizenTownListGUI {

    //
    // 🚀 OPEN
    //

    public static void open(
            Player p
    ) {

        Inventory inv =
                Bukkit.createInventory(

                        null,

                        54,

                        "§8✦ Avis Citoyens"
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

                45,46,47,48,49,50,51,52,53
        };

        for (int slot : borders) {

            inv.setItem(
                    slot,
                    glass
            );
        }

        //
        // 🏙️ LISTE VILLES
        //

        List<TownGrade> towns =
                new ArrayList<>(
                        GradeManager.getAll()
                );

        //
        // 🏆 TRI SCORE
        //

        towns.sort(

                Comparator.comparingDouble(

                        grade ->
                                -NationalScoreCalculator
                                        .getFinalScore(
                                                grade.getTown()
                                        )
                )
        );

        //
        // 📦 SLOT
        //

        int slot = 10;

        //
        // 📚 LOOP
        //

        for (TownGrade grade : towns) {

            //
            // ⛔ SLOTS INVALIDES
            //

            if (slot == 17
                    || slot == 26
                    || slot == 35
                    || slot == 44) {

                slot += 2;
            }

            //
            // ⛔ OVERFLOW
            //

            if (slot >= 45) {
                break;
            }

            //
            // 🏙️ DATA
            //

            String town =
                    grade.getTown();

            double score =
                    NationalScoreCalculator
                            .getFinalScore(
                                    town
                            );

            int citizens =
                    NationalScoreCalculator
                            .getCitizenCount(
                                    town
                            );

            //
            // 📦 ITEM
            //

            ItemStack item =
                    new ItemStack(
                            Material.GRASS_BLOCK
                    );

            ItemMeta meta =
                    item.getItemMeta();

            meta.setDisplayName(
                    "§b✦ " + town
            );

            meta.setLore(List.of(

                    "§8━━━━━━━━━━━━━━━━",

                    "§7Score national:",

                    " §e" + score + "§7/50",

                    "",

                    "§7Votes citoyens: §b"
                            + citizens,

                    "",

                    "§7Classement national actif",

                    "",

                    "§e▶ Cliquer pour voter"
            ));

            item.setItemMeta(meta);

            inv.setItem(
                    slot,
                    item
            );

            slot++;
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
                "§c✦ Retour"
        );

        back.setItemMeta(backMeta);

        inv.setItem(
                49,
                back
        );

        //
        // 🚀 OPEN
        //

        p.openInventory(inv);
    }
}