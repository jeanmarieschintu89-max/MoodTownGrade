package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.CitizenVoteManager;
import fr.moodcraft.tgrade.manager.NationalScoreCalculator;

import fr.moodcraft.tgrade.model.CitizenVote;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CitizenVoteGUI {

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

                        "§8✦ Vote Citoyen"
                );

        //
        // 📦 VOTE
        //

        CitizenVote vote =
                CitizenVoteManager.getVote(

                        p.getUniqueId(),

                        town
                );

        //
        // 🆕 CREATE
        //

        if (vote == null) {

            vote =
                    new CitizenVote(

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
        // 🏙 HEADER
        //

        set(

                inv,

                4,

                Material.NETHER_STAR,

                "§b✦ Vote Citoyen",

                "§8━━━━━━━━━━━━━━━━",

                "§7Ville: §b" + town,

                "",

                "§7Score national:",

                " §e"
                        + NationalScoreCalculator
                        .getFinalScore(town)

                        + "§7/50",

                "",

                "§7Votes citoyens: §b"
                        + NationalScoreCalculator
                        .getCitizenCount(town)
        );

        //
        // 🏗 BEAUTÉ
        //

        setVote(

                inv,

                BEAUTE,

                Material.QUARTZ_BLOCK,

                "§f🏗 Beauté",

                vote.getBeaute()
        );

        //
        // 🌆 AMBIANCE
        //

        setVote(

                inv,

                AMBIANCE,

                Material.LANTERN,

                "§e🌆 Ambiance",

                vote.getAmbiance()
        );

        //
        // ⚡ ACTIVITÉ
        //

        setVote(

                inv,

                ACTIVITE,

                Material.BELL,

                "§6⚡ Activité",

                vote.getActivite()
        );

        //
        // 🧭 ORIGINALITÉ
        //

        setVote(

                inv,

                ORIGINALITE,

                Material.COMPASS,

                "§b🧭 Originalité",

                vote.getOriginalite()
        );

        //
        // ❤️ POPULARITÉ
        //

        setVote(

                inv,

                POPULARITE,

                Material.REDSTONE,

                "§c❤️ Popularité",

                vote.getPopularite()
        );

        //
        // 💾 SAVE
        //

        set(

                inv,

                SAVE,

                Material.EMERALD_BLOCK,

                "§a✦ Sauvegarder le vote",

                "§8━━━━━━━━━━━━━━━━",

                "§7Enregistrer votre",

                "§7avis citoyen officiel.",

                "",

                "§a▶ Valider le vote"
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

            int value
    ) {

        set(

                inv,

                slot,

                mat,

                name,

                "§8━━━━━━━━━━━━━━━━",

                "§7Note actuelle: §e"
                        + value
                        + "§7/3",

                "",

                "§e▶ Cliquer pour modifier"
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