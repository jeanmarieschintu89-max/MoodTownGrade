package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.manager.ProjectDepositSessionManager;
import fr.moodcraft.tgrade.manager.ProjectDepositSessionManager.Session;
import fr.moodcraft.tgrade.manager.ProjectDepositSessionManager.Step;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class ProjectDepositChatListener
        implements Listener {

    private static final int MAX_NAME_LENGTH = 32;
    private static final int MAX_DESCRIPTION_LENGTH = 120;

    @EventHandler
    public void onChat(
            AsyncPlayerChatEvent e
    ) {

        Player p =
                e.getPlayer();

        if (!ProjectDepositSessionManager.has(p)) {
            return;
        }

        e.setCancelled(true);

        String message =
                e.getMessage().trim();

        Bukkit.getScheduler().runTask(
                Bukkit.getPluginManager()
                        .getPlugin("MoodTownGrade"),
                () -> handle(p, message)
        );
    }

    private void handle(
            Player p,
            String message
    ) {

        Session session =
                ProjectDepositSessionManager.get(p);

        if (session == null) {
            return;
        }

        if (message.equalsIgnoreCase("annuler")
                || message.equalsIgnoreCase("cancel")) {

            ProjectDepositSessionManager.remove(p);

            p.sendMessage("");
            p.sendMessage("§8----- §6Commission Urbaine §8-----");
            p.sendMessage("§cDépôt annulé.");
            p.sendMessage("§7Aucun dossier n'a été transmis.");
            p.sendMessage("");

            return;
        }

        if (session.getStep() == Step.NAME) {

            if (message.length() < 3) {

                p.sendMessage("");
                p.sendMessage("§8----- §6Commission Urbaine §8-----");
                p.sendMessage("§cNom trop court.");
                p.sendMessage("§7Minimum : §e3 caractères§7.");
                p.sendMessage("");

                return;
            }

            if (message.length() > MAX_NAME_LENGTH) {

                p.sendMessage("");
                p.sendMessage("§8----- §6Commission Urbaine §8-----");
                p.sendMessage("§cNom trop long.");
                p.sendMessage("§7Maximum : §e" + MAX_NAME_LENGTH + " caractères§7.");
                p.sendMessage("");

                return;
            }

            session.setProjectName(message);
            session.setStep(Step.DESCRIPTION);

            p.sendMessage("");
            p.sendMessage("§8----- §6Commission Urbaine §8-----");
            p.sendMessage("§fProjet : §e" + message);
            p.sendMessage("§7Écrivez maintenant la §edescription §7dans le chat.");
            p.sendMessage("§7Maximum : §e" + MAX_DESCRIPTION_LENGTH + " caractères§7.");
            p.sendMessage("§8Tapez §cannuler §8pour arrêter.");
            p.sendMessage("");

            return;
        }

        if (session.getStep() == Step.DESCRIPTION) {

            if (message.length() < 10) {

                p.sendMessage("");
                p.sendMessage("§8----- §6Commission Urbaine §8-----");
                p.sendMessage("§cDescription trop courte.");
                p.sendMessage("§7Minimum : §e10 caractères§7.");
                p.sendMessage("");

                return;
            }

            if (message.length() > MAX_DESCRIPTION_LENGTH) {

                p.sendMessage("");
                p.sendMessage("§8----- §6Commission Urbaine §8-----");
                p.sendMessage("§cDescription trop longue.");
                p.sendMessage("§7Maximum : §e" + MAX_DESCRIPTION_LENGTH + " caractères§7.");
                p.sendMessage("§7Actuel : §c" + message.length() + " caractères§7.");
                p.sendMessage("");

                return;
            }

            Location loc =
                    p.getLocation();

            String id =
                    UUID.randomUUID()
                            .toString()
                            .substring(0, 4)
                            .toUpperCase();

            TownSubmission sub =
                    new TownSubmission(
                            id,
                            session.getTown(),
                            session.getProjectName(),
                            message,
                            p.getWorld().getName(),
                            loc.getBlockX(),
                            loc.getBlockY(),
                            loc.getBlockZ(),
                            p.getUniqueId(),
                            System.currentTimeMillis(),
                            SubmissionStatus.PENDING
                    );

            SubmissionStorage.save(sub);
            ProjectDepositSessionManager.remove(p);

            p.sendMessage("");
            p.sendMessage("§8----- §6Commission Urbaine §8-----");
            p.sendMessage("§a✔ Projet déposé.");
            p.sendMessage("§7Ville : §b" + sub.getTown());
            p.sendMessage("§7Projet : §f" + sub.getBuildName());
            p.sendMessage("§7ID : §f" + sub.getId());
            p.sendMessage("§7Statut : §eInspection staff requise.");
            p.sendMessage("");

            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§8----- §6Commission Urbaine §8-----");
            Bukkit.broadcastMessage("§fNouveau projet déposé par §b" + p.getName());
            Bukkit.broadcastMessage("§7Ville : §e" + sub.getTown());
            Bukkit.broadcastMessage("§7Projet : §f" + sub.getBuildName());
            Bukkit.broadcastMessage("§7Description : §f" + sub.getDescription());
            Bukkit.broadcastMessage("§a✔ Dossier transmis à l'administration nationale.");
            Bukkit.broadcastMessage("");
        }
    }
}