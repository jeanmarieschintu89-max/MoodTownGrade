package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.ProjectReviewGUI;
import fr.moodcraft.tgrade.gui.UrbanismeAdminGUI;

import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

public class PendingProjectsListener
        implements Listener {

    //
    // 📋 CLICK
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
                        "§8✦ Commission Urbaine"
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
        // 📦 ITEM
        //

        if (e.getCurrentItem() == null)
            return;

        //
        // 📛 META
        //

        if (!e.getCurrentItem()
                .hasItemMeta())
            return;

        if (e.getCurrentItem()
                .getItemMeta()
                .getDisplayName() == null)
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

            p.closeInventory();

            UrbanismeAdminGUI.open(p);

            return;
        }

        //
        // 📛 NAME
        //

        String name =
                e.getCurrentItem()
                        .getItemMeta()
                        .getDisplayName()

                        .replace("§f✦ §e", "")
                        .replace("§e", "")
                        .replace("§b", "")
                        .replace("§6", "")
                        .replace("§a", "")
                        .replace("§c", "");

        //
        // 🔍 FIND
        //

        TownSubmission found = null;

        for (TownSubmission sub :
                SubmissionStorage.getAll()) {

            if (sub.getBuildName()
                    .equalsIgnoreCase(name)) {

                found = sub;

                break;
            }
        }

        //
        // ❌ NOT FOUND
        //

        if (found == null) {

            p.playSound(

                    p.getLocation(),

                    Sound.ENTITY_VILLAGER_NO,

                    1f,

                    1f
            );

            return;
        }

        //
        // 🔊 SOUND
        //

        p.playSound(

                p.getLocation(),

                Sound.UI_BUTTON_CLICK,

                1f,

                1.2f
        );

        //
        // 🏛 OPEN REVIEW
        //

        ProjectReviewGUI.open(
                p,
                found
        );
    }
}