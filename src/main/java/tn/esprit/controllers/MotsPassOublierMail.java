package tn.esprit.controllers;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.services.UserService;
import tn.esprit.util.Navigator;

import static tn.esprit.util.EmailsUtils.sendVerificationEmail_ForgotPassword;

public class MotsPassOublierMail {

    @FXML
    private TextField emailTF;
    private UserService us = new UserService();

    @FXML
    private ProgressBar loadingIndicator;

    @FXML
    void resetByEmail(ActionEvent event) {
        String email = emailTF.getText();
        if (!validateEmail(emailTF.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Adresse e-mail invalide.");
            return;
        }
        if (us.emailExists(email)){
            //show the loading indicator
            loadingIndicator.setVisible(true);
            //Send the email from a background thread so it doesnt block the application
            new Thread(() -> {
                //Send email for verification code
                String code=generateVerificationCode();
                sendVerificationEmail_ForgotPassword(emailTF.getText(),code);
                // Open the Verification windows after the email has been sent
                Platform.runLater(() -> {
                    // Hide the loading indicator
                    loadingIndicator.setVisible(false);
                    //Open the verification windows
                    if (openVerificationWindow(email, code)){
                        redirectToPasswordChange(event, email);
                    }else {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Code de vérification incorrect.");
                    }
                });
            }).start();
        }else{
            showAlert(Alert.AlertType.ERROR, "Erreur", "Email n'existe pas");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    private boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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

    private String generateVerificationCode() {
        int code = 100000 + (int) (Math.random() * 900000);
        return String.valueOf(code);
    }

    private void redirectToPasswordChange(ActionEvent event, String email){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Resetpassword.fxml"));
            Parent root = loader.load();

            Resetpassword controller = loader.getController();
            controller.initialize(email);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void directToLogin(ActionEvent event) {
        Navigator nav = new Navigator();
        nav.goToPage_WithEvent("/Log.fxml",event);
    }

}