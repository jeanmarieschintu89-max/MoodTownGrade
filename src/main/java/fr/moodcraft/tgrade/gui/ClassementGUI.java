
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

import java.util.ArrayList;
import java.util.Comparator;
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

        ItemStack glass =
                new ItemStack(
                        Material.BLACK_STAINED_GLASS_PANE
                );

        ItemMeta glassMeta =
                glass.getItemMeta();

        glassMeta.setDisplayName(" ");

        glass.setItemMeta(glassMeta);

        int[] borders = {

                0,1,2,3,4,5,6,7,8,

                9,17,

                18,26,

                27,35,

                36,44,

                45,46,47,48,50,51,52,53
        };

        for (int border : borders) {

            inv.setItem(border, glass);
        }

        List<TownGrade> top =
                new ArrayList<>(
                        RankingManager.getTop()
                );

        top.sort(
                Comparator.comparingDouble(
                        grade -> NationalScoreCalculator
                                .getFinalScore(
                                        grade.getTown()
                                )
                ).reversed()
        );

        //
        // 👑 HEADER
        //

        ItemStack header =
                new ItemStack(
                        Material.NETHER_STAR
                );

        ItemMeta headerMeta =
                header.getItemMeta();

        headerMeta.setDisplayName(
                "§6✦ Palmarès National"
        );

        if (!top.isEmpty()) {

            TownGrade best =
                    top.get(0);

            double national =
                    NationalScoreCalculator
                            .getFinalScore(
                                    best.getTown()
                            );

            headerMeta.setLore(List.of(

                    "§8----- §6Registre National §8-----",

                    "§7Ville dominante: §e"
                            + best.getTown(),

                    "§7Note nationale: §e"
                            + national + "§7/50",

                    "§7Titre actuel:",
                    getRank(national),

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

        int slot = 10;

        int pos = 1;

        for (TownGrade grade : top) {

            if (slot >= 44)
                break;

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

                    "§8----- §6Évaluation Nationale §8-----",

                    "§7Titre officiel:",
                    getRank(national),

                    "",

                    "§7Commission: §e"
                            + staff + "§7/50",

                    "§7Conseil des maires: §e"
                            + mayors + "§7/50",

                    "§7Votes citoyens: §e"
                            + citizens + "§7/50",

                    "",

                    "§7Note nationale: §e"
                            + national + "§7/50",

                    "§7Financement: §a"
                            + format(
                            getPayout(national)
                    )
                            + "€",

                    "",

                    "§7Appréciation:",
                    getAppreciation(national)
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

        p.openInventory(inv);
    }

    //
    // 🏅 RANK
    //

    private static String getRank(
            double score
    ) {

        if (score <= 10) {
            return "§8✦ Hameau en friche";
        }

        if (score <= 20) {
            return "§7✦ Commune rurale";
        }

        if (score <= 30) {
            return "§a✦ Ville reconnue";
        }

        if (score <= 40) {
            return "§b✦ Métropole prospère";
        }

        if (score <= 49) {
            return "§6✦ Capitale d'élite";
        }

        return "§e§l✦ Merveille Nationale";
    }

    //
    // 💰 PAYOUT
    //

    private static int getPayout(
            double score
    ) {

        if (score <= 10) {
            return 2500;
        }

        if (score <= 20) {
            return 5000;
        }

        if (score <= 25) {
            return 7500;
        }

        if (score <= 30) {
            return 10000;
        }

        if (score <= 35) {
            return 15000;
        }

        if (score <= 40) {
            return 20000;
        }

        if (score <= 45) {
            return 25000;
        }

        if (score <= 49) {
            return 30000;
        }

        return 40000;
    }

    //
    // 🏛 APPRECIATION
    //

    private static String getAppreciation(
            double score
    ) {

        if (score <= 10) {
            return "§7Avis: aide minimale accordée pour lancer le développement.";
        }

        if (score <= 20) {
            return "§7Avis: commune en construction, soutien national modéré.";
        }

        if (score <= 25) {
            return "§eAvis: dossier fragile mais recevable pour un financement.";
        }

        if (score <= 30) {
            return "§aAvis: ville reconnue, développement urbain encourageant.";
        }

        if (score <= 35)
        }
}