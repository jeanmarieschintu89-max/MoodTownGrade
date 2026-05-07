package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.ReviewGUI;
import fr.moodcraft.tgrade.gui.RateGUI;

import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

public class UrbanismeMainListener
        implements Listener {

    //
    // 🏛 GUI CLICK
    //

    @EventHandler
    public void click(
            InventoryClickEvent e
    ) {

        //
        // 📛 TITLE
        //

        if (e.getView()
                .getTitle()
                .equalsIgnoreCase(
                        "§8🏛 Commission Urbaine"
                )) {

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
            // 🔘 SLOT
            //

            int slot =
                    e.getSlot();

            //
            // 🔊 SOUND
            //

            p.playSound(

                    p.getLocation(),

                    Sound.UI_BUTTON_CLICK,

                    1f,

                    1f
            );

            //
            // 📜 PROJETS
            //

            if (slot == 11) {

                p.closeInventory();

                p.performCommand(
                        "urbanisme projets"
                );

                return;
            }

            //
            // ➕ SOUMISSION
            //

            if (slot == 13) {

                p.closeInventory();

                p.sendMessage("");

                p.sendMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                );

                p.sendMessage(
                        "§a🏗 Soumission Urbaine"
                );

                p.sendMessage("");

                p.sendMessage(
                        "§7Utilise:"
                );

                p.sendMessage(
                        "§e/urbanisme projet <nom>"
                );

                p.sendMessage("");

                p.sendMessage(
                        "§7Place-toi devant"
                );

                p.sendMessage(
                        "§7la construction RP."
                );

                p.sendMessage("");

                p.sendMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                );

                p.sendMessage("");

                return;
            }

            //
            // 🏆 SCORE
            //

            if (slot == 15) {

                p.closeInventory();

                p.sendMessage("");

                p.sendMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                );

                p.sendMessage(
                        "§6🏆 Prestige Urbain"
                );

                p.sendMessage("");

                p.sendMessage(
                        "§7Le système de classement"
                );

                p.sendMessage(
                        "§7sera disponible bientôt."
                );

                p.sendMessage("");

                p.sendMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                );

                p.sendMessage("");

                return;
            }

            //
            // 🛰 ADMIN
            //

            if (slot == 31) {

                if (!p.hasPermission(
                        "moodtowngrade.staff"))
                    return;

                p.closeInventory();

                p.sendMessage("");

                p.sendMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                );

                p.sendMessage(
                        "§c🛰 Administration Urbaine"
                );

                p.sendMessage("");

                p.sendMessage(
                        "§e/urbanisme validation"
                );

                p.sendMessage(
                        "§7Projets en attente"
                );

                p.sendMessage("");

                p.sendMessage(
                        "§e/urbanisme review <ville>"
                );

                p.sendMessage(
                        "§7Inspection GUI"
                );

                p.sendMessage("");

                p.sendMessage(
                        "§e/urbanisme noter <ville>"
                );

                p.sendMessage(
                        "§7Notation RP"
                );

                p.sendMessage("");

                p.sendMessage(
                        "§e/urbanisme payout"
                );

                p.sendMessage(
                        "§7Versement hebdomadaire"
                );

                p.sendMessage("");

                p.sendMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                );

                p.sendMessage("");

            }
        }
    }
}