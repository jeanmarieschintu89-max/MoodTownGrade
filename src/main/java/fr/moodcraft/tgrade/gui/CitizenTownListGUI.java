package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.NationalScoreCalculator;
import fr.moodcraft.tgrade.manager.RankingManager;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import fr.moodcraft.flag.api.MoodTownFlagAPI;

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
                        "§8✦ Votes Citoyens"
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

                "§8----- §6Participation publique §8-----",

                "§7Notez les villes ayant",

                "§7un projet en développement.",

                "",

                "§7Visitez le projet, observez",

                "§7la ville puis donnez votre vote.",

                "",

                "§7Vos votes comptent pour",

                "§7le classement hebdomadaire.",

                "",

                "§e▶ Choisir une ville"
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
                    "§c✖ Aucun vote ouvert"
            );

            meta.setLore(List.of(

                    "§8----- §6Votes Citoyens §8-----",

                    "§7Aucune ville ne possède",

                    "§7un projet validé pour",

                    "§7la notation citoyenne.",

                    "",

                    "§7Les votes s'ouvriront après",

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

            int citizens =
                    NationalScoreCalculator
                            .getCitizenCount(
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
                    "§7Votes citoyens : §b"
                            + citizens
            );

            lore.add(
                    "§7Classement : §6#"
                            + (position == -1
                            ? "Non classé"
                            : position)
            );

            lore.add("");
            lore.add("§7Votre rôle :");
            lore.add("§8• §fvisiter le projet");
            lore.add("§8• §fobserver la ville");
            lore.add("§8• §fvoter pour son évolution");
            lore.add("");
            lore.add("§7Votre vote compte pour");
            lore.add("§7le classement hebdomadaire.");
            lore.add("");
            lore.add("§e▶ Consulter et voter");

            meta.setLore(lore);

            item.setItemMeta(meta);

            if (position == 1) {

                item =
                        glow(
                                item
                        );
            }

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

    private static ItemStack glow(
            ItemStack item
    ) {

        ItemMeta meta =
                item.getItemMeta();

        if (meta == null)
            return item;

        meta.addEnchant(
                org.bukkit.enchantments.Enchantment.UNBREAKING,
                1,
                true
        );

        meta.addItemFlags(
                org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS
        );

        item.setItemMeta(meta);

        return item;
    }
}