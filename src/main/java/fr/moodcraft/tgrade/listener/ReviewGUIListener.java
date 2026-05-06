package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.RateGUI;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.inventory.ItemStack;

public class ReviewGUIListener implements Listener {

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
        // 🏙️ TOWN
        //

        String town =
                e.getView()
                        .getTitle()
                        .replace(
                                "§8✦ §bMoodCraft §8• §7Inspection",
                                ""
                        )
                        .trim();

        //
        // ❌ CLOSE
        //

        if (name.equals(
                "§cFermer l'inspection")) {

            p.closeInventory();

            return;
        }

        //
        // 📊 NOTATION
        //

        if (name.equals(
                "§6Commission d'évaluation")) {

            RateGUI.open(
                    p,
                    town
            );

            return;
        }

        //
        // 🏗️ BUILD TELEPORT
        //

        for (TownSubmission sub :
                SubmissionStorage.getTown(town)) {

            if (sub.getStatus()
                    != SubmissionStatus.APPROVED) {
                continue;
            }

            if (!name.equals(
                    "§6" + sub.getBuildName())) {
                continue;
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
            // ✨ TP
            //

            p.teleport(loc);

            //
            // 📢 MESSAGE RP
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
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            p.closeInventory();

            return;
        }
    }
}