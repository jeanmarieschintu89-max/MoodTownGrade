package fr.moodcraft.tgrade.gui;

import fr.moodcraft.flag.api.MoodTownFlagAPI;

import fr.moodcraft.tgrade.model.TownSubmission;
import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PendingProjectsGUI {

    public static void open(Player p) {

        Inventory inv =
                Bukkit.createInventory(
                        null,
                        54,
                        "§8✦ Demandes Urbaines"
                );

        ItemStack glass =
                new ItemStack(
                        Material.BLACK_STAINED_GLASS_PANE
                );

        ItemMeta glassMeta =
                glass.getItemMeta();

        if (glassMeta != null) {

            glassMeta.setDisplayName(" ");
            glass.setItemMeta(glassMeta);
        }

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

        ItemStack header =
                new ItemStack(
                        Material.NETHER_STAR
                );

        ItemMeta headerMeta =
                header.getItemMeta();

        if (headerMeta != null) {

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
        }

        inv.setItem(4, header);

        List<TownSubmission> list =
                SubmissionStorage.getAll();

        int slot = 10;

        for (TownSubmission sub : list) {

            if (slot >= 44)
                break;

            String date =
                    new SimpleDateFormat(
                            "dd/MM/yyyy"
                    ).format(
                            new Date(
                                    sub.getTimestamp()
                            )
                    );

            String status;
            String action;
            String participation;

            switch (sub.getStatus()) {

                case APPROVED -> {
                    status = "§a✔ Demande validée";
                    participation = "§aInscrit au classement hebdomadaire";
                    action = "§e▶ Ouvrir le suivi du projet";
                }

                case REJECTED -> {
                    status = "§c✖ Demande refusée";
                    participation = "§cNon éligible au classement";
                    action = "§c▶ Dossier fermé";
                }

                default -> {
                    status = "§6⌛ En examen";
                    participation = "§7En attente de validation staff";
                    action = "§e▶ Inspecter la demande";
                }
            }

            ItemStack item =
                    MoodTownFlagAPI.getTownShieldItem(
                            sub.getTown()
                    );

            boolean hasFlag =
                    item != null;

            if (item == null) {

                item =
                        new ItemStack(
                                Material.SHIELD
                        );
            }

            ItemMeta meta =
                    item.getItemMeta();

            if (meta != null) {

                meta.setDisplayName(
                        "§f✦ §e" + sub.getBuildName()
                );

                List<String> lore =
                        new ArrayList<>();

                lore.add("§8----- §6Demande de projet §8-----");
                lore.add("§7Ville: §b" + sub.getTown());
                lore.add("§7Projet: §f" + sub.getBuildName());
                lore.add("§7Statut: " + status);
                lore.add("");

                if (hasFlag) {

                    lore.add("§a✔ Blason officiel enregistré");

                } else {

                    lore.add("§7Blason : §fNon défini");
                }

                lore.add("");
                lore.add("§8----- §6Rôle du dossier §8-----");
                lore.add("§7Cette demande concerne un");
                lore.add("§7projet urbain précis.");
                lore.add("§7Elle ne note pas toute la ville.");
                lore.add("");
                lore.add("§7Après validation:");
                lore.add("§8• §fle projet participe au classement");
                lore.add("§8• §fles votes citoyens sont pris en compte");
                lore.add("§8• §fles maires peuvent l'évaluer");
                lore.add("§8• §fune subvention peut être attribuée");
                lore.add("");
                lore.add("§8----- §6Votes liés au projet §8-----");
                lore.add("§7Citoyens: §eavis public sur le projet");
                lore.add("§7Maires: §6évaluation du projet");
                lore.add("§7Staff: §cvalidation de la demande");
                lore.add("");
                lore.add("§7État: " + participation);
                lore.add("");
                lore.add("§7Position du projet:");
                lore.add(
                        "§fX §e" + sub.getX()
                                + " §8| §fY §e" + sub.getY()
                                + " §8| §fZ §e" + sub.getZ()
                );
                lore.add("");
                lore.add("§7Dépôt: §f" + date);
                lore.add("");
                lore.add(action);

                meta.setLore(lore);

                meta.addItemFlags(
                        ItemFlag.HIDE_ADDITIONAL_TOOLTIP,
                        ItemFlag.HIDE_ATTRIBUTES,
                        ItemFlag.HIDE_ENCHANTS
                );

                item.setItemMeta(meta);
            }

            inv.setItem(slot, item);

            slot++;

            if (slot == 17)
                slot = 19;

            if (slot == 26)
                slot = 28;

            if (slot == 35)
                slot = 37;
        }

        if (list.isEmpty()) {

            ItemStack empty =
                    new ItemStack(
                            Material.BARRIER
                    );

            ItemMeta meta =
                    empty.getItemMeta();

            if (meta != null) {

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
            }

            inv.setItem(22, empty);
        }

        ItemStack back =
                new ItemStack(
                        Material.ARROW
                );

        ItemMeta backMeta =
                back.getItemMeta();

        if (backMeta != null) {

            backMeta.setDisplayName(
                    "§c⬅ Retour"
            );

            backMeta.setLore(List.of(
                    "§8----- §6Commission Urbaine §8-----",
                    "§7Retour au menu principal",
                    "§7de l'administration urbaine."
            ));

            back.setItemMeta(backMeta);
        }

        inv.setItem(49, back);

        p.openInventory(inv);
    }
}