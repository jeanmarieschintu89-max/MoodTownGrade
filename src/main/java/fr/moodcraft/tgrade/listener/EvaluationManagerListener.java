package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.ProjectReviewGUI;
import fr.moodcraft.tgrade.gui.UrbanismeAdminGUI;

import fr.moodcraft.tgrade.manager.GradeManager;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownGrade;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Material;
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

        if (!e.getView()
                .getTitle()
                .equals("§8✦ Évaluations Nationales")) {
            return;
        }

        e.setCancelled(true);

        if (!(e.getWhoClicked()
                instanceof Player p)) {
            return;
        }

        if (e.getCurrentItem() == null) {
            return;
        }

        if (e.getRawSlot()
                >= e.getView()
                .getTopInventory()
                .getSize()) {
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

            UrbanismeAdminGUI.open(p);

            return;
        }

        Material material =
                e.getCurrentItem()
                        .getType();

        if (material == Material.BLACK_STAINED_GLASS_PANE
                || material == Material.BARRIER
                || material == Material.ARROW) {
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

        if (!name.startsWith("§f✦ §b")) {
            return;
        }

        String town =
                name.replace(
                        "§f✦ §b",
                        ""
                );

        TownGrade grade =
                GradeManager.get(town);

        if (grade == null) {

            deny(
                    p,
                    "§cVille introuvable.",
                    "§7Le dossier national n'existe plus."
            );

            return;
        }

        TownSubmission found =
                getActiveProject(town);

        if (found == null) {

            deny(
                    p,
                    "§cAucun projet actif.",
                    "§7Cette ville n'a aucun projet validé."
            );

            return;
        }

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
                "§fOuverture du dossier staff."
        );
        p.sendMessage(
                "§7Ville : §b" + town
        );
        p.sendMessage(
                "§7Projet : §f" + found.getBuildName()
        );
        p.sendMessage(
                "§a✔ Inspection nationale chargée."
        );
        p.sendMessage("");

        ProjectReviewGUI.open(
                p,
                found
        );
    }

    private TownSubmission getActiveProject(
            String town
    ) {

        for (TownSubmission sub :
                SubmissionStorage.getAll()) {

            if (!sub.getTown()
                    .equalsIgnoreCase(town)) {
                continue;
            }

            if (sub.getStatus()
                    == SubmissionStatus.APPROVED) {

                return sub;
            }
        }

        return null;
    }

    private void deny(
            Player p,
            String line1,
            String line2
    ) {

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
        p.sendMessage(line1);
        p.sendMessage(line2);
        p.sendMessage("");
    }
}