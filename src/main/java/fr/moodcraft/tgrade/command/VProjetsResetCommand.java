
package fr.moodcraft.tgrade.command;

import fr.moodcraft.tgrade.manager.GradeManager;

import fr.moodcraft.tgrade.model.TownGrade;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;
import fr.moodcraft.tgrade.storage.VoteStorage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

public class VProjetsResetCommand
        implements CommandExecutor {

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        if (!(sender instanceof Player p)) {

            sender.sendMessage(
                    "§cCommande joueur uniquement."
            );

            return true;
        }

        if (!p.hasPermission(
                "moodtowngrade.staff")) {

            p.sendMessage("");
            p.sendMessage(
                    "§8----- §6Commission Urbaine §8-----"
            );
            p.sendMessage(
                    "§cAccès refusé."
            );
            p.sendMessage(
                    "§7Commande réservée à l'administration nationale."
            );
            p.sendMessage("");

            return true;
        }

        for (TownSubmission sub :
                SubmissionStorage.getAll()) {

            SubmissionStorage.delete(
                    sub.getId()
            );
        }

        for (TownGrade grade :
                GradeManager.getAll()) {

            grade.setArchitecture(0);
            grade.setStyle(0);
            grade.setActivite(0);
            grade.setBanque(0);
            grade.setRemarquable(0);
            grade.setRp(0);
            grade.setTaille(0);
            grade.setVotes(0);

            grade.setLocked(false);
            grade.setFinished(false);
            grade.setFinalScore(0);
            grade.setPayoutClaimed(false);

            GradeManager.save(grade);
        }

        VoteStorage.clearAll();

        p.sendMessage("");
        p.sendMessage(
                "§8----- §6Commission Urbaine §8-----"
        );
        p.sendMessage(
                "§fRéinitialisation nationale terminée."
        );
        p.sendMessage(
                "§7Tous les projets urbains ont été supprimés."
        );
        p.sendMessage(
                "§7Les votes, notes et verrouillages sont remis à zéro."
        );
        p.sendMessage(
                "§a✔ Nouvelle semaine urbaine prête."
        );
        p.sendMessage("");

        return true;
    }
}