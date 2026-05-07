package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.GradeManager;
import fr.moodcraft.tgrade.manager.RateSessionManager;

import fr.moodcraft.tgrade.model.RateSession;
import fr.moodcraft.tgrade.model.TownGrade;

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
        // 📊 EXISTING GRADE
        //

        TownGrade grade =
                GradeManager.get(town);

        //
        // 🧠 SESSION
        //

        RateSession session =
                RateSessionManager.create(

                        p.getUniqueId(),
                        town
                );

        //
        // 🔄 LOAD CURRENT SCORES
        //

        session.setArchitecture(
                grade.getArchitecture()
        );

        session.setCoherence(
                grade.getStyle()
        );

        session.setActivite(
                grade.getActivite()
        );

        session.setBanque(
                grade.getBanque()
        );

        session.setBuild(
                grade.getRemarquable()
        );

        session.setRoleplay(
                grade.getRp()
        );

        session.setTaille(
                grade.getTaille()
        );

        session.setVotes(
                grade.getVotes()
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

            inv.setItem(slot, glass);
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

                "§7Prestige actuel:",

                "§6"
                        + session.getTotal()
                        + "§7/50",

                "",

                grade.getRank()
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

                12,

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

                14,

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

                16,

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

                28,

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

                30,

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

                32,

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

                34,

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

                "§7Mettre à jour",

                "§7l'évaluation urbaine.",

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

                "§e▶ Augmenter la note",

                "§7Retour à §c0",

                "§7après le maximum"
        ));

        item.setItemMeta(meta);

        inv.setItem(slot, item);
    }
}