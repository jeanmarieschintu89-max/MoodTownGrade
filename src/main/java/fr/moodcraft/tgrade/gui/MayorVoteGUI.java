package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.MayorVoteManager;
import fr.moodcraft.tgrade.manager.NationalScoreCalculator;

import fr.moodcraft.tgrade.model.MayorVote;
import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MayorVoteGUI {

    public static final int
            BEAUTE = 20;

    public static final int
            AMBIANCE = 21;

    public static final int
            ACTIVITE = 22;

    public static final int
            ORIGINALITE = 23;

    public static final int
            POPULARITE = 24;

    public static final int
            SAVE = 40;

    public static void open(
            Player p,
            String town
    ) {

        Inventory inv =
                Bukkit.createInventory(
                        null,
                        45,
                        "§8✦ Conseil des Maires"
                );

        MayorVote vote =
                MayorVoteManager.getVote(
                        p.getUniqueId(),
                        town
                );

        if (vote == null) {

            vote =
                    new MayorVote(
                            p.getUniqueId(),
                            town
                    );
        }

        TownSubmission project =
                getActiveProject(
                        town
                );

        String projectName =
                project == null
                        ? "Projet en cours"
                        : project.getBuildName();

        ItemStack glass =
                new ItemStack(
                        Material.BLACK_STAINED_GLASS_PANE
                );

        ItemMeta glassMeta =
                glass.getItemMeta();

        glassMeta.setDisplayName(
                " "
        );

        glass.setItemMeta(glassMeta);

        int[] borders = {

                0,1,2,3,4,5,6,7,8,

                9,17,

                18,26,

                27,35,

                36,37,38,39,41,42,43,44
        };

        for (int slot : borders) {

            inv.setItem(
                    slot,
                    glass
            );
        }

        set(

                inv,

                4,

                Material.NETHER_STAR,

                "§6✦ Conseil des Maires",

                "§8----- §6Vote municipal §8-----",

                "§7Ville : §b" + town,

                "§7Projet : §f" + projectName,

                "",

                "§7Donnez un avis municipal",

                "§7sur la ville et son projet",

                "§7actuellement en développement.",

                "",

                "§7Ce vote compte pour",

                "§7le classement hebdomadaire.",

                "",

                "§7Note provisoire : §e"
                        + String.format(
                        "%.1f",
                        NationalScoreCalculator
                                .getFinalScore(town)
                )
                        + "§7/50",

                "§7Votes des maires : §6"
                        + NationalScoreCalculator
                        .getMayorCount(town),

                "",

                "§6▶ Ajustez les critères"
        );

        setVote(

                inv,

                BEAUTE,

                Material.QUARTZ_BLOCK,

                "§f✦ Beauté",

                "§7Qualité visuelle de la ville",

                "§7et intégration du projet.",

                vote.getBeaute()
        );

        setVote(

                inv,

                AMBIANCE,

                Material.LANTERN,

                "§e✦ Ambiance",

                "§7Cohérence, atmosphère",

                "§7et identité municipale.",

                vote.getAmbiance()
        );

        setVote(

                inv,

                ACTIVITE,

                Material.BELL,

                "§6✦ Activité",

                "§7Dynamisme visible autour",

                "§7de la ville et du projet.",

                vote.getActivite()
        );

        setVote(

                inv,

                ORIGINALITE,

                Material.COMPASS,

                "§b✦ Originalité",

                "§7Créativité du développement",

                "§7urbain présenté.",

                vote.getOriginalite()
        );

        setVote(

                inv,

                POPULARITE,

                Material.REDSTONE,

                "§c✦ Popularité",

                "§7Pertinence générale du projet",

                "§7pour le territoire urbain.",

                vote.getPopularite()
        );

        set(

                inv,

                SAVE,

                Material.EMERALD_BLOCK,

                "§a✔ Valider le vote",

                "§8----- §6Conseil des Maires §8-----",

                "§7Ville : §b" + town,

                "§7Projet : §f" + projectName,

                "",

                "§7Enregistre votre avis municipal",

                "§7pour le classement hebdomadaire.",

                "",

                "§a▶ Sauvegarder"
        );

        p.openInventory(inv);
    }

    private static void setVote(

            Inventory inv,

            int slot,

            Material mat,

            String name,

            String line1,

            String line2,

            int value
    ) {

        set(

                inv,

                slot,

                mat,

                name,

                "§8----- §6Critère du Conseil §8-----",

                line1,

                line2,

                "",

                "§7Note actuelle : §e"
                        + value
                        + "§7/5",

                "",

                "§e▶ Cliquer pour ajuster"
        );
    }

    private static void set(

            Inventory inv,

            int slot,

            Material mat,

            String name,

            String... lore
    ) {

        ItemStack item =
                new ItemStack(mat);

        ItemMeta meta =
                item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(
                List.of(lore)
        );

        item.setItemMeta(meta);

        inv.setItem(
                slot,
                item
        );
    }

    private static TownSubmission getActiveProject(
            String town
    ) {

        TownSubmission fallback = null;

        for (TownSubmission sub :
                SubmissionStorage.getAll()) {

            if (!sub.getTown()
                    .equalsIgnoreCase(town)) {
                continue;
            }

            if (sub.getStatus()
                    == SubmissionStatus.APPROVED) {

                return sub;
            }

            fallback = sub;
        }

        return fallback;
    }
}