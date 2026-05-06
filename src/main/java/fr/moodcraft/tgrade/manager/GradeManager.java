package fr.moodcraft.tgrade.manager;

import fr.moodcraft.tgrade.model.TownGrade;

import java.util.HashMap;
import java.util.Map;

public class GradeManager {

    //
    // 📊 CACHE
    //

    private static final Map<String, TownGrade>
            grades = new HashMap<>();

    //
    // 📌 GET
    //

    public static TownGrade get(String town) {

        return grades.computeIfAbsent(
                town,
                TownGrade::new
        );
    }
}