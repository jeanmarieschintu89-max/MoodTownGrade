package fr.moodcraft.tgrade.listener;

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

import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) {
            return;
        }

        //
        // 🏛️ GUI CHECK
        //

        if (!e.getView()
                .getTitle()
                .contains("Inspection")) {
            return;
        }

        e.setCancelled(true);

        ItemStack item =
                e.getCurrentItem();

        if (item == null) return;

        if (!item.hasItemMeta()) return;

        String name =
                item.getItemMeta()
                        .getDisplayName();

        //
        // ❌ CLOSE
        //

        if (name.equals(
                "§cFermer l'inspection")) {

            p.closeInventory();

            p.playSound(
                    p.getLocation(),
                    Sound.UI_BUTTON_CLICK,
                    1f,
                    1f
            );

            return;
        }

        //
        // 📊 NOTATION
        //

        if (name.equals(
                "§6Commission d'évaluation")) {

            String town =
                    item.getItemMeta()
                            .getLore()
                            == null
                            ? ""
                            : "";

            String guiTown =
                    e.getView()
                            .getTitle();

            RateGUI.open(
                    p,
                    guiTown
            );

            p.playSound(
                    p.getLocation(),
                    Sound.BLOCK_BEACON_ACTIVATE,
                    1f,
                    1f
            );

            return;
        }

        //
        // 📊 RAPPORT
        //

        if (name.equals(
                "§aRapport urbain")) {

            p.playSound(
                    p.getLocation(),
                    Sound.UI_BUTTON_CLICK,
                    1f,
                    1.2f
            );

            return;
        }

        //
        // 🏗️ BUILD TP
        //

        for (TownSubmission sub :
                SubmissionStorage.getAll()) {

            if (sub.getStatus()
                    != SubmissionStatus.APPROVED) {
                continue;
            }

            if (!name.equals(
                    "§6" + sub.getBuildName())) {
                continue;
            }

            //
            // 🌍 WORLD CHECK
            //

            if (Bukkit.getWorld(
                    sub.getWorld()) == null) {

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
            // 🏛️ MESSAGE RP
            //

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§b🏛 Commission Urbaine"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Inspection du projet:"
            );

            p.sendMessage(
                    " §e" + sub.getBuildName()
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Ville: §b"
                            + sub.getTown()
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Coordonnées:"
            );

            p.sendMessage(
                    " §fX: §e" + sub.getX()
            );

            p.sendMessage(
                    " §fY: §e" + sub.getY()
            );

            p.sendMessage(
                    " §fZ: §e" + sub.getZ()
            );

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            p.closeInventory();

            return;
        }
    }
}