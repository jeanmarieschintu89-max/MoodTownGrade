package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.RankingManager;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownGrade;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import fr.moodcraft.tgrade.towny.TownyHook;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class UrbanismeMainGUI {

    //
    // 🏛 OPEN
    //

    public static void open(Player p) {

        Inventory inv =
                Bukkit.createInventory(

                        null,

                        54,

                        "§8✦ Commission Urbaine"
                );

        long pending =
                SubmissionStorage.getAll()
                        .stream()
                        .filter(sub ->
                                sub.getStatus()
                                        == SubmissionStatus.PENDING)
                        .count();

        TownGrade best =
                RankingManager.getBest();

        String bestTown =
                best == null
                        ? "Aucune"
                        : best.getTown();

        ItemStack glass =
                item(

                        Material.BLACK_STAINED_GLASS_PANE,

                        " "
                );

        int[] borders = {

                0,1,2,3,4,5,6,7,8,

                9,17,

                18,26,

                27,35,

                36,44,

                45,46,47,48,50,51,52,53
        };

        for (int slot : borders) {

            inv.setItem(slot, glass);
        }

        inv.setItem(

                4,

                item(

                        Material.NETHER_STAR,

                        "§6✦ Commission Urbaine Nationale",

                        "§8----- §6Registre National §8-----",

                        "§7Administration du prestige",

                        "§7urbain de MoodCraft.",

                        "",

                        "§7Capitale actuelle: §e"
                                + bestTown,

                        "§7Prestige moyen: §b"
                                + RankingManager
                                .getAverageScore()
                                + "§7/50",

                        "§7Villes classées: §a"
                                + RankingManager
                                .getFinishedTowns(),

                        "§7Dossiers actifs: §e"
                                + pending,

                        "",

                        "§e▶ Administration nationale"
                )
        );

        //
        // 👥 AVIS CITOYENS
        //

        inv.setItem(

                20,

                item(

                        Material.BOOK,

                        "§e✦ Votes Citoyens",

                        "§8----- §6Participation §8-----",

                        "§7Influencez le prestige",

                        "§7des villes par les",

                        "§7votes nationaux.",

                        "",

                        "§7Chaque avis participe",

                        "§7au classement officiel.",

                        "",

                        "§e▶ Participer aux votes"
                )
        );

        //
        // 🏆 CLASSEMENT
        //

        inv.setItem(

                22,

                item(

                        Material.GOLD_INGOT,

                        "§6✦ Palmarès National",

                        "§8----- §6Classement §8-----",

                        "§7Consultez les villes",

                        "§7les plus prestigieuses",

                        "§7de MoodCraft.",

                        "",

                        "§7Scores, influence",

                        "§7et prestige national.",

                        "",

                        "§6▶ Voir le classement"
                )
        );

        //
        // 👑 GOUVERNANCE
        //

        if (TownyHook.canManage(p)) {

            inv.setItem(

                    29,

                    item(

                            Material.NETHER_STAR,

                            "§a✦ Déposer un Projet",

                            "§8----- §6Urbanisme §8-----",

                            "§7Soumettre une nouvelle",

                            "§7construction au registre",

                            "§7national urbain.",

                            "",

                            "§a▶ Créer un dossier"
                    )
            );

            inv.setItem(

                    31,

                    item(

                            Material.GOLD_BLOCK,

                            "§6✦ Conseil des Maires",

                            "§8----- §6Gouvernance §8-----",

                            "§7Gestion des influences",

                            "§7et des décisions",

                            "§7municipales.",

                            "",

                            "§6▶ Accéder au conseil"
                    )
            );
        }

        //
        // 🛰 STAFF
        //

        if (p.hasPermission(
                "moodtowngrade.staff")) {

            inv.setItem(

                    33,

                    item(

                            Material.COMPASS,

                            "§c✦ Centre National",

                            "§8----- §6Administration §8-----",

                            "§7Gestion des inspections",

                            "§7et supervision des",

                            "§7registres urbains.",

                            "",

                            "§c▶ Accès administratif"
                    )
            );
        }

        //
        // 🔙 RETOUR
        //

        inv.setItem(

                49,

                item(

                        Material.ARROW,

                        "§c⬅ Retour",

                        "§8----- §6MoodCraft §8-----",

                        "§7Retourner au menu",

                        "§7principal du serveur."
                )
        );

        p.openInventory(inv);
    }

    //
    // 📦 ITEM
    //

    private static ItemStack item(

            Material mat,

            String name,

            String... loreLines
    ) {

        ItemStack item =
                new ItemStack(mat);

        ItemMeta meta =
                item.getItemMeta();

        if (meta == null)
            return item;

        meta.setDisplayName(name);

        List<String> lore =
                new ArrayList<>();

        for (String line : loreLines) {

            lore.add(line);
        }

        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }
}