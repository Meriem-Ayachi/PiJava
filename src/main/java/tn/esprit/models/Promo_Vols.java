package tn.esprit.models;

import java.sql.Date;

public class Promo_Vols {

    //attr
    private int id;
    private double pourcentage;
    private Date date_debut_promo,date_fin_promo;

    //constructor


    public Promo_Vols() {
    }

    public Promo_Vols(int id, double pourcentage, Date date_debut_promo, Date date_fin_promo) {
        this.id = id;
        this.pourcentage = pourcentage;
        this.date_debut_promo = date_debut_promo;
        this.date_fin_promo = date_fin_promo;
    }

    public Promo_Vols(double pourcentage, Date date_debut_promo, Date date_fin_promo) {
        this.pourcentage = pourcentage;
        this.date_debut_promo = date_debut_promo;
        this.date_fin_promo = date_fin_promo;
    }

    //getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }

    public Date getDate_debut_promo() {
        return date_debut_promo;
    }

    public void setDate_debut_promo(Date date_debut_promo) {
        this.date_debut_promo = date_debut_promo;
    }

    public Date getDate_fin_promo() {
        return date_fin_promo;
    }

    public void setDate_fin_promo(Date date_fin_promo) {
        this.date_fin_promo = date_fin_promo;
    }

    //display

    @Override
    public String toString() {
        return "Promo_Vols{" +
                "id=" + id +
                ", pourcentage=" + pourcentage +
                ", date_debut_promo=" + date_debut_promo +
                ", date_fin_promo=" + date_fin_promo +
                '}';
    }
}
