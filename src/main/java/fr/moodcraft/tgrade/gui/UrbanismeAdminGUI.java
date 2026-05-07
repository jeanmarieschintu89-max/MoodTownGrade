package fr.moodcraft.tgrade.gui;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class UrbanismeAdminGUI {

    //
    // 🛰 OPEN
    //

    public static void open(
            Player p
    ) {

        Inventory inv =
                Bukkit.createInventory(

                        null,

                        45,

                        "§8Centre Administratif"
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
        // 🧱 BORDERS ONLY
        //

        int[] borders = {

                0,1,2,3,4,5,6,7,8,

                9,17,

                18,26,

                27,35,

                36,44,

                45,46,47,48,49,50,51,52,53
        };

        for (int slot : borders) {

            if (slot < 45) {

                inv.setItem(slot, glass);
            }
        }

        //
        // 📋 PROJETS
        //

        set(

                inv,

                13,

                Material.WRITABLE_BOOK,

                "§e📋 Projets Urbains",

                "§7Consulter les projets",

                "§7de construction RP."
        );

        //
        // 💰 PAYOUT
        //

        set(

                inv,

                31,

                Material.EMERALD_BLOCK,

                "§2💰 Versements",

                "§7Distribuer les",

                "§7bourses municipales."
        );

        //
        // 🏆 CLASSEMENT
        //

        set(

                inv,

                33,

                Material.NETHER_STAR,

                "§b🏆 Classement",

                "§7Voir le classement",

                "§7national des villes."
        );

        //
        // 🔙 RETOUR
        //

        set(

                inv,

                40,

                Material.ARROW,

                "§c⬅ Retour",

                "§7Retour au menu principal."
        );

        //
        // 🚀 OPEN
        //

        p.openInventory(inv);
    }

    //
    // 🛠 ITEM
    //

    private static void set(

            Inventory inv,

            int slot,

            Material mat,

            String name,

            String... lore
    ) {

        ItemStack item =
                new ItemStack(mat);

        ItemMeta meta =
                item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(
                List.of(lore)
        );

        item.setItemMeta(meta);

        inv.setItem(
                slot,
                item
        );
    }
}