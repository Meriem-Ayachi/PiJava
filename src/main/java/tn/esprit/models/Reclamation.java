package tn.esprit.models;

import java.sql.Date;
import java.sql.Timestamp;


public class Reclamation {

    //attr
    private int id,user_id;
    private String sujet,description;
    private Timestamp datesoummission;

    private Byte est_traite;

    //constructor


    public Reclamation()
    {

    }
    public Reclamation(int id, String sujet,String description, Timestamp datesoummission,Byte est_traite, int user_id) {
        this.id = id;
        this.sujet = sujet;
        this.description = description;
        this.datesoummission = datesoummission;
        this.est_traite = est_traite;
        this.user_id = user_id;

    }

    public Reclamation( String sujet, String description, Timestamp datesoummission,Byte est_traite, int user_id) {
        this.sujet = sujet;
        this.description = description;
        this.datesoummission = datesoummission;
        this.est_traite = est_traite;
        this.user_id = user_id;
    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Byte getEst_traite() {
        return est_traite;
    }

    public void setEst_traite(Byte est_traite) {
        this.est_traite = est_traite;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDatesoummission() {
        return datesoummission;
    }

    public void setDatesoummission(Timestamp datesoummission) {
        this.datesoummission = datesoummission;
    }
    //display


    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", est_traite=" + est_traite +
                ", user_id=" + user_id +
                ", sujet='" + sujet + '\'' +
                ", description='" + description + '\'' +
                ", datesoummission=" + datesoummission +
                '}';
    }
}
