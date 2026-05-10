package fr.moodcraft.tgrade.listener;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;

import fr.moodcraft.tgrade.gui.CitizenTownListGUI;
import fr.moodcraft.tgrade.gui.MayorTownListGUI;
import fr.moodcraft.tgrade.gui.UrbanismeAdminGUI;

import fr.moodcraft.tgrade.manager.ProjectDepositSessionManager;

import fr.moodcraft.tgrade.towny.TownyHook;

import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

public class UrbanismeMainListener
        implements Listener {

    @EventHandler
    public void click(
            InventoryClickEvent e
    ) {

        if (!e.getView()
                .getTitle()
                .equalsIgnoreCase(
                        "§8✦ Commission Urbaine"
                )) {
            return;
        }

        e.setCancelled(true);

        if (!(e.getWhoClicked()
                instanceof Player p))
            return;

        if (e.getClickedInventory() == null)
            return;

        if (e.getRawSlot() >= e.getView()
                .getTopInventory()
                .getSize()) {
            return;
        }

        if (e.getCurrentItem() == null)
            return;

        if (e.getCurrentItem()
                .getType()
                .isAir()) {
            return;
        }

        int slot =
                e.getRawSlot();

        p.playSound(
                p.getLocation(),
                Sound.UI_BUTTON_CLICK,
                1f,
                1f
        );

        if (slot == 20) {

            CitizenTownListGUI.open(p);

            return;
        }

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

        if (slot == 29) {

            if (!TownyHook.canManage(p)) {

                p.playSound(
                        p.getLocation(),
                        Sound.ENTITY_VILLAGER_NO,
                        1f,
                        1f
                );

                p.sendMessage("");
                p.sendMessage("§8----- §6Commission Urbaine §8-----");
                p.sendMessage("§cAccès refusé.");
                p.sendMessage("§7Seuls les maires et assistants peuvent déposer des projets.");
                p.sendMessage("");

                return;
            }

            Resident resident =
                    TownyAPI.getInstance()
                            .getResident(
                                    p.getUniqueId()
                            );

            if (resident == null
                    || !resident.hasTown()
                    || resident.getTownOrNull() == null) {

                p.playSound(
                        p.getLocation(),
                        Sound.ENTITY_VILLAGER_NO,
                        1f,
                        0.9f
                );

                p.sendMessage("");
                p.sendMessage("§8----- §6Commission Urbaine §8-----");
                p.sendMessage("§cAucune ville détectée.");
                p.sendMessage("§7Impossible d'ouvrir un dossier urbain.");
                p.sendMessage("");

                return;
            }

            Town town =
                    resident.getTownOrNull();

            ProjectDepositSessionManager.start(
                    p,
                    town.getName()
            );

            p.closeInventory();

            p.playSound(
                    p.getLocation(),
                    Sound.BLOCK_NOTE_BLOCK_PLING,
                    1f,
                    1.5f
            );

            p.sendMessage("");
            p.sendMessage("§8----- §6Commission Urbaine §8-----");
            p.sendMessage("§fNouveau dossier urbain ouvert.");
            p.sendMessage("§7Ville : §b" + town.getName());
            p.sendMessage("§7Tapez dans le chat le §enom du projet§7.");
            p.sendMessage("§7Exemple : §eGare Centrale");
            p.sendMessage("§8Tapez §cannuler §8pour quitter.");
            p.sendMessage("");

            return;
        }

        if (slot == 31) {

            if (!TownyHook.canManage(p)) {

                p.playSound(
                        p.getLocation(),
                        Sound.ENTITY_VILLAGER_NO,
                        1f,
                        1f
                );

                p.sendMessage("");
                p.sendMessage("§8----- §6Commission Urbaine §8-----");
                p.sendMessage("§cAccès refusé.");
                p.sendMessage("§7Seuls les maires et assistants peuvent accéder au conseil.");
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
            p.sendMessage("§8----- §6Commission Urbaine §8-----");
            p.sendMessage("§cAccès refusé.");
            p.sendMessage("§7Ce centre est réservé à l'administration nationale.");
            p.sendMessage("");

            return;
        }

        if (slot == 33) {

            p.playSound(
                    p.getLocation(),
                    Sound.BLOCK_BEACON_ACTIVATE,
                    1f,
                    1f
            );

            p.sendMessage("");
            p.sendMessage("§8----- §6Commission Urbaine §8-----");
            p.sendMessage("§fOuverture du Centre National.");
            p.sendMessage("§7Accès aux registres administratifs.");
            p.sendMessage("§a✔ Session administrative ouverte.");
            p.sendMessage("");

            UrbanismeAdminGUI.open(p);

            return;
        }

        if (slot == 49) {

            p.closeInventory();

            p.performCommand(
                    "menu"
            );
        }
    }
}