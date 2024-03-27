package tn.esprit.models;

import java.sql.Time;
import java.sql.Timestamp;

public class Historique_Connexion {

    //att
    private int idConnexion,idUtilisateur;
    private Timestamp dateConnexion;
    private String adresseIP,typeAppareil,navigateurUtilisé;
    private Time duréeSession;

    //constructor


    public Historique_Connexion() {
    }

    public Historique_Connexion(int idConnexion, int idUtilisateur, Timestamp dateConnexion, String adresseIP, String typeAppareil, String navigateurUtilisé, Time duréeSession) {
        this.idConnexion = idConnexion;
        this.idUtilisateur = idUtilisateur;
        this.dateConnexion = dateConnexion;
        this.adresseIP = adresseIP;
        this.typeAppareil = typeAppareil;
        this.navigateurUtilisé = navigateurUtilisé;
        this.duréeSession = duréeSession;
    }

    public Historique_Connexion(int idUtilisateur, Timestamp dateConnexion, String adresseIP, String typeAppareil, String navigateurUtilisé, Time duréeSession) {
        this.idUtilisateur = idUtilisateur;
        this.dateConnexion = dateConnexion;
        this.adresseIP = adresseIP;
        this.typeAppareil = typeAppareil;
        this.navigateurUtilisé = navigateurUtilisé;
        this.duréeSession = duréeSession;
    }

    //getters and setters

    public int getIdConnexion() {
        return idConnexion;
    }

    public void setIdConnexion(int idConnexion) {
        this.idConnexion = idConnexion;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Timestamp getDateConnexion() {
        return dateConnexion;
    }

    public void setDateConnexion(Timestamp dateConnexion) {
        this.dateConnexion = dateConnexion;
    }

    public String getAdresseIP() {
        return adresseIP;
    }

    public void setAdresseIP(String adresseIP) {
        this.adresseIP = adresseIP;
    }

    public String getTypeAppareil() {
        return typeAppareil;
    }

    public void setTypeAppareil(String typeAppareil) {
        this.typeAppareil = typeAppareil;
    }

    public String getNavigateurUtilisé() {
        return navigateurUtilisé;
    }

    public void setNavigateurUtilisé(String navigateurUtilisé) {
        this.navigateurUtilisé = navigateurUtilisé;
    }

    public Time getDuréeSession() {
        return duréeSession;
    }

    public void setDuréeSession(Time duréeSession) {
        this.duréeSession = duréeSession;
    }


    //display


    @Override
    public String toString() {
        return "Historique_Connexion{" +
                "idConnexion=" + idConnexion +
                ", idUtilisateur=" + idUtilisateur +
                ", dateConnexion=" + dateConnexion +
                ", adresseIP='" + adresseIP + '\'' +
                ", typeAppareil='" + typeAppareil + '\'' +
                ", navigateurUtilisé='" + navigateurUtilisé + '\'' +
                ", duréeSession=" + duréeSession +
                '}';
    }
}
