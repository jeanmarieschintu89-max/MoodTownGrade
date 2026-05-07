package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.PendingProjectsGUI;
import fr.moodcraft.tgrade.gui.ProjectReviewGUI;
import fr.moodcraft.tgrade.gui.UrbanismeAdminGUI;

import fr.moodcraft.tgrade.model.SubmissionStatus;
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
                        "§8📋 Projets Urbains"
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
        // 🔙 RETOUR
        //

        if (e.getSlot() == 49) {

            p.playSound(

                    p.getLocation(),

                    Sound.UI_BUTTON_CLICK,

                    1f,

                    1f
            );

            UrbanismeAdminGUI.open(p);

            return;
        }

        //
        // 📛 ITEM
        //

        if (!e.getCurrentItem()
                .hasItemMeta())
            return;

        if (e.getCurrentItem()
                .getItemMeta()
                .getDisplayName() == null)
            return;

        //
        // 📛 NAME
        //

        String name =
                e.getCurrentItem()
                        .getItemMeta()
                        .getDisplayName()
                        .replace("§e", "");

        //
        // 🔍 FIND
        //

        TownSubmission found = null;

        for (TownSubmission sub :
                SubmissionStorage.getAll()) {

            if (sub.getBuildName()
                    .equalsIgnoreCase(name)

                    && sub.getStatus()
                    == SubmissionStatus.PENDING) {

                found = sub;

                break;
            }
        }

        //
        // ❌ NOT FOUND
        //

        if (found == null) {

            p.sendMessage(
                    "§cProjet introuvable."
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
        // 🏛 OPEN REVIEW GUI
        //

        ProjectReviewGUI.open(
                p,
                found
        );
    }
}