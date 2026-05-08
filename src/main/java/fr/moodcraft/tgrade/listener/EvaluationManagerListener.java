package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.EvaluationManagerGUI;
import fr.moodcraft.tgrade.gui.RateGUI;
import fr.moodcraft.tgrade.gui.UrbanismeAdminGUI;

import fr.moodcraft.tgrade.manager.GradeManager;

import fr.moodcraft.tgrade.model.TownGrade;

import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

public class EvaluationManagerListener
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
                .equals("§8✦ Évaluations Nationales")) {
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
        // ❌ NULL
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
        // 🔘 SLOT
        //

        int slot =
                e.getSlot();

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

            UrbanismeAdminGUI.open(p);

            return;
        }

        //
        // 📛 ITEM
        //

        if (!e.getCurrentItem()
                .hasItemMeta()) {
            return;
        }

        String name =
                e.getCurrentItem()
                        .getItemMeta()
                        .getDisplayName();

        //
        // ❌ HEADER/BORDERS
        //

        if (!name.startsWith("§f✦ §b")) {
            return;
        }

        //
        // 🏙 VILLE
        //

        String town =
                name.replace(
                        "§f✦ §b",
                        ""
                );

        //
        // 📚 GRADE
        //

        TownGrade grade =
                GradeManager.get(town);

        //
        // ❌ NULL
        //

        if (grade == null) {

            p.playSound(

                    p.getLocation(),

                    Sound.ENTITY_VILLAGER_NO,

                    1f,

                    1f
            );

            p.sendMessage("");
            p.sendMessage(
                    "§8----- §6Commission Urbaine §8-----"
            );
            p.sendMessage(
                    "§cVille introuvable."
            );
            p.sendMessage(
                    "§7Le dossier national n'existe plus."
            );
            p.sendMessage("");

            return;
        }

        //
        // 🔊 SOUND
        //

        p.playSound(

                p.getLocation(),

                Sound.BLOCK_BEACON_ACTIVATE,

                1f,

                1f
        );

        //
        // 📜 MESSAGE
        //

        p.sendMessage("");
        p.sendMessage(
                "§8----- §6Commission Urbaine §8-----"
        );
        p.sendMessage(
                "§fOuverture du dossier d'évaluation."
        );
        p.sendMessage(
                "§7Ville: §b" + town
        );
        p.sendMessage(
                "§a✔ Registre national chargé."
        );
        p.sendMessage("");

        //
        // ⭐ OPEN RATE GUI
        //

        RateGUI.open(
                p,
                town
        );
    }
}