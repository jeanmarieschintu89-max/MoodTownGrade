package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PendingProjectsGUI {

    //
    // 🛰 OPEN
    //

    public static void open(Player p) {

        Inventory inv =
                Bukkit.createInventory(

                        null,

                        54,

                        "§8📋 Projets Urbains"
                );

        //
        // 📚 LISTE
        //

        List<TownSubmission> list =
                SubmissionStorage.getAll()
                        .stream()
                        .filter(sub ->
                                sub.getStatus()
                                        == SubmissionStatus.PENDING)
                        .toList();

        //
        // ❌ VIDE
        //

        if (list.isEmpty()) {

            ItemStack item =
                    new ItemStack(
                            Material.BARRIER
                    );

            ItemMeta meta =
                    item.getItemMeta();

            meta.setDisplayName(
                    "§cAucun dossier"
            );

            item.setItemMeta(meta);

            inv.setItem(22, item);

            p.openInventory(inv);

            return;
        }

        //
        // 📦 ITEMS
        //

        int slot = 0;

        for (TownSubmission sub : list) {

            if (slot >= 45)
                break;

            ItemStack item =
                    new ItemStack(
                            Material.WRITABLE_BOOK
                    );

            ItemMeta meta =
                    item.getItemMeta();

            meta.setDisplayName(
                    "§e"
                            + sub.getBuildName()
            );

            meta.setLore(List.of(

                    "§8━━━━━━━━━━━━━━━━",

                    "§7Ville: §b"
                            + sub.getTown(),

                    "",

                    "§7ID: §f"
                            + sub.getId(),

                    "",

                    "§7📍 "
                            + sub.getX()
                            + " "
                            + sub.getY()
                            + " "
                            + sub.getZ(),

                    "",

                    "§a▶ Clic pour inspecter"
            ));

            item.setItemMeta(meta);

            inv.setItem(slot, item);

            slot++;
        }

        //
        // 🔙 RETOUR
        //

        ItemStack back =
                new ItemStack(
                        Material.ARROW
                );

        ItemMeta backMeta =
                back.getItemMeta();

        backMeta.setDisplayName(
                "§cRetour"
        );

        back.setItemMeta(backMeta);

        inv.setItem(49, back);

        //
        // 🚀 OPEN
        //

        p.openInventory(inv);
    }
}