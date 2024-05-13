package tn.esprit.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class sidebarAdmin {
    @FXML
    private Label name;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Label labelFX;



    @FXML
    void initialize() {
        updateLabels();
    }

    private void updateLabels() {
    }

    public void disconnect(ActionEvent event) {
    }

    public void directToUserm_back(ActionEvent event) {
    }

    public void directToLogs(ActionEvent event) {

    }

    public void listVoiture(ActionEvent event) {

    }

    public void listLocationVoiture(ActionEvent event) {

    }

    public void directDashboard(ActionEvent event) {
    }

    public void directToVol(ActionEvent event) {
    }

    public void directToListeReclamation(ActionEvent actionEvent) {
    }


    @FXML
    void GoToHotel(ActionEvent event) {

    }
    @FXML
    void goToReservation(ActionEvent event) {
    }
    
    @FXML
    void GoToOffres(ActionEvent event) {

    }
}

