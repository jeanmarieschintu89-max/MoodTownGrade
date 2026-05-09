package fr.moodcraft.tgrade.gui;

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
                        "§8✦ Demandes Urbaines"
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
                "§6✦ Commission Urbaine"
        );

        headerMeta.setLore(List.of(

                "§8----- §6Demandes de projets §8-----",

                "§7Consulte les projets transmis",

                "§7par les villes à la Commission.",

                "",

                "§7Objectif du menu:",

                "§8• §fInspecter le projet sur place",

                "§8• §aValider la demande",

                "§8• §cRefuser la demande",

                "§8• §eSuivre les votes liés au projet",

                "",

                "§7Demandes enregistrées: §e"
                        + SubmissionStorage.getAll().size(),

                "",

                "§e▶ Ouvrir les dossiers"
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

            String participation;

            switch (sub.getStatus()) {

                case APPROVED -> {

                    material =
                            Material.EMERALD;

                    status =
                            "§a✔ Demande validée";

                    participation =
                            "§aInscrit au classement hebdomadaire";

                    action =
                            "§e▶ Ouvrir le suivi du projet";
                }

                case REJECTED -> {

                    material =
                            Material.REDSTONE_BLOCK;

                    status =
                            "§c✖ Demande refusée";

                    participation =
                            "§cNon éligible au classement";

                    action =
                            "§c▶ Dossier fermé";
                }

                default -> {

                    material =
                            Material.WRITABLE_BOOK;

                    status =
                            "§6⌛ En examen";

                    participation =
                            "§7En attente de validation staff";

                    action =
                            "§e▶ Inspecter la demande";
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

                    "§8----- §6Demande de projet §8-----",

                    "§7Ville: §b" + sub.getTown(),

                    "§7Projet: §f" + sub.getBuildName(),

                    "§7Statut: " + status,

                    "",

                    "§8----- §6Rôle du dossier §8-----",

                    "§7Cette demande concerne un",

                    "§7projet urbain précis.",

                    "§7Elle ne note pas toute la ville.",

                    "",

                    "§7Après validation:",

                    "§8• §fle projet participe au classement",

                    "§8• §fles votes citoyens sont pris en compte",

                    "§8• §fles maires peuvent l'évaluer",

                    "§8• §fune subvention peut être attribuée",

                    "",

                    "§8----- §6Votes liés au projet §8-----",

                    "§7Citoyens: §eavis public sur le projet",

                    "§7Maires: §6évaluation du projet",

                    "§7Staff: §cvalidation de la demande",

                    "",

                    "§7État: " + participation,

                    "",

                    "§7Position du projet:",

                    "§fX §e" + sub.getX()
                            + " §8| §fY §e" + sub.getY()
                            + " §8| §fZ §e" + sub.getZ(),

                    "",

                    "§7Dépôt: §f" + date,

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
                    "§c✖ Aucune demande"
            );

            meta.setLore(List.of(

                    "§8----- §6Commission Urbaine §8-----",

                    "§7Aucune demande de projet",

                    "§7n'est actuellement enregistrée.",

                    "",

                    "§7Les villes peuvent déposer",

                    "§7un projet pour participer",

                    "§7au classement hebdomadaire."
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

                "§8----- §6Commission Urbaine §8-----",

                "§7Retour au menu principal",

                "§7de l'administration urbaine."
        ));

        back.setItemMeta(backMeta);

        inv.setItem(49, back);

        //
        // 🚀 OPEN
        //

        p.openInventory(inv);
    }
}