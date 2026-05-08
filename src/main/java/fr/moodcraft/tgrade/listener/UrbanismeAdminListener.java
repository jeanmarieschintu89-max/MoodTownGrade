package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.ClassementGUI;
import fr.moodcraft.tgrade.gui.PendingProjectsGUI;
import fr.moodcraft.tgrade.gui.UrbanismeMainGUI;
import fr.moodcraft.tgrade.gui.EvaluationManagerGUI;

import fr.moodcraft.tgrade.manager.GradeManager;

import fr.moodcraft.tgrade.model.TownGrade;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Town;

import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

public class UrbanismeAdminListener
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
                .equals("§8✦ Centre National")) {
            return;
        }

        e.setCancelled(true);

        //
        // ❌ PLAYER
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

        int slot =
                e.getRawSlot();

        //
        // 📋 DOSSIERS URBAINS
        //

        if (slot == 13) {

            p.playSound(

                    p.getLocation(),

                    Sound.UI_BUTTON_CLICK,

                    1f,

                    1.1f
            );

            PendingProjectsGUI.open(p);

            return;
        }

        //
        // ⭐ ÉVALUATIONS NATIONALES
        //

        if (slot == 22) {

            p.playSound(

                    p.getLocation(),

                    Sound.BLOCK_BEACON_ACTIVATE,

                    1f,

                    1f
            );

            EvaluationManagerGUI.open(p);

            return;
        }

        //
        // 💰 DISTRIBUTION NATIONALE
        //

        if (slot == 31) {

            p.playSound(

                    p.getLocation(),

                    Sound.BLOCK_BEACON_ACTIVATE,

                    1f,

                    1f
            );

            int paid = 0;

            double total = 0;

            //
            // 📚 LOOP VILLES
            //

            for (TownGrade grade :
                    GradeManager.getAll()) {

                //
                // ❌ NON VALIDÉ
                //

                if (!grade.isFinished()) {
                    continue;
                }

                //
                // ❌ DÉJÀ PAYÉ
                //

                if (grade.isPayoutClaimed()) {
                    continue;
                }

                //
                // 💰 BOURSE
                //

                double amount =
                        grade.getPayout();

                //
                // ❌ SÉCURITÉ
                //

                if (amount <= 0) {
                    continue;
                }

                //
                // 🏙 TOWN
                //

                Town town;

                try {

                    town = TownyAPI
                            .getInstance()
                            .getTown(
                                    grade.getTown()
                            );

                } catch (Exception ex) {

                    continue;
                }

                //
                // ❌ TOWN INTROUVABLE
                //

                if (town == null) {
                    continue;
                }

                //
                // 🏦 VERSEMENT
                //

                try {

                    town.getAccount()
                            .deposit(

                                    amount,

                                    "Subvention Nationale"
                            );

                } catch (Exception ex) {

                    continue;
                }

                //
                // ✅ PAYÉ
                //

                grade.setPayoutClaimed(true);

                GradeManager.save(grade);

                paid++;

                total += amount;
            }

            //
            // 📢 RAPPORT
            //

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§6✦ Distribution Nationale"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Subventions distribuées: §e"
                            + paid
            );

            p.sendMessage(
                    "§7Budget national versé: §a"
                            + String.format(
                            "%,.0f",
                            total
                    )
                            + "€"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§a✔ Commission budgétaire validée"
            );

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            return;
        }

        //
        // 🏆 CLASSEMENT
        //

        if (slot == 33) {

            p.playSound(

                    p.getLocation(),

                    Sound.UI_TOAST_CHALLENGE_COMPLETE,

                    1f,

                    1f
            );

            ClassementGUI.open(p);

            return;
        }

        //
        // 🔙 RETOUR
        //

        if (slot == 40) {

            p.playSound(

                    p.getLocation(),

                    Sound.UI_BUTTON_CLICK,

                    1f,

                    0.8f
            );

            UrbanismeMainGUI.open(p);
        }
    }
}