package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.User;
import tn.esprit.models.session;
import tn.esprit.services.UserService;
import tn.esprit.util.Navigator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateProfile {

    @FXML
    private TextField emailTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField numtelTF;

    @FXML
    private TextField prenomTF;

    @FXML
    void updateprofile(ActionEvent event) {
        if (!validateEmail(emailTF.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Adresse e-mail invalide.");
            return;
        }
        User currentUser = userservice.getOne(session.id_utilisateur);
        currentUser.setNom(nomTF.getText());
        currentUser.setPrenom(prenomTF.getText());
        currentUser.setNum_tel(Integer.parseInt(numtelTF.getText()));
        currentUser.setEmail(emailTF.getText());


        userservice.update(currentUser);


        // Show a confirmation alert
        showConfirmationAlert("User updated successfully!");

        Navigator nav = new Navigator();
        nav.goToPage_WithEvent("/ProfileUser.fxml",event);
    }




    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    private boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }



    @FXML
    private void initialize() {
        User user = userservice.getOne(session.id_utilisateur);
        nomTF.setText(user.getNom());
        prenomTF.setText(user.getPrenom());
        emailTF.setText(user.getEmail());
        numtelTF.setText(String.valueOf(user.getNum_tel()));

    }
UserService userservice = new UserService();




    private void showConfirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
