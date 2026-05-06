package fr.moodcraft.tgrade.command;

import fr.moodcraft.tgrade.gui.RateGUI;
import fr.moodcraft.tgrade.gui.ReviewGUI;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import fr.moodcraft.tgrade.towny.TownyHook;

import com.palmergames.bukkit.towny.object.Town;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class VilleCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String label,
                             String[] args) {

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
        // 📜 HELP
        //

        if (args.length == 0) {

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§b🏛 Commission Urbaine MoodCraft"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§e/ville projet <nom>"
            );

            p.sendMessage(
                    "§7Déposer un dossier urbain"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§e/ville projets"
            );

            p.sendMessage(
                    "§7Consulter les projets"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§e/ville retirer <id>"
            );

            p.sendMessage(
                    "§7Supprimer un dossier"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§e/ville review <ville>"
            );

            p.sendMessage(
                    "§7Inspection urbaine"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§e/ville noter <ville>"
            );

            p.sendMessage(
                    "§7Commission d'évaluation"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§e/ville validation"
            );

            p.sendMessage(
                    "§7Projets en attente"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§e/ville tp <id>"
            );

            p.sendMessage(
                    "§7Téléportation inspection"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§e/ville accepter <id>"
            );

            p.sendMessage(
                    "§7Accorder un permis"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§e/ville refuser <id>"
            );

            p.sendMessage(
                    "§7Refus administratif"
            );

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            return true;
        }

        //
        // 🏛️ REVIEW GUI
        //

        if (args[0].equalsIgnoreCase("review")) {

            if (!p.hasPermission(
                    "moodtowngrade.staff")) {

                p.sendMessage(
                        "§cAccès refusé."
                );

                return true;
            }

            if (args.length < 2) {

                p.sendMessage(
                        "§c/ville review <ville>"
                );

                return true;
            }

            String townName =
                    args[1];

            ReviewGUI.open(
                    p,
                    townName
            );

            return true;
        }

        //
        // 📊 RATE GUI
        //

        if (args[0].equalsIgnoreCase("noter")) {

            if (!p.hasPermission(
                    "moodtowngrade.staff")) {

                p.sendMessage(
                        "§cAccès refusé."
                );

                return true;
            }

            if (args.length < 2) {

                p.sendMessage(
                        "§c/ville noter <ville>"
                );

                return true;
            }

            String townName =
                    args[1];

            RateGUI.open(
                    p,
                    townName
            );

            return true;
        }

        //
        // 📋 STAFF COMMANDS
        //

        if (args[0].equalsIgnoreCase("validation")
                || args[0].equalsIgnoreCase("tp")
                || args[0].equalsIgnoreCase("accepter")
                || args[0].equalsIgnoreCase("refuser")) {

            if (!p.hasPermission(
                    "moodtowngrade.staff")) {

                p.sendMessage(
                        "§cPermission insuffisante."
                );

                return true;
            }
        }

        //
        // 📋 /ville validation
        //

        if (args[0].equalsIgnoreCase("validation")) {

            List<TownSubmission> pending =
                    SubmissionStorage.getAll()
                            .stream()
                            .filter(sub ->
                                    sub.getStatus()
                                            == SubmissionStatus.PENDING)
                            .toList();

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§b🏛 Dossiers en étude"
            );

            p.sendMessage("");

            if (pending.isEmpty()) {

                p.sendMessage(
                        "§7Aucun projet en attente."
                );

            } else {

                for (TownSubmission sub : pending) {

                    p.sendMessage(
                            "§f#"
                                    + sub.getId()
                                    + " §8• §e"
                                    + sub.getBuildName()
                    );

                    p.sendMessage(
                            " §7Ville: §b"
                                    + sub.getTown()
                    );

                    p.sendMessage(
                            " §7📍 "
                                    + sub.getX()
                                    + " "
                                    + sub.getY()
                                    + " "
                                    + sub.getZ()
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
        // 📍 /ville tp
        //

        if (args[0].equalsIgnoreCase("tp")) {

            if (args.length < 2) {

                p.sendMessage(
                        "§c/ville tp <id>"
                );

                return true;
            }

            String id = args[1];

            TownSubmission sub =
                    SubmissionStorage.get(id);

            if (sub == null) {

                p.sendMessage(
                        "§cProjet introuvable."
                );

                return true;
            }

            if (Bukkit.getWorld(
                    sub.getWorld()) == null) {

                p.sendMessage(
                        "§cMonde introuvable."
                );

                return true;
            }

            Location loc =
                    new Location(

                            Bukkit.getWorld(
                                    sub.getWorld()
                            ),

                            sub.getX() + 0.5,

                            sub.getY() + 1,

                            sub.getZ() + 0.5
                    );

            p.teleport(loc);

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§b🏛 Inspection urbaine"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Projet: §e"
                            + sub.getBuildName()
            );

            p.sendMessage(
                    "§7Ville: §b"
                            + sub.getTown()
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Coordonnées:"
            );

            p.sendMessage(
                    " §fX: §e" + sub.getX()
            );

            p.sendMessage(
                    " §fY: §e" + sub.getY()
            );

            p.sendMessage(
                    " §fZ: §e" + sub.getZ()
            );

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            return true;
        }

        //
        // ✅ /ville accepter
        //

        if (args[0].equalsIgnoreCase("accepter")) {

            if (args.length < 2) {

                p.sendMessage(
                        "§c/ville accepter <id>"
                );

                return true;
            }

            String id = args[1];

            TownSubmission sub =
                    SubmissionStorage.get(id);

            if (sub == null) {

                p.sendMessage(
                        "§cProjet introuvable."
                );

                return true;
            }

            sub.setStatus(
                    SubmissionStatus.APPROVED
            );

            SubmissionStorage.save(sub);

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§a🏛 Permis accordé"
            );

            p.sendMessage("");

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
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            Bukkit.broadcastMessage(
                    "§b🏛 Commission Urbaine"
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Le projet §e"
                            + sub.getBuildName()
            );

            Bukkit.broadcastMessage(
                    "§7vient d'obtenir"
            );

            Bukkit.broadcastMessage(
                    "§aun permis officiel."
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Ville: §b"
                            + sub.getTown()
            );

            Bukkit.broadcastMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            Bukkit.broadcastMessage("");

            return true;
        }

        //
        // ❌ /ville refuser
        //

        if (args[0].equalsIgnoreCase("refuser")) {

            if (args.length < 2) {

                p.sendMessage(
                        "§c/ville refuser <id>"
                );

                return true;
            }

            String id = args[1];

            TownSubmission sub =
                    SubmissionStorage.get(id);

            if (sub == null) {

                p.sendMessage(
                        "§cProjet introuvable."
                );

                return true;
            }

            sub.setStatus(
                    SubmissionStatus.REJECTED
            );

            SubmissionStorage.save(sub);

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§cRefus administratif"
            );

            p.sendMessage("");

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
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            return true;
        }

        //
        // 🛡 TOWNY CHECK
        //

        if (!TownyHook.canManage(p)) {

            p.sendMessage(
                    "§cTu dois être maire ou assistant."
            );

            return true;
        }

        Town town =
                TownyHook.getTown(p);

        if (town == null) {

            p.sendMessage(
                    "§cTu n'as aucune ville."
            );

            return true;
        }

        //
        // 📌 /ville projet
        //

        if (args[0].equalsIgnoreCase("projet")) {

            if (args.length < 2) {

                p.sendMessage(
                        "§c/ville projet <nom>"
                );

                return true;
            }

            if (SubmissionStorage
                    .getTown(town.getName())
                    .size() >= 5) {

                p.sendMessage(
                        "§cMaximum 5 projets par semaine."
                );

                return true;
            }

            String name =
                    String.join(" ", args)
                            .replaceFirst("projet ", "");

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
                    "§b🏛 Dossier transmis"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Projet: §e" + name
            );

            p.sendMessage(
                    "§7ID administratif: §f" + id
            );

            p.sendMessage(
                    "§7Ville: §b" + town.getName()
            );

            p.sendMessage(
                    "§7Statut: §eEN ÉTUDE"
            );

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            return true;
        }

        //
        // 📜 /ville projets
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
                    "§b🏛 Dossiers urbains • "
                            + town.getName()
            );

            p.sendMessage("");

            if (list.isEmpty()) {

                p.sendMessage(
                        "§7Aucun projet enregistré."
                );

            } else {

                for (TownSubmission sub : list) {

                    String status;

                    switch (sub.getStatus()) {

                        case APPROVED ->
                                status =
                                        "§aPERMIS ACCORDÉ";

                        case REJECTED ->
                                status =
                                        "§cREFUS ADMINISTRATIF";

                        default ->
                                status =
                                        "§eEN ÉTUDE";
                    }

                    p.sendMessage(
                            "§f#"
                                    + sub.getId()
                                    + " §8• §e"
                                    + sub.getBuildName()
                    );

                    p.sendMessage(
                            " §7📍 "
                                    + sub.getX()
                                    + " "
                                    + sub.getY()
                                    + " "
                                    + sub.getZ()
                    );

                    p.sendMessage(
                            " §7Statut: "
                                    + status
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
        // ❌ /ville retirer
        //

        if (args[0].equalsIgnoreCase("retirer")) {

            if (args.length < 2) {

                p.sendMessage(
                        "§c/ville retirer <id>"
                );

                return true;
            }

            String id = args[1];

            TownSubmission sub =
                    SubmissionStorage.get(id);

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
                        "§cCe projet n'appartient pas à ta ville."
                );

                return true;
            }

            SubmissionStorage.delete(id);

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§cDossier supprimé"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7ID administratif: §f" + id
            );

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            return true;
        }

        //
        // ❌ UNKNOWN
        //

        p.sendMessage(
                "§cSous-commande inconnue."
        );

        return true;
    }
}