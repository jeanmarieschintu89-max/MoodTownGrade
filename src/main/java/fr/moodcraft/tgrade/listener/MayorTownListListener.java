package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.MayorTownListGUI;
import fr.moodcraft.tgrade.gui.MayorVoteGUI;
import fr.moodcraft.tgrade.gui.UrbanismeMainGUI;

import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

public class MayorTownListListener
        implements Listener {

    @EventHandler
    public void click(
            InventoryClickEvent e
    ) {

        //
        // 👤 PLAYER
        //

        if (!(e.getWhoClicked()
                instanceof Player p)) {
            return;
        }

        //
        // 📛 TITLE
        //

        if (!e.getView()
                .getTitle()
                .equals("§8✦ Conseil National")) {
            return;
        }

        //
        // ❌ CANCEL
        //

        e.setCancelled(true);

        //
        // 📦 NULL
        //

        if (e.getCurrentItem() == null) {
            return;
        }

        //
        // 🛑 PLAYER INVENTORY
        //

        if (e.getRawSlot()
                >= e.getView()
                .getTopInventory()
                .getSize()) {

            return;
        }

        //
        // 🔙 RETOUR
        //

        if (e.getRawSlot() == 49) {

            p.playSound(

                    p.getLocation(),

                    Sound.UI_BUTTON_CLICK,

                    1f,

                    1f
            );

            UrbanismeMainGUI.open(p);

            return;
        }

        //
        // 📛 ITEM NAME
        //

        String name =
                e.getCurrentItem()
                        .getItemMeta()
                        .getDisplayName();

        //
        // 🏙️ TOWN
        //

        String town =
                name.replace(
                        "§6✦ ",
                        ""
                );

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
        // 🚀 OPEN VOTE
        //

        MayorVoteGUI.open(
                p,
                town
        );
    }
}