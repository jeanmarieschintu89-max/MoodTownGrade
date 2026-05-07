
package fr.moodcraft.tgrade.manager;

import fr.moodcraft.tgrade.model.TownGrade;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Town;

import org.bukkit.Bukkit;

public class PayoutManager {

    //
    // 💰 PAYOUT
    //

    public static void payoutAll() {

        //
        // 🌍 LOOP TOWNS
        //

        for (TownGrade grade :
                GradeManager.getAll()) {

            //
            // ✅ NOTATION TERMINÉE
            //

            if (!grade.isFinished()) {
                continue;
            }

            //
            // 💰 DÉJÀ PAYÉ
            //

            if (grade.isPayoutClaimed()) {
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
            // ❌ TOWN NULL
            //

            if (town == null) {
                continue;
            }

            //
            // 💰 MONTANT
            //

            int payout =
                    grade.getPayout();

            //
            // 🏦 DÉPÔT TOWNY
            //

            try {

                town.getAccount()
                        .deposit(

                                payout,

                                "Commission urbaine MoodCraft"
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
            // 🔊 BROADCAST
            //

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            Bukkit.broadcastMessage(
                    "§b🏛 Commission Urbaine Nationale"
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Inspection hebdomadaire terminée."
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Ville inspectée: §b"
                            + town.getName()
            );

            Bukkit.broadcastMessage(
                    "§7Rang national: "
                            + grade.getRank()
            );

            Bukkit.broadcastMessage(
                    "§7Score obtenu: "
                            + grade.getFormattedScore()
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Bourse municipale:"
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

            Bukkit.broadcastMessage("");

            //
            // 🏆 BONUS ÉLITE
            //

            if (grade.getTotal() >= 45) {

                Bukkit.broadcastMessage(
                        "§e✨ Cette ville devient"
                );

                Bukkit.broadcastMessage(
                        "§eune référence architecturale nationale."
                );

                Bukkit.broadcastMessage("");
            }

            Bukkit.broadcastMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            Bukkit.broadcastMessage("");
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