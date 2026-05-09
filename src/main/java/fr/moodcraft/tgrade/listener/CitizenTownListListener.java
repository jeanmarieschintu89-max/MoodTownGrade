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

        if (!(e.getWhoClicked()
                instanceof Player p)) {
            return;
        }

        if (!e.getView()
                .getTitle()
                .equals("§8✦ Votes Citoyens")) {
            return;
        }

        e.setCancelled(true);

        if (e.getCurrentItem() == null) {
            return;
        }

        if (e.getRawSlot()
                >= e.getView()
                .getTopInventory()
                .getSize()) {

            return;
        }

        if (e.getCurrentItem()
                .getType()
                .isAir()) {

            return;
        }

        int slot =
                e.getRawSlot();

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

        Material mat =
                e.getCurrentItem()
                        .getType();

        if (mat == Material.BLACK_STAINED_GLASS_PANE) {
            return;
        }

        if (mat == Material.BARRIER) {

            p.playSound(
                    p.getLocation(),
                    Sound.UI_BUTTON_CLICK,
                    1f,
                    1f
            );

            return;
        }

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

        if (!name.startsWith(
                "§f✦ §b")) {

            return;
        }

        String town =
                name.replace(
                        "§f✦ §b",
                        ""
                );

        p.playSound(
                p.getLocation(),
                Sound.UI_BUTTON_CLICK,
                1f,
                1f
        );

        CitizenVoteGUI.open(
                p,
                town
        );
    }
}