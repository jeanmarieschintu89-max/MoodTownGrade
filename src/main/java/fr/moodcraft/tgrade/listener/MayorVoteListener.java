package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.MayorTownListGUI;
import fr.moodcraft.tgrade.gui.MayorVoteGUI;

import fr.moodcraft.tgrade.manager.GradeManager;
import fr.moodcraft.tgrade.manager.MayorVoteManager;
import fr.moodcraft.tgrade.manager.NationalScoreCalculator;

import fr.moodcraft.tgrade.model.MayorVote;
import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownGrade;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;

public class MayorVoteListener
        implements Listener {

    private static final String TITLE =
            "§8✦ Conseil des Maires";

    @EventHandler
    public void click(
            InventoryClickEvent e
    ) {

        if (!(e.getWhoClicked()
                instanceof Player p)) {
            return;
        }

        if (!e.getView()
                .getTitle()
                .equals(TITLE)) {
            return;
        }

        //
        // Important :
        // Le menu liste des villes et le menu vote ont le même titre.
        // Si l'inventaire ne contient pas TOWN_DATA, ce n'est PAS le menu de vote.
        //

        if (!isVoteInventory(e)) {
            return;
        }

        e.setCancelled(true);

        if (e.getClickedInventory() == null) {
            return;
        }

        if (e.getRawSlot() < 0
                || e.getRawSlot()
                >= e.getView()
                .getTopInventory()
                .getSize()) {
            return;
        }

        ItemStack clicked =
                e.getCurrentItem();

        if (clicked == null
                || clicked.getType()
                .isAir()) {
            return;
        }

        if (isFiller(clicked)) {
            return;
        }

        String town =
                getTown(e);

        if (town == null
                || town.isEmpty()) {
            return;
        }

        TownSubmission project =
                getActiveProject(town);

        String projectName =
                project == null
                        ? "Projet en cours"
                        : project.getBuildName();

        int slot =
                e.getRawSlot();

        if (slot == MayorVoteGUI.BACK) {

            p.playSound(
                    p.getLocation(),
                    Sound.UI_BUTTON_CLICK,
                    1f,
                    1f
            );

            MayorTownListGUI.open(p);

            return;
        }

        if (slot == MayorVoteGUI.TP_PROJECT) {

            teleportToProject(
                    p,
                    town,
                    project,
                    projectName
            );

            return;
        }

        TownGrade grade =
                GradeManager.get(
                        town
                );

        if (grade != null
                && grade.isLocked()) {

            p.closeInventory();

            p.playSound(
                    p.getLocation(),
                    Sound.ENTITY_VILLAGER_NO,
                    1f,
                    1f
            );

            p.sendMessage("");
            p.sendMessage("§8----- §6Conseil des Maires §8-----");
            p.sendMessage("§cVotes municipaux clôturés.");
            p.sendMessage("§7Ville : §b" + town);
            p.sendMessage("§7Projet : §f" + projectName);
            p.sendMessage("§7Ce dossier ne reçoit plus");
            p.sendMessage("§7de nouvelles évaluations.");
            p.sendMessage("");

            return;
        }

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

        switch (slot) {

            case MayorVoteGUI.BEAUTE ->
                    vote.setBeaute(
                            next(vote.getBeaute())
                    );

            case MayorVoteGUI.AMBIANCE ->
                    vote.setAmbiance(
                            next(vote.getAmbiance())
                    );

            case MayorVoteGUI.ACTIVITE ->
                    vote.setActivite(
                            next(vote.getActivite())
                    );

            case MayorVoteGUI.ORIGINALITE ->
                    vote.setOriginalite(
                            next(vote.getOriginalite())
                    );

            case MayorVoteGUI.POPULARITE ->
                    vote.setPopularite(
                            next(vote.getPopularite())
                    );

            case MayorVoteGUI.SAVE -> {

                vote.updateTimestamp();

                MayorVoteManager.saveVote(
                        vote
                );

                double national =
                        NationalScoreCalculator
                                .getFinalScore(town);

                double staff =
                        NationalScoreCalculator
                                .getStaffScore(town);

                double mayors =
                        NationalScoreCalculator
                                .getMayorScore(town);

                double citizens =
                        NationalScoreCalculator
                                .getCitizenScore(town);

                int total =
                        vote.getBeaute()
                                + vote.getAmbiance()
                                + vote.getActivite()
                                + vote.getOriginalite()
                                + vote.getPopularite();

                p.closeInventory();

                p.sendMessage("");
                p.sendMessage("§8----- §6Conseil des Maires §8-----");
                p.sendMessage("§6Avis municipal enregistré.");
                p.sendMessage("§7Ville : §b" + town);
                p.sendMessage("§7Projet : §f" + projectName);
                p.sendMessage("§7Score municipal : §e" + total + "§7/25");
                p.sendMessage("§7Classement hebdomadaire actualisé.");
                p.sendMessage("");
                p.sendMessage("§7Note provisoire : §e" + national + "§7/50");
                p.sendMessage(
                        "§7Détail : §6Staff §e" + staff
                                + " §8| §6Maires §e" + mayors
                                + " §8| §6Citoyens §e" + citizens
                );
                p.sendMessage("");

                p.playSound(
                        p.getLocation(),
                        Sound.UI_TOAST_CHALLENGE_COMPLETE,
                        1f,
                        1f
                );

                return;
            }

            default -> {
                return;
            }
        }

        MayorVoteManager.saveVote(
                vote
        );

        p.playSound(
                p.getLocation(),
                Sound.UI_BUTTON_CLICK,
                1f,
                1f
        );

        MayorVoteGUI.open(
                p,
                town
        );
    }

    private boolean isVoteInventory(
            InventoryClickEvent e
    ) {

        Inventory inv =
                e.getView()
                        .getTopInventory();

        ItemStack data =
                inv.getItem(
                        MayorVoteGUI.TOWN_DATA
                );

        if (data == null
                || data.getType()
                .isAir()) {
            return false;
        }

        if (!data.hasItemMeta()) {
            return false;
        }

        ItemMeta meta =
                data.getItemMeta();

        return meta != null
                && meta.hasDisplayName()
                && meta.getDisplayName()
                .startsWith("§0");
    }

    private boolean isFiller(
            ItemStack item
    ) {

        Material mat =
                item.getType();

        return mat == Material.BLACK_STAINED_GLASS_PANE
                || mat == Material.GRAY_STAINED_GLASS_PANE
                || mat == Material.LIGHT_GRAY_STAINED_GLASS_PANE
                || mat == Material.WHITE_STAINED_GLASS_PANE;
    }

    private void teleportToProject(
            Player p,
            String town,
            TownSubmission project,
            String projectName
    ) {

        if (project == null) {

            deny(
                    p,
                    "§cProjet introuvable.",
                    "§7Aucun projet validé n'est disponible."
            );

            return;
        }

        if (Bukkit.getWorld(
                project.getWorld()
        ) == null) {

            deny(
                    p,
                    "§cMonde introuvable.",
                    "§7La zone du projet est inaccessible."
            );

            return;
        }

        Location loc =
                new Location(
                        Bukkit.getWorld(
                                project.getWorld()
                        ),
                        project.getX() + 0.5,
                        project.getY() + 1,
                        project.getZ() + 0.5
                );

        p.closeInventory();

        p.teleport(loc);

        p.playSound(
                p.getLocation(),
                Sound.ENTITY_ENDERMAN_TELEPORT,
                1f,
                1f
        );

        p.sendMessage("");
        p.sendMessage("§8----- §6Conseil des Maires §8-----");
        p.sendMessage("§bTéléportation au projet.");
        p.sendMessage("§7Ville : §b" + town);
        p.sendMessage("§7Projet : §f" + projectName);
        p.sendMessage("§7Inspectez la zone puis revenez");
        p.sendMessage("§7au conseil pour voter.");
        p.sendMessage("");
    }

    private String getTown(
            InventoryClickEvent e
    ) {

        Inventory inv =
                e.getView()
                        .getTopInventory();

        ItemStack item =
                inv.getItem(
                        MayorVoteGUI.TOWN_DATA
                );

        if (item == null
                || item.getType()
                .isAir()) {
            return null;
        }

        if (!item.hasItemMeta()) {
            return null;
        }

        ItemMeta meta =
                item.getItemMeta();

        if (meta == null
                || !meta.hasDisplayName()) {
            return null;
        }

        String name =
                meta.getDisplayName();

        return name.replace(
                "§0",
                ""
        ).trim();
    }

    private TownSubmission getActiveProject(
            String town
    ) {

        TownSubmission fallback =
                null;

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

            fallback =
                    sub;
        }

        return fallback;
    }

    private void deny(
            Player p,
            String line1,
            String line2
    ) {

        p.playSound(
                p.getLocation(),
                Sound.ENTITY_VILLAGER_NO,
                1f,
                1f
        );

        p.sendMessage("");
        p.sendMessage("§8----- §6Conseil des Maires §8-----");
        p.sendMessage(line1);
        p.sendMessage(line2);
        p.sendMessage("");
    }

    private int next(
            int current
    ) {

        current++;

        if (current > 5) {
            current = 0;
        }

        return current;
    }
}