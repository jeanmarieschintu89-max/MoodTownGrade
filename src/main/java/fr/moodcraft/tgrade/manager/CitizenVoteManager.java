package fr.moodcraft.tgrade.manager;

import fr.moodcraft.tgrade.model.CitizenVote;

import fr.moodcraft.tgrade.storage.VoteStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CitizenVoteManager {

    //
    // 📦 CACHE
    //

    private static final Map<String,
            Map<UUID, CitizenVote>>
            votes = new HashMap<>();

    //
    // 📥 LOAD
    //

    public static void loadTown(
            String town
    ) {

        Map<UUID, CitizenVote> map =
                new HashMap<>();

        List<CitizenVote> loaded =
                VoteStorage.getCitizenVotes(
                        town
                );

        for (CitizenVote vote : loaded) {

            map.put(
                    vote.getVoter(),
                    vote
            );
        }

        votes.put(
                town.toLowerCase(),
                map
        );
    }

    //
    // 👥 GET VOTE
    //

    public static CitizenVote getVote(

            UUID player,

            String town
    ) {

        Map<UUID, CitizenVote> map =
                votes.get(
                        town.toLowerCase()
                );

        if (map == null) {
            return null;
        }

        return map.get(player);
    }

    //
    // 💾 SAVE
    //

    public static void saveVote(
            CitizenVote vote
    ) {

        String town =
                vote.getTown()
                        .toLowerCase();

        votes.putIfAbsent(
                town,
                new HashMap<>()
        );

        votes.get(town)
                .put(
                        vote.getVoter(),
                        vote
                );

        VoteStorage.saveCitizenVote(
                vote
        );
    }

    //
    // 📚 GET ALL
    //

    public static List<CitizenVote>
    getVotes(
            String town
    ) {

        if (!votes.containsKey(
                town.toLowerCase()
        )) {

            loadTown(town);
        }

        return List.copyOf(

                votes.get(
                        town.toLowerCase()
                ).values()
        );
    }

    //
    // ❓ HAS VOTED
    //

    public static boolean hasVoted(

            UUID player,

            String town
    ) {

        return getVote(
                player,
                town
        ) != null;
    }

    //
    // 🗑 CLEAR
    //

    public static void clear() {

        votes.clear();
    }
}