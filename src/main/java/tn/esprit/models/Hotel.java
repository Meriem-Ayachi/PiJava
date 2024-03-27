package tn.esprit.models;

public class Hotel {

    //attr
    private int id,nbretoile;
    private String nom,emplacement,avis;


    //constructor

    public Hotel() {
    }

    public Hotel(int id, int nbretoile, String nom, String emplacement, String avis) {
        this.id = id;
        this.nbretoile = nbretoile;
        this.nom = nom;
        this.emplacement = emplacement;
        this.avis = avis;
    }

    public Hotel(int nbretoile, String nom, String emplacement, String avis) {
        this.nbretoile = nbretoile;
        this.nom = nom;
        this.emplacement = emplacement;
        this.avis = avis;
    }

    //getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbretoile() {
        return nbretoile;
    }

    public void setNbretoile(int nbretoile) {
        this.nbretoile = nbretoile;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
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
        return "Hotel{" +
                "id=" + id +
                ", nbretoile=" + nbretoile +
                ", nom='" + nom + '\'' +
                ", emplacement='" + emplacement + '\'' +
                ", avis='" + avis + '\'' +
                '}';
    }
}
