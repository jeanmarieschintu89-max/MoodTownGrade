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

    public static final int ARCHI = 10;
    public static final int COHERENCE = 12;
    public static final int ACTIVITE = 14;
    public static final int BANQUE = 16;

    public static final int BUILD = 28;
    public static final int RP = 30;
    public static final int TAILLE = 32;
    public static final int VOTES = 34;

    public static final int SAVE = 49;

    public static void open(
            Player p,
            String town
    ) {

        TownGrade grade =
                GradeManager.get(town);

        RateSession session =
                RateSessionManager.create(
                        p.getUniqueId(),
                        town
                );

        Inventory inv =
                Bukkit.createInventory(
                        null,
                        54,
                        "§8✦ Notation Nationale"
                );

        fill(inv);

        set(
                inv,
                ARCHI,
                Material.QUARTZ_BLOCK,
                "§f🏗 Architecture",
                session.getArchitecture(),
                10
        );

        set(
                inv,
                COHERENCE,
                Material.PAINTING,
                "§d🎨 Cohérence",
                session.getCoherence(),
                6
        );

        set(
                inv,
                ACTIVITE,
                Material.BELL,
                "§e⚡ Activité",
                session.getActivite(),
                8
        );

        set(
                inv,
                BANQUE,
                Material.GOLD_INGOT,
                "§6💰 Banque",
                session.getBanque(),
                4
        );

        set(
                inv,
                BUILD,
                Material.BRICKS,
                "§c🏛 Build",
                session.getBuild(),
                8
        );

        set(
                inv,
                RP,
                Material.WRITABLE_BOOK,
                "§a🎭 RP",
                session.getRoleplay(),
                6
        );

        set(
                inv,
                TAILLE,
                Material.MAP,
                "§2🌍 Taille",
                session.getTaille(),
                3
        );

        set(
                inv,
                VOTES,
                Material.DIAMOND,
                "§b🗳 Votes",
                session.getVotes(),
                5
        );

        ItemStack save =
                new ItemStack(
                        Material.LIME_CONCRETE
                );

        ItemMeta meta =
                save.getItemMeta();

        meta.setDisplayName(
                "§a✅ Sauvegarder"
        );

        save.setItemMeta(meta);

        inv.setItem(SAVE, save);

        p.openInventory(inv);
    }

    private static void fill(
            Inventory inv
    ) {

        ItemStack glass =
                new ItemStack(
                        Material.BLACK_STAINED_GLASS_PANE
                );

        ItemMeta meta =
                glass.getItemMeta();

        meta.setDisplayName(" ");

        glass.setItemMeta(meta);

        for (int i = 0; i < 54; i++) {

            inv.setItem(i, glass);
        }
    }

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

                "§7Note:",
                "§e" + current + "§7/" + max,
                "",
                "§a▶ Cliquer"
        ));

        item.setItemMeta(meta);

        inv.setItem(slot, item);
    }
}