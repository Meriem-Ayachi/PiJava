package tn.esprit.models;

public class hotelReserverModel {
    
    // Attributes
    private String sejour;
    private String date_debut;
    private String date_retour;
    private int nbr_personne;
    private int user_id;
    private int hotel_id;
    private int id;

    // Constructors
    public hotelReserverModel() {
    }

    public hotelReserverModel(String sejour, String date_debut, String date_retour, int nbr_personne, int user_id, int hotel_id) {
        this.sejour = sejour;
        this.date_debut = date_debut;
        this.date_retour = date_retour;
        this.nbr_personne = nbr_personne;
        this.user_id = user_id;
        this.hotel_id = hotel_id;
    }

    // Getters and Setters
    public String getSejour() {
        return sejour;
    }

    public void setSejour(String sejour) {
        this.sejour = sejour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotelId() {
        return hotel_id;
    }

    public void setHotelId(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_retour() {
        return date_retour;
    }

    public void setDate_retour(String date_retour) {
        this.date_retour = date_retour;
    }

    public int getNbr_personne() {
        return nbr_personne;
    }

    public void setNbr_personne(int nbr_personne) {
        this.nbr_personne = nbr_personne;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    // Display
    @Override
    public String toString() {
        return "HotelReservation{" +
                "sejour='" + sejour + '\'' +
                ", date_debut=" + date_debut +
                ", date_retour=" + date_retour +
                ", nbr_personne=" + nbr_personne +
                ", user_id=" + user_id +
                '}';
    }
}
