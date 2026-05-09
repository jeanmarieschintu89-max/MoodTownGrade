package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.NationalScoreCalculator;
import fr.moodcraft.tgrade.manager.RankingManager;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MayorTownListGUI {

    //
    // 🚀 OPEN
    //

    public static void open(
            Player p
    ) {

        Inventory inv =
                Bukkit.createInventory(
                        null,
                        54,
                        "§8✦ Conseil des Maires"
                );

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

        int[] borders = {

                0,1,2,3,4,5,6,7,8,

                9,17,

                18,26,

                27,35,

                36,44,

                45,46,47,48,50,51,52,53
        };

        for (int slot : borders) {

            inv.setItem(
                    slot,
                    glass
            );
        }

        ItemStack header =
                new ItemStack(
                        Material.NETHER_STAR
                );

        ItemMeta headerMeta =
                header.getItemMeta();

        headerMeta.setDisplayName(
                "§6✦ Conseil des Maires"
        );

        headerMeta.setLore(List.of(

                "§8----- §6Vote municipal §8-----",

                "§7Les maires donnent leur avis",

                "§7sur les villes ayant un projet",

                "§7validé en développement.",

                "",

                "§7Le vote du conseil compte",

                "§7pour le classement hebdomadaire.",

                "",

                "§7Ce vote ne remplace pas",

                "§7la validation du staff.",

                "",

                "§6▶ Choisir une ville"
        ));

        header.setItemMeta(headerMeta);

        inv.setItem(
                4,
                header
        );

        Set<String> towns =
                new HashSet<>();

        for (TownSubmission sub :
                SubmissionStorage.getAll()) {

            if (sub.getStatus()
                    != SubmissionStatus.APPROVED) {

                continue;
            }

            towns.add(
                    sub.getTown()
            );
        }

        if (towns.isEmpty()) {

            ItemStack empty =
                    new ItemStack(
                            Material.BARRIER
                    );

            ItemMeta meta =
                    empty.getItemMeta();

            meta.setDisplayName(
                    "§c✖ Aucun projet ouvert"
            );

            meta.setLore(List.of(

                    "§8----- §6Conseil des Maires §8-----",

                    "§7Aucune ville ne possède",

                    "§7un projet validé pour",

                    "§7la notation municipale.",

                    "",

                    "§7Le conseil s'ouvrira après",

                    "§7validation d'une demande",

                    "§7par le staff.",

                    "",

                    "§c▶ Revenez plus tard"
            ));

            empty.setItemMeta(meta);

            inv.setItem(
                    22,
                    empty
            );

            ItemStack back =
                    new ItemStack(
                            Material.BARRIER
                    );

            ItemMeta backMeta =
                    back.getItemMeta();

            backMeta.setDisplayName(
                    "§c⬅ Retour"
            );

            backMeta.setLore(List.of(

                    "§8----- §6Commission Urbaine §8-----",

                    "§7Retourner au menu",

                    "§7de la commission."
            ));

            back.setItemMeta(backMeta);

            inv.setItem(
                    49,
                    back
            );

            p.openInventory(inv);

            return;
        }

        List<String> sorted =
                new ArrayList<>(towns);

        sorted.sort((a, b) -> Double.compare(

                NationalScoreCalculator
                        .getFinalScore(b),

                NationalScoreCalculator
                        .getFinalScore(a)
        ));

        int slot = 10;

        for (String town : sorted) {

            if (slot == 17
                    || slot == 26
                    || slot == 35
                    || slot == 44) {

                slot += 2;
            }

            if (slot >= 45) {
                break;
            }

            double score =
                    NationalScoreCalculator
                            .getFinalScore(
                                    town
                            );

            int mayors =
                    NationalScoreCalculator
                            .getMayorCount(
                                    town
                            );

            int position =
                    RankingManager.getPosition(
                            town
                    );

            TownSubmission project =
                    getActiveProject(
                            town
                    );

            String projectName =
                    project == null
                            ? "Projet en cours"
                            : project.getBuildName();

            Material mat;

            if (position == 1) {

                mat = Material.NETHER_STAR;

            } else if (position <= 3
                    && position != -1) {

                mat = Material.DIAMOND_BLOCK;

            } else if (score >= 35) {

                mat = Material.EMERALD_BLOCK;

            } else if (score >= 20) {

                mat = Material.GOLD_BLOCK;

            } else {

                mat = Material.BRICKS;
            }

            ItemStack item =
                    new ItemStack(mat);

            ItemMeta meta =
                    item.getItemMeta();

            meta.setDisplayName(
                    "§f✦ §b" + town
            );

            meta.setLore(List.of(

                    "§8----- §6Ville en notation §8-----",

                    "§7Ville : §b" + town,

                    "§7Projet : §f" + projectName,

                    "",

                    "§7Note provisoire : §e"
                            + String.format("%.1f", score)
                            + "§7/50",

                    "§7Avis des maires : §6"
                            + mayors,

                    "§7Classement : §e#"
                            + (position == -1
                            ? "Non classé"
                            : position),

                    "",

                    "§7Rôle du conseil :",

                    "§8• §fvisiter le projet",

                    "§8• §fdonner un avis municipal",

                    "§8• §finfluencer le classement",

                    "",

                    "§6▶ Évaluer cette ville"
            ));

            item.setItemMeta(meta);

            inv.setItem(
                    slot,
                    item
            );

            slot++;
        }

        ItemStack back =
                new ItemStack(
                        Material.BARRIER
                );

        ItemMeta backMeta =
                back.getItemMeta();

        backMeta.setDisplayName(
                "§c⬅ Retour"
        );

        backMeta.setLore(List.of(

                "§8----- §6Commission Urbaine §8-----",

                "§7Retourner au menu",

                "§7de la commission."
        ));

        back.setItemMeta(backMeta);

        inv.setItem(
                49,
                back
        );

        p.openInventory(inv);
    }

    private static TownSubmission getActiveProject(
            String town
    ) {

        TownSubmission fallback = null;

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

            fallback = sub;
        }

        return fallback;
    }
}