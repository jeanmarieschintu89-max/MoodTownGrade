package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.RateSessionManager;

import fr.moodcraft.tgrade.model.RateSession;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class RateGUI {

    //
    // ⭐ OPEN
    //

    public static void open(
            Player p,
            String town
    ) {

        //
        // 🧠 SESSION
        //

        RateSession session =
                RateSessionManager.create(

                        p.getUniqueId(),
                        town
                );

        //
        // 📦 INVENTORY
        //

        Inventory inv =
                Bukkit.createInventory(

                        null,

                        54,

                        "§8⭐ Notation Urbaine"
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

        for (int i = 0; i < 54; i++) {

            inv.setItem(i, glass);
        }

        //
        // 🏛 INFO VILLE
        //

        ItemStack info =
                new ItemStack(
                        Material.NETHER_STAR
                );

        ItemMeta infoMeta =
                info.getItemMeta();

        infoMeta.setDisplayName(
                "§b🏛 " + town
        );

        infoMeta.setLore(List.of(

                "§8━━━━━━━━━━━━━━━━",

                "§7Commission nationale",
                "§7d'évaluation urbaine.",

                "",

                "§7Total actuel:",
                "§6"
                        + session.getTotal()
                        + "§7/50"
        ));

        info.setItemMeta(infoMeta);

        inv.setItem(4, info);

        //
        // 🏗 ARCHITECTURE
        //

        set(

                inv,

                10,

                Material.QUARTZ_BLOCK,

                "§f🏗 Architecture",

                session.getArchitecture(),

                10
        );

        //
        // 🎨 COHERENCE
        //

        set(

                inv,

                11,

                Material.PAINTING,

                "§d🎨 Cohérence",

                session.getCoherence(),

                6
        );

        //
        // ⚡ ACTIVITE
        //

        set(

                inv,

                12,

                Material.BELL,

                "§e⚡ Activité",

                session.getActivite(),

                8
        );

        //
        // 💰 BANQUE
        //

        set(

                inv,

                13,

                Material.GOLD_INGOT,

                "§6💰 Banque",

                session.getBanque(),

                4
        );

        //
        // 🏛 BUILD
        //

        set(

                inv,

                14,

                Material.BRICKS,

                "§c🏛 Build remarquable",

                session.getBuild(),

                8
        );

        //
        // 🎭 RP
        //

        set(

                inv,

                15,

                Material.WRITABLE_BOOK,

                "§a🎭 RolePlay",

                session.getRoleplay(),

                6
        );

        //
        // 🌍 TAILLE
        //

        set(

                inv,

                16,

                Material.MAP,

                "§2🌍 Taille",

                session.getTaille(),

                3
        );

        //
        // 🗳 VOTES
        //

        set(

                inv,

                19,

                Material.DIAMOND,

                "§b🗳 Votes",

                session.getVotes(),

                5
        );

        //
        // ✅ SAVE
        //

        ItemStack save =
                new ItemStack(
                        Material.LIME_CONCRETE
                );

        ItemMeta saveMeta =
                save.getItemMeta();

        saveMeta.setDisplayName(
                "§a✅ Valider la notation"
        );

        saveMeta.setLore(List.of(

                "§7Enregistrer",

                "§7la note finale.",

                "",

                "§e▶ Sauvegarder"
        ));

        save.setItemMeta(saveMeta);

        inv.setItem(49, save);

        //
        // 🚀 OPEN
        //

        p.openInventory(inv);
    }

    //
    // ⭐ ITEM
    //

    private static void set(

            Inventory inv,

            int slot,

            Material mat,

            String name,

            int current,

            int max
    ) {

        ItemStack item =
                new ItemStack(mat);

        ItemMeta meta =
                item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(List.of(

                "§8━━━━━━━━━━━━━━━━",

                "§7Note actuelle:",

                "§6"
                        + current
                        + "§7/"
                        + max,

                "",

                "§e▶ Clique pour augmenter",

                "§7Retour à §c0 §7après le maximum"
        ));

        item.setItemMeta(meta);

        inv.setItem(slot, item);
    }
}