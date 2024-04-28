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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.MainFX;
import tn.esprit.models.Voiture;
import tn.esprit.services.VoitureService;
import tn.esprit.util.Navigator;

public class ModifierVoiture {

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

    private int voiture_id;

    @FXML
    private Text uploadedFileName;

    private File selectedFile;

    public void initialize(Voiture voiture) {
        // get id
        voiture_id = voiture.getId();
        // fill the UI with the voiture values
        marqueTF.setText(voiture.getMarque());
        modelTF.setText(voiture.getModel());
        couleurTF.setText(voiture.getCouleur());
        energyTF.setValue(voiture.getEnergy());
        capaciteTF.setText(String.valueOf(voiture.getCapacite()));

        
        ObservableList<String> energyType = FXCollections.observableArrayList(
            "Gasoline"
            , "Diesel"
            , "Electricity"
        );
        energyTF.setItems(energyType);
    }

    @FXML
    void AfficherVoitures(ActionEvent event) {
        Stage stage = MainFX.getPrimaryStage();
        Navigator nav = new Navigator(stage);
        nav.goToPage("/ListVoitures.fxml");
    }

    @FXML
    void modifierVoiture(ActionEvent event) {
            
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



        VoitureService voitureService = new VoitureService();
        Voiture voiture = voitureService.getOne(voiture_id);
        {
            // confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Voiture Modifier");
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            // check if user has declined
            if (result != ButtonType.OK) {
                return;
            }
            //check if there is a file uploaded, if there isnt then dont change the saved file path
            if (selectedFile != null) {
                //delete the old image
                try {
                    File file = new File(voiture.getImage_file_name());
                    file.delete();
                } catch (Exception e) {
                    afficherErreur("Error while deleting the old image: " + e.getMessage());
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
                voiture.setImage_file_name(destFile.getAbsolutePath());
            }
            System.out.println(voiture.getImage_file_name());

            //set item
            voiture.setMarque(marqueTF.getText());
            voiture.setModel(modelTF.getText());
            voiture.setCouleur(couleurTF.getText());
            voiture.setEnergy(energyTF.getValue());
            voiture.setCapacite(Integer.parseInt(capaciteTF.getText()));
            // add item
            voitureService.update(voiture);
            
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


    private String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex != -1 && lastIndex < fileName.length() - 1) {
            return fileName.substring(lastIndex);
        }
        return "";
    }

    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        try {
            file.delete();
        } catch (Exception e) {

        }
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
}
