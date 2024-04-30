package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.User;
import tn.esprit.models.session;
import tn.esprit.services.UserService;
import tn.esprit.util.Navigator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
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
    private Text imagename;

    @FXML
    private ImageView imageuser;

    @FXML
    void updateprofile(ActionEvent event) {
        User currentUser = userservice.getOne(session.id_utilisateur);
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
        if (selectedFile != null) {
            //delete the old image
            try {
                File file = new File(currentUser.getImagefilename());
                file.delete();
            } catch (Exception e) {

            }
            //upload the new image
            String randomFileName = UUID.randomUUID().toString() + getFileExtension(selectedFile.getName());
            File destFile = new File(System.getProperty("user.home") + "/Downloads/" + randomFileName);
            try {
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                afficherErreur("Error uploading image: " + e.getMessage());
                return;
            }
            currentUser.setImagefilename(destFile.getAbsolutePath());
        }
        System.out.println(currentUser.getImagefilename());
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
        String imagepath = user.getImagefilename();
        if (imagepath != null){
            Image image = new Image("file:" + imagepath);
            imageuser.setImage(image);
        }

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
    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }



private File selectedFile;
    @FXML
    void uploadimage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");

        // Set initial directory to user's home directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        Stage stage = ((Stage) imagename.getScene().getWindow());
        selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            imagename.setText(selectedFile.getName());
        }
    }


    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }


    private String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex != -1 && lastIndex < fileName.length() - 1) {
            return fileName.substring(lastIndex);
        }
        return "";
    }


}
