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

                        "§8✦ Dossiers Urbains"
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
        // 🏛 HEADER
        //

        ItemStack header =
                new ItemStack(
                        Material.NETHER_STAR
                );

        ItemMeta headerMeta =
                header.getItemMeta();

        headerMeta.setDisplayName(
                "§6✦ Commission Nationale"
        );

        headerMeta.setLore(List.of(

                "§8━━━━━━━━━━━━━━━━",

                "§7Inspection des projets",

                "§7urbains de MoodCraft.",

                "",

                "§7Dossiers enregistrés: §e"
                        + SubmissionStorage.getAll().size(),

                "",

                "§e▶ Administration urbaine"
        ));

        header.setItemMeta(headerMeta);

        inv.setItem(4, header);

        //
        // 📂 PROJETS
        //

        List<TownSubmission> list =
                SubmissionStorage.getAll();

        //
        // 📦 SLOT
        //

        int slot = 10;

        for (TownSubmission sub : list) {

            if (slot >= 44)
                break;

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
            // 📊 STATUS
            //

            Material material;

            String status;

            String action;

            switch (sub.getStatus()) {

                case APPROVED -> {

                    material =
                            Material.EMERALD;

                    status =
                            "§a🟢 Projet validé";

                    action =
                            "§a▶ Projet approuvé";
                }

                case REJECTED -> {

                    material =
                            Material.REDSTONE_BLOCK;

                    status =
                            "§c🔴 Projet refusé";

                    action =
                            "§c▶ Dossier fermé";
                }

                default -> {

                    material =
                            Material.WRITABLE_BOOK;

                    status =
                            "§6🟡 En attente";

                    action =
                            "§e▶ Cliquer pour inspecter";
                }
            }

            //
            // 📘 ITEM
            //

            ItemStack item =
                    new ItemStack(material);

            ItemMeta meta =
                    item.getItemMeta();

            //
            // 📛 NAME
            //

            meta.setDisplayName(
                    "§f✦ §e" + sub.getBuildName()
            );

            //
            // 📜 LORE
            //

            meta.setLore(List.of(

                    "§8━━━━━━━━━━━━━━━━",

                    "§7Ville:",
                    "§b" + sub.getTown(),

                    "",

                    "§7Statut officiel:",
                    status,

                    "",

                    "§7Coordonnées:",
                    "§fX: §e" + sub.getX(),
                    "§fY: §e" + sub.getY(),
                    "§fZ: §e" + sub.getZ(),

                    "",

                    "§7Date dépôt:",
                    "§f" + date,

                    "",

                    action
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
                    "§c✖ Aucun dossier"
            );

            meta.setLore(List.of(

                    "§8━━━━━━━━━━━━━━━━",

                    "§7Aucun projet urbain",

                    "§7n'est actuellement",

                    "§7enregistré.",

                    "",

                    "§7La commission nationale",

                    "§7est en attente de",

                    "§7nouveaux dossiers."
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
                "§c⬅ Retour administratif"
        );

        backMeta.setLore(List.of(

                "§8━━━━━━━━━━━━━━━━",

                "§7Retourner au centre",

                "§7administratif national."
        ));

        back.setItemMeta(backMeta);

        inv.setItem(49, back);

        //
        // 🚀 OPEN
        //

        p.openInventory(inv);
    }
}