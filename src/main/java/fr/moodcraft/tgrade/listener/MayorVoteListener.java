package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.MayorVoteGUI;

import fr.moodcraft.tgrade.manager.MayorVoteManager;
import fr.moodcraft.tgrade.manager.NationalScoreCalculator;

import fr.moodcraft.tgrade.model.MayorVote;

import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

public class MayorVoteListener
        implements Listener {

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
                .equals("§8✦ Conseil des Maires")) {
            return;
        }

        e.setCancelled(true);

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