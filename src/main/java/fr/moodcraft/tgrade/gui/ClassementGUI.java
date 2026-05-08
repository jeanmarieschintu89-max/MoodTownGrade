package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.NationalScoreCalculator;
import fr.moodcraft.tgrade.manager.RankingManager;

import fr.moodcraft.tgrade.model.TownGrade;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ClassementGUI {

    //
    // 🏆 OPEN
    //

    public static void open(
            Player p
    ) {

        Inventory inv =
                Bukkit.createInventory(

                        null,

                        54,

                        "§8✦ Classement National"
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

        glassMeta.setDisplayName(" ");

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
        // 👑 HEADER
        //

        TownGrade best =
                RankingManager.getBest();

        ItemStack header =
                new ItemStack(
                        Material.NETHER_STAR
                );

        ItemMeta headerMeta =
                header.getItemMeta();

        headerMeta.setDisplayName(
                "§6✦ Palmarès National"
        );

        if (best != null) {

            double national =
                    NationalScoreCalculator
                            .getFinalScore(
                                    best.getTown()
                            );

            headerMeta.setLore(List.of(

                    "§8----- §6Registre National §8-----",

                    "§7Ville dominante: §e"
                            + best.getTown(),

                    "§7Prestige national: §e"
                            + national + "§7/50",

                    "§7Titre actuel:",
                    best.getRank(),

                    "",

                    "§e▶ Classement officiel MoodCraft"
            ));

        } else {

            headerMeta.setLore(List.of(

                    "§8----- §6Registre National §8-----",

                    "§7Aucune ville classée",

                    "§7pour cette semaine.",

                    "",

                    "§e▶ En attente d'inspections"
            ));
        }

        header.setItemMeta(headerMeta);

        inv.setItem(4, header);

        //
        // 🏆 TOP
        //

        List<TownGrade> top =
                RankingManager.getTop();

        int slot = 10;

        int pos = 1;

        for (TownGrade grade : top) {

            if (slot >= 44)
                break;

            //
            // 📊 SCORES
            //

            double staff =
                    NationalScoreCalculator
                            .getStaffScore(
                                    grade.getTown()
                            );

            double mayors =
                    NationalScoreCalculator
                            .getMayorScore(
                                    grade.getTown()
                            );

            double citizens =
                    NationalScoreCalculator
                            .getCitizenScore(
                                    grade.getTown()
                            );

            double national =
                    NationalScoreCalculator
                            .getFinalScore(
                                    grade.getTown()
                            );

            //
            // 🥇 MATERIAL
            //

            Material mat;

            String podium;

            switch (pos) {

                case 1 -> {

                    mat =
                            Material.NETHER_STAR;

                    podium =
                            "§6♛ Rang National I";
                }

                case 2 -> {

                    mat =
                            Material.DIAMOND;

                    podium =
                            "§b♢ Rang National II";
                }

                case 3 -> {

                    mat =
                            Material.EMERALD;

                    podium =
                            "§a♢ Rang National III";
                }

                default -> {

                    mat =
                            Material.GOLD_INGOT;

                    podium =
                            "§eRang National #" + pos;
                }
            }

            //
            // 📦 ITEM
            //

            ItemStack item =
                    new ItemStack(mat);

            ItemMeta meta =
                    item.getItemMeta();

            meta.setDisplayName(

                    podium
                            + " §8• §b"
                            + grade.getTown()
            );

            meta.setLore(List.of(

                    "§8----- §6Prestige Urbain §8-----",

                    "§7Titre officiel:",
                    grade.getRank(),

                    "",

                    "§7Commission: §e"
                            + staff + "§7/50",

                    "§7Conseil des maires: §e"
                            + Math.round(
                            (mayors / 2.0) * 10
                    ) / 10.0
                            + "§7/25",

                    "§7Votes citoyens: §e"
                            + Math.round(
                            ((citizens / 50.0) * 25.0)
                                    * 10
                    ) / 10.0
                            + "§7/25",

                    "",

                    "§7Score national: §e"
                            + national + "§7/50",

                    "§7Financement: §a"
                            + format(
                            grade.getPayout()
                    )
                            + "$",

                    "",

                    "§7Appréciation:",
                    grade.getAppreciation()
            ));

            item.setItemMeta(meta);

            inv.setItem(slot, item);

            //
            // ➡ NEXT
            //

            slot++;

            if (slot == 17)
                slot = 19;

            if (slot == 26)
                slot = 28;

            if (slot == 35)
                slot = 37;

            pos++;
        }

        //
        // ❌ EMPTY
        //

        if (top.isEmpty()) {

            ItemStack empty =
                    new ItemStack(
                            Material.BARRIER
                    );

            ItemMeta meta =
                    empty.getItemMeta();

            meta.setDisplayName(
                    "§c✖ Aucun classement"
            );

            meta.setLore(List.of(

                    "§8----- §6Registre National §8-----",

                    "§7Aucune ville n'a encore",

                    "§7été inspectée cette semaine.",

                    "",

                    "§e▶ Le palmarès attend ses premières données"
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

                "§7Retourner au menu",

                "§7de l'administration nationale."
        ));

        back.setItemMeta(backMeta);

        inv.setItem(49, back);

        //
        // 🚀 OPEN
        //

        p.openInventory(inv);
    }

    //
    // 💰 FORMAT
    //

    private static String format(
            int value
    ) {

        return String.format(
                "%,d",
                value
        ).replace(",", " ");
    }
}