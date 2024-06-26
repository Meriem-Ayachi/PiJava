package tn.esprit.models;

import java.sql.Date;

public class Offre_Commentaire {
    //attr
    private int id;
    private String avis;
    private Date created_at;
    private int offres_id;
    private boolean active;

    //constructor

    public Offre_Commentaire(int id, String avis, Date created_at,int offres_id,boolean active) {
        this.id = id;
        this.avis = avis;
        this.created_at = created_at;
        this.offres_id=offres_id;
        this.active=active;
    }


    public Offre_Commentaire(String avis, Date created_at) {
        this.avis = avis;
        this.created_at = created_at;
    }

    public Offre_Commentaire(String avis, Date created_at , int offres_id,boolean active) {
        this.avis = avis;
        this.created_at = created_at;
        this.offres_id=offres_id;
        this.active=active;
    }
    //getters and setters

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

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
        return "" +
                "" + avis + '\n' +
                "" + created_at + '\n' ;

    }
}
