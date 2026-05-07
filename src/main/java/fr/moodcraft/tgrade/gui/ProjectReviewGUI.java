package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownSubmission;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

public class ProjectReviewGUI {

    //
    // 🏛 OPEN
    //

    public static void open(
            Player p,
            TownSubmission sub
    ) {

        Inventory inv =
                Bukkit.createInventory(

                        null,

                        45,

                        "§8✦ Inspection Nationale"
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

                36,37,38,39,41,42,43,44
        };

        for (int slot : borders) {

            inv.setItem(slot, glass);
        }

        //
        // 📊 STATUS
        //

        String status;

        Material statusMaterial;

        switch (sub.getStatus()) {

            case APPROVED -> {

                status =
                        "§a🟢 Projet validé";

                statusMaterial =
                        Material.EMERALD;
            }

            case REJECTED -> {

                status =
                        "§c🔴 Projet refusé";

                statusMaterial =
                        Material.REDSTONE_BLOCK;
            }

            default -> {

                status =
                        "§6🟡 Inspection en attente";

                statusMaterial =
                        Material.GOLD_BLOCK;
            }
        }

        //
        // 📅 DATE
        //

        String date =
                new SimpleDateFormat(
                        "dd/MM/yyyy"
                ).format(
                        new Date(
                                sub.getTimestamp()
                        )
                );

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
                "§6✦ Commission Nationale"
        );

        headerMeta.setLore(List.of(

                "§8━━━━━━━━━━━━━━━━",

                "§7Inspection officielle",

                "§7des constructions RP.",

                "",

                "§7Statut dossier:",
                status
        ));

        header.setItemMeta(headerMeta);

        inv.setItem(4, header);

        //
        // 📋 DOSSIER
        //

        ItemStack info =
                new ItemStack(
                        statusMaterial
                );

        ItemMeta infoMeta =
                info.getItemMeta();

        infoMeta.setDisplayName(
                "§f✦ §e" + sub.getBuildName()
        );

        infoMeta.setLore(List.of(

                "§8━━━━━━━━━━━━━━━━",

                "§7Ville inspectée:",
                "§b" + sub.getTown(),

                "",

                "§7Coordonnées terrain:",
                "§fX: §e" + sub.getX(),
                "§fY: §e" + sub.getY(),
                "§fZ: §e" + sub.getZ(),

                "",

                "§7Date dépôt:",
                "§f" + date,

                "",

                "§7Identifiant:",
                "§f#" + sub.getId(),

                "",

                "§7État administratif:",
                status
        ));

        info.setItemMeta(infoMeta);

        inv.setItem(13, info);

        //
        // 📍 INSPECTION
        //

        ItemStack tp =
                new ItemStack(
                        Material.ENDER_PEARL
                );

        ItemMeta tpMeta =
                tp.getItemMeta();

        tpMeta.setDisplayName(
                "§b📍 Inspection terrain"
        );

        tpMeta.setLore(List.of(

                "§8━━━━━━━━━━━━━━━━",

                "§7Téléportation vers",

                "§7la construction déclarée.",

                "",

                "§7Début de l'inspection",

                "§7urbaine officielle.",

                "",

                "§e▶ Lancer inspection"
        ));

        tp.setItemMeta(tpMeta);

        inv.setItem(20, tp);

        //
        // ✅ APPROUVER
        //

        ItemStack approve =
                new ItemStack(
                        Material.LIME_CONCRETE
                );

        ItemMeta approveMeta =
                approve.getItemMeta();

        approveMeta.setDisplayName(
                "§a✅ Accorder le permis"
        );

        approveMeta.setLore(List.of(

                "§8━━━━━━━━━━━━━━━━",

                "§7Valider officiellement",

                "§7la conformité du projet.",

                "",

                "§7Le dossier sera reconnu",

                "§7par la commission nationale.",

                "",

                "§a▶ Valider le projet"
        ));

        approve.setItemMeta(approveMeta);

        inv.setItem(24, approve);

        //
        // ❌ REFUSER
        //

        ItemStack reject =
                new ItemStack(
                        Material.RED_CONCRETE
                );

        ItemMeta rejectMeta =
                reject.getItemMeta();

        rejectMeta.setDisplayName(
                "§c❌ Classer le dossier"
        );

        rejectMeta.setLore(List.of(

                "§8━━━━━━━━━━━━━━━━",

                "§7Refuser le projet",

                "§7administrativement.",

                "",

                "§7Le dossier sera retiré",

                "§7des inspections urbaines.",

                "",

                "§c▶ Fermer le dossier"
        ));

        reject.setItemMeta(rejectMeta);

        inv.setItem(26, reject);

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
                "§c⬅ Retour administratif"
        );

        backMeta.setLore(List.of(

                "§8━━━━━━━━━━━━━━━━",

                "§7Retour au centre",

                "§7de gestion urbaine."
        ));

        back.setItemMeta(backMeta);

        inv.setItem(40, back);

        //
        // 🚀 OPEN
        //

        p.openInventory(inv);
    }
}