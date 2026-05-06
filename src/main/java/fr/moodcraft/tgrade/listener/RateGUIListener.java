package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.RateGUI;

import fr.moodcraft.tgrade.manager.GradeManager;

import fr.moodcraft.tgrade.model.TownGrade;

import org.bukkit.Bukkit;
import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.inventory.ItemStack;

public class RateGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) {
            return;
        }

        //
        // 🏛️ GUI CHECK
        //

        if (!e.getView()
                .getTitle()
                .contains("Inspection")) {
            return;
        }

        e.setCancelled(true);

        ItemStack item =
                e.getCurrentItem();

        if (item == null) return;

        if (!item.hasItemMeta()) return;

        //
        // 🏙️ TOWN
        //

        String town =
                e.getView()
                        .getTitle()
                        .replace(
                                "§8✦ §bMoodCraft §8• §7Inspection",
                                ""
                        )
                        .trim();

        TownGrade grade =
                GradeManager.get(town);

        String name =
                item.getItemMeta()
                        .getDisplayName();

        //
        // 🏛️ ARCHITECTURE
        //

        if (name.equals(
                "§6Architecture urbaine")) {

            int value =
                    grade.getArchitecture();

            value++;

            if (value > 10) {
                value = 0;
            }

            grade.setArchitecture(value);

            playClick(p);
        }

        //
        // 🎨 STYLE
        //

        if (name.equals(
                "§dCohérence architecturale")) {

            int value =
                    grade.getStyle();

            value++;

            if (value > 6) {
                value = 0;
            }

            grade.setStyle(value);

            playClick(p);
        }

        //
        // 📈 ACTIVITÉ
        //

        if (name.equals(
                "§eDynamisme urbain")) {

            int value =
                    grade.getActivite();

            value++;

            if (value > 8) {
                value = 0;
            }

            grade.setActivite(value);

            playClick(p);
        }

        //
        // 💰 BANQUE
        //

        if (name.equals(
                "§6Financement municipal")) {

            int value =
                    grade.getBanque();

            value++;

            if (value > 4) {
                value = 0;
            }

            grade.setBanque(value);

            playClick(p);
        }

        //
        // 🌟 MONUMENT
        //

        if (name.equals(
                "§bMonument emblématique")) {

            int value =
                    grade.getRemarquable();

            value++;

            if (value > 8) {
                value = 0;
            }

            grade.setRemarquable(value);

            playClick(p);
        }

        //
        // 👥 RP
        //

        if (name.equals(
                "§dOrganisation citoyenne")) {

            int value =
                    grade.getRp();

            value++;

            if (value > 6) {
                value = 0;
            }

            grade.setRp(value);

            playClick(p);
        }

        //
        // 🗺️ TAILLE
        //

        if (name.equals(
                "§aExpansion territoriale")) {

            int value =
                    grade.getTaille();

            value++;

            if (value > 3) {
                value = 0;
            }

            grade.setTaille(value);

            playClick(p);
        }

        //
        // 🗳️ VOTES
        //

        if (name.equals(
                "§2Investissement communautaire")) {

            int value =
                    grade.getVotes();

            value++;

            if (value > 5) {
                value = 0;
            }

            grade.setVotes(value);

            playClick(p);
        }

        //
        // 📊 RAPPORT FINAL
        //

        if (name.equals(
                "§aRapport d'inspection")) {

            p.closeInventory();

            //
            // 🔊 SOUND
            //

            p.playSound(
                    p.getLocation(),
                    Sound.UI_TOAST_CHALLENGE_COMPLETE,
                    1f,
                    1f
            );

            //
            // 📢 MESSAGE
            //

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§b🏛 Inspection terminée"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Ville: §b" + town
            );

            p.sendMessage(
                    "§7Score final: §e"
                            + grade.getTotal()
                            + "§7/50"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§aRapport sauvegardé."
            );

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            //
            // 📢 BROADCAST
            //

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            Bukkit.broadcastMessage(
                    "§b🏛 Commission Urbaine"
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Inspection terminée pour"
            );

            Bukkit.broadcastMessage(
                    "§b" + town
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Score obtenu: §e"
                            + grade.getTotal()
                            + "§7/50"
            );

            Bukkit.broadcastMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            Bukkit.broadcastMessage("");

            return;
        }

        //
        // 💾 SAVE
        //

        GradeManager.save(grade);

        //
        // 🔄 REFRESH
        //

        RateGUI.open(
                p,
                town
        );
    }

    //
    // 🔊 SOUND
    //

    private void playClick(Player p) {

        p.playSound(
                p.getLocation(),
                Sound.UI_BUTTON_CLICK,
                1f,
                1.2f
        );
    }
}