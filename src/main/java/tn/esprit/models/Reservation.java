package tn.esprit.models;

import java.sql.Date;
import java.sql.Timestamp;

public class Reservation {
    //attr
    private int id,nbredepersonne;
    private Timestamp datededepart,datederetour;
    private String classe,destinationdepart,destinationretour;

    //constructor


    public Reservation() {
    }

    public Reservation(int id, int nbredepersonne, Timestamp datededepart, Timestamp datederetour, String classe, String destinationdepart, String destinationretour) {
        this.id = id;
        this.nbredepersonne = nbredepersonne;
        this.datededepart = datededepart;
        this.datederetour = datederetour;
        this.classe = classe;
        this.destinationdepart = destinationdepart;
        this.destinationretour = destinationretour;
    }

    public Reservation(int nbredepersonne, Timestamp datededepart, Timestamp datederetour, String classe, String destinationdepart, String destinationretour) {
        this.nbredepersonne = nbredepersonne;
        this.datededepart = datededepart;
        this.datederetour = datederetour;
        this.classe = classe;
        this.destinationdepart = destinationdepart;
        this.destinationretour = destinationretour;
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbredepersonne() {
        return nbredepersonne;
    }

    public void setNbredepersonne(int nbredepersonne) {
        this.nbredepersonne = nbredepersonne;
    }

    public Timestamp getDatededepart() {
        return datededepart;
    }

    public void setDatededepart(Timestamp datededepart) {
        this.datededepart = datededepart;
    }

    public Timestamp getDatederetour() {
        return datederetour;
    }

    public void setDatederetour(Timestamp datederetour) {
        this.datederetour = datederetour;
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

    //display

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", nbredepersonne=" + nbredepersonne +
                ", datededepart=" + datededepart +
                ", datederetour=" + datederetour +
                ", classe='" + classe + '\'' +
                ", destinationdepart='" + destinationdepart + '\'' +
                ", destinationretour='" + destinationretour + '\'' +
                '}';
    }
}
