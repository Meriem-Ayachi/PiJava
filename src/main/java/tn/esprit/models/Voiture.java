package tn.esprit.models;

public class Voiture {
    private int id,capacite;
    private String couleur,marque,model,energy, image_file_name;

    //constuctor


    public Voiture() {
    }

    public Voiture(int id, int capacite, String couleur, String marque, String model, String energy, String image_file_name) {
        this.id = id;
        this.capacite = capacite;
        this.couleur = couleur;
        this.marque = marque;
        this.model = model;
        this.energy = energy;
        this.image_file_name = image_file_name;
    }

    public Voiture(int capacite, String couleur, String marque, String model, String energy, String image_file_name) {
        this.capacite = capacite;
        this.couleur = couleur;
        this.marque = marque;
        this.model = model;
        this.energy = energy;
        this.image_file_name = image_file_name;
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

    public String getImage_file_name() {
        return image_file_name;
    }

    public void setImage_file_name(String image_file_name) {
        this.image_file_name = image_file_name;
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
