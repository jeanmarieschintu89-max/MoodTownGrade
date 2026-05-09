package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.NationalScoreCalculator;

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

public class EvaluationManagerGUI {

    //
    // ⭐ OPEN
    //

    public static void open(Player p) {

        Inventory inv =
                Bukkit.createInventory(
                        null,
                        54,
                        "§8✦ Notation Staff"
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

            inv.setItem(slot, glass);
        }

        //
        // 🏛 HEADER
        //

        ItemStack header =
                new ItemStack(
                        Material.ENCHANTED_BOOK
                );

        ItemMeta headerMeta =
                header.getItemMeta();

        headerMeta.setDisplayName(
                "§6✦ Notation Staff"
        );

        headerMeta.setLore(List.of(

                "§8----- §6Commission Urbaine §8-----",

                "§7Sélectionnez une ville ayant",

                "§7une demande de projet validée.",

                "",

                "§7La note staff compte pour",

                "§7le classement hebdomadaire.",

                "",

                "§7Elle concerne la ville",

                "§7et son projet en développement.",

                "",

                "§e▶ Choisir un dossier"
        ));

        header.setItemMeta(headerMeta);

        inv.setItem(4, header);

        //
        // 🏙 VILLES VALIDÉES
        //

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

        List<String> sorted =
                new ArrayList<>(towns);

        sorted.sort(String::compareToIgnoreCase);

        //
        // ❌ EMPTY
        //

        if (sorted.isEmpty()) {

            ItemStack empty =
                    new ItemStack(
                            Material.BARRIER
                    );

            ItemMeta meta =
                    empty.getItemMeta();

            meta.setDisplayName(
                    "§c✖ Aucun dossier ouvert"
            );

            meta.setLore(List.of(

                    "§8----- §6Notation Staff §8-----",

                    "§7Aucune ville ne possède",

                    "§7une demande de projet",

                    "§7validée pour notation.",

                    "",

                    "§7Validez d'abord une demande",

                    "§7depuis le centre administratif.",

                    "",

                    "§e▶ En attente de validation"
            ));

            empty.setItemMeta(meta);

            inv.setItem(22, empty);
        }

        //
        // 📦 LISTE
        //

        int slot = 10;

        for (String town : sorted) {

            if (slot >= 44)
                break;

            double score =
                    NationalScoreCalculator
                            .getFinalScore(town);

            TownSubmission project =
                    getActiveProject(town);

            String projectName =
                    project == null
                            ? "Projet en cours"
                            : project.getBuildName();

            ItemStack item =
                    new ItemStack(
                            Material.BEACON
                    );

            ItemMeta meta =
                    item.getItemMeta();

            meta.setDisplayName(
                    "§f✦ §b" + town
            );

            meta.setLore(List.of(

                    "§8----- §6Dossier de notation §8-----",

                    "§7Ville : §b" + town,

                    "§7Projet : §f" + projectName,

                    "",

                    "§7Note provisoire : §e"
                            + String.format("%.1f", score)
                            + "§7/50",

                    "",

                    "§7Ouvre la notation staff",

                    "§7pour la ville et son",

                    "§7projet en développement.",

                    "",

                    "§e▶ Noter ce dossier"
            ));

            item.setItemMeta(meta);

            inv.setItem(slot, item);

            slot++;

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
                        Material.ARROW
                );

        ItemMeta backMeta =
                back.getItemMeta();

        backMeta.setDisplayName(
                "§c⬅ Retour"
        );

        backMeta.setLore(List.of(

                "§8----- §6Commission Urbaine §8-----",

                "§7Retour au centre",

                "§7administratif."
        ));

        back.setItemMeta(backMeta);

        inv.setItem(49, back);

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