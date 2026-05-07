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

        //
        // 📊 STATS
        //

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

                "§8━━━━━━━━━━━━━━━━",

                "§7Administration officielle",

                "§7des villes de MoodCraft.",

                "",

                "§7Dossiers en attente: §6"
                        + pending,

                "§7Projets validés: §a"
                        + approved,

                "§7Villes inspectées: §b"
                        + GradeManager.getAll().size(),

                "",

                "§e▶ Réseau administratif national"
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

                "§e📋 Inspection des Projets",

                "§8━━━━━━━━━━━━━━━━",

                "§7Consulter les dossiers",

                "§7urbains transmis à",

                "§7la commission nationale.",

                "",

                "§7Dossiers actifs: §e"
                        + pending,

                "",

                "§e▶ Ouvrir les inspections"
        );

        //
        // 💰 PAYOUT
        //

        set(

                inv,

                31,

                Material.EMERALD_BLOCK,

                "§2💰 Financements Nationaux",

                "§8━━━━━━━━━━━━━━━━",

                "§7Distribuer les aides",

                "§7municipales officielles.",

                "",

                "§7Les villes sous 25/50",

                "§7ne reçoivent aucune aide.",

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

                "§b🏆 Palmarès National",

                "§8━━━━━━━━━━━━━━━━",

                "§7Classement officiel",

                "§7des villes inspectées.",

                "",

                "§7Consultation du prestige",

                "§7urbain national.",

                "",

                "§b▶ Voir le classement"
        );

        //
        // 🔙 RETOUR
        //

        set(

                inv,

                40,

                Material.ARROW,

                "§c⬅ Retour Administratif",

                "§8━━━━━━━━━━━━━━━━",

                "§7Retourner au centre",

                "§7urbain principal."
        );

        //
        // 🚀 OPEN
        //

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