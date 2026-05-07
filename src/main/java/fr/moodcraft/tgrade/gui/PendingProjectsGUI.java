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

import java.text.SimpleDateFormat;

import java.util.Date;
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

                36,44,

                45,46,47,48,50,51,52,53
        };

        for (int slot : borders) {

            inv.setItem(slot, glass);
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
        // 📦 SLOT
        //

        int slot = 10;

        for (TownSubmission sub : list) {

            if (slot >= 44)
                break;

            //
            // 📘 ITEM
            //

            ItemStack item =
                    new ItemStack(
                            Material.WRITABLE_BOOK
                    );

            ItemMeta meta =
                    item.getItemMeta();

            //
            // 📛 NAME
            //

            meta.setDisplayName(
                    "§e" + sub.getBuildName()
            );

            //
            // 📅 DATE
            //

            String date =
                    new SimpleDateFormat(
                            "dd/MM/yyyy"
                    ).format(
                            new Date(
                                    sub.getTimestamp()
                            )
                    );

            //
            // 📜 LORE
            //

            meta.setLore(List.of(

                    "§8━━━━━━━━━━━━━━━━",

                    "§7Ville:",
                    "§b" + sub.getTown(),

                    "",

                    "§7Statut:",
                    "§6En attente",

                    "",

                    "§7Coordonnées:",
                    "§fX: §e" + sub.getX(),
                    "§fY: §e" + sub.getY(),
                    "§fZ: §e" + sub.getZ(),

                    "",

                    "§7Date:",
                    "§f" + date,

                    "",

                    "§a▶ Cliquer pour inspecter"
            ));

            item.setItemMeta(meta);

            inv.setItem(slot, item);

            //
            // ➡ SLOT NEXT
            //

            slot++;

            //
            // ⬛ SKIPS
            //

            if (slot == 17)
                slot = 19;

            if (slot == 26)
                slot = 28;

            if (slot == 35)
                slot = 37;
        }

        //
        // ❌ EMPTY
        //

        if (list.isEmpty()) {

            ItemStack empty =
                    new ItemStack(
                            Material.BARRIER
                    );

            ItemMeta meta =
                    empty.getItemMeta();

            meta.setDisplayName(
                    "§cAucun projet"
            );

            meta.setLore(List.of(

                    "§7Aucun dossier",

                    "§7en attente."
            ));

            empty.setItemMeta(meta);

            inv.setItem(22, empty);
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
                "§c⬅ Retour"
        );

        backMeta.setLore(List.of(

                "§7Retour au centre",

                "§7administratif."
        ));

        back.setItemMeta(backMeta);

        inv.setItem(49, back);

        //
        // 🚀 OPEN
        //

        p.openInventory(inv);
    }
}