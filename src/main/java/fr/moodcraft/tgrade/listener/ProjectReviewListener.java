package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.PendingProjectsGUI;
import fr.moodcraft.tgrade.gui.RateGUI;

import fr.moodcraft.tgrade.manager.GradeManager;
import fr.moodcraft.tgrade.manager.NationalScoreCalculator;

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

public class ProjectReviewListener
        implements Listener {

    @EventHandler
    public void click(
            InventoryClickEvent e
    ) {

        if (!e.getView()
                .getTitle()
                .equalsIgnoreCase(
                        "§8✦ Inspection Nationale"
                )) {
            return;
        }

        e.setCancelled(true);

        if (!(e.getWhoClicked()
                instanceof Player p))
            return;

        if (e.getCurrentItem() == null)
            return;

        if (e.getRawSlot() >= e.getView()
                .getTopInventory()
                .getSize()) {

            return;
        }

        if (e.getInventory()
                .getItem(13) == null)
            return;

        String projectName =
                e.getInventory()
                        .getItem(13)
                        .getItemMeta()
                        .getDisplayName()
                        .replace("§f✦ §e", "");

        TownSubmission found = null;

        for (TownSubmission sub :
                SubmissionStorage.getAll()) {

            if (sub.getBuildName()
                    .equalsIgnoreCase(projectName)) {

                found = sub;

                break;
            }
        }

        if (found == null) {

            deny(
                    p,
                    "§cProjet introuvable.",
                    "§7Le registre urbain ne contient plus ce dossier."
            );

            return;
        }

        int slot =
                e.getRawSlot();

        //
        // 📍 TP INSPECTION
        //

        if (slot == 20) {

            if (Bukkit.getWorld(
                    found.getWorld()) == null) {

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
                                    found.getWorld()
                            ),

                            found.getX() + 0.5,

                            found.getY() + 1,

                            found.getZ() + 0.5
                    );

            p.teleport(loc);

            p.playSound(
                    p.getLocation(),
                    Sound.ENTITY_ENDERMAN_TELEPORT,
                    1f,
                    1f
            );

            p.sendMessage("");
            p.sendMessage("§8----- §6Commission Urbaine §8-----");
            p.sendMessage("§fInspection du projet ouverte.");
            p.sendMessage("§7Ville : §b" + found.getTown());
            p.sendMessage("§7Projet : §f" + found.getBuildName());
            p.sendMessage("§a✔ Téléportation vers la zone déclarée.");
            p.sendMessage("");

            return;
        }

        //
        // ✅ VALIDER DEMANDE
        //

        if (slot == 22) {

            if (found.getStatus()
                    == SubmissionStatus.APPROVED) {

                deny(
                        p,
                        "§cDemande déjà validée.",
                        "§7Les votes restent ouverts jusqu'à clôture."
                );

                return;
            }

            found.setStatus(
                    SubmissionStatus.APPROVED
            );

            SubmissionStorage.save(found);

            p.closeInventory();

            p.playSound(
                    p.getLocation(),
                    Sound.UI_TOAST_CHALLENGE_COMPLETE,
                    1f,
                    1f
            );

            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§8----- §6Commission Urbaine §8-----");
            Bukkit.broadcastMessage("§aDemande de projet validée.");
            Bukkit.broadcastMessage("§7Ville : §b" + found.getTown());
            Bukkit.broadcastMessage("§7Projet : §f" + found.getBuildName());
            Bukkit.broadcastMessage("§7Le dossier rejoint la phase");
            Bukkit.broadcastMessage("§7de notation publique.");
            Bukkit.broadcastMessage("");

            return;
        }

        //
        // ⭐ NOTATION STAFF
        //

        if (slot == 24) {

            if (found.getStatus()
                    != SubmissionStatus.APPROVED) {

                deny(
                        p,
                        "§cNotation impossible.",
                        "§7La demande doit d'abord être validée."
                );

                return;
            }

            TownGrade grade =
                    GradeManager.get(
                            found.getTown()
                    );

            if (grade.isLocked()) {

                p.playSound(
                        p.getLocation(),
                        Sound.ENTITY_VILLAGER_NO,
                        1f,
                        1f
                );

                p.sendMessage("");
                p.sendMessage("§8----- §6Commission Urbaine §8-----");
                p.sendMessage("§cVotes déjà clôturés.");
                p.sendMessage("§7Ville : §b" + found.getTown());
                p.sendMessage("§7Projet : §f" + found.getBuildName());
                p.sendMessage("§7Le dossier urbain ne reçoit plus");
                p.sendMessage("§7de nouvelles évaluations.");
                p.sendMessage("");

                return;
            }

            p.playSound(
                    p.getLocation(),
                    Sound.BLOCK_BEACON_ACTIVATE,
                    1f,
                    1f
            );

            p.sendMessage("");
            p.sendMessage("§8----- §6Commission Urbaine §8-----");
            p.sendMessage("§fNotation staff ouverte.");
            p.sendMessage("§7Ville : §b" + found.getTown());
            p.sendMessage("§7Projet : §f" + found.getBuildName());
            p.sendMessage("");

            RateGUI.open(
                    p,
                    found.getTown()
            );

            return;
        }

        //
        // 🔒 CLÔTURER LES VOTES
        //

        if (slot == 26) {

            if (found.getStatus()
                    != SubmissionStatus.APPROVED) {

                deny(
                        p,
                        "§cClôture impossible.",
                        "§7La demande doit d'abord être validée."
                );

                return;
            }

            TownGrade grade =
                    GradeManager.get(
                            found.getTown()
                    );

            if (grade.isLocked()) {

                p.playSound(
                        p.getLocation(),
                        Sound.ENTITY_VILLAGER_NO,
                        1f,
                        1f
                );

                p.sendMessage("");
                p.sendMessage("§8----- §6Commission Urbaine §8-----");
                p.sendMessage("§cVotes déjà clôturés.");
                p.sendMessage("§7Ville : §b" + found.getTown());
                p.sendMessage("§7Projet : §f" + found.getBuildName());
                p.sendMessage("");

                return;
            }

            double staff =
                    NationalScoreCalculator
                            .getStaffScore(
                                    found.getTown()
                            );

            if (staff <= 0) {

                deny(
                        p,
                        "§cClôture impossible.",
                        "§7Aucune note staff enregistrée."
                );

                return;
            }

            double national =
                    NationalScoreCalculator
                            .getFinalScore(
                                    found.getTown()
                            );

            double mayors =
                    NationalScoreCalculator
                            .getMayorScore(
                                    found.getTown()
                            );

            double citizens =
                    NationalScoreCalculator
                            .getCitizenScore(
                                    found.getTown()
                            );

            grade.setFinished(true);

            grade.setLocked(true);

            grade.setFinalScore(
                    national
            );

            GradeManager.save(grade);

            p.closeInventory();

            p.playSound(
                    p.getLocation(),
                    Sound.UI_TOAST_CHALLENGE_COMPLETE,
                    1f,
                    1f
            );

            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§8----- §6Commission Urbaine §8-----");
            Bukkit.broadcastMessage("§6Clôture des votes validée.");
            Bukkit.broadcastMessage("§7Ville : §b" + found.getTown());
            Bukkit.broadcastMessage("§7Projet : §f" + found.getBuildName());
            Bukkit.broadcastMessage("§7Le dossier urbain quitte la phase");
            Bukkit.broadcastMessage("§7de notation publique.");
            Bukkit.broadcastMessage("");

            p.sendMessage("§7Note finale : §e" + national + "§7/50");
            p.sendMessage("§7Détail : §6Staff §e" + staff
                    + " §8| §6Maires §e" + mayors
                    + " §8| §6Citoyens §e" + citizens);
            p.sendMessage("");

            return;
        }

        //
        // ❌ REFUSER DEMANDE
        //

        if (slot == 28) {

            found.setStatus(
                    SubmissionStatus.REJECTED
            );

            SubmissionStorage.save(found);

            p.closeInventory();

            p.playSound(
                    p.getLocation(),
                    Sound.BLOCK_ANVIL_BREAK,
                    1f,
                    0.8f
            );

            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§8----- §6Commission Urbaine §8-----");
            Bukkit.broadcastMessage("§cDemande de projet refusée.");
            Bukkit.broadcastMessage("§7Ville : §b" + found.getTown());
            Bukkit.broadcastMessage("§7Projet : §f" + found.getBuildName());
            Bukkit.broadcastMessage("§7Le dossier ne rejoint pas");
            Bukkit.broadcastMessage("§7la phase de notation publique.");
            Bukkit.broadcastMessage("");

            return;
        }

        //
        // 🔙 RETOUR
        //

        if (slot == 40) {

            p.playSound(
                    p.getLocation(),
                    Sound.UI_BUTTON_CLICK,
                    1f,
                    1f
            );

            PendingProjectsGUI.open(p);
        }
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
        p.sendMessage("§8----- §6Commission Urbaine §8-----");
        p.sendMessage(line1);
        p.sendMessage(line2);
        p.sendMessage("");
    }
}