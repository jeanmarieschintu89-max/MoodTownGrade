package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.GradeManager;

import fr.moodcraft.tgrade.model.SubmissionStatus;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import fr.moodcraft.tgrade.towny.TownyHook;

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

                        54,

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

                27,35,

                36,44,

                45,46,47,48,50,51,52,53
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

                        "§7Gestion des villes",

                        "§7et prestige urbain.",

                        "",

                        "§7Dossiers actifs: §e"
                                + pending,

                        "§7Villes inspectées: §b"
                                + GradeManager.getAll().size(),

                        "",

                        "§e▶ Système national MoodCraft"
                )
        );

        //
        // 🏗 PROJETS
        //

        inv.setItem(

                20,

                item(

                        Material.WRITABLE_BOOK,

                        "§b🏗 Projets Urbains",

                        "§8━━━━━━━━━━━━━━━━",

                        "§7Consultez les projets",

                        "§7et constructions RP",

                        "§7de votre municipalité.",

                        "",

                        "§7Suivi des validations",

                        "§7et inspections urbaines.",

                        "",

                        "§b▶ Accéder aux projets"
                )
        );

        //
        // 👥 AVIS CITOYENS
        //

        inv.setItem(

                22,

                item(

                        Material.BOOK,

                        "§b✦ Avis Citoyens",

                        "§8━━━━━━━━━━━━━━━━",

                        "§7Participez aux votes",

                        "§7et influencez le",

                        "§7prestige des villes.",

                        "",

                        "§7Impact sur le",

                        "§7classement national.",

                        "",

                        "§b▶ Participer aux votes"
                )
        );

        //
        // 🏆 CLASSEMENT
        //

        inv.setItem(

                24,

                item(

                        Material.GOLD_INGOT,

                        "§6🏆 Palmarès National",

                        "§8━━━━━━━━━━━━━━━━",

                        "§7Consultez le classement",

                        "§7officiel des villes",

                        "§7de MoodCraft.",

                        "",

                        "§7Prestige national,",

                        "§7votes et financements.",

                        "",

                        "§6▶ Voir le palmarès"
                )
        );

        //
        // 👑 GOUVERNANCE
        //

        if (TownyHook.canManage(p)) {

            //
            // ➕ SOUMISSION
            //

            inv.setItem(

                    30,

                    item(

                            Material.NETHER_STAR,

                            "§a➕ Déposer un Projet",

                            "§8━━━━━━━━━━━━━━━━",

                            "§7Proposer une nouvelle",

                            "§7construction RP pour",

                            "§7votre municipalité.",

                            "",

                            "§a▶ Créer un projet"
                    )
            );

            //
            // 👑 CONSEIL DES MAIRES
            //

            inv.setItem(

                    32,

                    item(

                            Material.GOLD_BLOCK,

                            "§6✦ Conseil des Maires",

                            "§8━━━━━━━━━━━━━━━━",

                            "§7Votes et influence",

                            "§7des municipalités",

                            "§7de MoodCraft.",

                            "",

                            "§6▶ Accéder au conseil"
                    )
            );
        }

        //
        // 🛰 STAFF
        //

        if (p.hasPermission(
                "moodtowngrade.staff")) {

            inv.setItem(

                    34,

                    item(

                            Material.COMPASS,

                            "§c🛰 Centre National",

                            "§8━━━━━━━━━━━━━━━━",

                            "§7Gestion des inspections",

                            "§7et administration",

                            "§7urbaine nationale.",

                            "",

                            "§c▶ Accéder au centre"
                    )
            );
        }

        //
        // 🔙 RETOUR
        //

        inv.setItem(

                49,

                item(

                        Material.ARROW,

                        "§c⬅ Retour",

                        "§8━━━━━━━━━━━━━━━━",

                        "§7Retourner au menu",

                        "§7principal de MoodCraft."
                )
        );

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