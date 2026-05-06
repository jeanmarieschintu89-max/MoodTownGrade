package fr.moodcraft.tgrade.manager;

import fr.moodcraft.tgrade.model.TownGrade;

import fr.moodcraft.tgrade.storage.GradeStorage;

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

        if (grades.containsKey(town)) {
            return grades.get(town);
        }

        TownGrade grade =
                GradeStorage.load(town);

        grades.put(
                town,
                grade
        );

        return grade;
    }

    //
    // 💾 SAVE
    //

    public static void save(TownGrade grade) {

        GradeStorage.save(grade);
    }
}