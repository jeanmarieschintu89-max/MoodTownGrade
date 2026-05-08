package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.EvaluationManagerGUI;
import fr.moodcraft.tgrade.gui.RateGUI;
import fr.moodcraft.tgrade.gui.TownEvaluationActionGUI;
import fr.moodcraft.tgrade.gui.UrbanismeAdminGUI;

import fr.moodcraft.tgrade.manager.GradeManager;
import fr.moodcraft.tgrade.manager.NationalScoreCalculator;

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

        if (e.getView()
                .getTitle()
                .equals("§8✦ Évaluations Nationales")) {

            clickList(e);

            return;
        }

        if (e.getView()
                .getTitle()
                .equals("§8✦ Dossier National")) {

            clickDossier(e);
        }
    }

    private void clickList(
            InventoryClickEvent e
    ) {

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

        if (!e.getCurrentItem()
                .hasItemMeta()) {
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

        TownEvaluationActionGUI.open(
                p,
                town
        );
    }

    private void clickDossier(
            InventoryClickEvent e
    ) {

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

        String town =
                getTown(e);

        if (town == null
                || town.isEmpty()) {

            p.playSound(
                    p.getLocation(),
                    Sound.ENTITY_VILLAGER_NO,
                    1f,
                    1f
            );

            return;
        }

        int slot =
                e.getRawSlot();

        if (slot == TownEvaluationActionGUI.BACK) {

            EvaluationManagerGUI.open(p);

            return;
        }

        if (slot == TownEvaluationActionGUI.NOTE) {

            p.playSound(
                    p.getLocation(),
                    Sound.BLOCK_BEACON_ACTIVATE,
                    1f,
                    1f
            );

            RateGUI.open(
                    p,
                    town
            );

            return;
        }

        if (slot == TownEvaluationActionGUI.FINALIZE) {

            TownGrade grade =
                    GradeManager.get(town);

            if (grade.isFinished()) {

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
                        "§cDossier déjà clôturé."
                );
                p.sendMessage(
                        "§7Ville: §b" + town
                );
                p.sendMessage("");

                return;
            }

            double staff =
                    NationalScoreCalculator.getStaffScore(town);

            if (staff <= 0) {

                p