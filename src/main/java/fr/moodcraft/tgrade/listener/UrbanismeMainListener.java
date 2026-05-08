package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.CitizenTownListGUI;
import fr.moodcraft.tgrade.gui.MayorTownListGUI;
import fr.moodcraft.tgrade.gui.UrbanismeAdminGUI;

import fr.moodcraft.tgrade.manager.ProjectInputManager;

import fr.moodcraft.tgrade.towny.TownyHook;

import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

public class UrbanismeMainListener
        implements Listener {

    //
    // 🏛 GUI CLICK
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
        // 🔊 SOUND
        //

        p.playSound(

                p.getLocation(),

                Sound.UI_BUTTON_CLICK,

                1f,

                1f
        );

        //
        // 📜 PROJETS
        //

        if (slot == 11) {

            //
            // 🛡 TOWNY CHECK
            //

            if (!TownyHook.canManage(p)) {

                p.playSound(

                        p.getLocation(),

                        Sound.ENTITY_VILLAGER_NO,

                        1f,

                        1f
                );

                p.sendMessage("");

                p.sendMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                );

                p.sendMessage(
                        "§c🏛 Accès refusé"
                );

                p.sendMessage("");

                p.sendMessage(
                        "§7Seuls les maires"
                );

                p.sendMessage(
                        "§7et assistants peuvent"
                );

                p.sendMessage(
                        "§7gérer les projets urbains."
                );

                p.sendMessage("");

                p.sendMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                );

                p.sendMessage("");

                return;
            }

            p.closeInventory();

            p.performCommand(
                    "urbanisme projets"
            );

            return;
        }

        //
        // ➕ SOUMISSION
        //

        if (slot == 13) {

            //
            // 🛡 TOWNY CHECK
            //

            if (!TownyHook.canManage(p)) {

                p.playSound(

                        p.getLocation(),

                        Sound.ENTITY_VILLAGER_NO,

                        1f,

                        1f
                );

                p.sendMessage("");

                p.sendMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                );

                p.sendMessage(
                        "§c🏛 Accès refusé"
                );

                p.sendMessage("");

                p.sendMessage(
                        "§7Seuls les maires"
                );

                p.sendMessage(
                        "§7et assistants peuvent"
                );

                p.sendMessage(
                        "§7déposer des projets."
                );

                p.sendMessage("");

                p.sendMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                );

                p.sendMessage("");

                return;
            }

            //
            // 🧠 WAIT INPUT
            //

            ProjectInputManager.start(
                    p.getUniqueId()
            );

            //
            // 🔒 CLOSE
            //

            p.closeInventory();

            //
            // 🔊 SOUND
            //

            p.playSound(

                    p.getLocation(),

                    Sound.BLOCK_NOTE_BLOCK_PLING,

                    1f,

                    1.5f
            );

            //
            // 📜 MESSAGE
            //

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§a✦ Création d'un dossier urbain"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Tape maintenant dans le chat"
            );

            p.sendMessage(
                    "§7le nom du projet."
            );

            p.sendMessage("");

            p.sendMessage(
                    "§8Exemple:"
            );

            p.sendMessage(
                    "§eGare Centrale"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§cTape 'annuler'"
            );

            p.sendMessage(
                    "§cpour quitter."
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

        if (slot == 15) {

            p.closeInventory();

            p.playSound(

                    p.getLocation(),

                    Sound.UI_TOAST_CHALLENGE_COMPLETE,

                    1f,

                    1f
            );

            p.performCommand(
                    "urbanisme classement"
            );

            return;
        }

        //
        // 🔙 MENU PRINCIPAL
        //

        if (slot == 21) {

            p.closeInventory();

            p.playSound(

                    p.getLocation(),

                    Sound.UI_BUTTON_CLICK,

                    1f,

                    1f
            );

            p.performCommand(
                    "menu"
            );

            return;
        }

        //
        // 👥 AVIS CITOYENS
        //

        if (slot == 22) {

            p.playSound(

                    p.getLocation(),

                    Sound.UI_BUTTON_CLICK,

                    1f,

                    1f
            );

            CitizenTownListGUI.open(p);

            return;
        }

        //
        // 👑 CONSEIL DES MAIRES
        //

        if (slot == 23) {

            //
            // 🛡 TOWNY CHECK
            //

            if (!TownyHook.canManage(p)) {

                p.playSound(

                        p.getLocation(),

                        Sound.ENTITY_VILLAGER_NO,

                        1f,

                        1f
                );

                p.sendMessage("");

                p.sendMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                );

                p.sendMessage(
                        "§c👑 Accès refusé"
                );

                p.sendMessage("");

                p.sendMessage(
                        "§7Seuls les maires"
                );

                p.sendMessage(
                        "§7et assistants municipaux"
                );

                p.sendMessage(
                        "§7peuvent accéder au conseil."
                );

                p.sendMessage("");

                p.sendMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
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
            // 🚀 OPEN
            //

            MayorTownListGUI.open(p);

            return;
        }

        //
        // ❌ ADMIN WITHOUT PERM
        //

        if (slot == 31
                && !p.hasPermission(
                "moodtowngrade.staff")) {

            p.playSound(

                    p.getLocation(),

                    Sound.ENTITY_VILLAGER_NO,

                    1f,

                    1f
            );

            return;
        }

        //
        // 🛰 ADMIN
        //

        if (slot == 31) {

            p.playSound(

                    p.getLocation(),

                    Sound.BLOCK_BEACON_ACTIVATE,

                    1f,

                    1f
            );

            UrbanismeAdminGUI.open(p);
        }
    }
}