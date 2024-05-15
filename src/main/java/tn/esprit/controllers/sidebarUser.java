package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.models.LogType;
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
        int oldUserId = session.id_utilisateur;
        User user = userService.getOne(oldUserId);
        LogController.saveLog("Utilisateur " + user.getPrenom() + " " + user.getNom() + " deconnecté.", LogType.DECONNEXION_UTILISATEUR, oldUserId);
        session.id_utilisateur = 0;
        nav.goToPage_WithEvent("/log.fxml", event);
    }

    public void directToHomepage(ActionEvent event) {
        System.out.println("Homepage user");
    }

    public void directToProfie(ActionEvent event) {
        Navigator nav = new Navigator();
        nav.goToPage_WithEvent("/UpdateProfile.fxml",event);
    }

    public void directToVol(ActionEvent event) {
        Navigator nav = new Navigator();
        nav.goToPage_WithEvent("/Showvolforuser.fxml",event);
    }

    @FXML
    void GoToHotel(ActionEvent event) {
        Navigator nav = new Navigator();
        nav.goToPage_WithEvent("/Fxml/hotelListF.fxml",event);

    }

    @FXML
    void GoToLocationVoiture(ActionEvent event) {
        Navigator nav = new Navigator();
        nav.goToPage_WithEvent("/userLocationList.fxml",event);
    }

    @FXML
    void GoToReservation(ActionEvent event) {
        Navigator nav = new Navigator();
        nav.goToPage_WithEvent("/Fxml/reservationAdd.fxml",event);
    }

    @FXML
    void GoToOffres(ActionEvent event) {
        Navigator nav = new Navigator();
        nav.goToPage_WithEvent("/home.fxml",event);

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


    @FXML
    void directToForum(ActionEvent event) {
        Navigator nav = new Navigator();
        nav.goToPage_WithEvent("/publication/PublicationFXML.fxml",event);
    }
}

