package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.RateGUI;
import fr.moodcraft.tgrade.gui.UrbanismeAdminGUI;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.inventory.ItemStack;

public class GUIListener
        implements Listener {

    //
    // 🎮 CLICK
    //

    @EventHandler
    public void onClick(
            InventoryClickEvent e
    ) {

        //
        // 👤 PLAYER
        //

        if (!(e.getWhoClicked()
                instanceof Player p)) {

            return;
        }

        //
        // 📛 TITLE
        //

        String title =
                e.getView().getTitle();

        //
        // 🏛 INSPECTION ONLY
        //

        if (!title.contains(
                "Inspection")) {

            return;
        }

        //
        // ❌ CANCEL
        //

        e.setCancelled(true);

        //
        // 📦 INVENTORY
        //

        if (e.getClickedInventory() == null) {
            return;
        }

        //
        // 📦 ITEM
        //

        ItemStack item =
                e.getCurrentItem();

        if (item == null) {
            return;
        }

        if (!item.hasItemMeta()) {
            return;
        }

        if (item.getItemMeta()
                .getDisplayName() == null) {

            return;
        }

        //
        // ❌ AIR
        //

        if (item.getType().isAir()) {
            return;
        }

        //
        // 📛 NAME
        //

        String name =
                item.getItemMeta()
                        .getDisplayName();

        //
        // 🔊 DEFAULT SOUND
        //

        p.playSound(

                p.getLocation(),

                Sound.UI_BUTTON_CLICK,

                1f,

                1f
        );

        //
        // ❌ CLOSE
        //

        if (name.equals(
                "§c⬅ Fermer l'Inspection"
        )) {

            //
            // 🔊 SOUND
            //

            p.playSound(

                    p.getLocation(),

                    Sound.BLOCK_CHEST_CLOSE,

                    1f,

                    1f
            );

            //
            // 🚀 RETURN
            //

            UrbanismeAdminGUI.open(p);

            return;
        }

        //
        // ⭐ NOTATION
        //

        if (name.equals(
                "§6⭐ Commission d'Évaluation"
        )) {

            //
            // 🏛 GET TOWN
            //

            String town =
                    title.replace(
                            "§8✦ Inspection Nationale",
                            ""
                    ).trim();

            //
            // 🔊 SOUND
            //

            p.playSound(

                    p.getLocation(),

                    Sound.BLOCK_BEACON_ACTIVATE,

                    1f,

                    1.2f
            );

            //
            // 🚀 OPEN
            //

            RateGUI.open(
                    p,
                    town
            );

            return;
        }

        //
        // 📊 RAPPORT
        //

        if (name.equals(
                "§a✦ Rapport National"
        )) {

            //
            // 🔊 SOUND
            //

            p.playSound(

                    p.getLocation(),

                    Sound.UI_TOAST_IN,

                    1f,

                    1f
            );

            return;
        }

        //
        // 🏗 BUILD INSPECTION
        //

        for (TownSubmission sub :
                SubmissionStorage.getAll()) {

            //
            // ✅ APPROVED ONLY
            //

            if (sub.getStatus()
                    != SubmissionStatus.APPROVED) {

                continue;
            }

            //
            // 📛 NAME CHECK
            //

            if (!name.equals(
                    "§e✦ "
                            + sub.getBuildName()
            )) {

                continue;
            }

            //
            // 🌍 WORLD CHECK
            //

            if (Bukkit.getWorld(
                    sub.getWorld()
            ) == null) {

                p.sendMessage(
                        "§cMonde introuvable."
                );

                return;
            }

            //
            // 📍 LOCATION
            //

            Location loc =
                    new Location(

                            Bukkit.getWorld(
                                    sub.getWorld()
                            ),

                            sub.getX() + 0.5,

                            sub.getY() + 1,

                            sub.getZ() + 0.5
                    );

            //
            // ✨ TELEPORT
            //

            p.teleport(loc);

            //
            // 🔊 SOUND
            //

            p.playSound(

                    p.getLocation(),

                    Sound.ENTITY_ENDERMAN_TELEPORT,

                    1f,

                    1f
            );

            //
            // 📜 MESSAGE
            //

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§6✦ Inspection Nationale"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Projet inspecté:"
            );

            p.sendMessage(
                    "§e" + sub.getBuildName()
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Ville:"
            );

            p.sendMessage(
                    "§b" + sub.getTown()
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Coordonnées:"
            );

            p.sendMessage(
                    "§fX: §e" + sub.getX()
            );

            p.sendMessage(
                    "§fY: §e" + sub.getY()
            );

            p.sendMessage(
                    "§fZ: §e" + sub.getZ()
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Inspection terrain active."
            );

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            //
            // 🔒 CLOSE
            //

            p.closeInventory();

            return;
        }
    }
}