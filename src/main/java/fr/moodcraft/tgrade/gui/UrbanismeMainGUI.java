package fr.moodcraft.tgrade.gui;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class UrbanismeMainGUI {

    //
    // 🏛 OPEN
    //

    public static void open(Player p) {

        Inventory inv =
                Bukkit.createInventory(

                        null,

                        36,

                        "§8🏛 Commission Urbaine"
                );

        //
        // 🌌 GLASS
        //

        ItemStack glass =
                item(

                        Material.BLACK_STAINED_GLASS_PANE,

                        " "
                );

        //
        // 🧱 BORDERS ONLY
        //

        int[] borders = {

                0,1,2,3,4,5,6,7,8,

                9,17,

                18,26,

                27,28,29,30,31,32,33,34,35
        };

        for (int slot : borders) {

            inv.setItem(slot, glass);
        }

        //
        // 📜 PROJETS
        //

        inv.setItem(

                11,

                item(

                        Material.WRITABLE_BOOK,

                        "§b📜 Projets Urbains",

                        "§7Voir les projets",

                        "§7de votre ville."
                )
        );

        //
        // ➕ SOUMISSION
        //

        inv.setItem(

                13,

                item(

                        Material.NETHER_STAR,

                        "§a➕ Nouveau Projet",

                        "§7Place-toi devant",

                        "§7la construction RP."
                )
        );

        //
        // 🏆 CLASSEMENT
        //

        inv.setItem(

                15,

                item(

                        Material.GOLD_INGOT,

                        "§6🏆 Classement National",

                        "§7Voir le prestige",

                        "§7des villes."
                )
        );

        //
        // 🔙 MENU
        //

        inv.setItem(

                22,

                item(

                        Material.ARROW,

                        "§c⬅ Retour Menu",

                        "§7Retourner au",

                        "§7menu principal."
                )
        );

        //
        // 🛰 STAFF
        //

        if (p.hasPermission(
                "moodtowngrade.staff")) {

            inv.setItem(

                    31,

                    item(

                            Material.COMPASS,

                            "§c🛰 Administration",

                            "§7Inspection et",

                            "§7gestion urbaine."
                    )
            );
        }

        //
        // 🚀 OPEN
        //

        p.openInventory(inv);
    }

    //
    // 📦 ITEM
    //

    private static ItemStack item(

            Material mat,

            String name,

            String... loreLines
    ) {

        ItemStack item =
                new ItemStack(mat);

        ItemMeta meta =
                item.getItemMeta();

        if (meta == null)
            return item;

        meta.setDisplayName(name);

        List<String> lore =
                new ArrayList<>();

        for (String line : loreLines) {

            lore.add(line);
        }

        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }
}