package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.models.User;
import tn.esprit.models.session;
import tn.esprit.services.UserService;
import tn.esprit.util.Navigator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModifierUserparAdmin {

    @FXML
    private TextField emailTF;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField numtelTF;

    @FXML
    private TextField prenomTF;

    @FXML
    public void initialize(User user) {

        nomTF.setText(user.getNom());
        prenomTF.setText(user.getPrenom());
        emailTF.setText(user.getEmail());
        numtelTF.setText(String.valueOf(user.getNum_tel()));
        currentUser = user;

    }
    User currentUser ;
    UserService userservice = new UserService();
    @FXML
    void ModifierUser(ActionEvent event) {
        if (nomTF.getText().isEmpty() ||
                prenomTF.getText().isEmpty() ||
                emailTF.getText().isEmpty() ||
                numtelTF.getText().isEmpty()
        ) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Remplissez tous les champs.");
            return;
        }
        if (!validateEmail(emailTF.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Adresse e-mail invalide.");
            return;
        }
        if (!emailTF.getText().equals(currentUser.getEmail()) && userservice.emailExists(emailTF.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "L'adresse e-mail existe déjà.");
            return;
        }
        if (!isNumeric(numtelTF.getText()) || numtelTF.getText().length() != 8) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le numéro de téléphone doit être numérique et contenir 8 chiffres.");
            return;
        }
        currentUser.setNom(nomTF.getText());
        currentUser.setPrenom(prenomTF.getText());
        currentUser.setNum_tel(Integer.parseInt(numtelTF.getText()));
        currentUser.setEmail(emailTF.getText());


        userservice.update(currentUser);


        // Show a confirmation alert
        showConfirmationAlert("User updated successfully!");

        Navigator nav = new Navigator();
        nav.goToPage_WithEvent("/GestionUtilisateurs.fxml",event);
    }


private static final String EMAIL_REGEX =
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

private boolean validateEmail(String email) {
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
}

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



    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

}