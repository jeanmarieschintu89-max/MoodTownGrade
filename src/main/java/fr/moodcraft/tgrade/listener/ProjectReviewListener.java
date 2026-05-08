package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.PendingProjectsGUI;
import fr.moodcraft.tgrade.gui.RateGUI;

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
                        "§8✦ Inspection Nationale"
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
        // 🛑 PLAYER INVENTORY
        //

        if (e.getRawSlot() >= e.getView()
                .getTopInventory()
                .getSize()) {

            return;
        }

        //
        // 📄 INFO ITEM
        //

        if (e.getInventory()
                .getItem(13) == null)
            return;

        //
        // 📛 PROJECT NAME
        //

        String projectName =
                e.getInventory()
                        .getItem(13)
                        .getItemMeta()
                        .getDisplayName()
                        .replace("§f✦ §e", "");

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

            p.playSound(

                    p.getLocation(),

                    Sound.ENTITY_VILLAGER_NO,

                    1f,

                    1f
            );

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
        // 📍 INSPECTION
        //

        if (slot == 20) {

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
            // 📜 MESSAGE
            //

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§a✦ Inspection Lancée"
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
                    "§7Téléportation vers la zone"
            );

            p.sendMessage(
                    "§7d'inspection du projet."
            );

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            return;
        }

        //
        // ✅ VALIDER
        //

        if (slot == 24) {

            //
            // 🔒 DÉJÀ VALIDÉ
            //

            if (found.getStatus()
                    == SubmissionStatus.APPROVED) {

                p.playSound(

                        p.getLocation(),

                        Sound.ENTITY_VILLAGER_NO,

                        1f,

                        1f
                );

                p.sendMessage("");

                p.sendMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                );

                p.sendMessage(
                        "§6✦ Projet Déjà Validé"
                );

                p.sendMessage("");

                p.sendMessage(
                        "§e" + found.getBuildName()
                );

                p.sendMessage(
                        "§7est déjà approuvé pour"
                );

                p.sendMessage(
                        "§bla ville de "
                                + found.getTown()
                );

                p.sendMessage("");

                p.sendMessage(
                        "§7Vous pouvez encore modifier"
                );

                p.sendMessage(
                        "§7les notes du projet."
                );

                p.sendMessage("");

                p.sendMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                );

                p.sendMessage("");

                //
                // ⭐ OPEN NOTES
                //

                RateGUI.open(
                        p,
                        found.getTown()
                );

                return;
            }

            //
            // ✅ VALIDATION
            //

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
                    "§a✦ Commission des Projets"
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§e" + found.getBuildName()
            );

            Bukkit.broadcastMessage(
                    "§7vient d'être accepté pour"
            );

            Bukkit.broadcastMessage(
                    "§bla ville de "
                            + found.getTown()
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Le projet pourra désormais"
            );

            Bukkit.broadcastMessage(
                    "§7être inspecté cette semaine."
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            Bukkit.broadcastMessage("");

            //
            // ⭐ OPEN RATE GUI
            //

            RateGUI.open(
                    p,
                    found.getTown()
            );

            return;
        }

        //
        // ❌ REFUSER
        //

        if (slot == 26) {

            //
            // 🗑 DELETE DIRECT
            //

            SubmissionStorage.delete(
                    found.getId()
            );

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
                    "§c✦ Projet Refusé"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§e" + found.getBuildName()
            );

            p.sendMessage(
                    "§7n'a pas été retenu pour"
            );

            p.sendMessage(
                    "§bla ville de "
                            + found.getTown()
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Le dossier a été fermé."
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

            p.playSound(

                    p.getLocation(),

                    Sound.UI_BUTTON_CLICK,

                    1f,

                    1f
            );

            PendingProjectsGUI.open(p);
        }
    }
}