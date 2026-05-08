
package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.manager.ProjectInputManager;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import fr.moodcraft.tgrade.towny.TownyHook;

import com.palmergames.bukkit.towny.object.Town;

import org.bukkit.Bukkit;
import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class ProjectChatListener
        implements Listener {

    //
    // 💬 CHAT
    //

    @EventHandler
    public void chat(
            AsyncPlayerChatEvent e
    ) {

        Player p =
                e.getPlayer();

        if (!ProjectInputManager.isWaiting(
                p.getUniqueId()
        )) {
            return;
        }

        e.setCancelled(true);

        String name =
                e.getMessage().trim();

        //
        // ❌ ANNULER
        //

        if (name.equalsIgnoreCase(
                "annuler")) {

            ProjectInputManager.stop(
                    p.getUniqueId()
            );

            Bukkit.getScheduler()
                    .runTask(

                            Bukkit.getPluginManager()
                                    .getPlugin("MoodTownGrade"),

                            () -> {

                                p.playSound(

                                        p.getLocation(),

                                        Sound.ENTITY_VILLAGER_NO,

                                        1f,

                                        1f
                                );

                                p.sendMessage("");
                                p.sendMessage(
                                        "§8----- §cProjet Annulé §8-----"
                                );
                                p.sendMessage("");
                                p.sendMessage(
                                        "§fCréation du dossier annulée."
                                );
                                p.sendMessage(
                                        "§7Aucun projet n'a été envoyé."
                                );
                                p.sendMessage("");
                            }
                    );

            return;
        }

        ProjectInputManager.stop(
                p.getUniqueId()
        );

        //
        // 🏛 TOWN
        //

        Town town =
                TownyHook.getTown(p);

        if (town == null) {

            Bukkit.getScheduler()
                    .runTask(

                            Bukkit.getPluginManager()
                                    .getPlugin("MoodTownGrade"),

                            () -> {

                                p.sendMessage("");
                                p.sendMessage(
                                        "§8----- §cProjet Impossible §8-----"
                                );
                                p.sendMessage("");
                                p.sendMessage(
                                        "§fAucune ville détectée."
                                );
                                p.sendMessage(
                                        "§7Rejoins une ville pour déposer un projet."
                                );
                                p.sendMessage("");
                            }
                    );

            return;
        }

        //
        // ❌ EMPTY
        //

        if (name.isEmpty()) {

            Bukkit.getScheduler()
                    .runTask(

                            Bukkit.getPluginManager()
                                    .getPlugin("MoodTownGrade"),

                            () -> {

                                p.sendMessage("");
                                p.sendMessage(
                                        "§8----- §cNom Invalide §8-----"
                                );
                                p.sendMessage("");
                                p.sendMessage(
                                        "§fLe nom du projet est vide."
                                );
                                p.sendMessage(
                                        "§7Relance la création depuis le menu."
                                );
                                p.sendMessage("");
                            }
                    );

            return;
        }

        //
        // ❌ TOO LONG
        //

        if (name.length() > 32) {

            Bukkit.getScheduler()
                    .runTask(

                            Bukkit.getPluginManager()
                                    .getPlugin("MoodTownGrade"),

                            () -> {

                                p.sendMessage("");
                                p.sendMessage(
                                        "§8----- §cNom Trop Long §8-----"
                                );
                                p.sendMessage("");
                                p.sendMessage(
                                        "§fMaximum: §e32 caractères"
                                );
                                p.sendMessage(
                                        "§7Choisis un nom plus court."
                                );
                                p.sendMessage("");
                            }
                    );

            return;
        }

        //
        // 📊 LIMIT
        //

        long pending =
                SubmissionStorage.getTown(
                        town.getName()
                ).stream()
                        .filter(sub ->
                                sub.getStatus()
                                        == SubmissionStatus.PENDING)
                        .count();

        //
        // ❌ MAX PROJECTS
        //

        if (pending >= 5) {

            Bukkit.getScheduler()
                    .runTask(

                            Bukkit.getPluginManager()
                                    .getPlugin("MoodTownGrade"),

                            () -> {

                                p.sendMessage("");
                                p.sendMessage(
                                        "§8----- §cLimite Atteinte §8-----"
                                );
                                p.sendMessage("");
                                p.sendMessage(
                                        "§fTa ville a déjà §e5 projets§f en attente."
                                );
                                p.sendMessage(
                                        "§7Attends une validation de la commission."
                                );
                                p.sendMessage("");
                            }
                    );

            return;
        }

        //
        // 🆔 ID
        //

        String id =
                UUID.randomUUID()
                        .toString()
                        .substring(0, 4)
                        .toUpperCase();

        //
        // 🏗 CREATE
        //

        TownSubmission sub =
                new TownSubmission(

                        id,

                        town.getName(),

                        name,

                        p.getWorld().getName(),

                        p.getLocation().getBlockX(),

                        p.getLocation().getBlockY(),

                        p.getLocation().getBlockZ(),

                        p.getUniqueId(),

                        System.currentTimeMillis(),

                        SubmissionStatus.PENDING
                );

        SubmissionStorage.save(sub);

        //
        // 🔔 MAIN THREAD
        //

        Bukkit.getScheduler()
                .runTask(

                        Bukkit.getPluginManager()
                                .getPlugin("MoodTownGrade"),

                        () -> {

                            p.playSound(

                                    p.getLocation(),

                                    Sound.UI_TOAST_CHALLENGE_COMPLETE,

                                    1f,

                                    1f
                            );

                            p.sendMessage("");
                            p.sendMessage(
                                    "§8----- §aProjet Enregistré §8-----"
                            );
                            p.sendMessage("");
                            p.sendMessage(
                                    "§fProjet: §e" + name
                            );
                            p.sendMessage(
                                    "§fVille: §b" + town.getName()
                            );
                            p.sendMessage(
                                    "§fID: §7" + id
                            );
                            p.sendMessage("");
                            p.sendMessage(
                                    "§7Statut: §6En attente de validation"
                            );
                            p.sendMessage(
                                    "§a✔ Transmis à la commission"
                            );
                            p.sendMessage("");
                        }
                );
    }
}