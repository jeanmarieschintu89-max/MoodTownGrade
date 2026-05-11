
package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.MayorVoteGUI;
import fr.moodcraft.tgrade.gui.UrbanismeMainGUI;

import org.bukkit.Material;
import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

public class MayorTownListListener
        implements Listener {

    private static final String TITLE =
            "§8✦ Conseil des Maires";

    private static final String TOWN_PREFIX =
            "§f✦ §b";

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
                .equals(TITLE)) {
            return;
        }

        e.setCancelled(true);

        if (e.getClickedInventory() == null) {
            return;
        }

        if (e.getRawSlot() < 0
                || e.getRawSlot()
                >= e.getView()
                .getTopInventory()
                .getSize()) {
            return;
        }

        ItemStack item =
                e.getCurrentItem();

        if (item == null
                || item.getType()
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
                item.getType();

        if (mat == Material.BLACK_STAINED_GLASS_PANE
                || mat == Material.GRAY_STAINED_GLASS_PANE
                || mat == Material.LIGHT_GRAY_STAINED_GLASS_PANE
                || mat == Material.WHITE_STAINED_GLASS_PANE
                || mat == Material.BARRIER) {
            return;
        }

        ItemMeta meta =
                item.getItemMeta();

        if (meta == null
                || !meta.hasDisplayName()) {
            return;
        }

        String name =
                meta.getDisplayName();

        if (!name.startsWith(TOWN_PREFIX)) {
            return;
        }

        String town =
                name.replace(
                        TOWN_PREFIX,
                        ""
                ).trim();

        if (town.isEmpty()) {
            return;
        }

        p.playSound(
                p.getLocation(),
                Sound.UI_BUTTON_CLICK,
                1f,
                1f
        );

        MayorVoteGUI.open(
                p,
                town
        );
    }
}