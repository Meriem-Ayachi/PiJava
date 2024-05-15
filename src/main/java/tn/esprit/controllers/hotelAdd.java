package tn.esprit.controllers;

import com.twilio.Twilio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import tn.esprit.models.Reservation;
import tn.esprit.models.hotel;
import tn.esprit.services.Hotelservices;
import com.twilio.type.PhoneNumber;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import com.twilio.rest.api.v2010.account.Message;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class hotelAdd {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String ACCOUNT_SID = dotenv.get("ACCOUNT_SID");
    private static final String AUTH_TOKEN = dotenv.get("AUTH_TOKEN");
    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    @FXML
    private TextField Nomf;
    @FXML
    private ListView<hotel> listView;
    @FXML
    private TextField emplacementf;
    @FXML
    private TextField avisf;
    @FXML
    private CheckBox etoile1;
    @FXML
    private CheckBox etoile2;
    @FXML
    private CheckBox etoile3;
    @FXML
    private CheckBox etoile4;
    @FXML
    private CheckBox etoile5;
    @FXML
    private ImageView etoileImageView1;
    @FXML
    private ImageView etoileImageView2;
    @FXML
    private ImageView etoileImageView3;
    @FXML
    private ImageView etoileImageView4;
    @FXML
    private ImageView etoileImageView5;

    private final Hotelservices hotelservice = new Hotelservices() {
        @Override
        public void generatePDF(List<Reservation> reservations, String filePath) {

        }

        @Override
        public void delete(hotel hotel) {

        }
    };

    @FXML
    public void add(ActionEvent actionEvent) {
        hotel hotel1 = new hotel();

        hotel1.setNom(Nomf.getText());
        hotel1.setEmplacement(emplacementf.getText());

        // Vérification du champ d'avis
        String avis = avisf.getText();
        if (containsForbiddenWords(avis)) {
            afficherErreur("Le champ d'avis contient des mots interdits.");
            return;
        }
        hotel1.setAvis(avis);

        // Récupération du nombre d'étoiles
        int nbetoiles = getNombreEtoiles();
        if (nbetoiles >= 1 && nbetoiles <= 5) {
            hotel1.setNbretoile(String.valueOf(nbetoiles));
        } else {
            afficherErreur("Veuillez sélectionner entre 1 et 5 étoiles.");
            return;
        }

        hotelservice.add(hotel1);
        afficherSucces("Hôtel ajouté avec succès!");

        // Envoyer un SMS après l'ajout d'un hôtel
        sendSMS();

        // Nettoyer les champs de saisie
        Nomf.clear();
        emplacementf.clear();
        avisf.clear();

        // Désélectionner les cases à cocher étoile
        etoile1.setSelected(false);
        etoile2.setSelected(false);
        etoile3.setSelected(false);
        etoile4.setSelected(false);
        etoile5.setSelected(false);
        goToHotelList(actionEvent);
    }

    private void afficherSucces(String s) {
        // À implémenter
    }

    private void afficherErreur(String s) {
        // À implémenter
    }

    private void sendSMS() {
        // Remplacer les valeurs suivantes par votre numéro Twilio et le numéro de téléphone de destination
        String twilioNumber = "+13082104766"; // Votre numéro Twilio
        String recipientNumber = "+21622303620"; // Numéro de téléphone du destinataire

        // Message à envoyer
        String messageBody = "merci d'avoir choisi notre hotel";

        // Envoyer le message SMS
        Message message = Message.creator(
                new PhoneNumber(recipientNumber),
                new PhoneNumber(twilioNumber),
                messageBody
        ).create();

        System.out.println("Message SID: " + message.getSid());
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

    // Méthode pour récupérer le nombre d'étoiles sélectionné
    private int getNombreEtoiles() {
        int nbEtoiles = 0;
        if (etoile1.isSelected()) {
            nbEtoiles++;
        }
        if (etoile2.isSelected()) {
            nbEtoiles++;
        }
        if (etoile3.isSelected()) {
            nbEtoiles++;
        }
        if (etoile4.isSelected()) {
            nbEtoiles++;
        }
        if (etoile5.isSelected()) {
            nbEtoiles++;
        }
        return nbEtoiles;
    }

    @FXML
    public void goToHotelList(ActionEvent event) {
        try {
            // Charger le fichier FXML de la liste des hôtels
            Parent root = FXMLLoader.load(getClass().getResource("/Fxml/hotelList.fxml"));
            // Modifier la racine de la scène actuelle pour afficher la liste des hôtels
            Nomf.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void deleteHotel(ActionEvent actionEvent) {
        hotel selectedHotel = listView.getSelectionModel().getSelectedItem();
        if (selectedHotel != null) {
            hotelservice.delete(selectedHotel);
            listView.getItems().remove(selectedHotel);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun hotel selectionne");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez selectionner un hotel a supprimer.");
            alert.showAndWait();
        }
    }
    @FXML
    private void resetEtoileImages() {
        // Réinitialiser toutes les images des étoiles à l'image vide
        etoileImageView1.setImage(new Image("/etoile_vide.png"));
        etoileImageView2.setImage(new Image("/etoile_vide.png"));
        etoileImageView3.setImage(new Image("/etoile_vide.png"));
        etoileImageView4.setImage(new Image("/etoile_vide.png"));
        etoileImageView5.setImage(new Image("/etoile_vide.png"));
    }


    @FXML
    private void etoile1Clicked() {
        // Changez l'image de l'étoile 1 pour l'image pleine
        etoileImageView1.setImage(new Image("/etoile_pleine.png"));
        // Réinitialisez les autres étoiles si nécessaire
        etoileImageView2.setImage(new Image("/etoile_vide.png"));
        etoileImageView3.setImage(new Image("/etoile_vide.png"));
        etoileImageView4.setImage(new Image("/etoile_vide.png"));
        etoileImageView5.setImage(new Image("/etoile_vide.png"));
    }

    @FXML
    private void etoile2Clicked() {
        // Changez l'image de l'étoile 2 pour l'image pleine
        etoileImageView1.setImage(new Image("/etoile_pleine.png"));
        etoileImageView2.setImage(new Image("/etoile_pleine.png"));
        // Réinitialisez les autres étoiles si nécessaire
        etoileImageView3.setImage(new Image("/etoile_vide.png"));
        etoileImageView4.setImage(new Image("/etoile_vide.png"));
        etoileImageView5.setImage(new Image("/etoile_vide.png"));
    }

    // Ajoutez des méthodes similaires pour les autres étoiles...
}
