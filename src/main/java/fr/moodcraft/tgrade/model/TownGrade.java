package fr.moodcraft.tgrade.model;

public class TownGrade {

    //
    // 🏙️ VILLE
    //

    private final String town;

    //
    // 🏛️ NOTES
    //

    private int architecture;
    private int style;
    private int activite;
    private int banque;
    private int remarquable;
    private int rp;
    private int taille;
    private int votes;

    //
    // ✅ INSPECTION
    //

    private boolean finished;

    public TownGrade(String town) {

        this.town = town;
    }

    //
    // 📊 TOTAL
    //

    public int getTotal() {

        return architecture
                + style
                + activite
                + banque
                + remarquable
                + rp
                + taille
                + votes;
    }

    //
    // 📈 POURCENTAGE
    //

    public double getPercentage() {

        return (getTotal() / 50.0) * 100.0;
    }

    //
    // 🏅 RANG
    //

    public String getRank() {

        int total =
                getTotal();

        if (total <= 10) {

            return
                    "§8Village abandonné";
        }

        if (total <= 20) {

            return
                    "§7Ville rurale";
        }

        if (total <= 30) {

            return
                    "§aVille active";
        }

        if (total <= 40) {

            return
                    "§bMétropole prospère";
        }

        if (total <= 49) {

            return
                    "§6Capitale d'élite";
        }

        return
                "§e§lMerveille de MoodCraft";
    }

    //
    // 💰 BOURSE
    //

    public int getPayout() {

        int total =
                getTotal();

        if (total <= 10) {
            return 1000;
        }

        if (total <= 20) {
            return 3000;
        }

        if (total <= 30) {
            return 10000;
        }

        if (total <= 40) {
            return 18000;
        }

        if (total <= 49) {
            return 25000;
        }

        return 30000;
    }

    //
    // 🏛️ APPRECIATION
    //

    public String getAppreciation() {

        int total =
                getTotal();

        if (total <= 10) {

            return
                    "§7Ville inactive ou naissante";
        }

        if (total <= 20) {

            return
                    "§eDéveloppement urbain limité";
        }

        if (total <= 30) {

            return
                    "§aVille active et organisée";
        }

        if (total <= 40) {

            return
                    "§bVille prospère et attractive";
        }

        if (total <= 49) {

            return
                    "§6Ville d'élite remarquable";
        }

        return
                "§e§lPerfection absolue";
    }

    //
    // 🎨 SCORE COLOR
    //

    public String getScoreColor() {

        int total =
                getTotal();

        if (total <= 10) {

            return "§8";
        }

        if (total <= 20) {

            return "§7";
        }

        if (total <= 30) {

            return "§a";
        }

        if (total <= 40) {

            return "§b";
        }

        if (total <= 49) {

            return "§6";
        }

        return "§e§l";
    }

    //
    // 📊 FORMAT SCORE
    //

    public String getFormattedScore() {

        return
                getScoreColor()
                        + getTotal()
                        + "§7/50";
    }

    //
    // 🏙️ GETTERS
    //

    public String getTown() {
        return town;
    }

    public int getArchitecture() {
        return architecture;
    }

    public int getStyle() {
        return style;
    }

    public int getActivite() {
        return activite;
    }

    public int getBanque() {
        return banque;
    }

    public int getRemarquable() {
        return remarquable;
    }

    public int getRp() {
        return rp;
    }

    public int getTaille() {
        return taille;
    }

    public int getVotes() {
        return votes;
    }

    public boolean isFinished() {
        return finished;
    }

    //
    // ✏️ SETTERS
    //

    public void setArchitecture(int architecture) {
        this.architecture =
                Math.max(0,
                        Math.min(10,
                                architecture));
    }

    public void setStyle(int style) {
        this.style =
                Math.max(0,
                        Math.min(6,
                                style));
    }

    public void setActivite(int activite) {
        this.activite =
                Math.max(0,
                        Math.min(8,
                                activite));
    }

    public void setBanque(int banque) {
        this.banque =
                Math.max(0,
                        Math.min(4,
                                banque));
    }

    public void setRemarquable(int remarquable) {
        this.remarquable =
                Math.max(0,
                        Math.min(8,
                                remarquable));
    }

    public void setRp(int rp) {
        this.rp =
                Math.max(0,
                        Math.min(6,
                                rp));
    }

    public void setTaille(int taille) {
        this.taille =
                Math.max(0,
                        Math.min(3,
                                taille));
    }

    public void setVotes(int votes) {
        this.votes =
                Math.max(0,
                        Math.min(5,
                                votes));
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}