package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.manager.ProjectInputManager;

import fr.moodcraft.tgrade.model.SubmissionStatus;
import fr.moodcraft.tgrade.model.TownSubmission;

import fr.moodcraft.tgrade.storage.SubmissionStorage;

import fr.moodcraft.tgrade.towny.TownyHook;

import com.palmergames.bukkit.towny.object.Town;

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

        //
        // 👤 PLAYER
        //

        Player p =
                e.getPlayer();

        //
        // ❌ NOT WAITING
        //

        if (!ProjectInputManager.isWaiting(
                p.getUniqueId()
        )) {
            return;
        }

        //
        // ❌ CANCEL CHAT
        //

        e.setCancelled(true);

        //
        // 🛑 STOP WAITING
        //

        ProjectInputManager.stop(
                p.getUniqueId()
        );

        //
        // 🏛 TOWN
        //

        Town town =
                TownyHook.getTown(p);

        if (town == null) {

            p.sendMessage(
                    "§cAucune ville détectée."
            );

            return;
        }

        //
        // 📛 NAME
        //

        String name =
                e.getMessage();

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

        //
        // 💾 SAVE
        //

        SubmissionStorage.save(sub);

        //
        // 🔊 SOUND
        //

        p.playSound(

                p.getLocation(),

                Sound.UI_TOAST_CHALLENGE_COMPLETE,

                1f,

                1f
        );

        //
        // 📜 MESSAGE
        //

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

        p.sendMessage("");

        p.sendMessage(
                "§7Le projet a été transmis"
        );

        p.sendMessage(
                "§7à la commission urbaine."
        );

        p.sendMessage("");

        p.sendMessage(
                "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
        );

        p.sendMessage("");
    }
}