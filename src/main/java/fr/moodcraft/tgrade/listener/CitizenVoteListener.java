package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.CitizenVoteGUI;

import fr.moodcraft.tgrade.manager.CitizenVoteManager;
import fr.moodcraft.tgrade.manager.GradeManager;
import fr.moodcraft.tgrade.manager.NationalScoreCalculator;

import fr.moodcraft.tgrade.model.CitizenVote;
import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownGrade;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

public class CitizenVoteListener
        implements Listener {

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
                .equals("§8✦ Vote Citoyen")) {
            return;
        }

        e.setCancelled(true);

        if (e.getCurrentItem() == null) {
            return;
        }

        if (e.getRawSlot()
                >= e.getView()
                .getTopInventory()
                .getSize()) {

            return;
        }

        String town =
                getTown(e);

        if (town == null
                || town.isEmpty()) {

            deny(
                    p,
                    "§cVille introuvable.",
                    "§7Le registre du vote est incomplet."
            );

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

        //
        // 📍 TP PROJET
        //

        if (slot == CitizenVoteGUI.TP_PROJECT) {

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
            p.sendMessage(
                    "§8----- §6Commission Urbaine §8-----"
            );
            p.sendMessage(
                    "§cVotes citoyens clôturés."
            );
            p.sendMessage(
                    "§7Ville : §b" + town
            );
            p.sendMessage(
                    "§7Projet : §f" + projectName
            );
            p.sendMessage(
                    "§7Ce dossier ne reçoit plus"
            );
            p.sendMessage(
                    "§7de nouvelles évaluations."
            );
            p.sendMessage("");

            return;
        }

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

        switch (slot) {

            case CitizenVoteGUI.BEAUTE -> {

                vote.setBeaute(
                        next(
                                vote.getBeaute()
                        )
                );

                CitizenVoteManager.saveVote(vote);
            }

            case CitizenVoteGUI.AMBIANCE -> {

                vote.setAmbiance(
                        next(
                                vote.getAmbiance()
                        )
                );

                CitizenVoteManager.saveVote(vote);
            }

            case CitizenVoteGUI.ACTIVITE -> {

                vote.setActivite(
                        next(
                                vote.getActivite()
                        )
                );

                CitizenVoteManager.saveVote(vote);
            }

            case CitizenVoteGUI.ORIGINALITE -> {

                vote.setOriginalite(
                        next(
                                vote.getOriginalite()
                        )
                );

                CitizenVoteManager.saveVote(vote);
            }

            case CitizenVoteGUI.POPULARITE -> {

                vote.setPopularite(
                        next(
                                vote.getPopularite()
                        )
                );

                CitizenVoteManager.saveVote(vote);
            }

            case CitizenVoteGUI.SAVE -> {

                vote.updateTimestamp();

                CitizenVoteManager.saveVote(
                        vote
                );

                double national =
                        NationalScoreCalculator
                                .getFinalScore(
                                        town
                                );

                double staff =
                        NationalScoreCalculator
                                .getStaffScore(
                                        town
                                );

                double mayors =
                        NationalScoreCalculator
                                .getMayorScore(
                                        town
                                );

                double citizens =
                        NationalScoreCalculator
                                .getCitizenScore(
                                        town
                                );

                p.closeInventory();

                p.sendMessage("");
                p.sendMessage(
                        "§8----- §6Commission Urbaine §8-----"
                );
                p.sendMessage(
                        "§aVote citoyen enregistré."
                );
                p.sendMessage(
                        "§7Ville : §b" + town
                );
                p.sendMessage(
                        "§7Projet : §f" + projectName
                );
                p.sendMessage(
                        "§7Le classement hebdomadaire"
                );
                p.sendMessage(
                        "§7a été actualisé."
                );
                p.sendMessage("");
                p.sendMessage(
                        "§7Note provisoire : §e" + national + "§7/50"
                );
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

        p.playSound(
                p.getLocation(),
                Sound.UI_BUTTON_CLICK,
                1f,
                1f
        );

        CitizenVoteGUI.open(
                p,
                town
        );
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
        p.sendMessage(
                "§8----- §6Votes Citoyens §8-----"
        );
        p.sendMessage(
                "§bTéléportation au projet."
        );
        p.sendMessage(
                "§7Ville : §b" + town
        );
        p.sendMessage(
                "§7Projet : §f" + projectName
        );
        p.sendMessage(
                "§7Visitez la zone puis revenez"
        );
        p.sendMessage(
                "§7au menu pour voter."
        );
        p.sendMessage("");
    }

    private String getTown(
            InventoryClickEvent e
    ) {

        if (e.getInventory()
                .getItem(4) == null) {

            return null;
        }

        if (!e.getInventory()
                .getItem(4)
                .hasItemMeta()) {

            return null;
        }

        if (e.getInventory()
                .getItem(4)
                .getItemMeta()
                .getLore() == null) {

            return null;
        }

        for (String line :
                e.getInventory()
                        .getItem(4)
                        .getItemMeta()
                        .getLore()) {

            if (line.startsWith(
                    "§7Ville : §b")) {

                return line.replace(
                        "§7Ville : §b",
                        ""
                );
            }

            if (line.startsWith(
                    "§7Ville: §b")) {

                return line.replace(
                        "§7Ville: §b",
                        ""
                );
            }
        }

        return null;
    }

    private TownSubmission getActiveProject(
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
        p.sendMessage(
                "§8----- §6Commission Urbaine §8-----"
        );
        p.sendMessage(line1);
        p.sendMessage(line2);
        p.sendMessage("");
    }

    private int next(
            int current
    ) {

        current++;

        if (current > 3) {
            current = 0;
        }

        return current;
    }
}