package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.GradeManager;
import fr.moodcraft.tgrade.manager.RateSessionManager;

import fr.moodcraft.tgrade.model.RateSession;
import fr.moodcraft.tgrade.model.TownGrade;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class RateGUI {

    //
    // ⭐ OPEN
    //

    public static void open(
            Player p,
            String town
    ) {

        //
        // 📊 EXISTING GRADE
        //

        TownGrade grade =
                GradeManager.get(town);

        //
        // 🧠 SESSION
        //

        RateSession session =
                RateSessionManager.create(

                        p.getUniqueId(),
                        town
                );

        //
        // 🔄 LOAD CURRENT SCORES
        //

        session.setArchitecture(
                grade.getArchitecture()
        );

        session.setCoherence(
                grade.getStyle()
        );

        session.setActivite(
                grade.getActivite()
        );

        session.setBanque(
                grade.getBanque()
        );

        session.setBuild(
                grade.getRemarquable()
        );

        session.setRoleplay(
                grade.getRp()
        );

        session.setTaille(
                grade.getTaille()
        );

        session.setVotes(
                grade.getVotes()
        );

        //
        // 📦 INVENTORY
        //

        Inventory inv =
                Bukkit.createInventory(

                        null,

                        54,

                        "§8✦ Notation Nationale"
                );

        //
        // 🌌 GLASS
        //

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

        //
        // 🧱 BORDERS
        //

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

        //
        // 🏛 HEADER
        //

        ItemStack info =
                new ItemStack(
                        Material.NETHER_STAR
                );

        ItemMeta infoMeta =
                info.getItemMeta();

        infoMeta.setDisplayName(
                "§6✦ Inspection de " + town
        );

        infoMeta.setLore(List.of(

                "§8━━━━━━━━━━━━━━━━",

                "§7Commission Nationale",

                "§7d'évaluation urbaine.",

                "",

                "§7Prestige actuel:",
                grade.getFormattedScore(),

                "",

                "§7Classement:",
                grade.getRank(),

                "",

                "§7Appréciation:",
                grade.getAppreciation(),

                "",

                "§7Financement estimé:",
                "§a" + grade.getPayout() + "$"
        ));

        info.setItemMeta(infoMeta);

        inv.setItem(4, info);

        //
        // 🏗 ARCHITECTURE
        //

        set(

                inv,

                10,

                Material.QUARTZ_BLOCK,

                "§f🏗 Architecture Nationale",

                session.getArchitecture(),

                10,

                "§7Qualité architecturale",

                "§7et cohérence des bâtiments."
        );

        //
        // 🎨 COHERENCE
        //

        set(

                inv,

                12,

                Material.PAINTING,

                "§d🎨 Harmonie Urbaine",

                session.getCoherence(),

                6,

                "§7Organisation générale",

                "§7et cohérence visuelle."
        );

        //
        // ⚡ ACTIVITE
        //

        set(

                inv,

                14,

                Material.BELL,

                "§e⚡ Activité Citadine",

                session.getActivite(),

                8,

                "§7Présence de joueurs",

                "§7et dynamisme urbain."
        );

        //
        // 💰 BANQUE
        //

        set(

                inv,

                16,

                Material.GOLD_INGOT,

                "§6💰 Richesse Municipale",

                session.getBanque(),

                4,

                "§7Puissance économique",

                "§7de la ville inspectée."
        );

        //
        // 🏛 BUILD
        //

        set(

                inv,

                28,

                Material.BRICKS,

                "§c🏛 Build Remarquable",

                session.getBuild(),

                8,

                "§7Présence de structures",

                "§7uniques et mémorables."
        );

        //
        // 🎭 RP
        //

        set(

                inv,

                30,

                Material.WRITABLE_BOOK,

                "§a🎭 Immersion RolePlay",

                session.getRoleplay(),

                6,

                "§7Qualité RP générale",

                "§7de la ville."
        );

        //
        // 🌍 TAILLE
        //

        set(

                inv,

                32,

                Material.MAP,

                "§2🌍 Expansion Territoriale",

                session.getTaille(),

                3,

                "§7Importance et ampleur",

                "§7du territoire urbain."
        );

        //
        // 🗳 VOTES
        //

        set(

                inv,

                34,

                Material.DIAMOND,

                "§b🗳 Popularité Nationale",

                session.getVotes(),

                5,

                "§7Avis général et",

                "§7attractivité de la ville."
        );

        //
        // ✅ SAVE
        //

        ItemStack save =
                new ItemStack(
                        Material.LIME_CONCRETE
                );

        ItemMeta saveMeta =
                save.getItemMeta();

        saveMeta.setDisplayName(
                "§a✅ Publier l'inspection"
        );

        saveMeta.setLore(List.of(

                "§8━━━━━━━━━━━━━━━━",

                "§7Mettre à jour le rapport",

                "§7officiel de la ville.",

                "",

                "§7Le classement national",

                "§7sera recalculé.",

                "",

                "§a▶ Sauvegarder l'inspection"
        ));

        save.setItemMeta(saveMeta);

        inv.setItem(49, save);

        //
        // 🚀 OPEN
        //

        p.openInventory(inv);
    }

    //
    // ⭐ ITEM
    //

    private static void set(

            Inventory inv,

            int slot,

            Material mat,

            String name,

            int current,

            int max,

            String line1,

            String line2
    ) {

        ItemStack item =
                new ItemStack(mat);

        ItemMeta meta =
                item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(List.of(

                "§8━━━━━━━━━━━━━━━━",

                line1,
                line2,

                "",

                "§7Note actuelle:",

                "§6"
                        + current
                        + "§7/"
                        + max,

                "",

                "§e▶ Modifier la note",

                "§7Retour à §c0",

                "§7après le maximum"
        ));

        item.setItemMeta(meta);

        inv.setItem(slot, item);
    }
}