package fr.moodcraft.tgrade.gui;

import fr.moodcraft.tgrade.manager.GradeManager;

import fr.moodcraft.tgrade.model.SubmissionStatus;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Bukkit;

import org.bukkit.Material;

import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class UrbanismeAdminGUI {

    //
    // 🛰 OPEN
    //

    public static void open(
            Player p
    ) {

        Inventory inv =
                Bukkit.createInventory(
                        null,
                        45,
                        "§8✦ Centre National"
                );

        long pending =
                SubmissionStorage.getAll()
                        .stream()
                        .filter(sub ->
                                sub.getStatus()
                                        == SubmissionStatus.PENDING)
                        .count();

        long approved =
                SubmissionStorage.getAll()
                        .stream()
                        .filter(sub ->
                                sub.getStatus()
                                        == SubmissionStatus.APPROVED)
                        .count();

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

            inv.setItem(slot, glass);
        }

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
                "§6✦ Centre National d'Urbanisme"
        );

        headerMeta.setLore(List.of(

                "§8----- §6Administration urbaine §8-----",

                "§7Supervision des demandes",

                "§7de projets déposées par",

                "§7les villes de MoodCraft.",

                "",

                "§7Demandes en examen : §e"
                        + pending,

                "§7Projets validés : §a"
                        + approved,

                "§7Villes suivies : §b"
                        + GradeManager.getAll().size(),

                "",

                "§7Une demande validée ouvre",

                "§7les votes et le classement hebdo.",

                "",

                "§e▶ Registres administratifs"
        ));

        header.setItemMeta(headerMeta);

        inv.setItem(4, header);

        //
        // 📋 DEMANDES
        //

        set(

                inv,

                13,

                Material.WRITABLE_BOOK,

                "§e✦ Demandes de Projets",

                "§8----- §6Inspection staff §8-----",

                "§7Consulter les demandes",

                "§7transmises à la Commission.",

                "",

                "§7Le staff peut :",

                "§8• §fse téléporter au projet",

                "§8• §avalider la demande",

                "§8• §crefuser la demande",

                "§8• §eouvrir la phase de notation",

                "",

                "§7Demandes en examen : §e"
                        + pending,

                "",

                "§e▶ Ouvrir les demandes"
        );

        //
        // ⭐ ÉVALUATIONS
        //

        set(

                inv,

                22,

                Material.ENCHANTED_BOOK,

                "§b✦ Notations & Clôtures",

                "§8----- §6Projets validés §8-----",

                "§7Gérer les villes ayant",

                "§7un projet validé en cours.",

                "",

                "§8• §fnotation staff",

                "§8• §fvotes citoyens",

                "§8• §favis des maires",

                "§8• §6clôture des votes",

                "",

                "§7La clôture verrouille les notes.",

                "§7Elle ne verse pas la subvention.",

                "",

                "§b▶ Gérer les évaluations"
        );

        //
        // 💰 PAYOUT
        //

        set(

                inv,

                31,

                Material.EMERALD_BLOCK,

                "§a✦ Subventions Urbaines",

                "§8----- §6Financement hebdomadaire §8-----",

                "§7Verser les subventions",

                "§7aux villes éligibles.",

                "",

                "§8• §fprojets validés",

                "§8• §fnotes clôturées",

                "§8• §fclassement hebdomadaire",

                "§8• §fversement municipal",

                "",

                "§7Le paiement est séparé",

                "§7de la clôture des votes.",

                "",

                "§a▶ Verser les subventions"
        );

        //
        // 🏆 CLASSEMENT
        //

        set(

                inv,

                33,

                Material.NETHER_STAR,

                "§6✦ Classement Hebdomadaire",

                "§8----- §6Registre urbain §8-----",

                "§7Consulter le classement",

                "§7des villes ayant un projet",

                "§7validé cette semaine.",

                "",

                "§7Le score est provisoire",

                "§7tant que les votes sont ouverts.",

                "",

                "§6▶ Voir le classement"
        );

        //
        // 🔙 RETOUR
        //

        set(

                inv,

                40,

                Material.ARROW,

                "§c⬅ Retour",

                "§8----- §6Commission Urbaine §8-----",

                "§7Retourner au menu",

                "§7urbain principal."
        );

        p.openInventory(inv);
    }

    //
    // 🛠 ITEM
    //

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
}