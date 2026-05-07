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
                new ArrayList<>(
                        GradeManager.getAll()
                );

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
}