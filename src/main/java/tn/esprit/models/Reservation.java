package tn.esprit.models;

import java.util.Date;
import java.util.List;

public class Reservation {
    private int id ;
    private String datedepart;
    private String dateretour;
    private String classe;
    private String destinationdepart;
    private String destinationretour;
    private int nbrdepersonne;
    private int userId;

    public Reservation(int id,String datedepart, String dateretour, String classe, String destinationdepart, String destinationretour, int nbrdepersonne) {
        this.id= id;
        this.datedepart = datedepart;
        this.dateretour = dateretour;
        this.classe = classe;
        this.destinationdepart = destinationdepart;
        this.destinationretour = destinationretour;
        this.nbrdepersonne = nbrdepersonne;
    }

    public int getId() {
        return id;
    }

    public int getUserId(){
        return userId;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatedepart() {
        return datedepart;
    }

    public void setDatedepart(String datedepart) {
        this.datedepart = datedepart;
    }

    public String getDateretour() {
        return dateretour;
    }

    public void setDateretour(String dateretour) {
        this.dateretour = dateretour;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getDestinationdepart() {
        return destinationdepart;
    }

    public void setDestinationdepart(String destinationdepart) {
        this.destinationdepart = destinationdepart;
    }

    public String getDestinationretour() {
        return destinationretour;
    }

    public void setDestinationretour(String destinationretour) {
        this.destinationretour = destinationretour;
    }

    public int getNbrdepersonne() {
        return nbrdepersonne;
    }

    public void setNbrdepersonne(int nbrdepersonne) {
        this.nbrdepersonne = nbrdepersonne;
    }

    public Reservation() {

    }
    @Override
    public String toString() {
        return "Reservation{" +
                "datedepart='" + datedepart + '\'' +
                ", dateretour='" + dateretour + '\'' +
                ", classe='" + classe + '\'' +
                ", destinationdepart='" + destinationdepart + '\'' +
                ", destinationretour='" + destinationretour + '\'' +
                ", nbrdepersonne=" + nbrdepersonne +
                '}';
    }

    public String getidres() {
        return null;
    }

    public void add(Reservation reservation) {
    }

    public List<Reservation> getAll() {
        return null;
    }
}