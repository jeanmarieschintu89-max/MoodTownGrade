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

                        "§8✦ Commission Urbaine"
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

        //
        // 🌌 GLASS
        //

        ItemStack glass =
                item(

                        Material.BLACK_STAINED_GLASS_PANE,

                        " "
                );

        //
        // 🧱 BORDERS
        //

        int[] borders = {

                0,1,2,3,4,5,6,7,8,

                9,17,

                18,26,

                27,28,29,30,31,32,33,34,35
        };

        for (int slot : borders) {

            inv.setItem(slot, glass);
        }

        //
        // 🏛 HEADER
        //

        inv.setItem(

                4,

                item(

                        Material.NETHER_STAR,

                        "§6✦ Commission Urbaine Nationale",

                        "§8━━━━━━━━━━━━━━━━",

                        "§7Administration officielle",

                        "§7des villes de MoodCraft.",

                        "",

                        "§7Dossiers actifs: §e"
                                + pending,

                        "§7Villes inspectées: §b"
                                + GradeManager.getAll().size(),

                        "",

                        "§e▶ Système urbain national"
                )
        );

        //
        // 📜 PROJETS
        //

        inv.setItem(

                11,

                item(

                        Material.WRITABLE_BOOK,

                        "§b📜 Dossiers Urbains",

                        "§8━━━━━━━━━━━━━━━━",

                        "§7Consulter les projets",

                        "§7de votre municipalité.",

                        "",

                        "§7Accès aux dossiers",

                        "§7validés et inspections.",

                        "",

                        "§b▶ Ouvrir les dossiers"
                )
        );

        //
        // ➕ SOUMISSION
        //

        inv.setItem(

                13,

                item(

                        Material.NETHER_STAR,

                        "§a➕ Déposer un Projet",

                        "§8━━━━━━━━━━━━━━━━",

                        "§7Déclarer une nouvelle",

                        "§7construction RP officielle.",

                        "",

                        "§7Place-toi devant",

                        "§7la construction concernée.",

                        "",

                        "§a▶ Créer un dossier urbain"
                )
        );

        //
        // 🏆 CLASSEMENT
        //

        inv.setItem(

                15,

                item(

                        Material.GOLD_INGOT,

                        "§6🏆 Palmarès National",

                        "§8━━━━━━━━━━━━━━━━",

                        "§7Consulter le classement",

                        "§7des villes inspectées.",

                        "",

                        "§7Prestige national",

                        "§7et financements urbains.",

                        "",

                        "§6▶ Voir le classement"
                )
        );

        //
        // 🔙 MENU
        //

        inv.setItem(

                22,

                item(

                        Material.ARROW,

                        "§c⬅ Retour Principal",

                        "§8━━━━━━━━━━━━━━━━",

                        "§7Retourner au menu",

                        "§7principal de MoodCraft."
                )
        );

        //
        // 🛰 STAFF ONLY
        //

        if (p.hasPermission(
                "moodtowngrade.staff")) {

            inv.setItem(

                    31,

                    item(

                            Material.COMPASS,

                            "§c🛰 Centre National",

                            "§8━━━━━━━━━━━━━━━━",

                            "§7Gestion gouvernementale",

                            "§7des inspections urbaines.",

                            "",

                            "§7Administration des",

                            "§7dossiers nationaux.",

                            "",

                            "§c▶ Accéder au centre"
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