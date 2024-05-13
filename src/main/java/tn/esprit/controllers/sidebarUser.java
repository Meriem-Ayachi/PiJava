package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;



public class sidebarUser {

    @FXML
    private Label name;

    @FXML
    private ImageView ImageUser;




    @FXML
    void initialize() {
        updateLabels();

    }



    private void updateLabels() {
    }

    public void disconnect(ActionEvent event) {
    }

    public void directToHomepage(ActionEvent event) {
        System.out.println("Homepage user");
    }

    public void directToProfie(ActionEvent event) {
    }

    public void directToVol(ActionEvent event) {
    }

    @FXML
    void GoToHotel(ActionEvent event) {

    }

    @FXML
    void GoToLocationVoiture(ActionEvent event) {
    }

    @FXML
    void GoToReservation(ActionEvent event) {
    }

    @FXML
    void GoToOffres(ActionEvent event) {

    }
    public void directToListeReclamationUser(ActionEvent event) {

    }
}
