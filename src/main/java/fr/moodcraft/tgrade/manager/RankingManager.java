
package fr.moodcraft.tgrade.manager;

import fr.moodcraft.tgrade.model.TownGrade;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RankingManager {

    //
    // 🏆 TOP
    //

    public static List<TownGrade> getTop() {

        List<TownGrade> list =
                new ArrayList<>();

        //
        // 📚 LOAD
        //

        for (TownGrade grade :
                GradeManager.getAll()) {

            //
            // ✅ FINISHED ONLY
            //

            if (!grade.isFinished()) {
                continue;
            }

            list.add(grade);
        }

        //
        // 📊 SORT
        //

        list.sort(

                Comparator.comparingInt(
                        TownGrade::getTotal
                ).reversed()
        );

        return list;
    }

    //
    // 🥇 POSITION
    //

    public static int getPosition(
            String town
    ) {

        List<TownGrade> top =
                getTop();

        for (int i = 0;
             i < top.size();
             i++) {

            if (top.get(i)
                    .getTown()
                    .equalsIgnoreCase(town)) {

                return i + 1;
            }
        }

        return -1;
    }

    //
    // 👑 BEST CITY
    //

    public static TownGrade getBest() {

        List<TownGrade> top =
                getTop();

        if (top.isEmpty()) {
            return null;
        }

        return top.get(0);
    }

    //
    // 📊 AVERAGE SCORE
    //

    public static double getAverageScore() {

        List<TownGrade> top =
                getTop();

        if (top.isEmpty()) {
            return 0;
        }

        double total = 0;

        for (TownGrade grade : top) {

            total += grade.getTotal();
        }

        return round(
                total / top.size()
        );
    }

    //
    // 🏙 FINISHED TOWNS
    //

    public static int getFinishedTowns() {

        return getTop().size();
    }

    //
    // 🏆 TOTAL PRESTIGE
    //

    public static int getTotalPrestige() {

        int total = 0;

        for (TownGrade grade :
                getTop()) {

            total += grade.getTotal();
        }

        return total;
    }

    //
    // 🔢 ROUND
    //

    private static double round(
            double value
    ) {

        return Math.round(
                value * 10.0
        ) / 10.0;
    }
}