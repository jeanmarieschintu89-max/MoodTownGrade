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

        //
        // 🏛 HEADER
        //

        ItemStack header =
                new ItemStack(
                        Material.NETHER_STAR
                );

        ItemMeta headerMeta =
                header.getItemMeta();

        headerMeta.setDisplayName(
                "§6✦ Commission Urbaine"
        );

        headerMeta.setLore(List.of(

                "§8----- §6Notation Nationale §8-----",

                "§7Ville inspectée: §b" + town,

                "",

                "§7Chaque critère participe",

                "§7au §ePrestige National§7.",

                "",

                "§e▶ Cliquer sur un critère"
        ));

        header.setItemMeta(headerMeta);

        inv.setItem(4, header);

        set(
                inv,
                ARCHI,
                Material.QUARTZ_BLOCK,
                "§f✦ Architecture",
                "§7Qualité visuelle des bâtiments.",
                session.getArchitecture(),
                10
        );

        set(
                inv,
                COHERENCE,
                Material.PAINTING,
                "§d✦ Cohérence",
                "§7Harmonie du style urbain.",
                session.getCoherence(),
                6
        );

        set(
                inv,
                ACTIVITE,
                Material.BELL,
                "§e✦ Activité",
                "§7Vie locale et présence citoyenne.",
                session.getActivite(),
                8
        );

        set(
                inv,
                BANQUE,
                Material.GOLD_INGOT,
                "§6✦ Banque",
                "§7Solidité économique de la ville.",
                session.getBanque(),
                4
        );

        set(
                inv,
                BUILD,
                Material.BRICKS,
                "§c✦ Urbanisme",
                "§7Qualité générale des constructions.",
                session.getBuild(),
                8
        );

        set(
                inv,
                RP,
                Material.WRITABLE_BOOK,
                "§a✦ Roleplay",
                "§7Identité, histoire et immersion.",
                session.getRoleplay(),
                6
        );

        set(
                inv,
                TAILLE,
                Material.MAP,
                "§2✦ Développement",
                "§7Taille et organisation du territoire.",
                session.getTaille(),
                3
        );

        set(
                inv,
                VOTES,
                Material.DIAMOND,
                "§b✦ Votes Citoyens",
                "§7Reconnaissance par les habitants.",
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
                "§a✔ Valider la notation"
        );

        meta.setLore(List.of(

                "§8----- §6Registre National §8-----",

                "§7Enregistrer les notes",

                "§7dans le dossier officiel.",

                "",

                "§a▶ Sauvegarder"
        ));

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
            String description,
            int current,
            int max
    ) {

        ItemStack item =
                new ItemStack(mat);

        ItemMeta meta =
                item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(List.of(

                "§8----- §6Critère National §8-----",

                description,

                "",

                "§7Note actuelle: §e" + current + "§7/" + max,

                "§7Impact: §ePrestige urbain",

                "",

                "§e▶ Cliquer pour ajuster"
        ));

        item.setItemMeta(meta);

        inv.setItem(slot, item);
    }
}