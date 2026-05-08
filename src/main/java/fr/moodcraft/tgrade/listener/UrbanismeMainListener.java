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
        // 👥 AVIS CITOYENS
        //

        if (slot == 20) {

            CitizenTownListGUI.open(p);

            return;
        }

        //
        // 🏆 CLASSEMENT
        //

        if (slot == 22) {

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
        // ➕ SOUMISSION
        //

        if (slot == 29) {

            if (!TownyHook.canManage(p)) {

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
                        "§cAccès refusé."
                );
                p.sendMessage(
                        "§7Seuls les maires et assistants peuvent déposer des projets."
                );
                p.sendMessage("");

                return;
            }

            ProjectInputManager.start(
                    p.getUniqueId()
            );

            p.closeInventory();

            p.playSound(

                    p.getLocation(),

                    Sound.BLOCK_NOTE_BLOCK_PLING,

                    1f,

                    1.5f
            );

            p.sendMessage("");
            p.sendMessage(
                    "§8----- §6Commission Urbaine §8-----"
            );
            p.sendMessage(
                    "§fNouveau dossier urbain ouvert."
            );
            p.sendMessage(
                    "§7Tapez dans le chat le nom du projet."
            );
            p.sendMessage(
                    "§7Exemple: §eGare Centrale"
            );
            p.sendMessage(
                    "§cTapez §fannuler §cpour quitter."
            );
            p.sendMessage("");

            return;
        }

        //
        // 👑 CONSEIL DES MAIRES
        //

        if (slot == 31) {

            if (!TownyHook.canManage(p)) {

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
                        "§cAccès refusé."
                );
                p.sendMessage(
                        "§7Seuls les maires et assistants peuvent accéder au conseil."
                );
                p.sendMessage("");

                return;
            }

            p.playSound(

                    p.getLocation(),

                    Sound.BLOCK_BEACON_ACTIVATE,

                    1f,

                    1f
            );

            MayorTownListGUI.open(p);

            return;
        }

        //
        // ❌ ADMIN WITHOUT PERM
        //

        if (slot == 33
                && !p.hasPermission(
                "moodtowngrade.staff")) {

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
                    "§cAccès refusé."
            );
            p.sendMessage(
                    "§7Ce centre est réservé à l'administration nationale."
            );
            p.sendMessage("");

            return;
        }

        //
        // 🛰 ADMIN
        //

        if (slot == 33) {

            p.playSound(

                    p.getLocation(),

                    Sound.BLOCK_BEACON_ACTIVATE,

                    1f,

                    1f
            );

            p.sendMessage("");
            p.sendMessage(
                    "§8----- §6Commission Urbaine §8-----"
            );
            p.sendMessage(
                    "§fOuverture du Centre National."
            );
            p.sendMessage(
                    "§7Accès aux registres administratifs."
            );
            p.sendMessage(
                    "§a✔ Session administrative ouverte."
            );
            p.sendMessage("");

            UrbanismeAdminGUI.open(p);

            return;
        }

        //
        // 🔙 RETOUR
        //

        if (slot == 49) {

            p.closeInventory();

            p.performCommand(
                    "menu"
            );
        }
    }
}