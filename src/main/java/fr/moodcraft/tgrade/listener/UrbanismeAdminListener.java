package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.PendingProjectsGUI;
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
                        "§8✦ Centre National"
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
                instanceof Player p)) {
            return;
        }

        //
        // 📦 INVENTORY
        //

        if (e.getClickedInventory() == null) {
            return;
        }

        //
        // 📦 ITEM
        //

        if (e.getCurrentItem() == null) {
            return;
        }

        //
        // ❌ AIR
        //

        if (e.getCurrentItem().getType().isAir()) {
            return;
        }

        //
        // 🔘 SLOT
        //

        int slot =
                e.getRawSlot();

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
        // 📋 INSPECTIONS
        //

        if (slot == 13) {

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

            PendingProjectsGUI.open(p);

            return;
        }

        //
        // 💰 PAYOUT
        //

        if (slot == 31) {

            //
            // 🔊 SOUND
            //

            p.playSound(

                    p.getLocation(),

                    Sound.ENTITY_PLAYER_LEVELUP,

                    1f,

                    0.8f
            );

            //
            // 🔒 CLOSE
            //

            p.closeInventory();

            //
            // 📜 MESSAGE
            //

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§2✦ Distribution Nationale"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Initialisation des aides"
            );

            p.sendMessage(
                    "§7municipales MoodCraft."
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Seules les villes avec"
            );

            p.sendMessage(
                    "§e25/50 §7minimum recevront"
            );

            p.sendMessage(
                    "§7un financement."
            );

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            //
            // 💰 EXECUTE
            //

            p.performCommand(
                    "urbanisme payout"
            );

            return;
        }

        //
        // 🏆 CLASSEMENT
        //

        if (slot == 33) {

            //
            // 🔊 SOUND
            //

            p.playSound(

                    p.getLocation(),

                    Sound.UI_TOAST_CHALLENGE_COMPLETE,

                    1f,

                    1f
            );

            //
            // 🚀 EXECUTE
            //

            p.performCommand(
                    "urbanisme classement"
            );

            return;
        }

        //
        // 🔙 RETOUR
        //

        if (slot == 40) {

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
            // 🚀 OPEN
            //

            UrbanismeMainGUI.open(p);
        }
    }
}