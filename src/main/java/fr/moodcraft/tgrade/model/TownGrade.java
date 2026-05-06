package fr.moodcraft.tgrade.model;

public class TownGrade {

    //
    // 🏙 VILLE
    //

    private final String town;

    //
    // 🏛 NOTES
    //

    private int architecture;
    private int style;
    private int activite;
    private int banque;
    private int remarquable;
    private int rp;
    private int taille;
    private int votes;

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
    // 🏙 GETTERS
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

    //
    // ✏ SETTERS
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
}