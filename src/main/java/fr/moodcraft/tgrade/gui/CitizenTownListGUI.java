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

public class CitizenTownListGUI {

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

                        "§8✦ Avis Citoyens"
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

            inv.setItem(
                    slot,
                    glass
            );
        }

        //
        // 🏛 HEADER
        //

        ItemStack header =
                new ItemStack(
                        Material.BOOK
                );

        ItemMeta headerMeta =
                header.getItemMeta();

        headerMeta.setDisplayName(
                "§e✦ Votes Citoyens"
        );

        headerMeta.setLore(List.of(

                "§8----- §6Participation §8-----",

                "§7Consultez les villes",

                "§7validées par la Commission.",

                "",

                "§7Votre avis influence",

                "§7le §ePrestige National§7.",

                "",

                "§e▶ Registre citoyen"
        ));

        header.setItemMeta(headerMeta);

        inv.setItem(
                4,
                header
        );

        //
        // 🏙 VILLES VALIDÉES
        //

        Set<String> towns =
                new HashSet<>();

        for (TownSubmission sub :
                SubmissionStorage.getAll()) {

            //
            // ✅ UNIQUEMENT VALIDÉS
            //

            if (sub.getStatus()
                    != SubmissionStatus.APPROVED) {

                continue;
            }

            towns.add(
                    sub.getTown()
            );
        }

        //
        // ❌ AUCUNE VILLE
        //

        if (towns.isEmpty()) {

            ItemStack empty =
                    new ItemStack(
                            Material.BARRIER
                    );

            ItemMeta meta =
                    empty.getItemMeta();

            meta.setDisplayName(
                    "§c✖ Aucun projet inspecté"
            );

            meta.setLore(List.of(

                    "§8----- §6Registre Citoyen §8-----",

                    "§7Aucune municipalité",

                    "§7n'a encore reçu",

                    "§7de validation nationale.",

                    "",

                    "§7Les villes apparaîtront",

                    "§7après inspection.",

                    "",

                    "§c▶ Revenez plus tard"
            ));

            empty.setItemMeta(meta);

            inv.setItem(
                    22,
                    empty
            );

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

        //
        // 📚 LISTE
        //

        List<String> sorted =
                new ArrayList<>(towns);

        sorted.sort((a, b) -> Double.compare(

                NationalScoreCalculator
                        .getFinalScore(b),

                NationalScoreCalculator
                        .getFinalScore(a)
        ));

        //
        // 📦 SLOT
        //

        int slot = 10;

        //
        // 🏙 LOOP
        //

        for (String town : sorted) {

            //
            // ⛔ SLOTS INVALIDES
            //

            if (slot == 17
                    || slot == 26
                    || slot == 35
                    || slot == 44) {

                slot += 2;
            }

            //
            // ⛔ OVERFLOW
            //

            if (slot >= 45) {
                break;
            }

            //
            // 📊 DATA
            //

            double score =
                    NationalScoreCalculator
                            .getFinalScore(
                                    town
                            );

            int citizens =
                    NationalScoreCalculator
                            .getCitizenCount(
                                    town
                            );

            int position =
                    RankingManager.getPosition(
                            town
                    );

            //
            // 🏆 MATERIAL
            //

            Material mat;

            if (position == 1) {

                mat = Material.NETHER_STAR;

            } else if (position <= 3
                    && position != -1) {

                mat = Material.DIAMOND_BLOCK;

            } else if (score >= 35) {

                mat = Material.EMERALD_BLOCK;

            } else if (score >= 20) {

                mat = Material.BRICKS;

            } else {

                mat = Material.GRASS_BLOCK;
            }

            //
            // 📦 ITEM
            //

            ItemStack item =
                    new ItemStack(mat);

            ItemMeta meta =
                    item.getItemMeta();

            meta.setDisplayName(
                    "§f✦ §b" + town
            );

            meta.setLore(List.of(

                    "§8----- §6Ville Validée §8-----",

                    "§7Prestige national: §e"
                            + String.format("%.1f", score)
                            + "§7/50",

                    "§7Votes citoyens: §b"
                            + citizens,

                    "§7Classement: §6#"
                            + (position == -1
                            ? "Non classé"
                            : position),

                    "",

                    "§7Votre avis peut modifier",

                    "§7l'influence populaire.",

                    "",

                    "§e▶ Consulter et voter"
            ));

            item.setItemMeta(meta);

            inv.setItem(
                    slot,
                    item
            );

            slot++;
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

        //
        // 🚀 OPEN
        //

        p.openInventory(inv);
    }
}