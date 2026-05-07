package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.GradeManager;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownGrade;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ReviewGUI {

    //
    // 🏛 OPEN
    //

    public static void open(
            Player p,
            String town
    ) {

        //
        // 📊 GRADE
        //

        TownGrade grade =
                GradeManager.get(town);

        //
        // 🏛 INVENTORY
        //

        Inventory inv =
                Bukkit.createInventory(

                        null,

                        54,

                        "§8✦ Inspection Nationale"
                );

        //
        // 📂 PROJETS VALIDÉS
        //

        List<TownSubmission> list =
                SubmissionStorage.getTown(town);

        //
        // 🌌 GLASS
        //

        ItemStack glass =
                new ItemStack(
                        Material.BLACK_STAINED_GLASS_PANE
                );

        ItemMeta glassMeta =
                glass.getItemMeta();

        glassMeta.setDisplayName(" ");

        glass.setItemMeta(glassMeta);

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

        ItemStack header =
                new ItemStack(
                        Material.NETHER_STAR
                );

        ItemMeta headerMeta =
                header.getItemMeta();

        headerMeta.setDisplayName(
                "§6✦ Commission d'Inspection"
        );

        headerMeta.setLore(List.of(

                "§8━━━━━━━━━━━━━━━━",

                "§7Ville inspectée: §b"
                        + town,

                "",

                "§7Prestige actuel:",

                grade.getFormattedScore(),

                "",

                "§7Classement urbain:",

                grade.getRank(),

                "",

                "§e▶ Dossier gouvernemental actif"
        ));

        header.setItemMeta(headerMeta);

        inv.setItem(4, header);

        //
        // 📦 SLOT
        //

        int slot = 10;

        //
        // 📋 DOSSIERS
        //

        for (TownSubmission sub : list) {

            //
            // ✅ APPROVED ONLY
            //

            if (sub.getStatus()
                    != SubmissionStatus.APPROVED) {

                continue;
            }

            //
            // 🛑 LIMIT
            //

            if (slot >= 44) {
                break;
            }

            //
            // 📘 ITEM
            //

            ItemStack item =
                    new ItemStack(
                            Material.WRITABLE_BOOK
                    );

            ItemMeta meta =
                    item.getItemMeta();

            meta.setDisplayName(

                    "§e✦ "
                            + sub.getBuildName()
            );

            List<String> lore =
                    new ArrayList<>();

            lore.add(
                    "§8━━━━━━━━━━━━━━━━"
            );

            lore.add("");

            lore.add(
                    "§7Ville:"
            );

            lore.add(
                    "§b" + sub.getTown()
            );

            lore.add("");

            lore.add(
                    "§7Statut:"
            );

            lore.add(
                    "§aPermis de construire accordé"
            );

            lore.add("");

            lore.add(
                    "§7Coordonnées:"
            );

            lore.add(
                    "§fX: §e" + sub.getX()
            );

            lore.add(
                    "§fY: §e" + sub.getY()
            );

            lore.add(
                    "§fZ: §e" + sub.getZ()
            );

            lore.add("");

            lore.add(
                    "§b▶ Inspection terrain"
            );

            meta.setLore(lore);

            item.setItemMeta(meta);

            inv.setItem(slot, item);

            //
            // ➡ NEXT SLOT
            //

            slot++;

            if (slot == 17)
                slot = 19;

            if (slot == 26)
                slot = 28;

            if (slot == 35)
                slot = 37;
        }

        //
        // 📊 RAPPORT GLOBAL
        //

        ItemStack report =
                new ItemStack(
                        Material.EMERALD_BLOCK
                );

        ItemMeta reportMeta =
                report.getItemMeta();

        reportMeta.setDisplayName(
                "§a✦ Rapport National"
        );

        reportMeta.setLore(List.of(

                "§8━━━━━━━━━━━━━━━━",

                "§7Évaluation urbaine",

                "§7officielle MoodCraft.",

                "",

                "§7Architecture: §e"
                        + grade.getArchitecture()
                        + "§7/10",

                "§7Cohérence: §e"
                        + grade.getStyle()
                        + "§7/6",

                "§7Activité: §e"
                        + grade.getActivite()
                        + "§7/8",

                "§7Banque: §e"
                        + grade.getBanque()
                        + "§7/4",

                "§7Build: §e"
                        + grade.getRemarquable()
                        + "§7/8",

                "§7RolePlay: §e"
                        + grade.getRp()
                        + "§7/6",

                "",

                "§6Prestige total:",

                grade.getFormattedScore()
        ));

        report.setItemMeta(reportMeta);

        inv.setItem(49, report);

        //
        // ⭐ NOTATION
        //

        ItemStack notation =
                new ItemStack(
                        Material.BEACON
                );

        ItemMeta notationMeta =
                notation.getItemMeta();

        notationMeta.setDisplayName(
                "§6⭐ Commission d'Évaluation"
        );

        notationMeta.setLore(List.of(

                "§8━━━━━━━━━━━━━━━━",

                "§7Accéder au système",

                "§7officiel de notation.",

                "",

                "§7Modification possible",

                "§7jusqu'au reset hebdomadaire.",

                "",

                "§6▶ Ouvrir la notation"
        ));

        notation.setItemMeta(notationMeta);

        inv.setItem(45, notation);

        //
        // ❌ FERMER
        //

        ItemStack close =
                new ItemStack(
                        Material.BARRIER
                );

        ItemMeta closeMeta =
                close.getItemMeta();

        closeMeta.setDisplayName(
                "§c⬅ Fermer l'Inspection"
        );

        closeMeta.setLore(List.of(

                "§8━━━━━━━━━━━━━━━━",

                "§7Retour au centre",

                "§7administratif."
        ));

        close.setItemMeta(closeMeta);

        inv.setItem(53, close);

        //
        // 🚀 OPEN
        //

        p.openInventory(inv);
    }
}