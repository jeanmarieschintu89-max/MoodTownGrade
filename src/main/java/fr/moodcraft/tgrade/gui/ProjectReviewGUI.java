package fr.moodcraft.tgrade.gui;

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
    // 🛰 OPEN
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
        // 📄 INFOS
        //

        ItemStack info =
                new ItemStack(
                        Material.WRITABLE_BOOK
                );

        ItemMeta infoMeta =
                info.getItemMeta();

        infoMeta.setDisplayName(
                "§e"
                        + sub.getBuildName()
        );

        infoMeta.setLore(List.of(

                "§8━━━━━━━━━━━━━━━━",

                "§7Ville: §b"
                        + sub.getTown(),

                "",

                "§7ID: §f"
                        + sub.getId(),

                "",

                "§7📍 "
                        + sub.getX()
                        + " "
                        + sub.getY()
                        + " "
                        + sub.getZ(),

                "",

                "§7Statut: §eEN ÉTUDE"
        ));

        info.setItemMeta(infoMeta);

        inv.setItem(13, info);

        //
        // 📍 TP
        //

        ItemStack tp =
                new ItemStack(
                        Material.ENDER_PEARL
                );

        ItemMeta tpMeta =
                tp.getItemMeta();

        tpMeta.setDisplayName(
                "§bTéléportation"
        );

        tpMeta.setLore(List.of(

                "§7Inspection terrain",
                "",
                "§a▶ Cliquer"
        ));

        tp.setItemMeta(tpMeta);

        inv.setItem(20, tp);

        //
        // ✅ ACCEPTER
        //

        ItemStack accept =
                new ItemStack(
                        Material.LIME_CONCRETE
                );

        ItemMeta acceptMeta =
                accept.getItemMeta();

        acceptMeta.setDisplayName(
                "§aValider Projet"
        );

        acceptMeta.setLore(List.of(

                "§7Accorder le permis",
                "",
                "§a▶ Validation"
        ));

        accept.setItemMeta(acceptMeta);

        inv.setItem(24, accept);

        //
        // ❌ REFUSER
        //

        ItemStack deny =
                new ItemStack(
                        Material.RED_CONCRETE
                );

        ItemMeta denyMeta =
                deny.getItemMeta();

        denyMeta.setDisplayName(
                "§cRefuser Projet"
        );

        denyMeta.setLore(List.of(

                "§7Refus administratif",
                "",
                "§c▶ Refuser"
        ));

        deny.setItemMeta(denyMeta);

        inv.setItem(26, deny);

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