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

    public static void open(Player p) {

        Inventory inv =
                Bukkit.createInventory(
                        null,
                        54,
                        "§8✦ Classement Hebdo"
                );

        ItemStack glass =
                new ItemStack(Material.BLACK_STAINED_GLASS_PANE);

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
                new ArrayList<>();

        for (Object object : RankingManager.getTop()) {

            if (object instanceof TownGrade grade) {
                top.add(grade);
            }
        }

        top.sort(new Comparator<TownGrade>() {

            @Override
            public int compare(
                    TownGrade a,
                    TownGrade b
            ) {

                return Double.compare(
                        getVisibleScore(b),
                        getVisibleScore(a)
                );
            }
        });

        ItemStack header =
                new ItemStack(Material.NETHER_STAR);

        ItemMeta headerMeta =
                header.getItemMeta();

        headerMeta.setDisplayName(
                "§6✦ Classement Hebdomadaire"
        );

        if (!top.isEmpty()) {

            TownGrade best =
                    top.get(0);

            double national =
                    getVisibleScore(best);

            headerMeta.setLore(List.of(
                    "§8----- §6Registre urbain §8-----",
                    "§7Classement en direct des villes",
                    "§7ayant une demande de projet validée.",
                    "",
                    "§7Ville en tête : §e" + best.getTown(),
                    "§7Note actuelle : §e"
                            + String.format("%.1f", national)
                            + "§7/50",
                    "§7État : " + getState(best),
                    "",
                    "§7Les subventions sont versées",
                    "§7séparément par le staff ou",
                    "§7lors du paiement hebdomadaire.",
                    "",
                    "§e▶ Résultats urbains"
            ));

        } else {

            headerMeta.setLore(List.of(
                    "§8----- §6Registre urbain §8-----",
                    "§7Aucune ville classée",
                    "§7pour cette semaine.",
                    "",
                    "§7Le classement s'active après",
                    "§7validation d'une demande",
                    "§7de projet par le staff.",
                    "",
                    "§e▶ En attente de projets"
            ));
        }

        header.setItemMeta(headerMeta);
        inv.setItem(4, header);

        int slot = 10;
        int pos = 1;

        for (TownGrade grade : top) {

            if (slot >= 44)
                break;

            double staff =
                    NationalScoreCalculator.getStaffScore(
                            grade.getTown()
                    );

            double mayors =
                    NationalScoreCalculator.getMayorScore(
                            grade.getTown()
                    );

            double citizens =
                    NationalScoreCalculator.getCitizenScore(
                            grade.getTown()
                    );

            double national =
                    getVisibleScore(grade);

            Material mat;
            String podium;

            switch (pos) {

                case 1 -> {
                    mat = Material.NETHER_STAR;
                    podium = "§6♛ Rang Hebdo I";
                }

                case 2 -> {
                    mat = Material.DIAMOND;
                    podium = "§b♢ Rang Hebdo II";
                }

                case 3 -> {
                    mat = Material.EMERALD;
                    podium = "§a♢ Rang Hebdo III";
                }

                default -> {
                    mat = Material.GOLD_INGOT;
                    podium = "§eRang Hebdo #" + pos;
                }
            }

            ItemStack item =
                    new ItemStack(mat);

            ItemMeta meta =
                    item.getItemMeta();

            meta.setDisplayName(
                    podium + " §8• §b" + grade.getTown()
            );

            meta.setLore(List.of(
                    "§8----- §6Classement hebdomadaire §8-----",
                    "§7Ville : §b" + grade.getTown(),
                    "§7État : " + getState(grade),
                    "",
                    "§7Titre urbain :",
                    getRank(national),
                    "",
                    "§7Staff : §e"
                            + String.format("%.1f", staff)
                            + "§7/50",
                    "§7Conseil des maires : §e"
                            + String.format("%.1f", may