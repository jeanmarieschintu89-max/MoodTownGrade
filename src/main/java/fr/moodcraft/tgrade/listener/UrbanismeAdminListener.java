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

        if (!e.getView()
                .getTitle()
                .equals("§8✦ Centre National")) {
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

        int slot =
                e.getRawSlot();

        //
        // 📋 DEMANDES DE PROJETS
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
        // ⭐ NOTATIONS & CLÔTURES
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
        // 💰 SUBVENTIONS URBAINES
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

            for (TownGrade grade :
                    GradeManager.getAll()) {

                if (!grade.isFinished()) {
                    continue;
                }

                if (!grade.isLocked()) {
                    continue;
                }

                if (grade.isPayoutClaimed()) {
                    continue;
                }

                double amount =
                        grade.getPayout();

                if (amount <= 0) {
                    continue;
                }

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

                if (town == null) {
                    continue;
                }

                try {

                    town.getAccount()
                            .deposit(
                                    amount,
                                    "Subvention urbaine hebdomadaire"
                            );

                } catch (Exception ex) {

                    continue;
                }

                grade.setPayoutClaimed(true);

                GradeManager.save(grade);

                paid++;

                total += amount;
            }

            p.sendMessage("");
            p.sendMessage(
                    "§8----- §6Commission Urbaine §8-----"
            );
            p.sendMessage(
                    "§aSubventions urbaines versées."
            );
            p.sendMessage(
                    "§7Villes financées : §e"
                            + paid
            );
            p.sendMessage(
                    "§7Budget total : §a"
                            + String.format(
                            "%,.0f",
                            total
                    )
                            .replace(",", " ")
                            + "€"
            );
            p.sendMessage(
                    "§7Seuls les projets validés,"
            );
            p.sendMessage(
                    "§7notés et clôturés sont payés."
            );
            p.sendMessage("");

            return;
        }

        //
        // 🏆 CLASSEMENT HEBDOMADAIRE
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