package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.models.User;
import tn.esprit.models.session;
import tn.esprit.services.UserService;

import java.io.IOException;

public class ProfileUser {
    @FXML
    private Label email;

    @FXML
    private Label nom;

    @FXML
    private Label prenom;

    @FXML
    private Label tel;

    @FXML
    public void initialize() {
        // Assurez-vous que le userService est initialisé avant d'appeler cette méthode
        updateLabels();
    }

    public UserService userService=new UserService();

    private void updateLabels() {
        User utilisateur = userService.getOne(session.id_utilisateur);


        // Mettez à jour les labels avec les données de l'utilisateur
        nom.setText(utilisateur.getNom());
        prenom.setText(utilisateur.getPrenom());
        email.setText(utilisateur.getEmail());
        tel.setText(String.valueOf(utilisateur.getNum_tel()));
    }

    @FXML
    void gotomodifieprofile(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateProfile.fxml"));
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
