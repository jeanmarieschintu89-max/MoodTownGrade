package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.PendingProjectsGUI;

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

public class ProjectReviewListener
        implements Listener {

    //
    // 🏛 CLICK
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
                        "§8🏛 Inspection Projet"
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
        // 📄 INFO ITEM
        //

        if (e.getInventory()
                .getItem(13) == null)
            return;

        String projectName =
                e.getInventory()
                        .getItem(13)
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
                    .equalsIgnoreCase(projectName)) {

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
        // 🔘 SLOT
        //

        int slot =
                e.getSlot();

        //
        // 📍 TELEPORT
        //

        if (slot == 20) {

            if (Bukkit.getWorld(
                    found.getWorld()) == null) {

                p.sendMessage(
                        "§cMonde introuvable."
                );

                return;
            }

            Location loc =
                    new Location(

                            Bukkit.getWorld(
                                    found.getWorld()
                            ),

                            found.getX() + 0.5,

                            found.getY() + 1,

                            found.getZ() + 0.5
                    );

            p.teleport(loc);

            p.playSound(

                    p.getLocation(),

                    Sound.ENTITY_ENDERMAN_TELEPORT,

                    1f,

                    1f
            );

            p.sendMessage(
                    "§aTéléportation vers le projet."
            );

            return;
        }

        //
        // ✅ VALIDER
        //

        if (slot == 24) {

            found.setStatus(
                    SubmissionStatus.APPROVED
            );

            SubmissionStorage.save(found);

            p.closeInventory();

            p.playSound(

                    p.getLocation(),

                    Sound.UI_TOAST_CHALLENGE_COMPLETE,

                    1f,

                    1f
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            Bukkit.broadcastMessage(
                    "§a🏛 Projet Validé"
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Le projet §e"
                            + found.getBuildName()
            );

            Bukkit.broadcastMessage(
                    "§7de §b"
                            + found.getTown()
            );

            Bukkit.broadcastMessage(
                    "§7a été approuvé par"
            );

            Bukkit.broadcastMessage(
                    "§ala Commission Urbaine."
            );

            Bukkit.broadcastMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            Bukkit.broadcastMessage("");

            return;
        }

        //
        // ❌ REFUSER
        //

        if (slot == 26) {

            found.setStatus(
                    SubmissionStatus.REJECTED
            );

            SubmissionStorage.save(found);

            p.closeInventory();

            p.playSound(

                    p.getLocation(),

                    Sound.BLOCK_ANVIL_BREAK,

                    1f,

                    0.8f
            );

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§cProjet refusé"
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
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            return;
        }

        //
        // 🔙 RETOUR
        //

        if (slot == 40) {

            PendingProjectsGUI.open(p);

            p.playSound(

                    p.getLocation(),

                    Sound.UI_BUTTON_CLICK,

                    1f,

                    1f
            );
        }
    }
}