package fr.moodcraft.tgrade.manager;

import fr.moodcraft.tgrade.model.RateSession;

import java.util.HashMap;
import java.util.Map;

import java.util.UUID;

public class RateSessionManager {

    //
    // 🧠 SESSIONS
    //

    private static final Map<UUID, RateSession>
            sessions = new HashMap<>();

    //
    // ➕ CREATE
    //

    public static RateSession create(
            UUID uuid,
            String town
    ) {

        RateSession session =
                new RateSession(town);

        sessions.put(
                uuid,
                session
        );

        return session;
    }

    //
    // 📦 GET
    //

    public static RateSession get(
            UUID uuid
    ) {

        return sessions.get(uuid);
    }

    //
    // ❌ REMOVE
    //

    public static void remove(
            UUID uuid
    ) {

        sessions.remove(uuid);
    }

    //
    // 🔍 HAS
    //

    public static boolean has(
            UUID uuid
    ) {

        return sessions.containsKey(uuid);
    }
}