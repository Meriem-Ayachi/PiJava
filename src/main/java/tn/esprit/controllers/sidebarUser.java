package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.models.User;
import tn.esprit.models.session;
import tn.esprit.services.UserService;

import java.io.IOException;

public class sidebarUser {

    @FXML
    private Label name;
    @FXML
    private Button acceuil;

    @FXML
    private Button profile;

    @FXML
    private Button carte;

    @FXML
    private Hyperlink out;
    UserService userService=new UserService();
    @FXML
    private void redirectAcceuil(ActionEvent event) {
        
    }
    @FXML
    void initialize() {
        updateLabels();
    }
    @FXML
    private void directToProfile(ActionEvent event) {
        
    }

    @FXML
    private void directToCard(ActionEvent event) {
        
    }

    @FXML
    private void redirecttoLogin(ActionEvent event) {
        
    }
    private void updateLabels() {
        

    }


    public void directTOcommande(ActionEvent event) {
        
    }

    public void DirectTopost(ActionEvent event) {
        
    }

    public void DirecttoFormations(ActionEvent event) {
        
    }

    public void directToProjet(ActionEvent event) {
        
    }
}

