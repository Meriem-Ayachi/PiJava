package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import tn.esprit.models.User;
import tn.esprit.models.session;
import tn.esprit.services.UserService;
import tn.esprit.util.Navigator;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;



public class sidebarUser {

    @FXML
    private Label name;

    
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

    public void directToHomepage(ActionEvent event) {
        System.out.println("Homepage user");
    }

    public void directToProfile(ActionEvent event) {
        System.out.println("Profile user");
    }
    public void directToListeReclamationUser(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamationUser.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

