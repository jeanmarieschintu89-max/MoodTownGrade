package fr.moodcraft.tgrade.listener;

import fr.moodcraft.tgrade.gui.MayorVoteGUI;

import fr.moodcraft.tgrade.manager.MayorVoteManager;
import fr.moodcraft.tgrade.manager.NationalScoreCalculator;

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
                getTown(e);

        if (town == null
                || town.isEmpty()) {

            p.playSound(

                    p.getLocation(),

                    Sound.ENTITY_VILLAGER_NO,

                    1f,

                    1f
            );

            p.sendMessage("");
            p.sendMessage(
                    "§8----- §6Commission Urbaine §8-----"
            );
            p.sendMessage(
                    "§cVille introuvable."
            );
            p.sendMessage(
                    "§7Le registre du vote est incomplet."
            );
            p.sendMessage("");

            return;
        }

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

            case MayorVoteGUI.BEAUTE ->

                    vote.setBeaute(
                            next(
                                    vote.getBeaute()
                            )
                    );

            case MayorVoteGUI.AMBIANCE ->

                    vote.setAmbiance(
                            next(
                                    vote.getAmbiance()
                            )
                    );

            case MayorVoteGUI.ACTIVITE ->

                    vote.setActivite(
                            next(
                                    vote.getActivite()
                            )
                    );

            case MayorVoteGUI.ORIGINALITE ->

                    vote.setOriginalite(
                            next(
                                    vote.getOriginalite()
                            )
                    );

            case MayorVoteGUI.POPULARITE ->

                    vote.setPopularite(
                            next(
                                    vote.getPopularite()
                            )
                    );

            case MayorVoteGUI.SAVE -> {

                vote.updateTimestamp();

                MayorVoteManager.saveVote(
                        vote
                );

                double national =
                        NationalScoreCalculator
                                .getFinalScore(
                                        town
                                );

                double mayors =
                        NationalScoreCalculator
                                .getMayorScore(
                                        town
                                );

                p.closeInventory();

                p.sendMessage("");
                p.sendMessage(
                        "§8----- §6Commission Urbaine §8-----"
                );
                p.sendMessage(
                        "§fVote du Conseil enregistré."
                );
                p.sendMessage(
                        "§7Ville: §b" + town
                );
                p.sendMessage(
                        "§7Influence des maires: §e" + mayors + "§7/50"
                );
                p.sendMessage(
                        "§7Prestige national: §e" + national + "§7/50"
                );
                p.sendMessage(
                        "§a✔ Avis municipal archivé au registre national."
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
    // 🏙 GET TOWN
    //

    private String getTown(
            InventoryClickEvent e
    ) {

        if (e.getInventory()
                .getItem(4) == null) {

            return null;
        }

        if (!e.getInventory()
                .getItem(4)
                .hasItemMeta()) {

            return null;
        }

        if (e.getInventory()
                .getItem(4)
                .getItemMeta()
                .getLore() == null) {

            return null;
        }

        for (String line :
                e.getInventory()
                        .getItem(4)
                        .getItemMeta()
                        .getLore()) {

            if (line.startsWith(
                    "§7Ville: §b")) {

                return line.replace(
                        "§7Ville: §b",
                        ""
                );
            }
        }

        return null;
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