package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.GradeManager;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownGrade;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ReviewGUI {

    //
    // 🏛 OPEN
    //

    public static void open(
            Player p,
            String town
    ) {

        TownGrade grade =
                GradeManager.get(town);

        Inventory inv =
                Bukkit.createInventory(
                        null,
                        54,
                        "§8✦ Suivi Urbain"
                );

        List<TownSubmission> list =
                SubmissionStorage.getTown(town);

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

        for (int slot : borders) {

            inv.setItem(slot, glass);
        }

        //
        // 🏛 HEADER
        //

        ItemStack header =
                new ItemStack(
                        Material.NETHER_STAR
                );

        ItemMeta headerMeta =
                header.getItemMeta();

        headerMeta.setDisplayName(
                "§6✦ Suivi Urbain"
        );

        headerMeta.setLore(List.of(

                "§8----- §6Registre hebdomadaire §8-----",

                "§7Ville suivie : §b"
                        + town,

                "",

                "§7Ce menu rassemble les projets",

                "§7validés et les notes liées",

                "§7au classement hebdomadaire.",

                "",

                "§7Note actuelle :",

                grade.getFormattedScore(),

                "§7Classement actuel :",

                grade.getRank(),

                "",

                "§e▶ Dossier urbain actif"
        ));

        header.setItemMeta(headerMeta);

        inv.setItem(4, header);

        //
        // 📦 SLOT
        //

        int slot = 10;

        //
        // 📋 DOSSIERS
        //

        for (TownSubmission sub : list) {

            if (sub.getStatus()
                    != SubmissionStatus.APPROVED) {

                continue;
            }

            if (slot >= 44) {
                break;
            }

            ItemStack item =
                    new ItemStack(
                            Material.WRITABLE_BOOK
                    );

            ItemMeta meta =
                    item.getItemMeta();

            meta.setDisplayName(
                    "§f✦ §e"
                            + sub.getBuildName()
            );

            List<String> lore =
                    new ArrayList<>();

            lore.add(
                    "§8----- §6Projet validé §8-----"
            );

            lore.add(
                    "§7Ville : §b" + sub.getTown()
            );

            lore.add(
                    "§7Projet : §f" + sub.getBuildName()
            );

            lore.add(
                    "§7Statut : §aDemande validée"
            );

            lore.add("");

            lore.add(
                    "§7Ce projet participe"
            );

            lore.add(
                    "§7au classement hebdomadaire."
            );

            lore.add("");

            lore.add(
                    "§7Position du projet :"
            );

            lore.add(
                    "§fX §e" + sub.getX()
                            + " §8| §fY §e" + sub.getY()
                            + " §8| §fZ §e" + sub.getZ()
            );

            lore.add("");

            lore.add(
                    "§b▶ Se téléporter au projet"
            );

            meta.setLore(lore);

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
        // 📊 RAPPORT GLOBAL
        //

        ItemStack report =
                new ItemStack(
                        Material.EMERALD_BLOCK
                );

        ItemMeta reportMeta =
                report.getItemMeta();

        reportMeta.setDisplayName(
                "§a✦ Rapport de la ville"
        );

        reportMeta.setLore(List.of(

                "§8----- §6Évaluation actuelle §8-----",

                "§7Ville : §b" + town,

                "",

                "§7Les notes affichées concernent",

                "§7la ville et ses projets",

                "§7en développement.",

                "",

                "§7Architecture : §e"
                        + grade.getArchitecture()
                        + "§7/10",

                "§7Cohérence : §e"
                        + grade.getStyle()
                        + "§7/6",

                "§7Activité : §e"
                        + grade.getActivite()
                        + "§7/8",

                "§7Banque : §e"
                        + grade.getBanque()
                        + "§7/4",

                "§7Urbanisme : §e"
                        + grade.getRemarquable()
                        + "§7/8",

                "§7RolePlay : §e"
                        + grade.getRp()
                        + "§7/6",

                "",

                "§7Note hebdomadaire :",

                grade.getFormattedScore()
        ));

        report.setItemMeta(reportMeta);

        inv.setItem(49, report);

        //
        // ⭐ NOTATION
        //

        ItemStack notation =
                new ItemStack(
                        Material.BEACON
                );

        ItemMeta notationMeta =
                notation.getItemMeta();

        notationMeta.setDisplayName(
                "§6✦ Notation staff"
        );

        notationMeta.setLore(List.of(

                "§8----- §6Commission Urbaine §8-----",

                "§7Ouvre la notation staff",

                "§7pour cette ville et son",

                "§7projet en cours.",

                "",

                "§7La note compte pour",

                "§7le classement hebdomadaire.",

                "",

                "§6▶ Ouvrir la notation"
        ));

        notation.setItemMeta(notationMeta);

        inv.setItem(45, notation);

        //
        // ❌ FERMER
        //

        ItemStack close =
                new ItemStack(
                        Material.BARRIER
                );

        ItemMeta closeMeta =
                close.getItemMeta();

        closeMeta.setDisplayName(
                "§c⬅ Fermer le suivi"
        );

        closeMeta.setLore(List.of(

                "§8----- §6Commission Urbaine §8-----",

                "§7Retour au centre",

                "§7administratif."
        ));

        close.setItemMeta(closeMeta);

        inv.setItem(53, close);

        p.openInventory(inv);
    }
}