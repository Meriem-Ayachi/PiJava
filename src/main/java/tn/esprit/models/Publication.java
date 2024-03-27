package tn.esprit.models;

import java.sql.Date;
import java.sql.Timestamp;

public class Publication {

    //attr
    private int id,commentaire_id;
    private String title,short_description,content;
    private Timestamp created_at,updated_at;

    //constructor


    public Publication() {
    }

    public Publication(int id, int commentaire_id, String title, String short_description, String content, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.commentaire_id = commentaire_id;
        this.title = title;
        this.short_description = short_description;
        this.content = content;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Publication(int commentaire_id, String title, String short_description, String content, Timestamp created_at, Timestamp updated_at) {
        this.commentaire_id = commentaire_id;
        this.title = title;
        this.short_description = short_description;
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

    public int getCommentaire_id() {
        return commentaire_id;
    }

    public void setCommentaire_id(int commentaire_id) {
        this.commentaire_id = commentaire_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
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
        return "Publication{" +
                "id=" + id +
                ", commentaire_id=" + commentaire_id +
                ", title='" + title + '\'' +
                ", short_description='" + short_description + '\'' +
                ", content='" + content + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
