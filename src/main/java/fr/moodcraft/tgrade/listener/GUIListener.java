package fr.moodcraft.tgrade.listener;

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

public class GUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) {
            return;
        }

        if (e.getView()
                .getTitle()
                .startsWith("§8Visite")) {

            e.setCancelled(true);

            ItemStack item =
                    e.getCurrentItem();

            if (item == null) return;

            if (!item.hasItemMeta()) return;

            String build =
                    item.getItemMeta()
                            .getDisplayName()
                            .replace("§6", "");

            for (TownSubmission sub :
                    SubmissionStorage.getAll()) {

                if (!sub.getBuildName()
                        .equalsIgnoreCase(build)) {
                    continue;
                }

                if (sub.getStatus()
                        != SubmissionStatus.APPROVED) {
                    continue;
                }

                Location loc =
                        new Location(

                                Bukkit.getWorld(
                                        sub.getWorld()
                                ),

                                sub.getX() + 0.5,

                                sub.getY() + 1,

                                sub.getZ() + 0.5
                        );

                p.teleport(loc);

                p.sendMessage("");

                p.sendMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                );

                p.sendMessage(
                        "§6🧭 Téléportation"
                );

                p.sendMessage("");

                p.sendMessage(
                        "§7Projet: §e"
                                + sub.getBuildName()
                );

                p.sendMessage(
                        "§7Ville: §a"
                                + sub.getTown()
                );

                p.sendMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                );

                p.sendMessage("");

                p.closeInventory();

                return;
            }
        }
    }
}