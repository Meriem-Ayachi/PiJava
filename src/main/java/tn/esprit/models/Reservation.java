package tn.esprit.models;

import java.util.Date;

public class Reservation {
    private int idres ;
    private String datedepart;
    private String dateretour;
    private String classe;
    private String destinationdepart;
    private String destinationretour;
    private int nbrdepersonne;

    public Reservation(int idres,String datedepart, String dateretour, String classe, String destinationdepart, String destinationretour, int nbrdepersonne) {
        this.idres= idres;
        this.datedepart = datedepart;
        this.dateretour = dateretour;
        this.classe = classe;
        this.destinationdepart = destinationdepart;
        this.destinationretour = destinationretour;
        this.nbrdepersonne = nbrdepersonne;
    }

    public int getIdres() {
        return idres;
    }

    public void setIdres(int idres) {
        this.idres = idres;
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
}