package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.models.hotel;
import tn.esprit.services.Hotelservices;

public class hotelModifier {

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField nbretoileTextField;

    @FXML
    private TextField emplacementTextField;

    @FXML
    private TextField avisTextField;

    private hotel hotelToModify;
    private Hotelservices hotelService = new Hotelservices();

    public void initData(hotel hotel) {
        hotelToModify = hotel;
        afficherDonneesInitiales();
    }

    private void afficherDonneesInitiales() {
        if (hotelToModify != null) {
            nomTextField.setText(hotelToModify.getNom());
            nbretoileTextField.setText(hotelToModify.getNbretoile());
            emplacementTextField.setText(hotelToModify.getEmplacement());
            avisTextField.setText(hotelToModify.getAvis());
        }
    }

    @FXML
    void sauvegarderModification(ActionEvent event) {
        if (hotelToModify == null) {
            showAlert("Aucun hôtel à modifier !");
            return;
        }

        // Récupérer les nouvelles valeurs des champs de texte
        String nouveauNom = nomTextField.getText();
        String nouvelleNbreEtoile = nbretoileTextField.getText();
        String nouvelEmplacement = emplacementTextField.getText();
        String nouvelAvis = avisTextField.getText();

        // Vérifier la validité des données (par exemple, si les champs sont vides)
        if (!isValidData(nouveauNom, nouvelleNbreEtoile, nouvelEmplacement, nouvelAvis)) {
            showAlert("Veuillez remplir tous les champs !");
            return;
        }

        // Mettre à jour les valeurs de l'objet hotelToModify
        hotelToModify.setNom(nouveauNom);
        hotelToModify.setNbretoile(nouvelleNbreEtoile);
        hotelToModify.setEmplacement(nouvelEmplacement);
        hotelToModify.setAvis(nouvelAvis);

        // Appeler la méthode update de la classe Hotelservices pour mettre à jour l'hôtel dans la base de données
        hotelService.update(hotelToModify);

        // Afficher un message de succès ou effectuer d'autres actions si nécessaire
        afficherMessageSucces();
    }

    private boolean isValidData(String nom, String nbreEtoile, String emplacement, String avis) {
        // Ajoutez ici vos conditions de validation, par exemple vérifier si les champs ne sont pas vides
        return !nom.isEmpty() && !nbreEtoile.isEmpty() && !emplacement.isEmpty() && !avis.isEmpty();
    }

    private void afficherMessageSucces() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modification réussie");
        alert.setHeaderText(null);
        alert.setContentText("Les modifications ont été enregistrées avec succès !");
        alert.showAndWait();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Champs vides");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
