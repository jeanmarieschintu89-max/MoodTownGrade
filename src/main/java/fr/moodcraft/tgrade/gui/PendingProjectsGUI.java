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
    // 📋 OPEN
    //

    public static void open(Player p) {

        Inventory inv =
                Bukkit.createInventory(

                        null,

                        54,

                        "§8📋 Projets Urbains"
                );

        //
        // 🌌 FILL
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

        for (int i = 0; i < 54; i++) {

            inv.setItem(i, glass);
        }

        //
        // 📂 PROJETS
        //

        List<TownSubmission> list =
                SubmissionStorage.getAll()
                        .stream()
                        .filter(sub ->
                                sub.getStatus()
                                        == SubmissionStatus.PENDING)
                        .toList();

        //
        // 📦 SLOT START
        //

        int slot = 10;

        for (TownSubmission sub : list) {

            if (slot >= 44)
                break;

            ItemStack item =
                    new ItemStack(
                            Material.WRITABLE_BOOK
                    );

            ItemMeta meta =
                    item.getItemMeta();

            meta.setDisplayName(
                    "§e" + sub.getBuildName()
            );

            meta.setLore(List.of(

                    "§8━━━━━━━━━━━━━━━━",

                    "§7Ville: §b"
                            + sub.getTown(),

                    "",

                    "§7Statut:",
                    "§6EN ATTENTE",

                    "",

                    "§7Coordonnées:",
                    "§fX: §e" + sub.getX(),
                    "§fY: §e" + sub.getY(),
                    "§fZ: §e" + sub.getZ(),

                    "",

                    "§e▶ Inspecter le projet"
            ));

            item.setItemMeta(meta);

            inv.setItem(slot, item);

            slot++;

            //
            // ⬛ SAUTS
            //

            if (slot == 17)
                slot = 19;

            if (slot == 26)
                slot = 28;

            if (slot == 35)
                slot = 37;
        }

        //
        // 🔙 RETOUR
        //

        ItemStack back =
                new ItemStack(
                        Material.BARRIER
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