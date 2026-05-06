package fr.moodcraft.tgrade.command;

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
            p.sendMessage("§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            p.sendMessage("§6🏛 Commandes Ville");
            p.sendMessage("");

            p.sendMessage("§e/ville projet <nom>");
            p.sendMessage("§7Soumettre un projet");

            p.sendMessage("");

            p.sendMessage("§e/ville projets");
            p.sendMessage("§7Voir les projets");

            p.sendMessage("");

            p.sendMessage("§e/ville retirer <id>");
            p.sendMessage("§7Supprimer un projet");

            p.sendMessage("§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

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

            String name =
                    String.join(" ", args)
                            .replaceFirst("projet ", "");

            //
            // 🆔 RANDOM ID
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

            //
            // 💾 SAVE
            //

            SubmissionStorage.save(sub);

            //
            // ✨ MESSAGE
            //

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§6🏛 Projet soumis"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Projet: §e" + name
            );

            p.sendMessage(
                    "§7ID: §f" + id
            );

            p.sendMessage(
                    "§7Ville: §a" + town.getName()
            );

            p.sendMessage(
                    "§7Statut: §eEN ATTENTE"
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
                    "§6🏗 Projets de "
                            + town.getName()
            );

            p.sendMessage("");

            if (list.isEmpty()) {

                p.sendMessage(
                        "§7Aucun projet."
                );

            } else {

                for (TownSubmission sub : list) {

                    String status;

                    switch (sub.getStatus()) {

                        case APPROVED ->
                                status = "§aVALIDÉ";

                        case REJECTED ->
                                status = "§cREFUSÉ";

                        default ->
                                status = "§eEN ATTENTE";
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

            //
            // 🛡 TOWN CHECK
            //

            if (!sub.getTown()
                    .equalsIgnoreCase(
                            town.getName()
                    )) {

                p.sendMessage(
                        "§cCe projet n'appartient pas à ta ville."
                );

                return true;
            }

            //
            // ❌ DELETE
            //

            SubmissionStorage.delete(id);

            //
            // ✨ MESSAGE
            //

            p.sendMessage("");

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage(
                    "§cProjet supprimé"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7ID: §f" + id
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