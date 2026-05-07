package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.RateGUI;

import fr.moodcraft.tgrade.manager.GradeManager;
import fr.moodcraft.tgrade.manager.RateSessionManager;

import fr.moodcraft.tgrade.model.RateSession;
import fr.moodcraft.tgrade.model.TownGrade;

import org.bukkit.Bukkit;
import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

import org.bukkit.inventory.ItemStack;

public class RateGUIListener
        implements Listener {

    //
    // ⭐ CLICK
    //

    @EventHandler
    public void onClick(
            InventoryClickEvent e
    ) {

        //
        // 👤 PLAYER
        //

        if (!(e.getWhoClicked()
                instanceof Player p)) {
            return;
        }

        //
        // 📛 GUI
        //

        if (!e.getView()
                .getTitle()
                .equalsIgnoreCase(
                        "§8⭐ Notation Urbaine"
                )) {
            return;
        }

        //
        // ❌ CANCEL
        //

        e.setCancelled(true);

        //
        // 📦 ITEM
        //

        ItemStack item =
                e.getCurrentItem();

        if (item == null)
            return;

        if (!item.hasItemMeta())
            return;

        //
        // 🧠 SESSION
        //

        RateSession session =
                RateSessionManager.get(
                        p.getUniqueId()
                );

        if (session == null)
            return;

        //
        // 🏛 TOWN
        //

        String town =
                session.getTown();

        //
        // 📛 NAME
        //

        String name =
                item.getItemMeta()
                        .getDisplayName();

        //
        // 🏗 ARCHITECTURE
        //

        if (name.equals(
                "§f🏗 Architecture"
        )) {

            int value =
                    session.getArchitecture() + 1;

            if (value > 10)
                value = 0;

            session.setArchitecture(value);

            playClick(p);
        }

        //
        // 🎨 COHERENCE
        //

        if (name.equals(
                "§d🎨 Cohérence"
        )) {

            int value =
                    session.getCoherence() + 1;

            if (value > 6)
                value = 0;

            session.setCoherence(value);

            playClick(p);
        }

        //
        // ⚡ ACTIVITE
        //

        if (name.equals(
                "§e⚡ Activité"
        )) {

            int value =
                    session.getActivite() + 1;

            if (value > 8)
                value = 0;

            session.setActivite(value);

            playClick(p);
        }

        //
        // 💰 BANQUE
        //

        if (name.equals(
                "§6💰 Banque"
        )) {

            int value =
                    session.getBanque() + 1;

            if (value > 4)
                value = 0;

            session.setBanque(value);

            playClick(p);
        }

        //
        // 🏛 BUILD
        //

        if (name.equals(
                "§c🏛 Build remarquable"
        )) {

            int value =
                    session.getBuild() + 1;

            if (value > 8)
                value = 0;

            session.setBuild(value);

            playClick(p);
        }

        //
        // 🎭 RP
        //

        if (name.equals(
                "§a🎭 RolePlay"
        )) {

            int value =
                    session.getRoleplay() + 1;

            if (value > 6)
                value = 0;

            session.setRoleplay(value);

            playClick(p);
        }

        //
        // 🌍 TAILLE
        //

        if (name.equals(
                "§2🌍 Taille"
        )) {

            int value =
                    session.getTaille() + 1;

            if (value > 3)
                value = 0;

            session.setTaille(value);

            playClick(p);
        }

        //
        // 🗳 VOTES
        //

        if (name.equals(
                "§b🗳 Votes"
        )) {

            int value =
                    session.getVotes() + 1;

            if (value > 5)
                value = 0;

            session.setVotes(value);

            playClick(p);
        }

        //
        // ✅ SAVE FINAL
        //

        if (name.equals(
                "§a✅ Valider la notation"
        )) {

            TownGrade grade =
                    GradeManager.get(town);

            grade.setArchitecture(
                    session.getArchitecture()
            );

            grade.setStyle(
                    session.getCoherence()
            );

            grade.setActivite(
                    session.getActivite()
            );

            grade.setBanque(
                    session.getBanque()
            );

            grade.setRemarquable(
                    session.getBuild()
            );

            grade.setRp(
                    session.getRoleplay()
            );

            grade.setTaille(
                    session.getTaille()
            );

            grade.setVotes(
                    session.getVotes()
            );

            //
            // 💾 SAVE
            //

            GradeManager.save(grade);

            //
            // 🧹 REMOVE SESSION
            //

            RateSessionManager.remove(
                    p.getUniqueId()
            );

            //
            // 🔒 CLOSE
            //

            p.closeInventory();

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
                    "§a🏛 Inspection sauvegardée"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§7Ville: §b" + town
            );

            p.sendMessage(
                    "§7Score final: §6"
                            + session.getTotal()
                            + "§7/50"
            );

            p.sendMessage("");

            p.sendMessage(
                    "§aRapport enregistré."
            );

            p.sendMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            p.sendMessage("");

            //
            // 📢 BROADCAST
            //

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            Bukkit.broadcastMessage(
                    "§b🏛 Commission Urbaine"
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Inspection terminée pour"
            );

            Bukkit.broadcastMessage(
                    "§b" + town
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Score obtenu: §6"
                            + session.getTotal()
                            + "§7/50"
            );

            Bukkit.broadcastMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            Bukkit.broadcastMessage("");

            return;
        }

        //
        // 🔄 REFRESH
        //

        RateGUI.open(
                p,
                town
        );
    }

    //
    // 🔊 SOUND
    //

    private void playClick(
            Player p
    ) {

        p.playSound(

                p.getLocation(),

                Sound.UI_BUTTON_CLICK,

                1f,

                1.2f
        );
    }
}