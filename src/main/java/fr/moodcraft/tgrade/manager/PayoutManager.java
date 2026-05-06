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

        for (TownGrade grade :
                GradeManager.getAll()) {

            //
            // ✅ INSPECTION CHECK
            //

            if (!grade.isFinished()) {
                continue;
            }

            //
            // 🏙️ TOWN
            //

            Town town =
                    TownyAPI.getInstance()
                            .getTown(
                                    grade.getTown()
                            );

            if (town == null) {
                continue;
            }

            //
            // 💰 MONEY
            //

            int payout =
                    grade.getPayout();

            try {

                //
                // 🏦 BANK DEPOSIT
                //

                town.getAccount()
                        .deposit(
                                payout,
                                "MoodTownGrade"
                        );

            } catch (Exception e) {

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
                    "§b🏛 Commission Urbaine"
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Ville: §b"
                            + grade.getTown()
            );

            Bukkit.broadcastMessage(
                    "§7Score: §e"
                            + grade.getTotal()
                            + "§7/50"
            );

            Bukkit.broadcastMessage(
                    "§7Bourse versée: §a"
                            + payout
                            + "$"
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    grade.getAppreciation()
            );

            Bukkit.broadcastMessage(
                    "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
            );

            Bukkit.broadcastMessage("");
        }
    }
}