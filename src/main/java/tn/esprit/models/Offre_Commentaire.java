package tn.esprit.models;

public class Offre_Commentaire {
    //attr
    private int id;
    private String avis;

    //constructor


    public Offre_Commentaire() {
    }

    public Offre_Commentaire(int id, String avis) {
        this.id = id;
        this.avis = avis;
    }

    public Offre_Commentaire(String avis) {
        this.avis = avis;
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    //display


    @Override
    public String toString() {
        return "Offre_Commentaire{" +
                "id=" + id +
                ", avis='" + avis + '\'' +
                '}';
    }
}
