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
            // 🌍 SCORES
            //

            double national =
                    NationalScoreCalculator
                            .getFinalScore(
                                    grade.getTown()
                            );

            double staff =
                    NationalScoreCalculator
                            .getStaffScore(
                                    grade.getTown()
                            );

            double mayors =
                    NationalScoreCalculator
                            .getMayorScore(
                                    grade.getTown()
                            );

            double citizens =
                    NationalScoreCalculator
                            .getCitizenScore(
                                    grade.getTown()
                            );

            //
            // ❌ MINIMUM 25/50
            //

            if (national < 25) {

                Bukkit.broadcastMessage("");

                Bukkit.broadcastMessage(
                        "§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
                );

                Bukkit.broadcastMessage(
                        "§c✦ Fonds Nationaux Refusés"
                );

                Bukkit.broadcastMessage("");

                Bukkit.broadcastMessage(
                        "§7Municipalité concernée:"
                );

                Bukkit.broadcastMessage(
                        " §b" + grade.getTown()
                );

                Bukkit.broadcastMessage("");

                Bukkit.broadcastMessage(
                        "§7Prestige national:"
                );

                Bukkit.broadcastMessage(
                        " §e" + national + "§7/50"
                );

                Bukkit.broadcastMessage("");

                Bukkit.broadcastMessage(
                        "§7Influence urbaine:"
                );

                Bukkit.broadcastMessage(
                        " §6🏛 Staff: §e" + staff
                );

                Bukkit.broadcastMessage(
                        " §6👑 Maires: §e" + mayors
                );

                Bukkit.broadcastMessage(
                        " §6👥 Citoyens: §e" + citizens
                );

                Bukkit.broadcastMessage("");

                Bukkit.broadcastMessage(
                        "§cLe seuil minimum de financement"
                );

                Bukkit.broadcastMessage(
                        "§cnational n'a pas été atteint."
                );

                Bukkit.broadcastMessage("");

                Bukkit.broadcastMessage(
                        "§7Minimum requis: §e25/50"
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

                                "Fonds Nationaux MoodCraft"
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
                                        + "Impossible de verser les fonds à "
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
                    "§a✦ Fonds Nationaux Urbains"
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Une aide gouvernementale vient"
            );

            Bukkit.broadcastMessage(
                    "§7d'être attribuée à la ville:"
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    " §b" + town.getName()
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Subvention accordée:"
            );

            Bukkit.broadcastMessage(
                    " §a+"
                            + format(payout)
                            + "$"
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Prestige national:"
            );

            Bukkit.broadcastMessage(
                    " §e" + national + "§7/50"
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Réputation gouvernementale:"
            );

            Bukkit.broadcastMessage(
                    " §6🏛 Staff: §e" + staff
            );

            Bukkit.broadcastMessage(
                    " §6👑 Maires: §e" + mayors
            );

            Bukkit.broadcastMessage(
                    " §6👥 Citoyens: §e" + citizens
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    "§7Classe urbaine:"
            );

            Bukkit.broadcastMessage(
                    " " + grade.getRank()
            );

            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    grade.getAppreciation()
            );

            //
            // 🏆 ELITE BONUS
            //

            if (national >= 45) {

                Bukkit.broadcastMessage("");

                Bukkit.broadcastMessage(
                        "§6👑 Distinction Architecturale"
                );

                Bukkit.broadcastMessage(
                        "§7Cette ville rejoint les"
                );

                Bukkit.broadcastMessage(
                        "§7références nationales de MoodCraft."
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