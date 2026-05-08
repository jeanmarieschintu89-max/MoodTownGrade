package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.GradeManager;
import fr.moodcraft.tgrade.manager.NationalScoreCalculator;

import fr.moodcraft.tgrade.model.TownGrade;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class EvaluationManagerGUI {

    public static final int NOTE = 20;
    public static final int FINALIZE = 24;
    public static final int BACK = 40;

    public static void open(
            Player p,
            String town
    ) {

        Inventory inv =
                Bukkit.createInventory(
                        null,
                        45,
                        "§8✦ Dossier National"
                );

        TownGrade grade =
                GradeManager.get(town);

        double national =
                NationalScoreCalculator.getFinalScore(town);

        double staff =
                NationalScoreCalculator.getStaffScore(town);

        double mayors =
                NationalScoreCalculator.getMayorScore(town);

        double citizens =
                NationalScoreCalculator.getCitizenScore(town);

        ItemStack glass =
                item(
                        Material.BLACK_STAINED_GLASS_PANE,
                        " "
                );

        int[] borders = {

                0,1,2,3,4,5,6,7,8,

                9,17,

                18,26,

                27,35,

                36,37,38,39,41,42,43,44
        };

        for (int slot : borders) {

            inv.setItem(slot, glass);
        }

        inv.setItem(

                4,

                item(

                        Material.NETHER_STAR,

                        "§6✦ Dossier National",

                        "§8----- §6Commission Urbaine §8-----",

                        "§7Ville: §b" + town,

                        "§7Statut: "
                                + (grade.isFinished()
                                ? "§aClôturé"
                                : "§eOuvert"),

                        "",

                        "§7Note nationale: §e"
                                + national
                                + "§7/50",

                        "§7Staff: §e" + staff + "§7/50",

                        "§7Maires: §e" + mayors + "§7/50",

                        "§7Citoyens: §e" + citizens + "§7/50"
                )
        );

        inv.setItem(

                NOTE,

                item(

                        Material.ENCHANTED_BOOK,

                        "§b✦ Noter la ville",

                        "§8----- §6Vote Staff §8-----",

                        "§7Ouvrir le barème",

                        "§7de notation staff.",

                        "",

                        "§b▶ Modifier la note"
                )
        );

        inv.setItem(

                FINALIZE,

                item(

                        Material.BEACON,

                        "§a✔ Clôturer cette ville",

                        "§8----- §6Validation Finale §8-----",

                        "§7Valide définitivement",

                        "§7le dossier de cette ville.",

                        "",

                        "§8• §fVotes staff conservés",

                        "§8• §fVotes maires conservés",

                        "§8• §fVotes citoyens conservés",

                        "",

                        "§a▶ Finaliser le dossier"
                )
        );

        inv.setItem(

                BACK,

                item(

                        Material.ARROW,

                        "§c⬅ Retour",

                        "§8----- §6Registre National §8-----",

                        "§7Retourner aux évaluations."
                )
        );

        p.openInventory(inv);
    }

    private static ItemStack item(
            Material mat,
            String name,
            String... lore
    ) {

        ItemStack item =
                new ItemStack(mat);

        ItemMeta meta =
                item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(
                List.of(lore)
        );

        item.setItemMeta(meta);

        return item;
    }
}