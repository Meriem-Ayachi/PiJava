package tn.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.models.User;
import tn.esprit.models.session;
import tn.esprit.services.UserService;

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
    @FXML
    void redirectVersAfficherCategories(ActionEvent event) {
        
    }

    @FXML
    void redirectVersAfficherProjets(ActionEvent event) {
        
    }


    @FXML
    void initialize() {
        updateLabels();
    }

    public void redirectVersAcceuil(ActionEvent event) {
        
    }

    @FXML
    public void diectutuilisateur(ActionEvent event) {
        
    }

    public void ProfileClient(MouseEvent mouseEvent) {
        

    }
    private void updateLabels() {
        

    }

    public void directTocommande(ActionEvent event) {
        
    }

    public void directToLogin(ActionEvent event) {
        
    }


}


