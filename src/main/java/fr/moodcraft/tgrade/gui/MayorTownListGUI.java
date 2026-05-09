package fr.moodcraft.tgrade.gui;

import fr.moodcraft.flag.api.MoodTownFlagAPI;

import fr.moodcraft.tgrade.manager.NationalScoreCalculator;
import fr.moodcraft.tgrade.manager.RankingManager;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.enchantments.Enchantment;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
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

        if (glassMeta != null) {

            glassMeta.setDisplayName(
                    " "
            );

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

        if (headerMeta != null) {

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
        }

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

            if (meta != null) {

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
            }

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

            if (backMeta != null) {

                backMeta.setDisplayName(
                        "§c⬅ Retour"
                );

                backMeta.setLore(List.of(

                        "§8----- §6Commission Urbaine §8-----",

                        "§7Retourner au menu",

                        "§7de la commission."
                ));

                back.setItemMeta(backMeta);
            }

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

            ItemStack item =
                    MoodTownFlagAPI.getTownFlagItem(
                            town
                    );

            boolean hasFlag =
                    item != null;

            if (item == null) {

                item =
                        new ItemStack(
                                Material.WHITE_BANNER
                        );
            }

            ItemMeta meta =
                    item.getItemMeta();

            if (meta != null) {

                meta.setDisplayName(
                        "§f✦ §b" + town
                );

                List<String> lore =
                        new ArrayList<>();

                lore.add("§8----- §6Ville en notation §8-----");
                lore.add("§7Ville : §b" + town);
                lore.add("§7Projet : §f" + projectName);
                lore.add("");

                if (hasFlag) {

                    lore.add("§a✔ Drapeau officiel enregistré");

                } else {

                    lore.add("§7Drapeau : §fNon défini");
                }

                lore.add("");
                lore.add(
                        "§7Note provisoire : §e"
                                + String.format("%.1f", score)
                                + "§7/50"
                );

                lore.add(
                        "§7Avis des maires : §6"
                                + mayors
                );

                lore.add(
                        "§7Classement : §e#"
                                + (position == -1
                                ? "Non classé"
                                : position)
                );

                lore.add("");
                lore.add("§7Rôle du conseil :");
                lore.add("§8• §fvisiter le projet");
                lore.add("§8• §fdonner un avis municipal");
                lore.add("§8• §finfluencer le classement");
                lore.add("");
                lore.add("§6▶ Évaluer cette ville");

                meta.setLore(lore);

                item.setItemMeta(meta);
            }

            if (position == 1) {

                item =
                        glow(item);
            }

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

        if (backMeta != null) {

            backMeta.setDisplayName(
                    "§c⬅ Retour"
            );

            backMeta.setLore(List.of(

                    "§8----- §6Commission Urbaine §8-----",

                    "§7Retourner au menu",

                    "§7de la commission."
            ));

            back.setItemMeta(backMeta);
        }

        inv.setItem(
                49,
                back
        );

        p.openInventory(inv);
    }

    private static ItemStack glow(
            ItemStack item
    ) {

        ItemMeta meta =
                item.getItemMeta();

        if (meta == null)
            return item;

        meta.addEnchant(
                Enchantment.UNBREAKING,
                1,
                true
        );

        meta.addItemFlags(
                ItemFlag.HIDE_ENCHANTS
        );

        item.setItemMeta(meta);

        return item;
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