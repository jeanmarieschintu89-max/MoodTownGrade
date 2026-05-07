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
                        "§8Centre Administratif"
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
        // 📦 INVENTORY CHECK
        //

        if (e.getClickedInventory() == null)
            return;

        //
        // 📦 ITEM
        //

        if (e.getCurrentItem() == null)
            return;

        //
        // 🔘 SLOT
        //

        int slot =
                e.getRawSlot();

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
        // 📋 PROJETS URBAINS
        //

        if (slot == 13) {

            p.closeInventory();

            PendingProjectsGUI.open(p);

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

            p.performCommand(
                    "urbanisme classement"
            );

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