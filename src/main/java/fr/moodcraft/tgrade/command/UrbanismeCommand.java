package fr.moodcraft.tgrade.command;

import fr.moodcraft.tgrade.gui.CitizenTownListGUI;
import fr.moodcraft.tgrade.gui.ClassementGUI;
import fr.moodcraft.tgrade.gui.PendingProjectsGUI;
import fr.moodcraft.tgrade.gui.RateGUI;
import fr.moodcraft.tgrade.gui.ReviewGUI;
import fr.moodcraft.tgrade.gui.UrbanismeMainGUI;

import fr.moodcraft.tgrade.manager.GradeManager;
import fr.moodcraft.tgrade.manager.PayoutManager;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownGrade;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import fr.moodcraft.tgrade.task.WeeklyResetTask;

import fr.moodcraft.tgrade.towny.TownyHook;

import com.palmergames.bukkit.towny.object.Town;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

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

        if (command.getName()
                .equalsIgnoreCase(
                        "topville")) {

            ClassementGUI.open(p);

            return true;
        }

        //
        // 🏗 /PROJET
        //

        if (command.getName()
                .equalsIgnoreCase(
                        "projet")) {

            if (!TownyHook.canManage(p)) {

                p.sendMessage("");
                p.sendMessage(
                        "§8----- §6Commission Urbaine §8-----"
                );
                p.sendMessage(
                        "§cAccès refusé."
                );
                p.sendMessage(
                        "§7Seuls les maires et assistants peuvent gérer les projets."
                );
                p.sendMessage("");

                return true;
            }

            PendingProjectsGUI.open(p);

            return true;
        }

        //
        // 👥 /VPROJET
        //

        if (command.getName()
                .equalsIgnoreCase(
                        "vprojet")) {

            CitizenTownListGUI.open(p);

            return true;
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
                || args[0].equalsIgnoreCase("payout")
                || args[0].equalsIgnoreCase("delete")
                || args[0].equalsIgnoreCase("resetweek")
                || args[0].equalsIgnoreCase("resetville")) {

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
                        "§7Ce registre est réservé à l'administration nationale."
                );
                p.sendMessage("");

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
                    "§8----- §6Commission Urbaine §8-----"
            );
            p.sendMessage(
                    "§fFinancements urbains distribués."
            );
            p.sendMessage(
                    "§a✔ Les bourses nationales ont été versées."
            );
            p.sendMessage("");

            return true;
        }

        //
        // 🗑 DELETE PROJECT ADMIN
        //

        if (args[0].equalsIgnoreCase("delete")) {

            if (args.length < 2) {

                p.sendMessage(
                        "§c/urbanisme delete <id>"
                );

                return true;
            }

            TownSubmission sub =
                    SubmissionStorage.get(
                            args[1]
                    );

            if (sub == null) {

                p.sendMessage("");
                p.sendMessage(
                        "§8----- §6Commission Urbaine §8-----"
                );
                p.sendMessage(
                        "§cProjet introuvable."
                );
                p.sendMessage(
                        "§7Aucun dossier ne correspond à cet identifiant."
                );
                p.sendMessage("");

                return true;
            }

            SubmissionStorage.delete(
                    sub.getId()
            );

            p.sendMessage("");
            p.sendMessage(
                    "§8----- §6Commission Urbaine §8-----"
            );
            p.sendMessage(
                    "§fProjet supprimé du registre."
            );
            p.sendMessage(
                    "§7Projet: §e"
                            + sub.getBuildName()
            );
            p.sendMessage(
                    "§7Ville: §b"
                            + sub.getTown()
            );
            p.sendMessage(
                    "§7ID: §f"
                            + sub.getId()
            );
            p.sendMessage(
                    "§cDossier retiré par l'administration."
            );
            p.sendMessage("");

            return true;
        }

        //
        // 🔄 RESET WEEK
        //

        if (args[0].equalsIgnoreCase("resetweek")) {

            new WeeklyResetTask().run();

            p.sendMessage("");
            p.sendMessage(
                    "§8----- §6Commission Urbaine §8-----"
            );
            p.sendMessage(
                    "§fSemaine urbaine réinitialisée."
            );
            p.sendMessage(
                    "§a✔ Les registres hebdomadaires sont prêts."
            );
            p.sendMessage("");

            return true;
        }

        //
        // 🏛 RESET VILLE
        //

        if (args[0].equalsIgnoreCase("resetville")) {

            if (args.length < 2) {

                p.sendMessage(
                        "§c/urbanisme resetville <ville>"
                );

                return true;
            }

            String townName =
                    args[1];

            TownGrade grade =
                    GradeManager.get(townName);

            grade.setArchitecture(0);
            grade.setStyle(0);
            grade.setActivite(0);
            grade.setBanque(0);
            grade.setRemarquable(0);
            grade.setRp(0);
            grade.setTaille(0);
            grade.setVotes(0);

            grade.setFinished(false);

            grade.setPayoutClaimed(false);

            GradeManager.save(grade);

            for (TownSubmission sub :
                    SubmissionStorage.getAll()) {

                if (sub.getTown()
                        .equalsIgnoreCase(
                                townName
                        )) {

                    SubmissionStorage.delete(
                            sub.getId()
                    );
                }
            }

            p.sendMessage("");
            p.sendMessage(
                    "§8----- §6Commission Urbaine §8-----"
            );
            p.sendMessage(
                    "§fVille réinitialisée."
            );
            p.sendMessage(
                    "§7Ville: §b"
                            + townName
            );
            p.sendMessage(
                    "§a✔ Notes supprimées §8| §aClassement supprimé §8| §aProjets supprimés"
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
                    "§8----- §6Commission Urbaine §8-----"
            );
            p.sendMessage(
                    "§cAccès refusé."
            );
            p.sendMessage(
                    "§7Seuls les maires et assistants peuvent gérer les projets urbains."
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

            p.sendMessage("");
            p.sendMessage(
                    "§8----- §6Commission Urbaine §8-----"
            );
            p.sendMessage(
                    "§cAucune ville détectée."
            );
            p.sendMessage(
                    "§7Votre dossier municipal est introuvable."
            );
            p.sendMessage("");

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

                p.sendMessage("");
                p.sendMessage(
                        "§8----- §6Commission Urbaine §8-----"
                );
                p.sendMessage(
                        "§cLimite atteinte."
                );
                p.sendMessage(
                        "§7Une ville peut déposer au maximum §e5 projets§7."
                );
                p.sendMessage("");

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
                    "§8----- §6Commission Urbaine §8-----"
            );
            p.sendMessage(
                    "§fProjet urbain enregistré."
            );
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
                    "§7Statut: §eEn attente d'inspection"
            );
            p.sendMessage(
                    "§a✔ Dossier transmis aux registres nationaux."
            );
            p.sendMessage("");

            return true;
        }

        //
        // 📜 PROJETS GUI
        //

        if (args[0].equalsIgnoreCase("projets")) {

            PendingProjectsGUI.open(p);

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

                p.sendMessage("");
                p.sendMessage(
                        "§8----- §6Commission Urbaine §8-----"
                );
                p.sendMessage(
                        "§cProjet introuvable."
                );
                p.sendMessage(
                        "§7Aucun dossier ne correspond à cet identifiant."
                );
                p.sendMessage("");

                return true;
            }

            if (!sub.getTown()
                    .equalsIgnoreCase(
                            town.getName()
                    )) {

                p.sendMessage("");
                p.sendMessage(
                        "§8----- §6Commission Urbaine §8-----"
                );
                p.sendMessage(
                        "§cProjet invalide."
                );
                p.sendMessage(
                        "§7Ce dossier n'appartient pas à votre ville."
                );
                p.sendMessage("");

                return true;
            }

            SubmissionStorage.delete(
                    sub.getId()
            );

            p.sendMessage("");
            p.sendMessage(
                    "§8----- §6Commission Urbaine §8-----"
            );
            p.sendMessage(
                    "§fProjet retiré du registre."
            );
            p.sendMessage(
                    "§7ID: §f"
                            + sub.getId()
            );
            p.sendMessage(
                    "§cDossier fermé par la municipalité."
            );
            p.sendMessage("");

            return true;
        }

        return true;
    }
}