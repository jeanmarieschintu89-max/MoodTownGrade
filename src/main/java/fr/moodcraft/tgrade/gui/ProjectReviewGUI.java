package fr.moodcraft.tgrade.gui;

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

        String status;

        Material statusMaterial;

        switch (sub.getStatus()) {

            case APPROVED -> {

                status =
                        "§a✔ Demande validée";

                statusMaterial =
                        Material.EMERALD;
            }

            case REJECTED -> {

                status =
                        "§c✖ Demande refusée";

                statusMaterial =
                        Material.REDSTONE_BLOCK;
            }

            default -> {

                status =
                        "§6⌛ Demande en examen";

                statusMaterial =
                        Material.GOLD_BLOCK;
            }
        }

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
                "§6✦ Commission Urbaine"
        );

        headerMeta.setLore(List.of(

                "§8----- §6Inspection administrative §8-----",

                "§7Contrôle d'une demande",

                "§7de projet urbain déposée",

                "§7par une ville.",

                "",

                "§7Rôle du staff :",

                "§8• §fse téléporter au projet",

                "§8• §avalider la demande",

                "§8• §crefuser la demande",

                "§8• §bnoter pendant la phase ouverte",

                "§8• §6clôturer les votes",

                "",

                "§7Statut : " + status
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

                "§8----- §6Demande de projet §8-----",

                "§7Ville : §b" + sub.getTown(),

                "§7Projet : §f" + sub.getBuildName(),

                "",

                "§7Coordonnées du projet :",

                "§fX §e" + sub.getX()
                        + " §8| §fY §e" + sub.getY()
                        + " §8| §fZ §e" + sub.getZ(),

                "",

                "§7Dépôt : §f" + date,

                "§7Identifiant : §f#" + sub.getId(),

                "",

                "§7État du dossier :",

                status,

                "",

                "§7Une fois validée, cette demande",

                "§7ouvre la notation publique et",

                "§7le suivi du classement hebdomadaire."
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
                "§b📍 Se téléporter au projet"
        );

        tpMeta.setLore(List.of(

                "§8----- §6Inspection terrain §8-----",

                "§7Ville : §b" + sub.getTown(),

                "§7Projet : §f" + sub.getBuildName(),

                "",

                "§7Téléporte le staff vers",

                "§7la position déclarée du projet.",

                "",

                "§e▶ Inspecter sur place"
        ));

        tp.setItemMeta(tpMeta);

        inv.setItem(20, tp);

        //
        // ✅ VALIDER DEMANDE
        //

        ItemStack approve =
                new ItemStack(
                        Material.LIME_CONCRETE
                );

        ItemMeta approveMeta =
                approve.getItemMeta();

        approveMeta.setDisplayName(
                "§a✔ Valider la demande"
        );

        approveMeta.setLore(List.of(

                "§8----- §6Validation staff §8-----",

                "§7Ville : §b" + sub.getTown(),

                "§7Projet : §f" + sub.getBuildName(),

                "",

                "§7Valide la demande de projet.",

                "§7Le dossier rejoint alors",

                "§7la phase de notation publique.",

                "",

                "§7Citoyens, maires et staff",

                "§7pourront participer au classement.",

                "",

                "§a▶ Ouvrir les votes"
        ));

        approve.setItemMeta(approveMeta);

        inv.setItem(22, approve);

        //
        // ⭐ NOTATION STAFF
        //

        ItemStack note =
                new ItemStack(
                        Material.ENCHANTED_BOOK
                );

        ItemMeta noteMeta =
                note.getItemMeta();

        noteMeta.setDisplayName(
                "§b✦ Notation staff"
        );

        noteMeta.setLore(List.of(

                "§8----- §6Évaluation interne §8-----",

                "§7Ville : §b" + sub.getTown(),

                "§7Projet : §f" + sub.getBuildName(),

                "",

                "§7Ouvre le barème de notation",

                "§7réservé au staff.",

                "",

                "§7Cette note compte dans",

                "§7le classement hebdomadaire.",

                "",

                "§b▶ Noter le dossier"
        ));

        note.setItemMeta(noteMeta);

        inv.setItem(24, note);

        //
        // 🔒 CLÔTURE VOTES
        //

        ItemStack close =
                new ItemStack(
                        Material.BEACON
                );

        ItemMeta closeMeta =
                close.getItemMeta();

        closeMeta.setDisplayName(
                "§6✔ Clôturer les votes"
        );

        closeMeta.setLore(List.of(

                "§8----- §6Fin de notation §8-----",

                "§7Ville : §b" + sub.getTown(),

                "§7Projet : §f" + sub.getBuildName(),

                "",

                "§7Ferme la phase de vote",

                "§7pour ce dossier urbain.",

                "",

                "§7Après clôture, les notes",

                "§7ne peuvent plus être modifiées.",

                "",

                "§6▶ Valider la clôture"
        ));

        close.setItemMeta(closeMeta);

        inv.setItem(26, close);

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
                "§c✖ Refuser la demande"
        );

        rejectMeta.setLore(List.of(

                "§8----- §6Décision staff §8-----",

                "§7Ville : §b" + sub.getTown(),

                "§7Projet : §f" + sub.getBuildName(),

                "",

                "§7Refuse la demande de projet.",

                "§7Le dossier ne rejoint pas",

                "§7la notation publique.",

                "",

                "§c▶ Refuser le dossier"
        ));

        reject.setItemMeta(rejectMeta);

        inv.setItem(28, reject);

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

                "§7Retour aux demandes",

                "§7de projets urbains."
        ));

        back.setItemMeta(backMeta);

        inv.setItem(40, back);

        p.openInventory(inv);
    }
}