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

                        "§8🛰 Centre Administratif"
                );

        //
        // 🌌 FILL
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

        for (int i = 0; i < 45; i++) {

            inv.setItem(i, glass);
        }

        //
        // 📋 PROJETS
        //

        set(

                inv,

                10,

                Material.WRITABLE_BOOK,

                "§e📋 Projets Urbains",

                "§7Voir les dossiers",
                "§7en attente."
        );

        //
        // ⭐ NOTATION
        //

        set(

                inv,

                12,

                Material.GOLD_INGOT,

                "§6⭐ Notation RP",

                "§7Noter les villes",
                "§7du serveur."
        );

        //
        // 📍 INSPECTION
        //

        set(

                inv,

                14,

                Material.COMPASS,

                "§a📍 Inspection Terrain",

                "§7Inspection rapide",
                "§7des constructions."
        );

        //
        // ✅ VALIDATION
        //

        set(

                inv,

                16,

                Material.LIME_CONCRETE,

                "§a✅ Validation",

                "§7Valider ou refuser",
                "§7les projets."
        );

        //
        // 💰 PAYOUT
        //

        set(

                inv,

                31,

                Material.EMERALD_BLOCK,

                "§2💰 Payout",

                "§7Distribuer les",
                "§7récompenses."
        );

        //
        // 🏆 CLASSEMENT
        //

        set(

                inv,

                33,

                Material.NETHER_STAR,

                "§b🏆 Classement",

                "§7Classement national",
                "§7des villes."
        );