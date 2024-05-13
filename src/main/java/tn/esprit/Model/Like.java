/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.Model;
import java.util.ArrayList;
import java.util.List;

public class Like {
    private Integer id;
    private String nom;
    private List<Publication> publication;
    private Integer likes;

    public Like() {
        this.publication = new ArrayList<>();
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Publication> getPublication() {
        return publication;
    }

    public void setPublication(List<Publication> publication) {
        this.publication = publication;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", publication=" + publication +
                ", likes=" + likes +
                '}';
    }
}