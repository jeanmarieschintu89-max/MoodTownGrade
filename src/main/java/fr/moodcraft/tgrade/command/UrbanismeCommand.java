package fr.moodcraft.tgrade.command;

import fr.moodcraft.tgrade.gui.ClassementGUI;
import fr.moodcraft.tgrade.gui.RateGUI;
import fr.moodcraft.tgrade.gui.ReviewGUI;
import fr.moodcraft.tgrade.gui.UrbanismeMainGUI;

import fr.moodcraft.tgrade.manager.PayoutManager;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import fr.moodcraft.tgrade.towny.TownyHook;

import com.palmergames.bukkit.towny.object.Town;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class UrbanismeCommand
        implements CommandExecutor {

    @Override
    public boolean onCommand(

            CommandSender sender,

            Command command,

            String label,

            String[] args
    ) {

        //
        // 👤 PLAYER ONLY
        //

        if (!(sender instanceof Player p)) {

            sender.sendMessage(
                    "§cCommande joueur uniquement."
            );

            return true;
        }

        //
        // 🏆 /TOPVILLE
        //

        if (label.equalsIgnoreCase(
                "topville")) {

            args = new String[] {
                    "classement"
            };
        }

        //
        // 🏛 OPEN GUI
        //

        if (args.length == 0) {

            UrbanismeMainGUI.open(p);

            return true;
        }

        //
        // 🏆 CLASSEMENT
        //

        if (args[0].equalsIgnoreCase("classement")) {

            ClassementGUI.open(p);

            return true;
        }

        //
        // 🛰 STAFF COMMANDS
        //

        if (args[0].equalsIgnoreCase("review")
                || args[0].equalsIgnoreCase("noter")
                || args[0].equalsIgnoreCase("validation")
                || args[0].equalsIgnoreCase("payout")) {

            if (!p.hasPermission(
                    "moodtowngrade.staff")) {

                p.sendMessage(
                        "§cAccès refusé."
                );

                return true;
            }
        }

        //
        // 📍 REVIEW
        //

        if (args[0].equalsIgnoreCase("review")) {

            if (args.length < 2) {

                p.sendMessage(
                        "§c/urbanisme review <ville>"
                );

                return true;
            }

            ReviewGUI.open(
                    p,
                    args[1]
            );

            return true;
        }

        //
        // ⭐ NOTER
        //

        if (args[0].equalsIgnoreCase("noter")) {

            if (args.length < 2) {

                p.sendMessage(
                        "§c/urbanisme noter <ville>"
                );

                return true;
            }

            RateGUI.open(
                    p,
                    args[1]
            );

            return true;
        }

        //
        // 💰 PAYOUT
        //

        if (args[0].equalsIgnoreCase("payout")) {

            PayoutManager.payoutAll();

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§a💰 Bourses distribuées"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Toutes les villes"
            );

            p.sendMessage(
                    "§7éligibles ont reçu"
            );

            p.sendMessage(
                    "§7leur financement."
            );

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            return true;
        }

        //
        // 🛡 TOWNY CHECK
        //

        if (!TownyHook.canManage(p)) {

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§c🏛 Accès refusé"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Seuls les maires"
            );

            p.sendMessage(
                    "§7et assistants peuvent"
            );

            p.sendMessage(
                    "§7gérer les projets urbains."
            );

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            return true;
        }

        //
        // 🏛 TOWN
        //

        Town town =
                TownyHook.getTown(p);

        if (town == null) {

            p.sendMessage(
                    "§cAucune ville détectée."
            );

            return true;
        }

        //
        // ➕ PROJET
        //

        if (args[0].equalsIgnoreCase("projet")) {

            if (args.length < 2) {

                p.sendMessage(
                        "§c/urbanisme projet <nom>"
                );

                return true;
            }

            if (SubmissionStorage
                    .getTown(town.getName())
                    .size() >= 5) {

                p.sendMessage(
                        "§cMaximum 5 projets."
                );

                return true;
            }

            String name =
                    String.join(" ", args)
                            .replaceFirst(
                                    "projet ",
                                    ""
                            );

            String id =
                    UUID.randomUUID()
                            .toString()
                            .substring(0, 4)
                            .toUpperCase();

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

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§a🏗 Projet enregistré"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Projet: §e" + name
            );

            p.sendMessage(
                    "§7Ville: §b"
                            + town.getName()
            );

            p.sendMessage(
                    "§7ID: §f" + id
            );

            p.sendMessage(
                    "§7Statut: §eEN ATTENTE"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            return true;
        }

        //
        // 📜 PROJETS
        //

        if (args[0].equalsIgnoreCase("projets")) {

            List<TownSubmission> list =
                    SubmissionStorage.getTown(
                            town.getName()
                    );

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§b🏛 Projets urbains"
            );

            p.sendMessage("");

            if (list.isEmpty()) {

                p.sendMessage(
                        "§7Aucun projet."
                );

            } else {

                for (TownSubmission sub : list) {

                    p.sendMessage(
                            "§e"
                                    + sub.getBuildName()
                    );

                    p.sendMessage(
                            " §7ID: §f"
                                    + sub.getId()
                    );

                    p.sendMessage(
                            " §7Statut: "
                                    + sub.getStatus()
                                            .getDisplay()
                    );

                    p.sendMessage("");
                }
            }

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            return true;
        }

        //
        // ❌ RETIRER
        //

        if (args[0].equalsIgnoreCase("retirer")) {

            if (args.length < 2) {

                p.sendMessage(
                        "§c/urbanisme retirer <id>"
                );

                return true;
            }

            TownSubmission sub =
                    SubmissionStorage.get(
                            args[1]
                    );

            if (sub == null) {

                p.sendMessage(
                        "§cProjet introuvable."
                );

                return true;
            }

            if (!sub.getTown()
                    .equalsIgnoreCase(
                            town.getName()
                    )) {

                p.sendMessage(
                        "§cProjet invalide."
                );

                return true;
            }

            SubmissionStorage.delete(
                    sub.getId()
            );

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§cProjet supprimé"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7ID: §f"
                            + sub.getId()
            );

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            return true;
        }

        return true;
    }
}