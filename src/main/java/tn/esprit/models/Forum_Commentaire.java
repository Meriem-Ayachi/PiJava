package tn.esprit.models;

import java.sql.Date;
import java.sql.Timestamp;

public class Forum_Commentaire {

    //attr
    private int id;
    private String content;
    private Timestamp created_at,updated_at;

    //constructor


    public Forum_Commentaire() {
    }

    public Forum_Commentaire(int id, String content, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.content = content;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Forum_Commentaire(String content, Timestamp created_at, Timestamp updated_at) {
        this.content = content;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    //display

    @Override
    public String toString() {
        return "Forum_Commentaire{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
