package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.MayorVoteGUI;

import fr.moodcraft.tgrade.manager.MayorVoteManager;

import fr.moodcraft.tgrade.model.MayorVote;

import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

public class MayorVoteListener
        implements Listener {

    @EventHandler
    public void click(
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
        // 📛 TITLE
        //

        if (!e.getView()
                .getTitle()
                .equals("§8✦ Conseil des Maires")) {
            return;
        }

        //
        // ❌ CANCEL
        //

        e.setCancelled(true);

        //
        // 📦 NULL
        //

        if (e.getCurrentItem() == null) {
            return;
        }

        //
        // 🛑 PLAYER INVENTORY
        //

        if (e.getRawSlot()
                >= e.getView()
                .getTopInventory()
                .getSize()) {

            return;
        }

        //
        // 🏙️ TOWN
        //

        String town =
                e.getInventory()
                        .getItem(4)
                        .getItemMeta()
                        .getLore()
                        .get(2)
                        .replace(
                                "§7Ville: §b",
                                ""
                        );

        //
        // 📦 VOTE
        //

        MayorVote vote =
                MayorVoteManager.getVote(

                        p.getUniqueId(),

                        town
                );

        //
        // 🆕 CREATE
        //

        if (vote == null) {

            vote =
                    new MayorVote(

                            p.getUniqueId(),

                            town
                    );
        }

        //
        // 🔘 SLOT
        //

        int slot =
                e.getRawSlot();

        switch (slot) {

            //
            // 🏗 BEAUTÉ
            //

            case MayorVoteGUI.BEAUTE ->

                    vote.setBeaute(
                            next(
                                    vote.getBeaute()
                            )
                    );

            //
            // 🌆 AMBIANCE
            //

            case MayorVoteGUI.AMBIANCE ->

                    vote.setAmbiance(
                            next(
                                    vote.getAmbiance()
                            )
                    );

            //
            // ⚡ ACTIVITÉ
            //

            case MayorVoteGUI.ACTIVITE ->

                    vote.setActivite(
                            next(
                                    vote.getActivite()
                            )
                    );

            //
            // 🧭 ORIGINALITÉ
            //

            case MayorVoteGUI.ORIGINALITE ->

                    vote.setOriginalite(
                            next(
                                    vote.getOriginalite()
                            )
                    );

            //
            // ❤️ POPULARITÉ
            //

            case MayorVoteGUI.POPULARITE ->

                    vote.setPopularite(
                            next(
                                    vote.getPopularite()
                            )
                    );

            //
            // 💾 SAVE
            //

            case MayorVoteGUI.SAVE -> {

                vote.updateTimestamp();

                MayorVoteManager.saveVote(
                        vote
                );

                p.closeInventory();

                p.sendMessage("");

                p.sendMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━"
                );

                p.sendMessage(
                        "§6✦ Conseil des Maires"
                );

                p.sendMessage("");

                p.sendMessage(
                        "§7Votre évaluation de §b"
                                + town
                );

                p.sendMessage(
                        "§7a été enregistrée."
                );

                p.sendMessage("");

                p.sendMessage(
                        "§a✔ Vote gouvernemental validé"
                );

                p.sendMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━"
                );

                p.sendMessage("");

                p.playSound(

                        p.getLocation(),

                        Sound.UI_TOAST_CHALLENGE_COMPLETE,

                        1f,

                        1f
                );

                return;
            }

            default -> {
                return;
            }
        }

        //
        // 🔊 SOUND
        //

        p.playSound(

                p.getLocation(),

                Sound.UI_BUTTON_CLICK,

                1f,

                1f
        );

        //
        // 🔄 REOPEN
        //

        MayorVoteGUI.open(
                p,
                town
        );
    }

    //
    // 🔢 NEXT
    //

    private int next(
            int current
    ) {

        current++;

        if (current > 5) {
            current = 0;
        }

        return current;
    }
}