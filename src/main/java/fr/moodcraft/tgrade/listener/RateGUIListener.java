package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.RateGUI;

import fr.moodcraft.tgrade.manager.GradeManager;

import fr.moodcraft.tgrade.model.TownGrade;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.ClickType;
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

        ClickType click =
                e.getClick();

        //
        // 🏛 ARCHITECTURE
        //

        if (name.equals("§6Architecture")) {

            int value =
                    grade.getArchitecture();

            if (click.isLeftClick()) {

                if (value < 10) {
                    grade.setArchitecture(
                            value + 1
                    );
                }

            } else if (click.isRightClick()) {

                if (value > 0) {
                    grade.setArchitecture(
                            value - 1
                    );
                }
            }
        }

        //
        // 🎨 STYLE
        //

        if (name.equals("§dStyle RP")) {

            int value =
                    grade.getStyle();

            if (click.isLeftClick()) {

                if (value < 6) {
                    grade.setStyle(
                            value + 1
                    );
                }

            } else if (click.isRightClick()) {

                if (value > 0) {
                    grade.setStyle(
                            value - 1
                    );
                }
            }
        }

        //
        // 📈 ACTIVITÉ
        //

        if (name.equals("§eActivité")) {

            int value =
                    grade.getActivite();

            if (click.isLeftClick()) {

                if (value < 8) {
                    grade.setActivite(
                            value + 1
                    );
                }

            } else if (click.isRightClick()) {

                if (value > 0) {
                    grade.setActivite(
                            value - 1
                    );
                }
            }
        }

        //
        // 🔄 REFRESH GUI
        //

        RateGUI.open(
                p,
                town
        );
    }
}