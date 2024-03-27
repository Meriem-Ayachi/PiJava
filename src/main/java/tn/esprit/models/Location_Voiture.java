package tn.esprit.models;

import java.sql.Date;
import java.sql.Timestamp;

public class Location_Voiture {

    //attr
    private int id;
    private double prix;

    private Timestamp date_debut,datefin;
    private String type,status;


    //constructor

    public Location_Voiture() {
    }

    public Location_Voiture(int id, double prix, Timestamp date_debut, Timestamp datefin, String type, String status) {
        this.id = id;
        this.prix = prix;
        this.date_debut = date_debut;
        this.datefin = datefin;
        this.type = type;
        this.status = status;
    }

    public Location_Voiture(double prix, Timestamp date_debut, Timestamp datefin, String type, String status) {
        this.prix = prix;
        this.date_debut = date_debut;
        this.datefin = datefin;
        this.type = type;
        this.status = status;
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Timestamp getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Timestamp date_debut) {
        this.date_debut = date_debut;
    }

    public Timestamp getDatefin() {
        return datefin;
    }

    public void setDatefin(Timestamp datefin) {
        this.datefin = datefin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //display


    @Override
    public String toString() {
        return "Location_Voiture{" +
                "id=" + id +
                ", prix=" + prix +
                ", date_debut=" + date_debut +
                ", datefin=" + datefin +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
