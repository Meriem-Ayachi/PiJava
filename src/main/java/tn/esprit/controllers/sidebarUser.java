package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.models.User;
import tn.esprit.models.session;
import tn.esprit.services.UserService;
import tn.esprit.util.Navigator;


public class sidebarUser {

    @FXML
    private Label name;

    @FXML
    private ImageView ImageUser;


    UserService userService=new UserService();
    Navigator nav = new Navigator();
    
    
    @FXML
    void initialize() {
        updateLabels();
        User user = userService.getOne(session.id_utilisateur);
        String imagepath = user.getImagefilename();
        if (imagepath != null){
            Image image = new Image("file:" + imagepath);
            ImageUser.setImage(image);
        }

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
        Navigator nav = new Navigator();
        nav.goToPage_WithEvent("/ProfileUser.fxml",event);
    }

}

