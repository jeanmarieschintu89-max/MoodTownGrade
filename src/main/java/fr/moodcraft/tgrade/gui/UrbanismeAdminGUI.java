package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.GradeManager;

import fr.moodcraft.tgrade.model.SubmissionStatus;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

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

                        "§8✦ Centre National"
                );

        long pending =
                SubmissionStorage.getAll()
                        .stream()
                        .filter(sub ->
                                sub.getStatus()
                                        == SubmissionStatus.PENDING)
                        .count();

        long approved =
                SubmissionStorage.getAll()
                        .stream()
                        .filter(sub ->
                                sub.getStatus()
                                        == SubmissionStatus.APPROVED)
                        .count();

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

        int[] borders = {

                0,1,2,3,4,5,6,7,8,

                9,17,

                18,26,

                27,35,

                36,37,38,39,41,42,43,44
        };

        for (int slot : borders) {

            inv.setItem(slot, glass);
        }

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
                "§6✦ Centre National d'Urbanisme"
        );

        headerMeta.setLore(List.of(

                "§8----- §6Administration §8-----",

                "§7Supervision officielle",

                "§7des villes de MoodCraft.",

                "",

                "§7Dossiers en attente: §e"
                        + pending,

                "§7Projets validés: §a"
                        + approved,

                "§7Villes inspectées: §b"
                        + GradeManager.getAll().size(),

                "",

                "§e▶ Registres nationaux"
        ));

        header.setItemMeta(headerMeta);

        inv.setItem(4, header);

        //
        // 📋 PROJETS
        //

        set(

                inv,

                13,

                Material.WRITABLE_BOOK,

                "§e✦ Inspection des Projets",

                "§8----- §6Dossiers Urbains §8-----",

                "§7Consulter les dossiers",

                "§7transmis à la Commission.",

                "",

                "§7Dossiers actifs: §e"
                        + pending,

                "",

                "§e▶ Accéder aux inspections"
        );

        //
        // ⭐ ÉVALUATIONS
        //

        set(

                inv,

                22,

                Material.ENCHANTED_BOOK,

                "§b✦ Évaluations Nationales",

                "§8----- §6Notation §8-----",

                "§7Réviser les notes",

                "§7et rapports urbains.",

                "",

                "§8• §fRéévaluer une ville",

                "§8• §fConsulter les scores",

                "§8• §fAnalyser le classement",

                "",

                "§b▶ Accéder aux évaluations"
        );

        //
        // 💰 PAYOUT
        //

        set(

                inv,

                31,

                Material.EMERALD_BLOCK,

                "§a✦ Financements Nationaux",

                "§8----- §6Bourses Urbaines §8-----",

                "§7Distribuer les aides",

                "§7municipales officielles.",

                "",

                "§8• §fVilles validées",

                "§8• §fSubventions progressives",

                "§8• §fVersement banque municipale",

                "",

                "§a▶ Distribuer les financements"
        );

        //
        // 🏆 CLASSEMENT
        //

        set(

                inv,

                33,

                Material.NETHER_STAR,

                "§6✦ Palmarès National",

                "§8----- §6Classement §8-----",

                "§7Consulter le prestige",

                "§7officiel des villes.",

                "",

                "§6▶ Voir le palmarès"
        );

        //
        // 🔙 RETOUR
        //

        set(

                inv,

                40,

                Material.ARROW,

                "§c⬅ Retour",

                "§8----- §6Commission Urbaine §8-----",

                "§7Retourner au menu",

                "§7urbain principal."
        );

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