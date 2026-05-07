package fr.moodcraft.tgrade.manager;

import java.util.HashMap;
import java.util.Map;

import java.util.UUID;

public class ProjectInputManager {

    //
    // 🧠 INPUT WAITING
    //

    private static final Map<UUID, Boolean>
            waiting = new HashMap<>();

    //
    // ➕ START
    //

    public static void start(
            UUID uuid
    ) {

        waiting.put(uuid, true);
    }

    //
    // ❌ STOP
    //

    public static void stop(
            UUID uuid
    ) {

        waiting.remove(uuid);
    }

    //
    // 🔍 CHECK
    //

    public static boolean isWaiting(
            UUID uuid
    ) {

        return waiting.containsKey(uuid);
    }
}