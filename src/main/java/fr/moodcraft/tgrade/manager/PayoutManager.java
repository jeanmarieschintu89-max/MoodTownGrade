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

        for (TownGrade grade :
                GradeManager.getAll()) {

            if (!grade.isFinished()) {
                continue;
            }

            if (grade.isPayoutClaimed()) {
                continue;
            }

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

            Town town =
                    TownyAPI.getInstance()
                            .getTown(
                                    grade.getTown()
                            );

            if (town == null) {
                continue;
            }

            int payout =
                    grade.getPayout();

            try {

                town.getAccount()
                        .deposit(

                                payout,

                                "Fonds Nationaux MoodCraft"
                        );

                grade.setPayoutClaimed(true);

                GradeManager.save(grade);

            } catch (Exception e) {

                Bukkit.getConsoleSender()
                        .sendMessage(
                                "§c[MoodTownGrade] Impossible de verser les fonds à "
                                        + town.getName()
                        );

                e.printStackTrace();

                continue;
            }

            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            Bukkit.broadcastMessage("§a✦ Fonds Nationaux Urbains");
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§7Une aide vient d'être attribuée à:");
            Bukkit.broadcastMessage(" §b" + town.getName());
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§7Subvention accordée:");
            Bukkit.broadcastMessage(" §a+" + format(payout) + "$");
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§7Prestige national:");
            Bukkit.broadcastMessage(" §e" + national + "§7/50");
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§7Influence urbaine:");
            Bukkit.broadcastMessage(" §6🏛 Staff: §e" + staff);
            Bukkit.broadcastMessage(" §6👑 Maires: §e" + mayors);
            Bukkit.broadcastMessage(" §6👥 Citoyens: §e" + citizens);
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§7Classe urbaine:");
            Bukkit.broadcastMessage(" " + grade.getRank());
            Bukkit.broadcastMessage("");

            Bukkit.broadcastMessage(
                    grade.getAppreciation()
            );

            if (national >= 45) {

                Bukkit.broadcastMessage("");
                Bukkit.broadcastMessage("§6👑 Distinction Architecturale");
                Bukkit.broadcastMessage("§7Cette ville rejoint les");
                Bukkit.broadcastMessage("§7références nationales de MoodCraft.");
            }

            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            Bukkit.broadcastMessage("");

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