package tn.esprit.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tn.esprit.models.User;
import tn.esprit.models.session;
import tn.esprit.services.UserService;
import tn.esprit.util.Navigator;

public class sidebarAdmin {
    @FXML
    private Label name;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Label labelFX;


    UserService userService=new UserService();
    Navigator nav = new Navigator();
    

    @FXML
    void initialize() {
        updateLabels();
    }

    private void updateLabels() {
        User utilisateur = userService.getOne(session.id_utilisateur);
        name.setText(utilisateur.getPrenom() + " " + utilisateur.getNom());
    }

    public void disconnect(ActionEvent event) {
        session.id_utilisateur = 0;
        nav.goToPage_WithEvent("/log.fxml", event);
    }

    public void directToHomepage_back(ActionEvent event) {

        Navigator nav = new Navigator();
        nav.goToPage_WithEvent("/ProfileAdmin.fxml",event);
    }

    public void directToUserm_back(ActionEvent event) {

        Navigator nav = new Navigator();
        nav.goToPage_WithEvent("/GestionUtilisateurs.fxml",event);
    }




}


