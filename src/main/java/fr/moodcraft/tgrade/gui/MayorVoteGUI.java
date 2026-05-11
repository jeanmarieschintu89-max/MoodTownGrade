package fr.moodcraft.tgrade.gui;

import fr.moodcraft.flag.api.MoodTownFlagAPI;

import fr.moodcraft.tgrade.manager.MayorVoteManager;
import fr.moodcraft.tgrade.manager.NationalScoreCalculator;

import fr.moodcraft.tgrade.model.MayorVote;
import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MayorVoteGUI {

    public static final String TITLE =
            "§8✦ Vote des Maires";

    public static final int BEAUTE = 20;
    public static final int AMBIANCE = 21;
    public static final int ACTIVITE = 22;
    public static final int ORIGINALITE = 23;
    public static final int POPULARITE = 24;

    public static final int TOWN_DATA = 13;

    public static final int BACK = 36;
    public static final int SAVE = 40;
    public static final int TP_PROJECT = 44;

    public static void open(
            Player p,
            String town
    ) {

        Inventory inv =
                Bukkit.createInventory(
                        null,
                        45,
                        TITLE
                );

        MayorVote vote =
                MayorVoteManager.getVote(
                        p.getUniqueId(),
                        town
                );

        if (vote == null) {

            vote =
                    new MayorVote(
                            p.getUniqueId(),
                            town
                    );
        }

        TownSubmission project =
                getActiveProject(
                        town
                );

        String projectName =
                project == null
                        ? "Projet en cours"
                        : project.getBuildName();

        int total =
                vote.getBeaute()
                        + vote.getAmbiance()
                        + vote.getActivite()
                        + vote.getOriginalite()
                        + vote.getPopularite();

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
                0, 1, 2, 3, 4, 5, 6, 7, 8,
                9, 17,
                18, 26,
                27, 35,
                37, 38, 39, 41, 42, 43
        };

        for (int slot : borders) {

            inv.setItem(
                    slot,
                    glass
            );
        }

        ItemStack header =
                MoodTownFlagAPI.getTownShieldItem(
                        town
                );

        boolean hasFlag =
                header != null;

        if (header == null) {

            header =
                    new ItemStack(
                            Material.SHIELD
                    );
        }

        ItemMeta headerMeta =
                header.getItemMeta();

        if (headerMeta != null) {

            headerMeta.setDisplayName(
                    "§6✦ Vote des Maires"
            );

            List<String> lore =
                    new ArrayList<>();

            lore.add("§8----- §6Vote municipal §8-----");
            lore.add("§7Ville : §b" + town);
            lore.add("§7Projet : §f" + projectName);
            lore.add("");

            if (hasFlag) {
                lore.add("§a✔ Blason officiel enregistré");
            } else {
                lore.add("§7Blason : §fNon défini");
            }

            lore.add("");
            lore.add("§7Donnez un avis municipal");
            lore.add("§7sur la ville et son projet");
            lore.add("§7en développement.");
            lore.add("");
            lore.add("§7Ce vote compte pour");
            lore.add("§7le classement hebdomadaire.");
            lore.add("");
            lore.add(
                    "§7Votre score : §e"
                            + total
                            + "§7/25"
            );
            lore.add(
                    "§7Note provisoire : §e"
                            + String.format(
                            "%.1f",
                            NationalScoreCalculator
                                    .getFinalScore(town)
                    )
                            + "§7/50"
            );
            lore.add(
                    "§7Votes des maires : §6"
                            + NationalScoreCalculator
                            .getMayorCount(town)
            );
            lore.add("");
            lore.add("§6▶ Ajustez les critères");

            headerMeta.setLore(lore);

            headerMeta.addItemFlags(
                    ItemFlag.HIDE_ADDITIONAL_TOOLTIP,
                    ItemFlag.HIDE_ATTRIBUTES,
                    ItemFlag.HIDE_ENCHANTS
            );

            header.setItemMeta(headerMeta);
        }

        inv.setItem(
                4,
                header
        );

        set(
                inv,
                TOWN_DATA,
                Material.PAPER,
                "§0" + town,
                "§0" + projectName
        );

        setVote(
                inv,
                BEAUTE,
                Material.QUARTZ_BLOCK,
                "§f✦ Visuel",
                "§7Beauté générale, détails",
                "§7et qualité des constructions.",
                vote.getBeaute()
        );

        setVote(
                inv,
                AMBIANCE,
                Material.LANTERN,
                "§e✦ Ambiance",
                "§7Cohérence, atmosphère",
                "§7et identité municipale.",
                vote.getAmbiance()
        );

        setVote(
                inv,
                ACTIVITE,
                Material.BELL,
                "§6✦ Activité",
                "§7Dynamisme visible autour",
                "§7de la ville et du projet.",
                vote.getActivite()
        );

        setVote(
                inv,
                ORIGINALITE,
                Material.COMPASS,
                "§b✦ Originalité",
                "§7Créativité du développement",
                "§7urbain présenté.",
                vote.getOriginalite()
        );

        setVote(
                inv,
                POPULARITE,
                Material.REDSTONE,
                "§c✦ Avis général",
                "§7Pertinence générale",
                "§7du projet municipal.",
                vote.getPopularite()
        );

        set(
                inv,
                BACK,
                Material.BARRIER,
                "§c✖ Retour",
                "§8----- §6Conseil des Maires §8-----",
                "§7Retour à la liste",
                "§7des villes à évaluer.",
                "",
                "§c▶ Retour"
        );

        set(
                inv,
                SAVE,
                Material.EMERALD_BLOCK,
                "§a✔ Valider le vote",
                "§8----- §6Conseil des Maires §8-----",
                "§7Ville : §b" + town,
                "§7Projet : §f" + projectName,
                "",
                "§7Votre score : §e"
                        + total
                        + "§7/25",
                "",
                "§7Enregistre votre avis municipal",
                "§7pour le classement hebdomadaire.",
                "",
                "§a▶ Sauvegarder"
        );

        set(
                inv,
                TP_PROJECT,
                Material.ENDER_PEARL,
                "§b📍 Visiter le projet",
                "§8----- §6Téléportation §8-----",
                "§7Ville : §b" + town,
                "§7Projet : §f" + projectName,
                "",
                "§7Téléporte vers le projet",
                "§7à visiter avant le vote.",
                "",
                "§b▶ Se téléporter"
        );

        p.openInventory(inv);
    }

    private static void setVote(
            Inventory inv,
            int slot,
            Material mat,
            String name,
            String line1,
            String line2,
            int value
    ) {

        set(
                inv,
                slot,
                mat,
                name,
                "§8----- §6Critère du Conseil §8-----",
                line1,
                line2,
                "",
                "§7Note actuelle : §e"
                        + value
                        + "§7/5",
                "",
                "§e▶ Cliquer pour ajuster"
        );
    }

    private static void set(
            Inventory inv,
            int slot,
            Material mat,
            String name,
            String... lore
    ) {

        ItemStack item =
                new ItemStack(mat);

        ItemMeta meta =
                item.getItemMeta();

        if (meta == null) {
            return;
        }

        meta.setDisplayName(name);

        meta.setLore(
                List.of(lore)
        );

        meta.addItemFlags(
                ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_ADDITIONAL_TOOLTIP
        );

        item.setItemMeta(meta);

        inv.setItem(
                slot,
                item
        );
    }

    private static TownSubmission getActiveProject(
            String town
    ) {

        TownSubmission fallback =
                null;

        for (TownSubmission sub :
                SubmissionStorage.getAll()) {

            if (!sub.getTown()
                    .equalsIgnoreCase(town)) {
                continue;
            }

            if (sub.getStatus()
                    == SubmissionStatus.APPROVED) {

                return sub;
            }

            fallback =
                    sub;
        }

        return fallback;
    }
}