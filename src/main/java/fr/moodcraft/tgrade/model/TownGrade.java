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
    // 💰 BOURSE
    //

    public int getPayout() {

        int total =
                getTotal();

        //
        // 📉 0 → 10
        //

        if (total <= 10) {
            return 1000;
        }

        //
        // 📈 11 → 20
        //

        if (total <= 20) {
            return 3000;
        }

        //
        // 🌆 21 → 30
        //

        if (total <= 30) {
            return 10000;
        }

        //
        // 🏛️ 31 → 40
        //

        if (total <= 40) {
            return 18000;
        }

        //
        // 🌟 41 → 49
        //

        if (total <= 49) {
            return 25000;
        }

        //
        // 👑 50
        //

        return 30000;
    }

    //
    // 🏅 APPRECIATION
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
                    "§eEffort minimal";
        }

        if (total <= 30) {

            return
                    "§aVille active";
        }

        if (total <= 40) {

            return
                    "§bTrès belle ville";
        }

        if (total <= 49) {

            return
                    "§6Ville d'élite";
        }

        return
                "§e§lPerfection absolue";
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
        this.architecture = architecture;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public void setActivite(int activite) {
        this.activite = activite;
    }

    public void setBanque(int banque) {
        this.banque = banque;
    }

    public void setRemarquable(int remarquable) {
        this.remarquable = remarquable;
    }

    public void setRp(int rp) {
        this.rp = rp;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}