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

        //
        // 🏛️ COMMISSION URBAINE
        //

        Inventory inv =
                Bukkit.createInventory(
                        null,
                        27,
                        "§8✦ §bMoodCraft §8• §7Inspection"
                );

        //
        // 🏛️ ARCHITECTURE
        //

        ItemStack archi =
                new ItemStack(Material.QUARTZ_BLOCK);

        ItemMeta meta =
                archi.getItemMeta();

        meta.setDisplayName(
                "§6Architecture urbaine"
        );

        meta.setLore(List.of(

                "",

                "§7Qualité générale des bâtiments",
                "§7Palette, détails et finition.",

                "",

                "§fInspection: §e"
                        + grade.getArchitecture()
                        + "§7/10",

                "",

                "§b● §7Clique pour ajuster",
                "§8Retour à 0 après maximum"
        ));

        archi.setItemMeta(meta);

        inv.setItem(10, archi);

        //
        // 🎨 COHÉRENCE RP
        //

        ItemStack style =
                new ItemStack(Material.PAINTING);

        meta = style.getItemMeta();

        meta.setDisplayName(
                "§dCohérence architecturale"
        );

        meta.setLore(List.of(

                "",

                "§7Analyse du style global",
                "§7et de l'identité visuelle.",

                "",

                "§fInspection: §e"
                        + grade.getStyle()
                        + "§7/6",

                "",

                "§b● §7Clique pour ajuster",
                "§8Retour à 0 après maximum"
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
                "§eDynamisme urbain"
        );

        meta.setLore(List.of(

                "",

                "§7Développement récent",
                "§7activité et expansion.",

                "",

                "§fInspection: §e"
                        + grade.getActivite()
                        + "§7/8",

                "",

                "§b● §7Clique pour ajuster",
                "§8Retour à 0 après maximum"
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
                "§6Financement municipal"
        );

        meta.setLore(List.of(

                "",

                "§7Stabilité économique",
                "§7et investissements urbains.",

                "",

                "§fInspection: §e"
                        + grade.getBanque()
                        + "§7/4",

                "",

                "§b● §7Clique pour ajuster",
                "§8Retour à 0 après maximum"
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
                "§bMonument emblématique"
        );

        meta.setLore(List.of(

                "",

                "§7Projet majeur représentant",
                "§7la puissance de la ville.",

                "",

                "§fInspection: §e"
                        + grade.getRemarquable()
                        + "§7/8",

                "",

                "§b● §7Clique pour ajuster",
                "§8Retour à 0 après maximum"
        ));

        remarquable.setItemMeta(meta);

        inv.setItem(14, remarquable);

        //
        // 👥 RP
        //

        ItemStack rp =
                new ItemStack(Material.WRITABLE_BOOK);

        meta = rp.getItemMeta();

        meta.setDisplayName(
                "§dOrganisation citoyenne"
        );

        meta.setLore(List.of(

                "",

                "§7Structure RP, métiers,",
                "§7districts et administration.",

                "",

                "§fInspection: §e"
                        + grade.getRp()
                        + "§7/6",

                "",

                "§b● §7Clique pour ajuster",
                "§8Retour à 0 après maximum"
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
                "§aExpansion territoriale"
        );

        meta.setLore(List.of(

                "",

                "§7Analyse de l'étendue",
                "§7et des quartiers urbains.",

                "",

                "§fInspection: §e"
                        + grade.getTaille()
                        + "§7/3",

                "",

                "§b● §7Clique pour ajuster",
                "§8Retour à 0 après maximum"
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
                "§2Investissement communautaire"
        );

        meta.setLore(List.of(

                "",

                "§7Participation au serveur",
                "§7et soutien communautaire.",

                "",

                "§fInspection: §e"
                        + grade.getVotes()
                        + "§7/5",

                "",

                "§b● §7Clique pour ajuster",
                "§8Retour à 0 après maximum"
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
                "§aRapport d'inspection"
        );

        meta.setLore(List.of(

                "",

                "§7Ville: §b" + town,

                "",

                "§fScore global: §e"
                        + grade.getTotal()
                        + "§7/50",

                "",

                "§8Commission urbaine MoodCraft"
        ));

        total.setItemMeta(meta);

        inv.setItem(25, total);

        //
        // 🧱 FILLER
        //

        ItemStack glass =
                new ItemStack(
                        Material.GRAY_STAINED_GLASS_PANE
                );

        meta = glass.getItemMeta();

        meta.setDisplayName(" ");

        glass.setItemMeta(meta);

        for (int i = 0; i < 27; i++) {

            if (inv.getItem(i) == null) {
                inv.setItem(i, glass);
            }
        }

        //
        // ✨ OPEN
        //

        p.openInventory(inv);
    }
}