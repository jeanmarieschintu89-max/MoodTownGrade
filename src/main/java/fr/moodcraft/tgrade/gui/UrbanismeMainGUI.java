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

        boolean canManage =
                TownyHook.canManage(p);

        boolean staff =
                p.hasPermission(
                        "moodtowngrade.staff"
                );

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
                        "§6✦ Commission Urbaine",
                        "§8----- §6Registre hebdomadaire §8-----",
                        "§7Suivi des projets urbains",
                        "§7et du classement MoodCraft.",
                        "",
                        "§7Ville en tête : §e" + bestTown,
                        "§7Prestige moyen : §b"
                                + RankingManager.getAverageScore()
                                + "§7/50",
                        "§7Villes classées : §a"
                                + RankingManager.getFinishedTowns(),
                        "§7Demandes en attente : §e"
                                + pending,
                        "",
                        "§e▶ Centre urbain"
                )
        );

        inv.setItem(
                20,
                item(
                        Material.BOOK,
                        "§e✦ Votes Citoyens",
                        "§8----- §6Participation publique §8-----",
                        "§7Notez les villes ayant",
                        "§7un projet en développement.",
                        "",
                        "§7Votre vote compte pour",
                        "§7le classement hebdomadaire.",
                        "",
                        "§e▶ Voter"
                )
        );

        inv.setItem(
                22,
                item(
                        Material.GOLD_INGOT,
                        "§6✦ Classement Hebdo",
                        "§8----- §6Registre urbain §8-----",
                        "§7Consultez le classement",
                        "§7des villes et projets validés.",
                        "",
                        "§7Score en direct tant que",
                        "§7les votes sont ouverts.",
                        "",
                        "§6▶ Voir le classement"
                )
        );

        if (canManage) {

            inv.setItem(
                    29,
                    item(
                            Material.NETHER_STAR,
                            "§a✦ Déposer un Projet",
                            "§8----- §6Demande urbaine §8-----",
                            "§7Créer une demande de projet",
                            "§7pour votre ville.",
                            "",
                            "§7Après validation staff,",
                            "§7les votes seront ouverts.",
                            "",
                            "§a▶ Déposer une demande"
                    )
            );
        }

        if (canManage || staff) {

            inv.setItem(
                    31,
                    item(
                            Material.GOLD_BLOCK,
                            "§6✦ Conseil des Maires",
                            "§8----- §6Vote municipal §8-----",
                            "§7Les maires donnent leur avis",
                            "§7sur les villes ayant un projet",
                            "§7validé en développement.",
                            "",
                            "§7Le vote compte pour",
                            "§7le classement hebdomadaire.",
                            "",
                            "§6▶ Accéder au conseil"
                    )
            );
        }

        if (staff) {

            inv.setItem(
                    33,
                    item(
                            Material.COMPASS,
                            "§c✦ Centre National",
                            "§8----- §6Administration §8-----",
                            "§7Gérer les demandes,",
                            "§7notations, clôtures",
                            "§7et subventions.",
                            "",
                            "§c▶ Accès staff"
                    )
            );
        }

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