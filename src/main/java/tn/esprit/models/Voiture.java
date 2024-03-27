package tn.esprit.models;

public class Voiture {
    private int id,capacite;
    private String couleur,marque,model,energy;

    //constuctor


    public Voiture() {
    }

    public Voiture(int id, int capacite, String couleur, String marque, String model, String energy) {
        this.id = id;
        this.capacite = capacite;
        this.couleur = couleur;
        this.marque = marque;
        this.model = model;
        this.energy = energy;
    }

    public Voiture(int capacite, String couleur, String marque, String model, String energy) {
        this.capacite = capacite;
        this.couleur = couleur;
        this.marque = marque;
        this.model = model;
        this.energy = energy;
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    //display

    @Override
    public String toString() {
        return "Voiture{" +
                "id=" + id +
                ", capacite=" + capacite +
                ", couleur='" + couleur + '\'' +
                ", marque='" + marque + '\'' +
                ", model='" + model + '\'' +
                ", energy='" + energy + '\'' +
                '}';
    }
}
