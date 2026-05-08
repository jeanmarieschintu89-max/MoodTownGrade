package fr.moodcraft.tgrade.manager;

import fr.moodcraft.tgrade.model.MayorVote;

import fr.moodcraft.tgrade.storage.VoteStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MayorVoteManager {

    //
    // 📦 CACHE
    //

    private static final Map<String,
            Map<UUID, MayorVote>>
            votes = new HashMap<>();

    //
    // 📥 LOAD
    //

    public static void loadTown(
            String town
    ) {

        Map<UUID, MayorVote> map =
                new HashMap<>();

        List<MayorVote> loaded =
                VoteStorage.getMayorVotes(
                        town
                );

        for (MayorVote vote : loaded) {

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
    // 👑 GET VOTE
    //

    public static MayorVote getVote(

            UUID player,

            String town
    ) {

        Map<UUID, MayorVote> map =
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
            MayorVote vote
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

        VoteStorage.saveMayorVote(
                vote
        );
    }

    //
    // 📚 GET ALL
    //

    public static List<MayorVote>
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