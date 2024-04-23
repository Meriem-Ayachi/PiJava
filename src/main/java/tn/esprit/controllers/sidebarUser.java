package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tn.esprit.controllers.locationVoiture.userLocationList;
import tn.esprit.models.User;
import tn.esprit.models.session;
import tn.esprit.services.UserService;
import tn.esprit.util.Navigator;


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

    public void directToLocationVoiture(ActionEvent event) {
        nav.goToPage_WithEvent("/userLocationList.fxml", event);
    }

    public void directToProfile(ActionEvent event) {
        System.out.println("Profile user");
    }

}

