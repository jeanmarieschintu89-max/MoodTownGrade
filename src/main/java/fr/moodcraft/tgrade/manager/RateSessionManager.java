package fr.moodcraft.tgrade.manager;

import fr.moodcraft.tgrade.model.RateSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RateSessionManager {

    private static final Map<UUID, RateSession> sessions =
            new HashMap<>();

    //
    // 🏛️ CRÉATION / RÉCUPÉRATION
    //

    public static RateSession create(
            UUID uuid,
            String town
    ) {

        RateSession existing =
                sessions.get(uuid);

        /*
         * Important :
         *
         * RateGUI.open(...) est aussi appelé quand on clique sur un critère,
         * pour rafraîchir le menu.
         *
         * Donc on garde la session si c'est la même ville.
         * Mais si l'admin choisit une autre ville, on remplace la session.
         *
         * Ancien bug :
         * le plugin gardait l'ancienne session même si la ville changeait.
         * Résultat : la note partait sur la mauvaise ville.
         */

        if (existing != null
                && existing.getTown() != null
                && existing.getTown().equalsIgnoreCase(town)) {

            return existing;
        }

        RateSession session =
                new RateSession(town);

        sessions.put(
                uuid,
                session
        );

        return session;
    }

    //
    // 🔎 GET
    //

    public static RateSession get(
            UUID uuid
    ) {

        return sessions.get(uuid);
    }

    //
    // 🧹 REMOVE
    //

    public static void remove(
            UUID uuid
    ) {

        sessions.remove(uuid);
    }

    //
    // ✅ HAS
    //

    public static boolean has(
            UUID uuid
    ) {

        return sessions.containsKey(uuid);
    }
}