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

    public static void open(Player p,
                            String town) {

        //
        // 📊 GRADE
        //

        TownGrade grade =
                GradeManager.get(town);

        //
        // 🏛️ COMMISSION URBAINE
        //

        Inventory inv =
                Bukkit.createInventory(
                        null,
                        54,
                        "§8✦ §bMoodCraft §8• §7Inspection"
                );

        //
        // 📂 PROJETS
        //

        List<TownSubmission> list =
                SubmissionStorage.getTown(town);

        int slot = 10;

        for (TownSubmission sub : list) {

            //
            // ✅ VALIDÉS UNIQUEMENT
            //

            if (sub.getStatus()
                    != SubmissionStatus.APPROVED) {
                continue;
            }

            //
            // 🏗️ DOSSIER URBAIN
            //

            ItemStack item =
                    new ItemStack(Material.WRITABLE_BOOK);

            ItemMeta meta =
                    item.getItemMeta();

            meta.setDisplayName(
                    "§6" + sub.getBuildName()
            );

            List<String> lore =
                    new ArrayList<>();

            lore.add("");

            lore.add(
                    "§7Ville: §b"
                            + sub.getTown()
            );

            lore.add("");

            lore.add(
                    "§7Statut: §aPermis accordé"
            );

            lore.add("");

            lore.add(
                    "§7Coordonnées:"
            );

            lore.add(
                    " §fX: §e" + sub.getX()
            );

            lore.add(
                    " §fY: §e" + sub.getY()
            );

            lore.add(
                    " §fZ: §e" + sub.getZ()
            );

            lore.add("");

            lore.add(
                    "§b● §7Clique pour inspecter"
            );

            meta.setLore(lore);

            item.setItemMeta(meta);

            inv.setItem(slot, item);

            slot++;

            //
            // 🛑 LIMITE GUI
            //

            if (slot >= 44) {
                break;
            }
        }

        //
        // 📊 RAPPORT GLOBAL
        //

        ItemStack report =
                new ItemStack(Material.EMERALD_BLOCK);

        ItemMeta meta =
                report.getItemMeta();

        meta.setDisplayName(
                "§aRapport urbain"
        );

        meta.setLore(List.of(

                "",

                "§7Ville inspectée: §b"
                        + town,

                "",

                "§7Score actuel: §e"
                        + grade.getTotal()
                        + "§7/50",

                "",

                "§7Architecture: §e"
                        + grade.getArchitecture()
                        + "§7/10",

                "§7Activité: §e"
                        + grade.getActivite()
                        + "§7/8",

                "§7RP: §e"
                        + grade.getRp()
                        + "§7/6",

                "",

                "§b● §7Inspection active"
        ));

        report.setItemMeta(meta);

        inv.setItem(49, report);

        //
        // 🏛️ BOUTON NOTATION
        //

        ItemStack notation =
                new ItemStack(Material.BEACON);

        meta = notation.getItemMeta();

        meta.setDisplayName(
                "§6Commission d'évaluation"
        );

        meta.setLore(List.of(

                "",

                "§7Ouvre le système officiel",
                "§7de notation MoodCraft.",

                "",

                "§b● §7Clique pour noter"
        ));

        notation.setItemMeta(meta);

        inv.setItem(45, notation);

        //
        // ❌ FERMER
        //

        ItemStack close =
                new ItemStack(Material.BARRIER);

        meta = close.getItemMeta();

        meta.setDisplayName(
                "§cFermer l'inspection"
        );

        close.setItemMeta(meta);

        inv.setItem(53, close);

        //
        // 🌫️ FILLER
        //

        ItemStack glass =
                new ItemStack(
                        Material.GRAY_STAINED_GLASS_PANE
                );

        meta = glass.getItemMeta();

        meta.setDisplayName(" ");

        glass.setItemMeta(meta);

        for (int i = 0; i < 54; i++) {

            if (inv.getItem(i) == null) {

                inv.setItem(i, glass);
            }
        }

        //
        // ✨ OPEN
        //

        p.openInventory(inv);
    }
}