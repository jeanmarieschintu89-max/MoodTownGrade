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
        // 🌌 FOND
        //

        ItemStack glass =
                item(

                        Material.BLACK_STAINED_GLASS_PANE,

                        " "
                );

        for (int i = 0; i < 36; i++) {

            inv.setItem(i, glass);
        }

        //
        // 📜 PROJETS
        //

        inv.setItem(

                11,

                item(

                        Material.WRITABLE_BOOK,

                        "§b📜 Projets Urbains",

                        "§8━━━━━━━━━━━━━━━━",

                        "§7Consulter les projets",

                        "§7de votre ville.",

                        "",

                        "§e▶ Ouvrir"
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

                        "§8━━━━━━━━━━━━━━━━",

                        "§7Déclarer une nouvelle",

                        "§7construction RP.",

                        "",

                        "§e▶ Soumettre"
                )
        );

        //
        // 🏆 SCORE
        //

        inv.setItem(

                15,

                item(

                        Material.GOLD_INGOT,

                        "§6🏆 Prestige Ville",

                        "§8━━━━━━━━━━━━━━━━",

                        "§7Consulter le score",

                        "§7hebdomadaire RP.",

                        "",

                        "§e▶ Voir"
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

                            "§8━━━━━━━━━━━━━━━━",

                            "§7Inspection urbaine",

                            "§7et notation.",

                            "",

                            "§e▶ Administration"
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