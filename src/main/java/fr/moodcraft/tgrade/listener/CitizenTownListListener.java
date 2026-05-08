package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.CitizenVoteGUI;
import fr.moodcraft.tgrade.gui.UrbanismeMainGUI;

import org.bukkit.Material;

import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

public class CitizenTownListListener
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
                .equals("§8✦ Avis Citoyens")) {
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
        // ❌ AIR
        //

        if (e.getCurrentItem()
                .getType()
                .isAir()) {

            return;
        }

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

            UrbanismeMainGUI.open(p);

            return;
        }

        //
        // 🌌 BORDURES
        //

        Material mat =
                e.getCurrentItem()
                        .getType();

        if (mat
                == Material.BLACK_STAINED_GLASS_PANE) {

            return;
        }

        //
        // ❌ BARRIER
        //

        if (mat
                == Material.BARRIER) {

            p.playSound(

                    p.getLocation(),

                    Sound.UI_BUTTON_CLICK,

                    1f,

                    1f
            );

            return;
        }

        //
        // 📛 ITEM NAME
        //

        if (!e.getCurrentItem()
                .hasItemMeta()) {

            return;
        }

        if (e.getCurrentItem()
                .getItemMeta()
                .getDisplayName() == null) {

            return;
        }

        String name =
                e.getCurrentItem()
                        .getItemMeta()
                        .getDisplayName();

        //
        // 🏙️ INVALID
        //

        if (!name.startsWith(
                "§b✦ ")) {

            return;
        }

        //
        // 🏙️ TOWN
        //

        String town =
                name.replace(
                        "§b✦ ",
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

        CitizenVoteGUI.open(
                p,
                town
        );
    }
}