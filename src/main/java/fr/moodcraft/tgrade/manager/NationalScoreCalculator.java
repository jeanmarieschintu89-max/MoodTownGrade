package fr.moodcraft.tgrade.manager;

import fr.moodcraft.tgrade.model.CitizenVote;
import fr.moodcraft.tgrade.model.MayorVote;
import fr.moodcraft.tgrade.model.StaffVote;

import fr.moodcraft.tgrade.storage.VoteStorage;

import java.util.List;

public class NationalScoreCalculator {

    //
    // 🏛️ COEFFICIENTS
    //

    private static final double
            STAFF_WEIGHT = 0.70;

    private static final double
            MAYOR_WEIGHT = 0.20;

    private static final double
            CITIZEN_WEIGHT = 0.10;

    //
    // ⭐ SCORE FINAL
    //

    public static double getFinalScore(
            String town
    ) {

        if (town == null) {
            return 0;
        }

        double staff =
                getStaffScore(town);

        double mayor =
                getMayorScore(town);

        double citizen =
                getCitizenScore(town);

        return round(

                (staff * STAFF_WEIGHT)

                        + (mayor * MAYOR_WEIGHT)

                        + (citizen * CITIZEN_WEIGHT)
        );
    }

    //
    // 🏛️ STAFF SCORE /50
    //

    public static double getStaffScore(
            String town
    ) {

        if (town == null) {
            return 0;
        }

        List<StaffVote> votes =
                VoteStorage
                        .getStaffVotes(town);

        if (votes.isEmpty()) {
            return 0;
        }

        double total = 0;

        for (StaffVote vote : votes) {

            total += vote.getTotal();
        }

        return round(
                total / votes.size()
        );
    }

    //
    // 👑 MAYOR SCORE /50
    //

    public static double getMayorScore(
            String town
    ) {

        if (town == null) {
            return 0;
        }

        List<MayorVote> votes =
                VoteStorage
                        .getMayorVotes(town);

        if (votes.isEmpty()) {
            return 0;
        }

        double total = 0;

        for (MayorVote vote : votes) {

            //
            // 📊 /25 -> /50
            //

            total +=
                    (vote.getTotal() * 2.0);
        }

        return round(
                total / votes.size()
        );
    }

    //
    // 👥 CITIZEN SCORE /50
    //

    public static double getCitizenScore(
            String town
    ) {

        if (town == null) {
            return 0;
        }

        List<CitizenVote> votes =
                VoteStorage
                        .getCitizenVotes(town);

        if (votes.isEmpty()) {
            return 0;
        }

        double total = 0;

        for (CitizenVote vote : votes) {

            //
            // 📊 /15 -> /50
            //

            total +=
                    ((vote.getTotal() / 15.0) * 50.0);
        }

        return round(
                total / votes.size()
        );
    }

    //
    // 👥 NB CITOYENS
    //

    public static int getCitizenCount(
            String town
    ) {

        if (town == null) {
            return 0;
        }

        return VoteStorage
                .getCitizenVotes(town)
                .size();
    }

    //
    // 👑 NB MAIRES
    //

    public static int getMayorCount(
            String town
    ) {

        if (town == null) {
            return 0;
        }

        return VoteStorage
                .getMayorVotes(town)
                .size();
    }

    //
    // 🏛️ NB STAFF
    //

    public static int getStaffCount(
            String town
    ) {

        if (town == null) {
            return 0;
        }

        return VoteStorage
                .getStaffVotes(town)
                .size();
    }

    //
    // 🔢 PUBLIC ROUND
    //

    public static double roundScore(
            double value
    ) {

        return round(value);
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