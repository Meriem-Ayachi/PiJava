package tn.esprit.controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.models.User;
import tn.esprit.services.UserService;
import tn.esprit.util.EmailsUtils;
import tn.esprit.util.Navigator;

import static tn.esprit.util.EmailsUtils.sendVerificationEmail;;

public class inscription {


    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;

    @FXML
    private TextField emailTF;

    @FXML
    private PasswordField passwordTF;

    @FXML
    private PasswordField confirmPasswordTF;

    @FXML
    private TextField phoneTF;

    @FXML
    private ProgressBar loadingIndicator;
    @FXML
    private ProgressIndicator progressIndicator;

    private UserService userService = new UserService();
    private EmailsUtils emailUtils = new EmailsUtils();

    private Navigator nav = new Navigator();

    public void adduser(ActionEvent event) {
        // Vérification de la saisie
        if (nomTF.getText().isEmpty() || 
            prenomTF.getText().isEmpty() || 
            passwordTF.getText().isEmpty() || 
            confirmPasswordTF.getText().isEmpty()|| 
            emailTF.getText().isEmpty() || 
            phoneTF.getText().isEmpty()
            ) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Remplissez tous les champs.");
        }
        if (passwordTF.getText().length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le mot de passe doit contenir au moins 8 caractères.");
            return;
        }

        // Vérification de l'adresse e-mail avec une regex
        if (! emailUtils.validateEmail(emailTF.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Adresse e-mail invalide.");
            return;
        }

        if (userService.emailExists(emailTF.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "L'adresse e-mail existe déjà.");
            return;
        }

        if (!passwordTF.getText().equals(confirmPasswordTF.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Les mots de passe ne correspondent pas.");
            return;
        } 
        if (!isNumeric(phoneTF.getText()) || phoneTF.getText().length() != 8) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le numéro de téléphone doit être numérique et contenir 8 chiffres.");
            return;
        } 

        // Show the loading indicator
        loadingIndicator.setVisible(true);
        
        // Move email sending to a separate thread
        new Thread(() -> {
            String x=generateVerificationCode();
            sendVerificationEmail(emailTF.getText(),x);
            // Update UI on the JavaFX Application thread after email sending is complete
            Platform.runLater(() -> {
                // Hide the loading indicator
                loadingIndicator.setVisible(false);

                // Continue with the rest of your logic
                if (openVerificationWindow(emailTF.getText(),x) ){
                    // Verification window opened successfully
                    String[] role = {"ROLE_USER"};
                    User user = new User(emailTF.getText(), role, passwordTF.getText(), 1, nomTF.getText(), emailTF.getText(), Integer.parseInt(phoneTF.getText()));
                    userService.add(user);

                    // Afficher une confirmation
                    redirectToLoginPage(event);
                    EmailsUtils.envoyerEmailConfirmation(user);
                } else {
                    // Verification window failed to open, show an error message or take appropriate action
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Code de vérification incorrect.");
                }
            });
        }).start();
    }


    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    private String generateVerificationCode() {
        int code = 100000 + (int) (Math.random() * 900000);
        return String.valueOf(code);
    }

    private boolean openVerificationWindow(String userEmail, String verificationCode) {
        try {
            // Charger la page de vérification à partir du fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/verification.fxml"));
            Parent root = loader.load();

            // Obtenez le contrôleur de la fenêtre de vérification
            VerificationController verificationController = loader.getController();
            // Passez les informations nécessaires au contrôleur de la fenêtre de vérification
            verificationController.setUserEmail(userEmail);
            verificationController.setVerificationCode(verificationCode);

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Créer une nouvelle étape (stage) pour la fenêtre de vérification
            Stage verificationStage = new Stage();
            verificationStage.setScene(scene);

            // Afficher la fenêtre de vérification
            verificationStage.showAndWait();

            // Renvoyer true si la vérification a réussi, false sinon
            return verificationController.isVerificationSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }




    private void redirectToLoginPage(ActionEvent event) {
        nav.goToPage_WithEvent("/log.fxml", event);
    }

    public void directToLogin(ActionEvent event) {
        nav.goToPage_WithEvent("/log.fxml", event);
    }

}
