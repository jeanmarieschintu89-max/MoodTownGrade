package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.CitizenVoteGUI;

import fr.moodcraft.tgrade.manager.CitizenVoteManager;

import fr.moodcraft.tgrade.model.CitizenVote;

import org.bukkit.Sound;

import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryClickEvent;

public class CitizenVoteListener
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
                .equals("§8✦ Vote Citoyen")) {
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

        CitizenVote vote =
                CitizenVoteManager.getVote(

                        p.getUniqueId(),

                        town
                );

        //
        // 🆕 CREATE
        //

        if (vote == null) {

            vote =
                    new CitizenVote(

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

            case CitizenVoteGUI.BEAUTE ->

                    vote.setBeaute(
                            next(
                                    vote.getBeaute()
                            )
                    );

            //
            // 🌆 AMBIANCE
            //

            case CitizenVoteGUI.AMBIANCE ->

                    vote.setAmbiance(
                            next(
                                    vote.getAmbiance()
                            )
                    );

            //
            // ⚡ ACTIVITÉ
            //

            case CitizenVoteGUI.ACTIVITE ->

                    vote.setActivite(
                            next(
                                    vote.getActivite()
                            )
                    );

            //
            // 🧭 ORIGINALITÉ
            //

            case CitizenVoteGUI.ORIGINALITE ->

                    vote.setOriginalite(
                            next(
                                    vote.getOriginalite()
                            )
                    );

            //
            // ❤️ POPULARITÉ
            //

            case CitizenVoteGUI.POPULARITE ->

                    vote.setPopularite(
                            next(
                                    vote.getPopularite()
                            )
                    );

            //
            // 💾 SAVE
            //

            case CitizenVoteGUI.SAVE -> {

                vote.updateTimestamp();

                CitizenVoteManager.saveVote(
                        vote
                );

                p.closeInventory();

                p.sendMessage("");

                p.sendMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━"
                );

                p.sendMessage(
                        "§b✦ Vote Citoyen"
                );

                p.sendMessage("");

                p.sendMessage(
                        "§7Votre avis sur §b"
                                + town
                );

                p.sendMessage(
                        "§7a été enregistré."
                );

                p.sendMessage("");

                p.sendMessage(
                        "§a✔ Merci pour votre participation"
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

        CitizenVoteGUI.open(
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

        if (current > 3) {
            current = 0;
        }

        return current;
    }
}