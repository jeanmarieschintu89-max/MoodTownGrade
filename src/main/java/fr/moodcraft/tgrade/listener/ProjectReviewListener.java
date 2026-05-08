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

            p.playSound(
                    p.getLocation(),
                    Sound.ENTITY_VILLAGER_NO,
                    1f,
                    1f
            );

            p.sendMessage("");
            p.sendMessage("§8----- §6Commission Urbaine §8-----");
            p.sendMessage("§cProjet introuvable.");
            p.sendMessage("§7Le registre national ne contient plus ce dossier.");
            p.sendMessage("");

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

                p.playSound(
                        p.getLocation(),
                        Sound.ENTITY_VILLAGER_NO,
                        1f,
                        1f
                );

                p.sendMessage("");
                p.sendMessage("§8----- §6Commission Urbaine §8-----");
                p.sendMessage("§cMonde introuvable.");
                p.sendMessage("§7La zone d'inspection est inaccessible.");
                p.sendMessage("");

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
            p.sendMessage("§fInspection nationale ouverte.");
            p.sendMessage("§7Projet: §e" + found.getBuildName());
            p.sendMessage("§7Ville: §b" + found.getTown());
            p.sendMessage("§a✔ Téléportation vers la zone du dossier.");
            p.sendMessage("");

            return;
        }

        //
        // ✅ VALIDER PERMIS
        //

        if (slot == 22) {

            if (found.getStatus()
                    == SubmissionStatus.APPROVED) {

                p.playSound(
                        p.getLocation(),
                        Sound.ENTITY_VILLAGER_NO,
                        1f,
                        1f
                );

                p.sendMessage("");
                p.sendMessage("§8----- §6Commission Urbaine §8-----");
                p.sendMessage("§fProjet déjà approuvé.");
                p.sendMessage("§7Les votes restent ouverts jusqu'à clôture.");
                p.sendMessage("");

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
            Bukkit.broadcastMessage("§fPermis urbain accordé.");
            Bukkit.broadcastMessage("§7Projet: §e" + found.getBuildName());
            Bukkit.broadcastMessage("§7Ville: §b" + found.getTown());
            Bukkit.broadcastMessage("§a✔ Le projet est ouvert aux votes cette semaine.");
            Bukkit.broadcastMessage("");

            return;
        }

        //
        // ⭐ NOTATION STAFF
        //

        if (slot == 24) {

            if (found.getStatus()
                    != SubmissionStatus.APPROVED) {

                p.playSound(
                        p.getLocation(),
                        Sound.ENTITY_VILLAGER_NO,
                        1f,
                        1f
                );

                p.sendMessage("");
                p.sendMessage("§8----- §6Commission Urbaine §8-----");
                p.sendMessage("§cNotation impossible.");
                p.sendMessage("§7Le permis doit d'abord être validé.");
                p.sendMessage("");

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
                p.sendMessage("§cLes notations sont clôturées.");
                p.sendMessage("§7Ville: §b" + found.getTown());
                p.sendMessage("§7Le registre national est verrouillé.");
                p.sendMessage("");

                return;
            }

            p.playSound(
                    p.getLocation(),
                    Sound.BLOCK_BEACON_ACTIVATE,
                    1f,
                    1f
            );

            RateGUI.open(
                    p,
                    found.getTown()
            );

            return;
        }

        //
        // 🔒 CLÔTURER LES NOTES
        //

        if (slot == 26) {

            if (found.getStatus()
                    != SubmissionStatus.APPROVED) {

                p.playSound(
                        p.getLocation(),
                        Sound.ENTITY_VILLAGER_NO,
                        1f,
                        1f
                );

                p.sendMessage("");
                p.sendMessage("§8----- §6Commission Urbaine §8-----");
                p.sendMessage("§cClôture impossible.");
                p.sendMessage("§7Le permis doit d'abord être validé.");
                p.sendMessage("");

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
                p.sendMessage("§cNotations déjà clôturées.");
                p.sendMessage("§7Ville: §b" + found.getTown());
                p.sendMessage("");

                return;
            }

            double staff =
                    NationalScoreCalculator
                            .getStaffScore(
                                    found.getTown()
                            );

            if (staff <= 0) {

                p.playSound(
                        p.getLocation(),
                        Sound.ENTITY_VILLAGER_NO,
                        1f,
                        1f
                );

                p.sendMessage("");
                p.sendMessage("§8----- §6Commission Urbaine §8-----");
                p.sendMessage("§cClôture impossible.");
                p.sendMessage("§7Aucune note staff enregistrée.");
                p.sendMessage("");

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

            p.sendMessage("");
            p.sendMessage("§8----- §6Commission Urbaine §8-----");
            p.sendMessage("§fNotations clôturées.");
            p.sendMessage("§7Ville: §b" + found.getTown());
            p.sendMessage("§7Note nationale: §e" + national + "§7/50");
            p.sendMessage("§7Influences: §6Staff §e" + staff
                    + " §8| §6Maires §e" + mayors
                    + " §8| §6Citoyens §e" + citizens);
            p.sendMessage("§a✔ Dossier validé définitivement.");
            p.sendMessage("");

            return;
        }

        //
        // ❌ REFUSER DOSSIER
        //

        if (slot == 28) {

            SubmissionStorage.delete(
                    found.getId()
            );

            p.closeInventory();

            p.playSound(
                    p.getLocation(),
                    Sound.BLOCK_ANVIL_BREAK,
                    1f,
                    0.8f
            );

            p.sendMessage("");
            p.sendMessage("§8----- §6Commission Urbaine §8-----");
            p.sendMessage("§fLe projet a été refusé.");
            p.sendMessage("§7Projet: §e" + found.getBuildName());
            p.sendMessage("§7Ville: §b" + found.getTown());
            p.sendMessage("§cDossier fermé par la Commission.");
            p.sendMessage("");

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
}