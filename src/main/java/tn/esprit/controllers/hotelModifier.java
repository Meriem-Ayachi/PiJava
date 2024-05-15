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

        // Vérifier la validité des données
        if (!isValidData(nouveauNom, nouvelleNbreEtoile, nouvelEmplacement, nouvelAvis)) {
            showAlert("Veuillez remplir tous les champs et saisir un nombre valide pour le nombre d'étoiles !");
            return;
        }

        // Vérifier si le nombre d'étoiles est valide
        if (!isValidNumberOfStars(nouvelleNbreEtoile)) {
            showAlert("Veuillez saisir un nombre valide entre 1 et 5 pour le nombre d'étoiles !");
            return;
        }

        // Vérifier si le champ d'avis contient des gros mots
        if (containsForbiddenWords(nouvelAvis)) {
            showAlert("Le champ d'avis contient des mots interdits !");
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

    // Méthode pour vérifier si le champ d'avis contient des mots interdits
    private boolean containsForbiddenWords(String avis) {
        // Liste de mots interdits (vous pouvez ajouter vos propres mots ici)
        String[] forbiddenWords = {"gros", "gros mot 2", "gros mot 3"};

        for (String word : forbiddenWords) {
            if (avis.toLowerCase().contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidNumberOfStars(String nbreEtoile) {
        // Vérifier si le nombre d'étoiles est entre 1 et 5
        try {
            int nbEtoiles = Integer.parseInt(nbreEtoile);
            return nbEtoiles >= 1 && nbEtoiles <= 5;
        } catch (NumberFormatException e) {
            return false; // Si la conversion en nombre échoue
        }
    }

    private boolean isValidData(String nom, String nbreEtoile, String emplacement, String avis) {
        // Vérifier si les champs ne sont pas vides
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