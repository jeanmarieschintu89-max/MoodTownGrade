package fr.moodcraft.tgrade.manager;

import fr.moodcraft.tgrade.model.TownGrade;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Town;

import org.bukkit.Bukkit;
import org.bukkit.Sound;

import org.bukkit.entity.Player;

public class PayoutManager {

    //
    // 💰 PAYOUT
    //

    public static void payoutAll() {

        //
        // 🌍 LOOP
        //

        for (TownGrade grade :
                GradeManager.getAll()) {

            //
            // ✅ INSPECTION FINIE
            //

            if (!grade.isFinished()) {
                continue;
            }

            //
            // 💰 ALREADY CLAIMED
            //

            if (grade.isPayoutClaimed()) {
                continue;
            }

            //
            // ❌ MINIMUM 25/50
            //

            if (grade.getTotal() < 25) {

                Bukkit.broadcastMessage("");

                Bukkit.broadcastMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                );

                Bukkit.broadcastMessage(
                        "§6✦ Commission Urbaine Nationale"
                );

                Bukkit.broadcastMessage("");

                Bukkit.broadcastMessage(
                        "§7Ville inspectée: §b"
                                + grade.getTown()
                );

                Bukkit.broadcastMessage(
                        "§7Prestige urbain: "
                                + grade.getFormattedScore()
                );

                Bukkit.broadcastMessage("");

                Bukkit.broadcastMessage(
                        "§cFinancement refusé"
                );

                Bukkit.broadcastMessage(
                        "§7Score minimum requis: §e25/50"
                );

                Bukkit.broadcastMessage("");

                Bukkit.broadcastMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                );

                Bukkit.broadcastMessage("");

                //
                // 🔒 CLAIMED
                //

                grade.setPayoutClaimed(true);

                GradeManager.save(grade);

                continue;
            }

            //
            // 🏛 TOWN
            //

            Town town =
                    TownyAPI.getInstance()
                            .getTown(
                                    grade.getTown()
                            );

            //
            // ❌ NULL
            //

            if (town == null) {
                continue;
            }

            //
            // 💰 PAYOUT
            //

            int payout =
                    grade.getPayout();

            //
            // 🏦 DEPOSIT
            //

            try {

                town.getAccount()
                        .deposit(

                                payout,

                                "Financement urbain MoodCraft"
                        );

                //
                // ✅ CLAIMED
                //

                grade.setPayoutClaimed(
                        true
                );

                GradeManager.save(grade);

            } catch (Exception e) {

                Bukkit.getConsoleSender()
                        .sendMessage(

                                "§c[MoodTownGrade] "
                                        + "Impossible de verser la bourse à "
                                        + town.getName()
                        );

                e.printStackTrace();

                continue;
            }

            //
            // 📢 BROADCAST
            //

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            Bukkit.broadcastMessage(
                    "§6✦ Commission Urbaine Nationale"
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Inspection hebdomadaire finalisée."
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Ville inspectée: §b"
                            + town.getName()
            );

            Bukkit.broadcastMessage(
                    "§7Prestige urbain: "
                            + grade.getFormattedScore()
            );

            Bukkit.broadcastMessage(
                    "§7Classement national: "
                            + grade.getRank()
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Financement accordé:"
            );

            Bukkit.broadcastMessage(
                    "§a+"
                            + format(payout)
                            + "$"
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    grade.getAppreciation()
            );

            //
            // 🏆 ELITE BONUS
            //

            if (grade.getTotal() >= 45) {

                Bukkit.broadcastMessage("");

                Bukkit.broadcastMessage(
                        "§e✦ Cette ville devient"
                );

                Bukkit.broadcastMessage(
                        "§eune référence architecturale nationale."
                );
            }

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            Bukkit.broadcastMessage("");

            //
            // 🔊 PLAY SOUND
            //

            for (Player online :
                    Bukkit.getOnlinePlayers()) {

                online.playSound(

                        online.getLocation(),

                        Sound.UI_TOAST_CHALLENGE_COMPLETE,

                        0.8f,

                        1f
                );
            }
        }
    }

    //
    // 💰 FORMAT
    //

    private static String format(
            int value
    ) {

        return String.format(
                "%,d",
                value
        ).replace(",", " ");
    }
}