package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.GradeManager;
import fr.moodcraft.tgrade.model.TownGrade;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class RateGUI {

    public static void open(Player p,
                            String town) {

        TownGrade grade =
                GradeManager.get(town);

        Inventory inv =
                Bukkit.createInventory(
                        null,
                        27,
                        "§8Notation • " + town
                );

        //
        // 🏛 ARCHITECTURE
        //

        ItemStack archi =
                new ItemStack(Material.QUARTZ_BLOCK);

        ItemMeta meta =
                archi.getItemMeta();

        meta.setDisplayName(
                "§6Architecture"
        );

        meta.setLore(List.of(

                "",
                "§7Note: §e"
                        + grade.getArchitecture()
                        + "§7/10",

                "",

                "§aClic gauche = +1",
                "§cClic droit = -1"
        ));

        archi.setItemMeta(meta);

        inv.setItem(10, archi);

        //
        // 🎨 STYLE
        //

        ItemStack style =
                new ItemStack(Material.PAINTING);

        meta = style.getItemMeta();

        meta.setDisplayName(
                "§dStyle RP"
        );

        meta.setLore(List.of(

                "",
                "§7Note: §e"
                        + grade.getStyle()
                        + "§7/6",

                "",

                "§aClic gauche = +1",
                "§cClic droit = -1"
        ));

        style.setItemMeta(meta);

        inv.setItem(11, style);

        //
        // 📈 ACTIVITÉ
        //

        ItemStack activite =
                new ItemStack(Material.BELL);

        meta = activite.getItemMeta();

        meta.setDisplayName(
                "§eActivité"
        );

        meta.setLore(List.of(

                "",
                "§7Note: §e"
                        + grade.getActivite()
                        + "§7/8",

                "",

                "§aClic gauche = +1",
                "§cClic droit = -1"
        ));

        activite.setItemMeta(meta);

        inv.setItem(12, activite);

        //
        // 💰 TOTAL
        //

        ItemStack total =
                new ItemStack(Material.EMERALD_BLOCK);

        meta = total.getItemMeta();

        meta.setDisplayName(
                "§aScore Final"
        );

        meta.setLore(List.of(

                "",

                "§7Total: §e"
                        + grade.getTotal()
                        + "§7/50"
        ));

        total.setItemMeta(meta);

        inv.setItem(26, total);

        p.openInventory(inv);
    }
}