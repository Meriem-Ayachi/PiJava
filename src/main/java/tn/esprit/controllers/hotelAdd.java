package tn.esprit.controllers;

import com.twilio.Twilio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.esprit.models.Reservation;
import tn.esprit.models.hotel;
import tn.esprit.services.Hotelservices;
import com.twilio.type.PhoneNumber;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import com.twilio.rest.api.v2010.account.Message;
import io.github.cdimascio.dotenv.Dotenv;

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
    private TextField nbetoilesf;
    @FXML
    private TextField emplacementf;
    @FXML
    private TextField avisf;

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

        try {
            int nbetoiles = Integer.parseInt(nbetoilesf.getText());
            if (nbetoiles >= 1 && nbetoiles <= 5) {
                hotel1.setNbretoile(String.valueOf(nbetoiles));
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            afficherErreur("Veuillez entrer un nombre valide entre 1 et 5 pour le nombre d'étoiles.");
            return;
        }

        hotelservice.add(hotel1);
        afficherSucces("Hôtel ajouté avec succès!");

        // Envoyer un SMS après l'ajout d'un hôtel
        sendSMS();

        // Nettoyer les champs de saisie
        Nomf.clear();
        emplacementf.clear();
        nbetoilesf.clear();
        avisf.clear();
    }

    private void afficherSucces(String s) {
    }

    private void afficherErreur(String s) {
        
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



    // Méthode pour envoyer un e-mail
//    private void sendEmail() {
//        // Adresse e-mail du destinataire
//        String recipient = "maloukabensdira3@gmail.com";
//
//        // Objet du message
//        String subject = "Nouvelle réservation effectuée";
//
//        // Contenu du message
//        String content = "La réservation a été effectuée avec succès !";
//
//        // Propriétés pour configurer la connexion SMTP
//        Properties properties = new Properties();
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.port", "587");
//
//        // Adresse e-mail et mot de passe de l'expéditeur
//        String senderEmail = "malekabdelkader.bensdira@esprit.tn";
//        String password = "sdira123";
//
//        // Création de la session
//        Session session = Session.getInstance(properties, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(senderEmail, password);
//            }
//        });
//
//        try {
//            // Création de l'objet MimeMessage
//            Message message = new MimeMessage(session);
//
//            // Définition de l'expéditeur
//
//
//            // Définition du destinataire
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
//
//            // Définition de l'objet et du contenu du message
//            message.setSubject(subject);
//            message.setText(content);
//
//            // Envoi du message
//            Transport.send(message);
//
//            System.out.println("Message envoyé avec succès !");
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            System.out.println("Erreur lors de l'envoi du message : " + e.getMessage());
//        }
//    }

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
}
