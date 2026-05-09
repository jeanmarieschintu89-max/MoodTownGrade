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
import java.util.List;

public class ClassementGUI {

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, "§8✦ Classement Hebdo");

        ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta glassMeta = glass.getItemMeta();
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

        List<TownGrade> top = new ArrayList<>();

        for (Object object : RankingManager.getTop()) {
            if (object instanceof TownGrade grade) {
                top.add(grade);
            }
        }

        top.sort((a, b) -> Double.compare(getVisibleScore(b), getVisibleScore(a)));

        ItemStack header = new ItemStack(Material.NETHER_STAR);
        ItemMeta headerMeta = header.getItemMeta();

        headerMeta.setDisplayName("§6✦ Classement Hebdomadaire");

        if (top.isEmpty()) {

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

        } else {

            TownGrade best = top.get(0);
            double national = getVisibleScore(best);

            headerMeta.setLore(List.of(
                    "§8----- §6Registre urbain §8-----",
                    "§7Classement en direct des villes",
                    "§7ayant une demande de projet validée.",
                    "",
                    "§7Ville en tête : §e" + best.getTown(),
                    "§7Note actuelle : §e" + formatScore(national) + "§7/50",
                    "§7État : " + getState(best),
                    "",
                    "§7Les subventions sont versées",
                    "§7séparément par le staff ou",
                    "§7lors du paiement hebdomadaire.",
                    "",
                    "§e▶ Résultats urbains"
            ));
        }

        header.setItemMeta(headerMeta);
        inv.setItem(4, header);

        int slot = 10;
        int pos = 1;

        for (TownGrade grade : top) {

            if (slot >= 44) {
                break;
            }

            double staff = NationalScoreCalculator.getStaffScore(grade.getTown());
            double mayors = NationalScoreCalculator.getMayorScore(grade.getTown());
            double citizens = NationalScoreCalculator.getCitizenScore(grade.getTown());
            double national = getVisibleScore(grade);

            Material mat;
            String podium;

            if (pos == 1) {
                mat = Material.NETHER_STAR;
                podium = "§6♛ Rang Hebdo I";
            } else if (pos == 2) {
                mat = Material.DIAMOND;
                podium = "§b♢ Rang Hebdo II";
            } else if (pos == 3) {
                mat = Material.EMERALD;
                podium = "§a♢ Rang Hebdo III";
            } else {
                mat = Material.GOLD_INGOT;
                podium = "§eRang Hebdo #" + pos;
            }

            ItemStack item = new ItemStack(mat);
            ItemMeta meta = item.getItemMeta();

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
                    "§7Staff : §e" + formatScore(staff) + "§7/50",
                    "§7Conseil des maires : §e" + formatScore(mayors) + "§7/50",
                    "§7Votes citoyens : §e" + formatScore(citizens) + "§7/50",
                    "",
                    "§7Note hebdomadaire : §e" + formatScore(national) + "§7/50",
                    "§7Subvention estimée : §a" + formatMoney(getPayout(national)) + "€",
                    "",
                    "§7Paiement : §6hebdomadaire ou staff",
                    "",
                    "§7Appréciation :",
                    getAppreciation(national)
            ));

            item.setItemMeta(meta);
            inv.setItem(slot, item);

            slot++;

            if (slot == 17) {
                slot = 19;
            }

            if (slot == 26) {
                slot = 28;
            }

            if (slot == 35) {
                slot = 37;
            }

            pos++;
        }

        if (top.isEmpty()) {

            ItemStack empty = new ItemStack(Material.BARRIER);
            ItemMeta meta = empty.getItemMeta();

            meta.setDisplayName("§c✖ Aucun classement");

            meta.setLore(List.of(
                    "§8----- §6Classement hebdomadaire §8-----",
                    "§7Aucune ville n'a encore",
                    "§7de projet validé cette semaine.",
                    "",
                    "§7Le classement attend",
                    "§7ses premières demandes validées."
            ));

            empty.setItemMeta(meta);
            inv.setItem(22, empty);
        }

        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta backMeta = back.getItemMeta();

        backMeta.setDisplayName("§c⬅ Retour");

        backMeta.setLore(List.of(
                "§8----- §6Commission Urbaine §8-----",
                "§7Retourner au menu",
                "§7de la commission."
        ));

        back.setItemMeta(backMeta);
        inv.setItem(49, back);

        p.openInventory(inv);
    }

    private static double getVisibleScore(TownGrade grade) {

        if (grade.isLocked()) {
            return grade.getFinalScore();
        }

        return NationalScoreCalculator.getFinalScore(grade.getTown());
    }

    private static String getState(TownGrade grade) {

        if (grade.isLocked()) {
            return "§6Votes clôturés";
        }

        return "§aVotes ouverts";
    }

    private static String getRank(double score) {

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

    private static int getPayout(double score) {

        if (score <= 15) {
            return 0;
        }

        if (score <= 20) {
            return 15000;
        }

        if (score <= 25) {
            return 35000;
        }

        if (score <= 30) {
            return 75000;
        }

        if (score <= 35) {
            return 125000;
        }

        if (score <= 40) {
            return 200000;
        }

        if (score <= 45) {
            return 350000;
        }

        if (score <= 49) {
            return 500000;
        }

        return 1000000;
    }

    private static String getAppreciation(double score) {

        if (score <= 15) {
            return "§7Dossier trop faible pour une subvention.";
        }

        if (score <= 20) {
            return "§7Projet recevable, soutien urbain limité.";
        }

        if (score <= 25) {
            return "§eVille en progression, dossier encourageant.";
        }

        if (score <= 30) {
            return "§aDéveloppement urbain sérieux et visible.";
        }

        if (score <= 35) {
            return "§bVille solide, projet bien intégré.";
        }

        if (score <= 40) {
            return "§bMétropole attractive, dossier confirmé.";
        }

        if (score <= 45) {
            return "§6Cité majeure, forte influence urbaine.";
        }

        if (score <= 49) {
            return "§6Capitale d'élite, projet remarquable.";
        }

        return "§e§lMerveille nationale inscrite au sommet de MoodCraft.";
    }

    private static String formatScore(double value) {

        return String.format("%.1f", value);
    }

    private static String formatMoney(int value) {

        return String.format("%,d", value).replace(",", " ");
    }
}