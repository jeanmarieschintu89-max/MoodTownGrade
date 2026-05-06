package fr.moodcraft.tgrade.model;

import java.util.UUID;

public class TownSubmission {

    //
    // 🆔 ID UNIQUE
    //

    private final String id;

    //
    // 🏙 VILLE
    //

    private final String town;

    //
    // 🏗 NOM DU PROJET
    //

    private final String buildName;

    //
    // 🌍 MONDE
    //

    private final String world;

    //
    // 📍 COORDONNÉES
    //

    private final int x;
    private final int y;
    private final int z;

    //
    // 👤 JOUEUR
    //

    private final UUID submittedBy;

    //
    // 📅 DATE
    //

    private final long timestamp;

    //
    // 📌 STATUT
    //

    private SubmissionStatus status;

    //
    // 🏗 CONSTRUCTEUR
    //

    public TownSubmission(String id,
                          String town,
                          String buildName,
                          String world,
                          int x,
                          int y,
                          int z,
                          UUID submittedBy,
                          long timestamp,
                          SubmissionStatus status) {

        this.id = id;

        this.town = town;

        this.buildName = buildName;

        this.world = world;

        this.x = x;
        this.y = y;
        this.z = z;

        this.submittedBy = submittedBy;

        this.timestamp = timestamp;

        this.status = status;
    }

    //
    // 🆔 GETTERS
    //

    public String getId() {
        return id;
    }

    public String getTown() {
        return town;
    }

    public String getBuildName() {
        return buildName;
    }

    public String getWorld() {
        return world;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public UUID getSubmittedBy() {
        return submittedBy;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    //
    // 🔄 STATUS
    //

    public void setStatus(SubmissionStatus status) {
        this.status = status;
    }
}