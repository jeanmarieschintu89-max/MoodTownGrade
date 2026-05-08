package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.MayorVoteManager;
import fr.moodcraft.tgrade.manager.NationalScoreCalculator;

import fr.moodcraft.tgrade.model.MayorVote;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MayorVoteGUI {

    //
    // 📍 SLOTS
    //

    public static final int
            BEAUTE = 20;

    public static final int
            AMBIANCE = 21;

    public static final int
            ACTIVITE = 22;

    public static final int
            ORIGINALITE = 23;

    public static final int
            POPULARITE = 24;

    public static final int
            SAVE = 40;

    //
    // 🚀 OPEN
    //

    public static void open(

            Player p,

            String town
    ) {

        Inventory inv =
                Bukkit.createInventory(

                        null,

                        45,

                        "§8✦ Conseil des Maires"
                );

        //
        // 📦 VOTE
        //

        MayorVote vote =
                MayorVoteManager.getVote(

                        p.getUniqueId(),

                        town
                );

        //
        // 🆕 CREATE
        //

        if (vote == null) {

            vote =
                    new MayorVote(

                            p.getUniqueId(),

                            town
                    );
        }

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

                36,37,38,39,41,42,43,44
        };

        for (int slot : borders) {

            inv.setItem(
                    slot,
                    glass
            );
        }

        //
        // 👑 HEADER
        //

        set(

                inv,

                4,

                Material.NETHER_STAR,

                "§6✦ Conseil des Maires",

                "§8----- §6Gouvernance §8-----",

                "§7Ville: §b" + town,

                "",

                "§7Prestige national: §e"
                        + String.format(
                        "%.1f",
                        NationalScoreCalculator
                                .getFinalScore(town)
                )
                        + "§7/50",

                "§7Votes du conseil: §6"
                        + NationalScoreCalculator
                        .getMayorCount(town),

                "",

                "§6▶ Influence municipale"
        );

        //
        // 🏗 BEAUTÉ
        //

        setVote(

                inv,

                BEAUTE,

                Material.QUARTZ_BLOCK,

                "§f✦ Beauté",

                "§7Qualité visuelle de la ville.",

                vote.getBeaute()
        );

        //
        // 🌆 AMBIANCE
        //

        setVote(

                inv,

                AMBIANCE,

                Material.LANTERN,

                "§e✦ Ambiance",

                "§7Atmosphère et identité locale.",

                vote.getAmbiance()
        );

        //
        // ⚡ ACTIVITÉ
        //

        setVote(

                inv,

                ACTIVITE,

                Material.BELL,

                "§6✦ Activité",

                "§7Dynamisme municipal observé.",

                vote.getActivite()
        );

        //
        // 🧭 ORIGINALITÉ
        //

        setVote(

                inv,

                ORIGINALITE,

                Material.COMPASS,

                "§b✦ Originalité",

                "§7Singularité du développement urbain.",

                vote.getOriginalite()
        );

        //
        // ❤️ POPULARITÉ
        //

        setVote(

                inv,

                POPULARITE,

                Material.REDSTONE,

                "§c✦ Popularité",

                "§7Reconnaissance politique de la ville.",

                vote.getPopularite()
        );

        //
        // 💾 SAVE
        //

        set(

                inv,

                SAVE,

                Material.EMERALD_BLOCK,

                "§a✔ Valider le vote",

                "§8----- §6Registre National §8-----",

                "§7Enregistrer l'avis",

                "§7du conseil municipal.",

                "",

                "§a▶ Sauvegarder"
        );

        //
        // 🚀 OPEN
        //

        p.openInventory(inv);
    }

    //
    // ⭐ ITEM VOTE
    //

    private static void setVote(

            Inventory inv,

            int slot,

            Material mat,

            String name,

            String description,

            int value
    ) {

        set(

                inv,

                slot,

                mat,

                name,

                "§8----- §6Critère du Conseil §8-----",

                description,

                "",

                "§7Note actuelle: §e"
                        + value
                        + "§7/5",

                "",

                "§e▶ Cliquer pour ajuster"
        );
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