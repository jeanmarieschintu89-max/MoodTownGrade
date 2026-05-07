package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import fr.moodcraft.tgrade.gui.UrbanismeAdminGUI;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

public class PendingProjectsListener
        implements Listener {

    //
    // 📋 CLICK
    //

    @EventHandler
    public void click(
            InventoryClickEvent e
    ) {

        //
        // 📛 TITLE
        //

        if (!e.getView()
                .getTitle()
                .equalsIgnoreCase(
                        "§8📋 Projets Urbains"
                )) {
            return;
        }

        //
        // ❌ CANCEL
        //

        e.setCancelled(true);

        //
        // 👤 PLAYER
        //

        if (!(e.getWhoClicked()
                instanceof Player p))
            return;

        //
        // 📦 NULL
        //

        if (e.getCurrentItem() == null)
            return;

        //
        // 🔙 RETOUR
        //

        if (e.getSlot() == 49) {

            p.playSound(

                    p.getLocation(),

                    Sound.UI_BUTTON_CLICK,

                    1f,

                    1f
            );

            UrbanismeAdminGUI.open(p);

            return;
        }

        //
        // 📛 ITEM NAME
        //

        String name =
                e.getCurrentItem()
                        .getItemMeta()
                        .getDisplayName()
                        .replace("§e", "");

        //
        // 🔍 FIND
        //

        TownSubmission found = null;

        for (TownSubmission sub :
                SubmissionStorage.getAll()) {

            if (sub.getBuildName()
                    .equalsIgnoreCase(name)

                    && sub.getStatus()
                    == SubmissionStatus.PENDING) {

                found = sub;

                break;
            }
        }

        //
        // ❌ NOT FOUND
        //

        if (found == null) {

            p.sendMessage(
                    "§cProjet introuvable."
            );

            return;
        }

        //
        // 🌍 WORLD
        //

        if (Bukkit.getWorld(
                found.getWorld()) == null) {

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
                                found.getWorld()
                        ),

                        found.getX() + 0.5,

                        found.getY() + 1,

                        found.getZ() + 0.5
                );

        //
        // 🚀 TP
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
        // 📜 INFO
        //

        p.sendMessage("");

        p.sendMessage(
                "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        );

        p.sendMessage(
                "§b🏛 Inspection Urbaine"
        );

        p.sendMessage("");

        p.sendMessage(
                "§7Projet: §e"
                        + found.getBuildName()
        );

        p.sendMessage(
                "§7Ville: §b"
                        + found.getTown()
        );

        p.sendMessage("");

        p.sendMessage(
                "§7Coordonnées:"
        );

        p.sendMessage(
                " §fX: §e" + found.getX()
        );

        p.sendMessage(
                " §fY: §e" + found.getY()
        );

        p.sendMessage(
                " §fZ: §e" + found.getZ()
        );

        p.sendMessage("");

        p.sendMessage(
                "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        );

        p.sendMessage("");
    }
}