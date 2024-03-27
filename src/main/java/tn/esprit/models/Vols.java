package tn.esprit.models;

import java.sql.Time;
import java.sql.Timestamp;

public class Vols {
    private int id,nbrescale,nbrplace;
    private Time duree;
    private Timestamp datedepart,datearrive;

    private String classe,destination,pointdepart;

    private Double prix;

    //constructor


    public Vols() {
    }

    public Vols(int id, int nbrescale, int nbrplace, Time duree, Timestamp datedepart, Timestamp datearrive, String classe, String destination, String pointdepart, Double prix) {
        this.id = id;
        this.nbrescale = nbrescale;
        this.nbrplace = nbrplace;
        this.duree = duree;
        this.datedepart = datedepart;
        this.datearrive = datearrive;
        this.classe = classe;
        this.destination = destination;
        this.pointdepart = pointdepart;
        this.prix = prix;
    }

    public Vols(int nbrescale, int nbrplace, Time duree, Timestamp datedepart, Timestamp datearrive, String classe, String destination, String pointdepart, Double prix) {
        this.nbrescale = nbrescale;
        this.nbrplace = nbrplace;
        this.duree = duree;
        this.datedepart = datedepart;
        this.datearrive = datearrive;
        this.classe = classe;
        this.destination = destination;
        this.pointdepart = pointdepart;
        this.prix = prix;
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbrescale() {
        return nbrescale;
    }

    public void setNbrescale(int nbrescale) {
        this.nbrescale = nbrescale;
    }

    public int getNbrplace() {
        return nbrplace;
    }

    public void setNbrplace(int nbrplace) {
        this.nbrplace = nbrplace;
    }

    public Time getDuree() {
        return duree;
    }

    public void setDuree(Time duree) {
        this.duree = duree;
    }

    public Timestamp getDatedepart() {
        return datedepart;
    }

    public void setDatedepart(Timestamp datedepart) {
        this.datedepart = datedepart;
    }

    public Timestamp getDatearrive() {
        return datearrive;
    }

    public void setDatearrive(Timestamp datearrive) {
        this.datearrive = datearrive;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPointdepart() {
        return pointdepart;
    }

    public void setPointdepart(String pointdepart) {
        this.pointdepart = pointdepart;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    //display


    @Override
    public String toString() {
        return "Vols{" +
                "id=" + id +
                ", nbrescale=" + nbrescale +
                ", nbrplace=" + nbrplace +
                ", duree=" + duree +
                ", datedepart=" + datedepart +
                ", datearrive=" + datearrive +
                ", classe='" + classe + '\'' +
                ", destination='" + destination + '\'' +
                ", pointdepart='" + pointdepart + '\'' +
                ", prix=" + prix +
                '}';
    }
}
