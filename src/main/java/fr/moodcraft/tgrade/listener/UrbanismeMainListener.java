package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.UrbanismeAdminGUI;

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

            p.closeInventory();

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§a🏗 Nouveau Projet"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Place-toi devant"
            );

            p.sendMessage(
                    "§7la construction RP."
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Commande:"
            );

            p.sendMessage(
                    "§e/urbanisme projet <nom>"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§cTout faux dossier"
            );

            p.sendMessage(
                    "§csera refusé."
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

        if (slot == 22) {

            p.closeInventory();

            p.playSound(

                    p.getLocation(),

                    Sound.UI_BUTTON_CLICK,

                    1f,

                    1f
            );

            p.performCommand("menu");

            return;
        }

        //
        // 🛰 ADMIN
        //

        if (slot == 31) {

            //
            // 🔒 STAFF CHECK
            //

            if (!p.hasPermission(
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

            UrbanismeAdminGUI.open(p);
        }
    }
}