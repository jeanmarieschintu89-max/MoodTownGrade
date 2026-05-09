package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.GradeManager;
import fr.moodcraft.tgrade.manager.RateSessionManager;

import fr.moodcraft.tgrade.model.RateSession;
import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownGrade;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class RateGUI {

    public static final int ARCHI = 10;
    public static final int COHERENCE = 12;
    public static final int ACTIVITE = 14;
    public static final int BANQUE = 16;

    public static final int BUILD = 28;
    public static final int RP = 30;
    public static final int TAILLE = 32;
    public static final int VOTES = 34;

    public static final int SAVE = 49;

    public static void open(
            Player p,
            String town
    ) {

        TownGrade grade =
                GradeManager.get(town);

        RateSession session =
                RateSessionManager.create(
                        p.getUniqueId(),
                        town
                );

        TownSubmission project =
                getActiveProject(
                        town
                );

        String projectName =
                project == null
                        ? "Projet en cours"
                        : project.getBuildName();

        Inventory inv =
                Bukkit.createInventory(
                        null,
                        54,
                        "§8✦ Notation Staff"
                );

        fill(inv);

        //
        // 🏛 HEADER
        //

        ItemStack header =
                new ItemStack(
                        Material.NETHER_STAR
                );

        ItemMeta headerMeta =
                header.getItemMeta();

        headerMeta.setDisplayName(
                "§6✦ Notation Staff"
        );

        headerMeta.setLore(List.of(

                "§8----- §6Commission Urbaine §8-----",

                "§7Ville : §b" + town,

                "§7Projet : §f" + projectName,

                "",

                "§7Cette notation staff concerne",

                "§7la ville et son projet",

                "§7actuellement en développement.",

                "",

                "§7Elle compte pour",

                "§7le classement hebdomadaire.",

                "",

                "§7Note actuelle :",

                grade.getFormattedScore(),

                "",

                "§e▶ Cliquer sur un critère"
        ));

        header.setItemMeta(headerMeta);

        inv.setItem(4, header);

        set(
                inv,
                ARCHI,
                Material.QUARTZ_BLOCK,
                "§f✦ Architecture",
                "§7Qualité visuelle de la ville",
                "§7et intégration du projet.",
                session.getArchitecture(),
                10
        );

        set(
                inv,
                COHERENCE,
                Material.PAINTING,
                "§d✦ Cohérence",
                "§7Harmonie du style urbain",
                "§7et continuité avec le projet.",
                session.getCoherence(),
                6
        );

        set(
                inv,
                ACTIVITE,
                Material.BELL,
                "§e✦ Activité",
                "§7Vie locale, usage visible",
                "§7et dynamisme autour du projet.",
                session.getActivite(),
                8
        );

        set(
                inv,
                BANQUE,
                Material.GOLD_INGOT,
                "§6✦ Économie",
                "§7Capacité de la ville à soutenir",
                "§7son développement urbain.",
                session.getBanque(),
                4
        );

        set(
                inv,
                BUILD,
                Material.BRICKS,
                "§c✦ Urbanisme",
                "§7Organisation du territoire",
                "§7et qualité du développement.",
                session.getBuild(),
                8
        );

        set(
                inv,
                RP,
                Material.WRITABLE_BOOK,
                "§a✦ Roleplay",
                "§7Identité, histoire, cohérence",
                "§7et immersion municipale.",
                session.getRoleplay(),
                6
        );

        set(
                inv,
                TAILLE,
                Material.MAP,
                "§2✦ Développement",
                "§7Taille, progression et logique",
                "§7du territoire urbain.",
                session.getTaille(),
                3
        );

        set(
                inv,
                VOTES,
                Material.DIAMOND,
                "§b✦ Participation",
                "§7Prise en compte des votes",
                "§7citoyens et municipaux.",
                session.getVotes(),
                5
        );

        ItemStack save =
                new ItemStack(
                        Material.LIME_CONCRETE
                );

        ItemMeta meta =
                save.getItemMeta();

        meta.setDisplayName(
                "§a✔ Valider la notation"
        );

        meta.setLore(List.of(

                "§8----- §6Notation Staff §8-----",

                "§7Ville : §b" + town,

                "§7Projet : §f" + projectName,

                "",

                "§7Enregistre la note staff",

                "§7dans le dossier hebdomadaire.",

                "",

                "§7Le classement sera actualisé",

                "§7après sauvegarde.",

                "",

                "§a▶ Sauvegarder"
        ));

        save.setItemMeta(meta);

        inv.setItem(SAVE, save);

        p.openInventory(inv);
    }

    private static void fill(
            Inventory inv
    ) {

        ItemStack glass =
                new ItemStack(
                        Material.BLACK_STAINED_GLASS_PANE
                );

        ItemMeta meta =
                glass.getItemMeta();

        meta.setDisplayName(" ");

        glass.setItemMeta(meta);

        for (int i = 0; i < 54; i++) {

            inv.setItem(i, glass);
        }
    }

    private static void set(
            Inventory inv,
            int slot,
            Material mat,
            String name,
            String line1,
            String line2,
            int current,
            int max
    ) {

        ItemStack item =
                new ItemStack(mat);

        ItemMeta meta =
                item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(List.of(

                "§8----- §6Critère Staff §8-----",

                line1,

                line2,

                "",

                "§7Note actuelle : §e" + current + "§7/" + max,

                "§7Impact : §eClassement hebdomadaire",

                "",

                "§e▶ Cliquer pour ajuster"
        ));

        item.setItemMeta(meta);

        inv.setItem(slot, item);
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