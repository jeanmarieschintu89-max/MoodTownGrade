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

                "§eClique pour augmenter",
                "§7Retour à 0 après maximum"
        ));

        archi.setItemMeta(meta);

        inv.setItem(10, archi);

        //
        // 🎨 STYLE RP
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

                "§eClique pour augmenter",
                "§7Retour à 0 après maximum"
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

                "§eClique pour augmenter",
                "§7Retour à 0 après maximum"
        ));

        activite.setItemMeta(meta);

        inv.setItem(12, activite);

        //
        // 💰 BANQUE
        //

        ItemStack banque =
                new ItemStack(Material.GOLD_INGOT);

        meta = banque.getItemMeta();

        meta.setDisplayName(
                "§6Banque"
        );

        meta.setLore(List.of(

                "",

                "§7Note: §e"
                        + grade.getBanque()
                        + "§7/4",

                "",

                "§eClique pour augmenter",
                "§7Retour à 0 après maximum"
        ));

        banque.setItemMeta(meta);

        inv.setItem(13, banque);

        //
        // 🌟 BUILD REMARQUABLE
        //

        ItemStack remarquable =
                new ItemStack(Material.BEACON);

        meta = remarquable.getItemMeta();

        meta.setDisplayName(
                "§bBuild remarquable"
        );

        meta.setLore(List.of(

                "",

                "§7Note: §e"
                        + grade.getRemarquable()
                        + "§7/8",

                "",

                "§eClique pour augmenter",
                "§7Retour à 0 après maximum"
        ));

        remarquable.setItemMeta(meta);

        inv.setItem(14, remarquable);

        //
        // 👥 ORGANISATION RP
        //

        ItemStack rp =
                new ItemStack(Material.WRITABLE_BOOK);

        meta = rp.getItemMeta();

        meta.setDisplayName(
                "§dOrganisation RP"
        );

        meta.setLore(List.of(

                "",

                "§7Note: §e"
                        + grade.getRp()
                        + "§7/6",

                "",

                "§eClique pour augmenter",
                "§7Retour à 0 après maximum"
        ));

        rp.setItemMeta(meta);

        inv.setItem(15, rp);

        //
        // 🗺️ TAILLE
        //

        ItemStack taille =
                new ItemStack(Material.MAP);

        meta = taille.getItemMeta();

        meta.setDisplayName(
                "§aTaille"
        );

        meta.setLore(List.of(

                "",

                "§7Note: §e"
                        + grade.getTaille()
                        + "§7/3",

                "",

                "§eClique pour augmenter",
                "§7Retour à 0 après maximum"
        ));

        taille.setItemMeta(meta);

        inv.setItem(16, taille);

        //
        // 🗳️ VOTES
        //

        ItemStack votes =
                new ItemStack(Material.EMERALD);

        meta = votes.getItemMeta();

        meta.setDisplayName(
                "§2Votes serveur"
        );

        meta.setLore(List.of(

                "",

                "§7Note: §e"
                        + grade.getVotes()
                        + "§7/5",

                "",

                "§eClique pour augmenter",
                "§7Retour à 0 après maximum"
        ));

        votes.setItemMeta(meta);

        inv.setItem(22, votes);

        //
        // 📊 SCORE FINAL
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

        inv.setItem(25, total);

        //
        // ✨ OPEN
        //

        p.openInventory(inv);
    }
}