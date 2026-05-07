package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownSubmission;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

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

                        "§8🏛 Inspection Projet"
                );

        //
        // 🌌 FILL
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

        for (int i = 0; i < 45; i++) {

            inv.setItem(i, glass);
        }

        //
        // 📋 INFO PROJET
        //

        ItemStack info =
                new ItemStack(
                        Material.WRITABLE_BOOK
                );

        ItemMeta infoMeta =
                info.getItemMeta();

        infoMeta.setDisplayName(
                "§e" + sub.getBuildName()
        );

        String status;

        switch (sub.getStatus()) {

            case APPROVED ->
                    status = "§aAPPROUVÉ";

            case REJECTED ->
                    status = "§cREFUSÉ";

            default ->
                    status = "§6EN ATTENTE";
        }

        infoMeta.setLore(List.of(

                "§8━━━━━━━━━━━━━━━━",

                "§7Ville:",
                "§b" + sub.getTown(),

                "",

                "§7Coordonnées:",
                "§fX: §e" + sub.getX(),
                "§fY: §e" + sub.getY(),
                "§fZ: §e" + sub.getZ(),

                "",

                "§7Statut:",
                status,

                "",

                "§7ID:",
                "§f" + sub.getId()
        ));

        info.setItemMeta(infoMeta);

        inv.setItem(13, info);

        //
        // 📍 TELEPORT
        //

        ItemStack tp =
                new ItemStack(
                        Material.ENDER_PEARL
                );

        ItemMeta tpMeta =
                tp.getItemMeta();

        tpMeta.setDisplayName(
                "§b📍 Téléportation"
        );

        tpMeta.setLore(List.of(

                "§7Téléportation vers",
                "§7le projet urbain.",

                "",

                "§e▶ Inspecter"
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
                "§a✅ Approuver"
        );

        approveMeta.setLore(List.of(

                "§7Valider officiellement",
                "§7le projet urbain.",

                "",

                "§e▶ Accorder permis"
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
                "§c❌ Refuser"
        );

        rejectMeta.setLore(List.of(

                "§7Refuser le projet",
                "§7administrativement.",

                "",

                "§e▶ Refuser dossier"
        ));

        reject.setItemMeta(rejectMeta);

        inv.setItem(26, reject);

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
                "§cRetour"
        );

        back.setItemMeta(backMeta);

        inv.setItem(40, back);

        //
        // 🚀 OPEN
        //

        p.openInventory(inv);
    }
}