package tn.esprit.models;

import java.sql.Timestamp;

public class Location_Voiture {

    //attr
    private int id,voiture_id,user_id;

    private double prix;

    private Timestamp date_debut,datefin;
    private String type,status;


    //constructor

    public Location_Voiture() {
    }

    public Location_Voiture(int id, double prix, Timestamp date_debut, Timestamp datefin, String type, String status, int voiture_id) {
        this.id = id;
        this.prix = prix;
        this.date_debut = date_debut;
        this.datefin = datefin;
        this.type = type;
        this.status = status;
        this.voiture_id = voiture_id;
    }

    public Location_Voiture(double prix, Timestamp date_debut, Timestamp datefin, String type, String status, int voiture_id) {
        this.prix = prix;
        this.date_debut = date_debut;
        this.datefin = datefin;
        this.type = type;
        this.status = status;
        this.voiture_id = voiture_id;
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
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getVoiture_id() {
        return voiture_id;
    }

    public void setVoiture_id(int voiture_id) {
        this.voiture_id = voiture_id;
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
