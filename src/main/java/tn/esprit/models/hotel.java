package tn.esprit.models;

public class hotel {

    private String nom;
    private String nbretoile;
    private Integer id;
    private String emplacement; // Modifié en minuscule pour correspondre à la variable dans votre FXML
    private String avis;

    public hotel(String nom, String nbretoile, String emplacement, String avis) {
        this.nom = nom;
        this.nbretoile = nbretoile;
        this.emplacement = emplacement; // Correction du nom de variable
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

    // Les méthodes add et afficher ne sont pas utilisées dans cette classe, vous pouvez les supprimer

    @Override
    public String toString() {
        return "hotel" +
                "nom='" + nom + '\'' +
                ", nbretoile='" + nbretoile + '\'' +
                ", id=" + id +
                ", emplacement='" + emplacement + '\'' +
                ", avis='" + avis + '\'' +
                ' ';
    }
}
