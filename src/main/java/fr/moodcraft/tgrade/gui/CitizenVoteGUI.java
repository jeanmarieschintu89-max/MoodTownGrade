
package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.CitizenVoteManager;
import fr.moodcraft.tgrade.manager.NationalScoreCalculator;

import fr.moodcraft.tgrade.model.CitizenVote;
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

public class CitizenVoteGUI {

    public static final int BEAUTE = 20;
    public static final int AMBIANCE = 21;
    public static final int ACTIVITE = 22;
    public static final int ORIGINALITE = 23;
    public static final int POPULARITE = 24;

    public static final int BACK = 36;
    public static final int SAVE = 40;
    public static final int TP_PROJECT = 44;

    public static void open(
            Player p,
            String town
    ) {

        Inventory inv =
                Bukkit.createInventory(
                        null,
                        45,
                        "§8✦ Vote Citoyen"
                );

        CitizenVote vote =
                CitizenVoteManager.getVote(
                        p.getUniqueId(),
                        town
                );

        if (vote == null) {

            vote =
                    new CitizenVote(
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

        int total =
                vote.getBeaute()
                        + vote.getAmbiance()
                        + vote.getActivite()
                        + vote.getOriginalite()
                        + vote.getPopularite();

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

                37,38,39,41,42,43
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
                "§e✦ Vote Citoyen",
                "§8----- §6Notation publique §8-----",
                "§7Ville : §b" + town,
                "§7Projet : §f" + projectName,
                "",
                "§7Visitez le projet puis notez",
                "§7la ville dans son ensemble.",
                "",
                "§7Votre vote compte pour",
                "§7le classement hebdomadaire.",
                "",
                "§7Votre score : §e"
                        + total
                        + "§7/15",
                "§7Note provisoire : §e"
                        + String.format(
                        "%.1f",
                        NationalScoreCalculator
                                .getFinalScore(town)
                )
                        + "§7/50",
                "§7Votes citoyens : §b"
                        + NationalScoreCalculator
                        .getCitizenCount(town),
                "",
                "§e▶ Ajustez les critères"
        );

        setVote(
                inv,
                BEAUTE,
                Material.QUARTZ_BLOCK,
                "§f✦ Visuel",
                "§7Beauté générale, détails",
                "§7et qualité des constructions.",
                vote.getBeaute()
        );

        setVote(
                inv,
                AMBIANCE,
                Material.LANTERN,
                "§e✦ Ambiance",
                "§7Vie, atmosphère",
                "§7et ressenti en ville.",
                vote.getAmbiance()
        );

        setVote(
                inv,
                ACTIVITE,
                Material.BELL,
                "§6✦ Activité",
                "§7Présence, dynamisme",
                "§7et usage des lieux.",
                vote.getActivite()
        );

        setVote(
                inv,
                ORIGINALITE,
                Material.COMPASS,
                "§b✦ Originalité",
                "§7Idées uniques",
                "§7et personnalité du projet.",
                vote.getOriginalite()
        );

        setVote(
                inv,
                POPULARITE,
                Material.REDSTONE,
                "§c✦ Avis général",
                "§7Votre impression globale",
                "§7après visite.",
                vote.getPopularite()
        );

        set(
                inv,
                BACK,
                Material.BARRIER,
                "§c✖ Retour",
                "§8----- §6Votes Citoyens §8-----",
                "§7Retour à la liste",
                "§7des villes à noter.",
                "",
                "§c▶ Retour"
        );

        set(
                inv,
                SAVE,
                Material.EMERALD_BLOCK,
                "§a✔ Valider le vote",
                "§8----- §6Votes Citoyens §8-----",
                "§7Ville : §b" + town,
                "§7Projet : §f" + projectName,
                "",
                "§7Votre score : §e"
                        + total
                        + "§7/15",
                "",
                "§7Enregistre votre vote",
                "§7pour le classement hebdomadaire.",
                "",
                "§a▶ Sauvegarder"
        );

        set(
                inv,
                TP_PROJECT,
                Material.ENDER_PEARL,
                "§b📍 Visiter le projet",
                "§8----- §6Téléportation §8-----",
                "§7Ville : §b" + town,
                "§7Projet : §f" + projectName,
                "",
                "§7Téléporte vers le projet",
                "§7à visiter avant le vote.",
                "",
                "§b▶ Se téléporter"
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
                "§8----- §6Critère Citoyen §8-----",
                line1,
                line2,
                "",
                "§7Note actuelle : §e"
                        + value
                        + "§7/3",
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