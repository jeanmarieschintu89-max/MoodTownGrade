package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.RateGUI;

import fr.moodcraft.tgrade.manager.GradeManager;
import fr.moodcraft.tgrade.manager.NationalScoreCalculator;
import fr.moodcraft.tgrade.manager.RateSessionManager;

import fr.moodcraft.tgrade.model.RateSession;
import fr.moodcraft.tgrade.model.StaffVote;
import fr.moodcraft.tgrade.model.TownGrade;

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
                .equals("§8✦ Notation Nationale")) {
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

        //
        // 🏙 GRADE
        //

        TownGrade grade =
                GradeManager.get(
                        session.getTown()
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
                    "§cLes notations sont clôturées."
            );
            p.sendMessage(
                    "§7Ville: §b" + session.getTown()
            );
            p.sendMessage(
                    "§7Le registre national a été verrouillé."
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
                                session.getTown()
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
                                        session.getTown()
                                );

                double mayors =
                        NationalScoreCalculator
                                .getMayorScore(
                                        session.getTown()
                                );

                double citizens =
                        NationalScoreCalculator
                                .getCitizenScore(
                                        session.getTown()
                                );

                double national =
                        NationalScoreCalculator
                                .getFinalScore(
                                        session.getTown()
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
                        "§fVote staff enregistré."
                );
                p.sendMessage(
                        "§7Ville: §b" + session.getTown()
                );
                p.sendMessage(
                        "§7Commission: §e" + staff + "§7/50"
                );
                p.sendMessage(
                        "§7Conseil des maires: §e" + mayors + "§7/50"
                );
                p.sendMessage(
                        "§7Votes citoyens: §e" + citizens + "§7/50"
                );
                p.sendMessage(
                        "§7Note nationale actuelle: §6" + national + "§7/50"
                );
                p.sendMessage(
                        "§a✔ Le dossier reste ouvert aux votes."
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
                return;
            }
        }

        p.playSound(
                p.getLocation(),
                Sound.UI_BUTTON_CLICK,
                1f,
                1f
        );

        RateGUI.open(
                p,
                session.getTown()
        );
    }

    private int next(
            int current,
            int max
    ) {

        current++;

        if (current > max)
            current = 0;

        return current;
    }
}