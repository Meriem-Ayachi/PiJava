package tn.esprit.models;

public class hotel {

    private String nom;
    private String nbretoile;
    private Integer id;
    private String Emplacement;

    private String avis;

    public hotel(String nom, String nbretoile, String emplacement, String avis) {
        this.nom = nom;
        this.id= id;
        this.nbretoile = nbretoile;
        Emplacement = emplacement;
        this.avis = avis;
    }

    public hotel() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNbretoile() {
        return nbretoile;
    }

    public void setNbretoile(String nbretoile) {
        this.nbretoile = nbretoile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmplacement() {
        return Emplacement;
    }

    public void setEmplacement(String emplacement) {
        Emplacement = emplacement;
    }

    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public void add(hotel hotel) {
    }
    public void afficher(hotel hotel){

    }
    @Override
    public String toString() {

        return "Hotel{" +
                "nom='" + nom + '\'' +
                ", nbretoile=" + nbretoile +
                ", id=" + id +
                ", emplacement='" + Emplacement + '\'' +
                ", avis='" + avis + '\'' +
                '}';
    }
}
