package tn.esprit.models;

import java.sql.Date;

public class Offres {

    //attr

    private int id, vol_id;
    private double prix;
    private String title,description,lieu,image;

    private boolean published;
    private Date created_at;

    //constructor


    public Offres() {
    }

    public Offres(int id, String title, String description,boolean published, double prix, String lieu, String image,  Date created_at) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.published = published;
        this.prix = prix;
        this.lieu = lieu;
        this.image = image;
        this.created_at = created_at;
    }

    public Offres(String title, String description,boolean published, double prix, String lieu, String image,  Date created_at) {
        this.title = title;
        this.description = description;
        this.published = published;
        this.prix = prix;
        this.lieu = lieu;
        this.image = image;
        this.created_at = created_at;
    }

    //getters and setters

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVolId() {
        return vol_id;
    }

    public void setVolId(int vol_id) {
        this.vol_id = vol_id;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "" +
                "" + image + '\n' +
                "" + prix + '\n' +
                "" + title + '\n' +
                "" + description + '\n' +
                "" + lieu + '\n' +
                "" + created_at + '\n' ;
    }
}
