package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.RateGUI;

import fr.moodcraft.tgrade.manager.GradeManager;
import fr.moodcraft.tgrade.manager.NationalScoreCalculator;
import fr.moodcraft.tgrade.manager.RateSessionManager;

import fr.moodcraft.tgrade.model.RateSession;
import fr.moodcraft.tgrade.model.StaffVote;
import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownGrade;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;
import fr.moodcraft.tgrade.storage.VoteStorage;

import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

public class RateGUIListener
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
                .equals("§8✦ Notation Staff")) {
            return;
        }

        e.setCancelled(true);

        if (e.getRawSlot() < 0
                || e.getRawSlot() > 53) {
            return;
        }

        RateSession session =
                RateSessionManager.get(
                        p.getUniqueId()
                );

        if (session == null)
            return;

        String town =
                session.getTown();

        TownSubmission project =
                getActiveProject(
                        town
                );

        String projectName =
                project == null
                        ? "Projet en cours"
                        : project.getBuildName();

        //
        // 🏙 GRADE
        //

        TownGrade grade =
                GradeManager.get(
                        town
                );

        //
        // 🔒 LOCKED
        //

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
                    "§cVotes clôturés."
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

        int slot =
                e.getRawSlot();

        switch (slot) {

            case RateGUI.ARCHI ->
                    session.setArchitecture(
                            next(session.getArchitecture(), 10)
                    );

            case RateGUI.COHERENCE ->
                    session.setCoherence(
                            next(session.getCoherence(), 6)
                    );

            case RateGUI.ACTIVITE ->
                    session.setActivite(
                            next(session.getActivite(), 8)
                    );

            case RateGUI.BANQUE ->
                    session.setBanque(
                            next(session.getBanque(), 4)
                    );

            case RateGUI.BUILD ->
                    session.setBuild(
                            next(session.getBuild(), 8)
                    );

            case RateGUI.RP ->
                    session.setRoleplay(
                            next(session.getRoleplay(), 6)
                    );

            case RateGUI.TAILLE ->
                    session.setTaille(
                            next(session.getTaille(), 3)
                    );

            case RateGUI.VOTES ->
                    session.setVotes(
                            next(session.getVotes(), 5)
                    );

            case RateGUI.SAVE -> {

                //
                // 🏛 SAVE STAFF VOTE ONLY
                //

                StaffVote vote =
                        new StaffVote(
                                p.getUniqueId(),
                                town
                        );

                vote.setArchitecture(
                        session.getArchitecture()
                );

                vote.setStyle(
                        session.getCoherence()
                );

                vote.setActivite(
                        session.getActivite()
                );

                vote.setBanque(
                        session.getBanque()
                );

                vote.setRemarquable(
                        session.getBuild()
                );

                vote.setRp(
                        session.getRoleplay()
                );

                vote.setTaille(
                        session.getTaille()
                );

                vote.setVotes(
                        session.getVotes()
                );

                VoteStorage.saveStaffVote(vote);

                //
                // 📊 SCORES
                //

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

                double national =
                        NationalScoreCalculator
                                .getFinalScore(
                                        town
                                );

                p.closeInventory();

                //
                // 📢 MESSAGE
                //

                p.sendMessage("");
                p.sendMessage(
                        "§8----- §6Commission Urbaine §8-----"
                );
                p.sendMessage(
                        "§aNotation staff enregistrée."
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

                //
                // 🔊 SOUND
                //

                p.playSound(
                        p.getLocation(),
                        Sound.UI_TOAST_CHALLENGE_COMPLETE,
                        1f,
                        1f
                );

                return;
            }

            default -> {