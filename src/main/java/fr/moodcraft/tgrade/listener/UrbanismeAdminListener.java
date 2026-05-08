package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.ClassementGUI;
import fr.moodcraft.tgrade.gui.PendingProjectsGUI;
import fr.moodcraft.tgrade.gui.UrbanismeMainGUI;

import fr.moodcraft.tgrade.manager.GradeManager;

import fr.moodcraft.tgrade.model.TownGrade;

import org.bukkit.Bukkit;

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
        // 📋 PROJETS + 📝 NOTATION
        //

        if (slot == 13 || slot == 22) {

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
                // 💰 CALCUL SUBVENTION
                //

                double amount =
                        grade.getTotal() * 850;

                //
                // ❌ SÉCURITÉ
                //

                if (amount <= 0) {
                    continue;
                }

                //
                // 👑 MAIRE
                //

                String mayor =
                        grade.getMayor();

                if (mayor == null
                        || mayor.isEmpty()) {
                    continue;
                }

                //
                // 🏦 VAULT CHECK
                //

                if (fr.moodcraft.bridge.util.VaultHook.economy != null) {

                    Bukkit.getOfflinePlayer(
                            mayor
                    );

                    fr.moodcraft.bridge.util.VaultHook.economy.depositPlayer(

                            Bukkit.getOfflinePlayer(
                                    mayor
                            ),

                            amount
                    );
                }

                //
                // ✅ CLAIMED
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
                    "§7Budget total versé: §a"
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