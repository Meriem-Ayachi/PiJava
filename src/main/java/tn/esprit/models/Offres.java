package tn.esprit.models;

public class Offres {

    //attr

    private int id;
    private double prix;
    private String title,description,lieu,image;

    private Byte published;

    //constructor


    public Offres() {
    }

    public Offres(int id, Byte published, double prix, String title, String description, String lieu, String image) {
        this.id = id;
        this.published = published;
        this.prix = prix;
        this.title = title;
        this.description = description;
        this.lieu = lieu;
        this.image = image;
    }

    public Offres(Byte published, double prix, String title, String description, String lieu, String image) {
        this.published = published;
        this.prix = prix;
        this.title = title;
        this.description = description;
        this.lieu = lieu;
        this.image = image;
    }

    //getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Byte getPublished() {
        return published;
    }

    public void setPublished(Byte published) {
        this.published = published;
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

    //display


    @Override
    public String toString() {
        return "Offres{" +
                "id=" + id +
                ", published=" + published +
                ", prix=" + prix +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", lieu='" + lieu + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
