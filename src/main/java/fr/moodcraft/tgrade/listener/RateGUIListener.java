package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.RateGUI;

import fr.moodcraft.tgrade.manager.GradeManager;

import fr.moodcraft.tgrade.model.TownGrade;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.entity.Player;

import org.bukkit.inventory.ItemStack;

public class RateGUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) {
            return;
        }

        //
        // 🎨 GUI CHECK
        //

        if (!e.getView()
                .getTitle()
                .startsWith("§8Notation")) {
            return;
        }

        e.setCancelled(true);

        ItemStack item =
                e.getCurrentItem();

        if (item == null) return;

        if (!item.hasItemMeta()) return;

        //
        // 🏙 TOWN
        //

        String town =
                e.getView()
                        .getTitle()
                        .replace("§8Notation • ", "");

        TownGrade grade =
                GradeManager.get(town);

        String name =
                item.getItemMeta()
                        .getDisplayName();

        //
        // 🏛 ARCHITECTURE
        //

        if (name.equals("§6Architecture")) {

            int value =
                    grade.getArchitecture();

            value++;

            if (value > 10) {
                value = 0;
            }

            grade.setArchitecture(value);
        }

        //
        // 🎨 STYLE RP
        //

        if (name.equals("§dStyle RP")) {

            int value =
                    grade.getStyle();

            value++;

            if (value > 6) {
                value = 0;
            }

            grade.setStyle(value);
        }

        //
        // 📈 ACTIVITÉ
        //

        if (name.equals("§eActivité")) {

            int value =
                    grade.getActivite();

            value++;

            if (value > 8) {
                value = 0;
            }

            grade.setActivite(value);
        }

        //
        // 💰 BANQUE
        //

        if (name.equals("§6Banque")) {

            int value =
                    grade.getBanque();

            value++;

            if (value > 4) {
                value = 0;
            }

            grade.setBanque(value);
        }

        //
        // 🌟 BUILD REMARQUABLE
        //

        if (name.equals("§bBuild remarquable")) {

            int value =
                    grade.getRemarquable();

            value++;

            if (value > 8) {
                value = 0;
            }

            grade.setRemarquable(value);
        }

        //
        // 👥 ORGANISATION RP
        //

        if (name.equals("§dOrganisation RP")) {

            int value =
                    grade.getRp();

            value++;

            if (value > 6) {
                value = 0;
            }

            grade.setRp(value);
        }

        //
        // 🗺️ TAILLE
        //

        if (name.equals("§aTaille")) {

            int value =
                    grade.getTaille();

            value++;

            if (value > 3) {
                value = 0;
            }

            grade.setTaille(value);
        }

        //
        // 🗳️ VOTES
        //

        if (name.equals("§2Votes serveur")) {

            int value =
                    grade.getVotes();

            value++;

            if (value > 5) {
                value = 0;
            }

            grade.setVotes(value);
        }

        //
        // 💾 SAVE
        //

        GradeManager.save(grade);

        //
        // 🔄 REFRESH GUI
        //

        RateGUI.open(
                p,
                town
        );
    }
}