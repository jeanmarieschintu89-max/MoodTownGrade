package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.ClassementGUI;
import fr.moodcraft.tgrade.gui.PendingProjectsGUI;
import fr.moodcraft.tgrade.gui.RateGUI;
import fr.moodcraft.tgrade.gui.UrbanismeAdminGUI;
import fr.moodcraft.tgrade.gui.UrbanismeMainGUI;

import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

public class UrbanismeAdminListener
        implements Listener {

    @EventHandler
    public void click(
            InventoryClickEvent e
    ) {

        //
        // 🌌 GUI CHECK
        //

        if (!e.getView()
                .getTitle()
                .equals("§8✦ Centre National")) {
            return;
        }

        e.setCancelled(true);

        //
        // ❌ PLAYER
        //

        if (!(e.getWhoClicked()
                instanceof Player p)) {
            return;
        }

        //
        // ❌ NULL
        //

        if (e.getCurrentItem() == null) {
            return;
        }

        int slot =
                e.getRawSlot();

        //
        // 📋 PROJETS
        //

        if (slot == 13) {

            p.playSound(

                    p.getLocation(),

                    Sound.UI_BUTTON_CLICK,

                    1f,

                    1.2f
            );

            PendingProjectsGUI.open(p);

            return;
        }

        //
        // 📝 NOTATION
        //

        if (slot == 22) {

            p.playSound(

                    p.getLocation(),

                    Sound.UI_BUTTON_CLICK,

                    1f,

                    1.1f
            );

            RateGUI.open(p);

            return;
        }

        //
        // 💰 PAYOUT
        //

        if (slot == 31) {

            p.playSound(

                    p.getLocation(),

                    Sound.BLOCK_BEACON_ACTIVATE,

                    1f,

                    1f
            );

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§2✦ Distribution Nationale"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Les financements municipaux"
            );

            p.sendMessage(
                    "§7seront bientôt disponibles."
            );

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            return;
        }

        //
        // 🏆 CLASSEMENT
        //

        if (slot == 33) {

            p.playSound(

                    p.getLocation(),

                    Sound.UI_TOAST_CHALLENGE_COMPLETE,

                    1f,

                    1f
            );

            ClassementGUI.open(p);

            return;
        }

        //
        // 🔙 RETURN
        //

        if (slot == 40) {

            p.playSound(

                    p.getLocation(),

                    Sound.UI_BUTTON_CLICK,

                    1f,

                    0.8f
            );

            UrbanismeMainGUI.open(p);
        }
    }
}