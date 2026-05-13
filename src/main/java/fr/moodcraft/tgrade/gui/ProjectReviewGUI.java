package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.gui.holder.ProjectReviewHolder;
import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownSubmission;
import fr.moodcraft.tgrade.util.MoodStyle;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProjectReviewGUI {

    public static final String TITLE =
            MoodStyle.PROJECT_REVIEW_TITLE;

    private static final int[] BORDER_SLOTS = {
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 17,
            18, 26,
            27, 35,
            36, 37, 38, 39, 41, 42, 43, 44
    };

    public static void open(
            Player p,
            TownSubmission submission
    ) {

        Inventory inv = Bukkit.createInventory(
                new ProjectReviewHolder(submission),
                45,
                TITLE
        );

        fillBorders(inv);

        String statusText =
                statusText(submission.getStatus());

        Material statusMaterial =
                statusMaterial(submission.getStatus());

        String date =
                new SimpleDateFormat("dd/MM/yyyy").format(
                        new Date(submission.getTimestamp())
                );

        inv.setItem(
                4,
                item(
                        Material.NETHER_STAR,
                        "§6✦ §fCommission Urbaine §6✦",
                        List.of(
                                "§7Inspection d'un projet",
                                "§7déposé par une ville.",
                                "",
                                "§8• §7Téléportation",
                                "§8• §7Validation",
                                "§8• §7Notation staff",
                                "§8• §7Clôture des votes",
                                "",
                                "§7État: " + statusText
                        )
                )
        );

        inv.setItem(
                13,
                item(
                        statusMaterial,
                        "§6✦ §f" + submission.getBuildName() + " §6✦",
                        List.of(
                                "§7Ville: §b" + submission.getTown(),
                                "§7Projet: §e" + submission.getBuildName(),
                                "§7État: " + statusText,
                                "",
                                "§7Position:",
                                "§8• §7" + submission.getWorld(),
                                "§8• §7X " + submission.getX()
                                        + "  Y " + submission.getY()
                                        + "  Z " + submission.getZ(),
                                "",
                                "§7Date: §e" + date,
                                "§7ID: §8" + submission.getId(),
                                "",
                                "§8• §7Dossier national"
                        )
                )
        );

        inv.setItem(
                20,
                item(
                        Material.ENDER_PEARL,
                        "§6✦ §fVoir le projet §6✦",
                        List.of(
                                "§7Téléporte le staff",
                                "§7à la zone déclarée.",
                                "",
                                "§8• §7Ville: §b" + submission.getTown(),
                                "§8• §7Projet: §e" + submission.getBuildName(),
                                "",
                                "§eInspecter sur place"
                        )
                )
        );

        inv.setItem(
                22,
                item(
                        Material.LIME_CONCRETE,
                        "§6✦ §fValider le dossier §6✦",
                        List.of(
                                "§7Valide la demande",
                                "§7de projet urbain.",
                                "",
                                "§8• §7Ouvre les votes",
                                "§8• §7Autorise la notation",
                                "§8• §7Classement hebdo",
                                "",
                                "§aOuvrir la phase de vote"
                        )
                )
        );

        inv.setItem(
                24,
                item(
                        Material.ENCHANTED_BOOK,
                        "§6✦ §fNotation staff §6✦",
                        List.of(
                                "§7Ouvre le barème",
                                "§7réservé au staff.",
                                "",
                                "§8• §7Ville: §b" + submission.getTown(),
                                "§8• §7Projet: §e" + submission.getBuildName(),
                                "§8• §7Score hebdo",
                                "",
                                "§eNoter ce dossier"
                        )
                )
        );

        inv.setItem(
                26,
                item(
                        Material.BEACON,
                        "§6✦ §fClôturer les votes §6✦",
                        List.of(
                                "§7Ferme la phase",
                                "§7de vote du dossier.",
                                "",
                                "§8• §7Note finale",
                                "§8• §7Subvention prête",
                                "§8• §7Notes bloquées",
                                "",
                                "§6Valider la clôture"
                        )
                )
        );

        inv.setItem(
                28,
                item(
                        Material.RED_CONCRETE,
                        "§6✦ §fRefuser le dossier §6✦",
                        List.of(
                                "§7Refuse la demande",
                                "§7de projet urbain.",
                                "",
                                "§8• §7Pas de notation",
                                "§8• §7Pas de classement",
                                "",
                                "§cRefuser la demande"
                        )
                )
        );

        inv.setItem(
                40,
                item(
                        Material.ARROW,
                        "§6✦ §fRetour §6✦",
                        List.of(
                                "§7Retour aux demandes",
                                "§7de projets urbains.",
                                "",
                                "§8• §7Commission Urbaine"
                        )
                )
        );

        p.openInventory(inv);
    }

    private static void fillBorders(
            Inventory inv
    ) {

        ItemStack glass =
                item(
                        Material.BLACK_STAINED_GLASS_PANE,
                        " ",
                        null
                );

        for (int slot : BORDER_SLOTS) {
            inv.setItem(slot, glass);
        }
    }

    private static ItemStack item(
            Material material,
            String name,
            List<String> lore
    ) {

        ItemStack item =
                new ItemStack(material);

        ItemMeta meta =
                item.getItemMeta();

        if (meta != null) {

            meta.setDisplayName(name);

            if (lore != null) {
                meta.setLore(lore);
            }

            meta.addItemFlags(
                    ItemFlag.HIDE_ADDITIONAL_TOOLTIP,
                    ItemFlag.HIDE_ATTRIBUTES,
                    ItemFlag.HIDE_ENCHANTS
            );

            item.setItemMeta(meta);
        }

        return item;
    }

    private static String statusText(
            SubmissionStatus status
    ) {

        if (status == SubmissionStatus.APPROVED) {
            return "§aValidé";
        }

        if (status == SubmissionStatus.REJECTED) {
            return "§cRefusé";
        }

        if (status == SubmissionStatus.FINISHED) {
            return "§6Clôturé";
        }

        return "§eEn examen";
    }

    private static Material statusMaterial(
            SubmissionStatus status
    ) {

        if (status == SubmissionStatus.APPROVED) {
            return Material.EMERALD;
        }

        if (status == SubmissionStatus.REJECTED) {
            return Material.REDSTONE_BLOCK;
        }

        if (status == SubmissionStatus.FINISHED) {
            return Material.BEACON;
        }

        return Material.GOLD_BLOCK;
    }
}
