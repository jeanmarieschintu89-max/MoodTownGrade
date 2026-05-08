package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.RateGUI;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;

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
                                "§8✦ Inspection Nationale",
                                ""
                        )
                        .trim();

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

            p.playSound(
                    p.getLocation(),
                    Sound.BLOCK_BEACON_ACTIVATE,
                    1f,
                    1f
            );

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

            //
            // ✅ APPROVED ONLY
            //

            if (sub.getStatus()
                    != SubmissionStatus.APPROVED) {
                continue;
            }

            //
            // 🏷️ NAME CHECK
            //

            if (!name.equals(
                    "§6" + sub.getBuildName())) {
                continue;
            }

            //
            // 🌍 WORLD CHECK
            //

            World world =
                    Bukkit.getWorld(
                            sub.getWorld()
                    );

            if (world == null) {

                p.sendMessage(
                        "§cMonde introuvable."
                );

                p.playSound(
                        p.getLocation(),
                        Sound.ENTITY_VILLAGER_NO,
                        1f,
                        1f
                );

                return;
            }

            //
            // 📍 LOCATION
            //

            Location loc =
                    new Location(

                            world,

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
            // ❌ CLOSE
            //

            p.closeInventory();

            return;
        }
    }
}