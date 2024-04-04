package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import tn.esprit.models.User;
import tn.esprit.services.UserService;

import javax.print.DocFlavor;
import java.util.ResourceBundle;

public class AjoutUser {


    @FXML
    private TextField emailid;

    @FXML
    private CheckBox isvid;

    @FXML
    private TextField nomid;

    @FXML
    private TextField passwordid;

    @FXML
    private TextField phoneid;

    @FXML
    private TextField prenomid;

    @FXML
    private TextField rolesid;

    @FXML
    void Adduser(ActionEvent event) {
        // Vérifier si le champ nom est vide
        if (nomid.getText().isEmpty()) {
            afficherErreur("Veuillez saisir un nom.");
            return;
        }
        if (nomid.getText().length() < 3) {
            afficherErreur("Le nom doit comporter au moins 3 caractères.");
            return;
        }
        // Vérifier si le champ prenom est vide
        if (prenomid.getText().isEmpty()) {
            afficherErreur("Veuillez saisir un prenom.");
            return;
        }
        if (prenomid.getText().length() < 3) {
            afficherErreur("Le prenom doit comporter au moins 3 caractères.");
            return;
        }

        // Vérifier si le champ description est vide
        if (emailid.getText().isEmpty()) {
            afficherErreur("Veuillez saisir une email.");
            return;
        }

        // Vérifier si le champ phone est vide
        if (phoneid.getText().isEmpty()) {
            afficherErreur("Veuillez saisir un phone number.");
            return;
        }
        if (phoneid.getText().length() < 3) {
            afficherErreur("Le numtel doit comporter au moins 3 chiffre.");
            return;
        }

        // Vérifier si le champ phone est vide
        if (passwordid.getText().isEmpty()) {
            afficherErreur("Veuillez saisir un Password.");
            return;
        }
        if (passwordid.getText().length() < 3) {
            afficherErreur("Le Password doit comporter au moins 4 caractères.");
            return;
        }



        // Rendre le libellé en lecture seule
        // Note: Vous ne pouvez pas rendre un Label en lecture seule, mais vous pouvez le rendre non-éditable
        // Et il est préférable de ne pas modifier l'interface utilisateur après l'initialisation dans cette méthode


        UserService userservice = new UserService();
        User user = new User();
        user.setEmail(emailid.getText());
        user.setNom(nomid.getText());
        user.setPrenom(prenomid.getText());
        user.setPassword(passwordid.getText());
        user.setRoles(rolesid.getText());
        user.setNum_tel(Integer.parseInt(phoneid.getText()));
        user.setIs_verified(isvid.isSelected()? (byte) 1 : (byte) 0);
        {
            userservice.add(user);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("User ajoutée");
            alert.showAndWait();
        }
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void ListeUser(ActionEvent event) {

    }
}
