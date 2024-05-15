package tn.esprit.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.User;
import tn.esprit.services.UserService;
import static tn.esprit.util.EmailsUtils.envoyerEmailConfirmation_ForgotPassword;

public class Resetpassword {

    private UserService us = new UserService();

    private User currentUser;


    @FXML
    private PasswordField confirmPasswordTF;

    @FXML
    private PasswordField passwordTF;

    public void initialize(String email){
        int userId = us.getUtilisateurid(email);
        User user = us.getOne(userId);
        currentUser = user;
    }

    @FXML
    void resetPassword(ActionEvent event) {
        // control sasie
        if (passwordTF.getText().length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le mot de passe doit contenir au moins 8 caractères.");
            return;
        }
        if (!passwordTF.getText().equals(confirmPasswordTF.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Les mots de passe ne correspondent pas.");
            return;
        }

        // update password
        currentUser.setPassword(passwordTF.getText());
        us.updatePassword(currentUser);
        showAlert(Alert.AlertType.INFORMATION, "Success", "mot de passe a été modifié avec success.");
        redirectToLogin(event);
        new Thread(() -> { envoyerEmailConfirmation_ForgotPassword(currentUser);
        }).start();
    }

    private void redirectToLogin(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/log.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}