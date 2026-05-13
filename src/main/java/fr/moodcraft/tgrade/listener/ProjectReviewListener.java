package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.PendingProjectsGUI;
import fr.moodcraft.tgrade.gui.ProjectReviewGUI;
import fr.moodcraft.tgrade.gui.RateGUI;
import fr.moodcraft.tgrade.gui.holder.ProjectReviewHolder;
import fr.moodcraft.tgrade.manager.GradeManager;
import fr.moodcraft.tgrade.manager.NationalScoreCalculator;
import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownGrade;
import fr.moodcraft.tgrade.model.TownSubmission;
import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
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

        if (!e.getView().getTitle().equals(ProjectReviewGUI.TITLE)) {
            return;
        }

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player p)) {
            return;
        }

        if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) {
            return;
        }

        if (!(e.getView().getTopInventory().getHolder() instanceof ProjectReviewHolder holder)) {

            deny(
                    p,
                    "§cDossier introuvable.",
                    "§7Le menu ne contient aucun dossier valide."
            );

            return;
        }

        TownSubmission submission =
                refresh(holder.getSubmission());

        if (submission == null) {

            deny(
                    p,
                    "§cProjet introuvable.",
                    "§7Le dossier n'existe plus dans les registres."
            );

            return;
        }

        int slot =
                e.getRawSlot();

        if (slot == 20) {
            teleportToProject(p, submission);
            return;
        }

        if (slot == 22) {
            approve(p, submission);
            return;
        }

        if (slot == 24) {
            openStaffRate(p, submission);
            return;
        }

        if (slot == 26) {
            closeVotes(p, submission);
            return;
        }

        if (slot == 28) {
            reject(p, submission);
            return;
        }

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

    private static TownSubmission refresh(
            TownSubmission submission
    ) {

        if (submission == null) {
            return null;
        }

        TownSubmission stored =
                SubmissionStorage.get(
                        submission.getId()
                );

        if (stored == null) {
            return submission;
        }

        return stored;
    }

    private static void teleportToProject(
            Player p,
            TownSubmission submission
    ) {

        World world =
                Bukkit.getWorld(
                        submission.getWorld()
                );

        if (world == null) {

            deny(
                    p,
                    "§cMonde introuvable.",
                    "§7La zone du projet est inaccessible."
            );

            return;
        }

        Location location =
                new Location(
                        world,
                        submission.getX() + 0.5,
                        submission.getY() + 1.0,
                        submission.getZ() + 0.5
                );

        p.teleport(location);

        p.playSound(
                p.getLocation(),
                Sound.ENTITY_ENDERMAN_TELEPORT,
                1f,
                1f
        );

        p.sendMessage("");
        p.sendMessage("§8----- §6✦ Commission Urbaine ✦ §8-----");
        p.sendMessage("");
        p.sendMessage("§fInspection du projet ouverte.");
        p.sendMessage("");
        p.sendMessage("§7Ville: §b" + submission.getTown());
        p.sendMessage("§7Projet: §e" + submission.getBuildName());
        p.sendMessage("");
        p.sendMessage("§8• §7Téléportation vers la zone déclarée");
        p.sendMessage("§8-----------------------------");
    }

    private static void approve(
            Player p,
            TownSubmission submission
    ) {

        if (submission.getStatus() == SubmissionStatus.APPROVED) {

            deny(
                    p,
                    "§cDossier déjà validé.",
                    "§7La phase de notation est déjà ouverte."
            );

            return;
        }

        if (submission.getStatus() == SubmissionStatus.FINISHED) {

            deny(
                    p,
                    "§cDossier clôturé.",
                    "§7Les votes sont déjà terminés."
            );

            return;
        }

        submission.setStatus(
                SubmissionStatus.APPROVED
        );

        SubmissionStorage.save(submission);

        p.closeInventory();

        p.playSound(
                p.getLocation(),
                Sound.UI_TOAST_CHALLENGE_COMPLETE,
                1f,
                1f
        );

        broadcast(
                "§a✔ §fDemande de projet validée.",
                submission,
                "§8• §7Le dossier rejoint la notation publique",
                "§8• §7Il peut participer au classement hebdo"
        );
    }

    private static void openStaffRate(
            Player p,
            TownSubmission submission
    ) {

        if (submission.getStatus() != SubmissionStatus.APPROVED) {

            deny(
                    p,
                    "§cNotation impossible.",
                    "§7La demande doit d'abord être validée."
            );

            return;
        }

        TownGrade grade =
                GradeManager.get(
                        submission.getTown()
                );

        if (grade != null
                && grade.isLocked()) {

            denyLocked(
                    p,
                    submission
            );

            return;
        }

        p.playSound(
                p.getLocation(),
                Sound.BLOCK_BEACON_ACTIVATE,
                1f,
                1f
        );

        p.sendMessage("");
        p.sendMessage("§8----- §6✦ Commission Urbaine ✦ §8-----");
        p.sendMessage("");
        p.sendMessage("§fNotation staff ouverte.");
        p.sendMessage("");
        p.sendMessage("§7Ville: §b" + submission.getTown());
        p.sendMessage("§7Projet: §e" + submission.getBuildName());
        p.sendMessage("");
        p.sendMessage("§8• §7Barème national chargé");
        p.sendMessage("§8-----------------------------");

        RateGUI.open(
                p,
                submission.getTown()
        );
    }

    private static void closeVotes(
            Player p,
            TownSubmission submission
    ) {

        if (submission.getStatus() != SubmissionStatus.APPROVED) {

            deny(
                    p,
                    "§cClôture impossible.",
                    "§7La demande doit d'abord être validée."
            );

            return;
        }

        TownGrade grade =
                GradeManager.get(
                        submission.getTown()
                );

        if (grade == null) {

            deny(
                    p,
                    "§cDossier introuvable.",
                    "§7La note nationale n'existe plus."
            );

            return;
        }

        if (grade.isLocked()) {

            denyLocked(
                    p,
                    submission
            );

            return;
        }

        double staffScore =
                NationalScoreCalculator.getStaffScore(
                        submission.getTown()
                );

        if (staffScore <= 0) {

            deny(
                    p,
                    "§cClôture impossible.",
                    "§7Aucune note staff enregistrée."
            );

            return;
        }

        double finalScore =
                NationalScoreCalculator.getFinalScore(
                        submission.getTown()
                );

        double mayorScore =
                NationalScoreCalculator.getMayorScore(
                        submission.getTown()
                );

        double citizenScore =
                NationalScoreCalculator.getCitizenScore(
                        submission.getTown()
                );

        grade.setFinished(true);
        grade.setLocked(true);
        grade.setFinalScore(finalScore);

        GradeManager.save(grade);

        submission.setStatus(
                SubmissionStatus.FINISHED
        );

        SubmissionStorage.save(submission);

        p.closeInventory();

        p.playSound(
                p.getLocation(),
                Sound.UI_TOAST_CHALLENGE_COMPLETE,
                1f,
                1f
        );

        broadcast(
                "§6✦ §fClôture des votes validée.",
                submission,
                "§8• §7Note finale: §e" + oneDecimal(finalScore) + "/50",
                "§8• §7Staff §e" + oneDecimal(staffScore)
                        + " §8| §7Maires §e" + oneDecimal(mayorScore)
                        + " §8| §7Citoyens §e" + oneDecimal(citizenScore)
        );
    }

    private static void reject(
            Player p,
            TownSubmission submission
    ) {

        if (submission.getStatus() == SubmissionStatus.FINISHED) {

            deny(
                    p,
                    "§cDossier clôturé.",
                    "§7Les votes sont déjà terminés."
            );

            return;
        }

        submission.setStatus(
                SubmissionStatus.REJECTED
        );

        SubmissionStorage.save(submission);

        p.closeInventory();

        p.playSound(
                p.getLocation(),
                Sound.BLOCK_ANVIL_BREAK,
                1f,
                0.8f
        );

        broadcast(
                "§c✘ §fDemande de projet refusée.",
                submission,
                "§8• §7Le dossier ne rejoint pas la notation publique",
                "§8• §7La ville pourra déposer un nouveau dossier"
        );
    }

    private static void denyLocked(
            Player p,
            TownSubmission submission
    ) {

        p.playSound(
                p.getLocation(),
                Sound.ENTITY_VILLAGER_NO,
                1f,
                1f
        );

        p.sendMessage("");
        p.sendMessage("§8----- §6✦ Commission Urbaine ✦ §8-----");
        p.sendMessage("");
        p.sendMessage("§cVotes déjà clôturés.");
        p.sendMessage("");
        p.sendMessage("§7Ville: §b" + submission.getTown());
        p.sendMessage("§7Projet: §e" + submission.getBuildName());
        p.sendMessage("");
        p.sendMessage("§8• §7Ce dossier ne reçoit plus de notes");
        p.sendMessage("§8-----------------------------");
    }

    private static void deny(
            Player p,
            String message,
            String detail
    ) {

        p.playSound(
                p.getLocation(),
                Sound.ENTITY_VILLAGER_NO,
                1f,
                1f
        );

        p.sendMessage("");
        p.sendMessage("§8----- §6✦ Commission Urbaine ✦ §8-----");
        p.sendMessage("");
        p.sendMessage(message);
        p.sendMessage(detail);
        p.sendMessage("");
        p.sendMessage("§8• §7Service officiel de §aMood§6Craft");
        p.sendMessage("§8-----------------------------");
    }

    private static void broadcast(
            String message,
            TownSubmission submission,
            String line1,
            String line2
    ) {

        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§8----- §6✦ Commission Urbaine ✦ §8-----");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(message);
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage("§7Ville: §b" + submission.getTown());
        Bukkit.broadcastMessage("§7Projet: §e" + submission.getBuildName());
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(line1);
        Bukkit.broadcastMessage(line2);
        Bukkit.broadcastMessage("§8-----------------------------");
    }

    private static String oneDecimal(
            double value
    ) {

        return String.format(
                "%.1f",
                value
        );
    }
}
