package tn.esprit.controllers.locationVoiture;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.MainFX;
import tn.esprit.models.Voiture;
import tn.esprit.services.VoitureService;
import tn.esprit.util.Navigator;

public class AjouterVoiture {
    @FXML
    private TextField marqueTF;

    @FXML
    private TextField modelTF;
    
    @FXML
    private TextField couleurTF;

    @FXML
    private ComboBox<String> energyTF;

    @FXML
    private TextField capaciteTF;
    
    @FXML
    private Text uploadedFileName;

    private File selectedFile;

    @FXML
    void AfficherVoitures(ActionEvent event) {
        Stage stage = MainFX.getPrimaryStage();
        Navigator nav = new Navigator(stage);
        nav.goToPage("/ListVoitures.fxml");
    }

    @FXML
    void initialize() {
        ObservableList<String> energyType = FXCollections.observableArrayList(
            "Gasoline"
            , "Diesel"
            , "Electric"
        );
        energyTF.setItems(energyType);
    }

    public void fillInputs(String marque, String model, String energy, String capacite){
        modelTF.setText(model);
        marqueTF.setText(marque);
        energyTF.setValue(energy);
        capaciteTF.setText(capacite);
    }

    @FXML
    void ajouterVoiture(ActionEvent event) {
            
        // marque control sasie
        if (marqueTF.getText().isEmpty()) {
            afficherErreur("Veuillez saisir une marque.");
            return;
        }
        if (marqueTF.getText().length() < 3) {
            afficherErreur("La marque doit comporter au moins 3 caractères.");
            return;
        }

        // model control sasie
        if (modelTF.getText().isEmpty()) {
            afficherErreur("Veuillez saisir une model.");
            return;
        }
        if (modelTF.getText().length() < 3) {
            afficherErreur("La model doit comporter au moins 3 caractères.");
            return;
        }

        // couleur control sasie
        if (couleurTF.getText().isEmpty()) {
            afficherErreur("Veuillez saisir une couleur.");
            return;
        }
        if (couleurTF.getText().length() < 3) {
            afficherErreur("La couleur doit comporter au moins 3 caractères.");
            return;
        }

        // energy control sasie
        if (energyTF.getValue() == null) {
            afficherErreur("Veuillez saisir une energy.");
            return;
        }

        // capacité control sasie
        try {
            double capacite = Double.parseDouble(capaciteTF.getText());
            if (capacite <= 0) {
                afficherErreur("La valeur du capacite doit être supérieure à 0.");
                return;
            }
        } catch (NumberFormatException e) {
            afficherErreur("La valeur du capacite doit être un nombre.");
            return;
        }

        // image control sasie
        if (selectedFile == null) {
            afficherErreur("No image selected.");
            return;
        }


        VoitureService voitureService = new VoitureService();
        Voiture voiture = new Voiture();

        {
            // confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Voiture ajoutée");
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            // check if user has declined
            if (result != ButtonType.OK) {
                return;
            }
            //  upload the selected image
            String randomFileName = UUID.randomUUID().toString() + getFileExtension(selectedFile.getName());
            File destFile = new File(System.getProperty("user.home") + "/Downloads/" + randomFileName);
            try {
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                afficherErreur("Error uploading image: " + e.getMessage());
                return;
            }
            //set the item
            voiture.setImage_file_name(destFile.getAbsolutePath());
            voiture.setMarque(marqueTF.getText());
            voiture.setModel(modelTF.getText());
            voiture.setCouleur(couleurTF.getText());
            voiture.setEnergy(energyTF.getValue());
            voiture.setCapacite(Integer.parseInt(capaciteTF.getText()));
            // add item
            voitureService.add(voiture);
            // go to the list
            Stage stage = MainFX.getPrimaryStage();
            Navigator nav = new Navigator(stage);
            nav.goToPage("/ListVoitures.fxml");
        }
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void uploadFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        selectedFile = fileChooser.showOpenDialog(MainFX.getPrimaryStage());
        if (selectedFile != null) {
            uploadedFileName.setText(selectedFile.getName());
        }
    }


    private String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex != -1 && lastIndex < fileName.length() - 1) {
            return fileName.substring(lastIndex);
        }
        return "";
    }



    @FXML
    void rechercheVoitureApi(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/rechercheVoiture.fxml"));
            AnchorPane rechercheVoitureAnchorPane = loader.load();
            rechercheVoiture controller = loader.getController();

            // Appeler la méthode pour initialiser les détails de la réclamation
            controller.initialize();

            // Afficher l'interface dans une nouvelle fenêtre
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(rechercheVoitureAnchorPane));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*private void afficherMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setContentText(message);
        alert.showAndWait();
    }*/

}