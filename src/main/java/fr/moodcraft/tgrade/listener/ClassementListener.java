package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.UrbanismeMainGUI;

import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

public class ClassementListener
        implements Listener {

    //
    // 🏆 CLICK
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
                        "§8✦ Classement National"
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
        // 🛑 PLAYER INVENTORY
        //

        if (e.getRawSlot() >= e.getView()
                .getTopInventory()
                .getSize()) {

            return;
        }

        //
        // 📦 ITEM CHECK
        //

        if (e.getCurrentItem() == null)
            return;

        //
        // 🔘 SLOT
        //

        int slot =
                e.getRawSlot();

        //
        // 🔙 RETOUR
        //

        if (slot == 49) {

            p.playSound(

                    p.getLocation(),

                    Sound.UI_BUTTON_CLICK,

                    1f,

                    1f
            );

            //
            // 🔒 CLOSE
            //

            p.closeInventory();

            //
            // 🏛 OPEN
            //

            UrbanismeMainGUI.open(p);
        }
    }
}