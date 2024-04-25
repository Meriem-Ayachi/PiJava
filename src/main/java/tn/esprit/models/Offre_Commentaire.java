package tn.esprit.models;

import java.sql.Date;

public class Offre_Commentaire {
    //attr
    private int id;
    private String avis;
    private Date created_at;
    private int offres_id;

    //constructor

    public Offre_Commentaire(int id, String avis, Date created_at,int offres_id) {
        this.id = id;
        this.avis = avis;
        this.created_at = created_at;
        this.offres_id=offres_id;
    }


    public Offre_Commentaire(String avis, Date created_at) {
        this.avis = avis;
        this.created_at = created_at;
    }

    public Offre_Commentaire(String avis, Date created_at , int offres_id) {
        this.avis = avis;
        this.created_at = created_at;
        this.offres_id=offres_id;
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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
    public int getOffres_id() {
        return offres_id;
    }

    public void setOffres_id(int offres_id) {
        this.offres_id = offres_id;
    }

    //display


    @Override
    public String toString() {
        return "Offre_Commentaire{" +
                "id=" + id +
                ", avis='" + avis + '\'' +
                ", created_at=" + created_at +
                "offres_id=" + offres_id +
                '}';
    }

}
