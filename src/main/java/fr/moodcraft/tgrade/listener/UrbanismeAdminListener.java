package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.UrbanismeMainGUI;

import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

public class UrbanismeAdminListener
        implements Listener {

    //
    // 🛰 CLICK
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
                        "§8🛰 Centre Administratif"
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
        // 📋 DOSSIERS
        //

        if (slot == 10) {

            p.closeInventory();

            p.performCommand(
                    "urbanisme validation"
            );

            return;
        }

        //
        // ⭐ NOTATION
        //

        if (slot == 12) {

            p.closeInventory();

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§6⭐ Notation RP"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Utilise:"
            );

            p.sendMessage(
                    "§e/urbanisme noter <ville>"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            return;
        }

        //
        // 📍 INSPECTION
        //

        if (slot == 14) {

            p.closeInventory();

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§a📍 Inspection Terrain"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Utilise:"
            );

            p.sendMessage(
                    "§e/urbanisme review <ville>"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            return;
        }

        //
        // ✅ VALIDATION
        //

        if (slot == 16) {

            p.closeInventory();

            p.performCommand(
                    "urbanisme validation"
            );

            return;
        }

        //
        // 💰 PAYOUT
        //

        if (slot == 31) {

            p.closeInventory();

            p.performCommand(
                    "urbanisme payout"
            );

            return;
        }

        //
        // 🏆 CLASSEMENT
        //

        if (slot == 33) {

            p.closeInventory();

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§b🏆 Classement National"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Le classement RP"
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
        // 🔙 RETOUR
        //

        if (slot == 40) {

            p.playSound(

                    p.getLocation(),

                    Sound.BLOCK_CHEST_CLOSE,

                    1f,

                    1f
            );

            UrbanismeMainGUI.open(p);
        }
    }
}