package tn.esprit.models;

import java.sql.Date;
import java.sql.Timestamp;

public class Reclamation_Commentaire {

    //attr
    private int id,reclamation_id,user_id;
    private String contenu;
    private Timestamp date_creation;

    //constructor


    public Reclamation_Commentaire() {

    }

    public Reclamation_Commentaire(int id, String contenu, Timestamp date_creation, int reclamation_id, int user_id) {
        this.id = id;
        this.contenu = contenu;
        this.date_creation = date_creation;
        this.reclamation_id = reclamation_id;
        this.user_id = user_id;

    }

    public Reclamation_Commentaire(String contenu, Timestamp date_creation,int reclamation_id, int user_id) {
        this.contenu = contenu;
        this.date_creation = date_creation;
        this.reclamation_id = reclamation_id;
        this.user_id = user_id;

    }

    //getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReclamation_id() {
        return reclamation_id;
    }

    public void setReclamation_id(int reclamation_id) {
        this.reclamation_id = reclamation_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Timestamp getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Timestamp date_creation) {
        this.date_creation = date_creation;
    }

    //display

    @Override
    public String toString() {
        return "Reclamation_Commentaire{" +
                "id=" + id +
                ", reclamation_id=" + reclamation_id +
                ", user_id=" + user_id +
                ", contenu='" + contenu + '\'' +
                ", date_creation=" + date_creation +
                '}';
    }

}
