package tn.esprit.controllers;

import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.models.hotel;
import tn.esprit.services.Hotelservices;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class hotelListF {

    @FXML
    private ListView<hotel> hotelListView;

    @FXML
    private Label nomLabel;

    @FXML
    private Label nbretoileLabel;

    @FXML
    private Label emplacementLabel;

    @FXML
    private Label avisLabel;
    @FXML
    private Pane chatBubble;

    @FXML
    private Button question1Button;

    @FXML
    private Button question2Button;

    @FXML
    private Button question3Button;

    @FXML
    private Button question4Button;

    @FXML
    private Label responseLabel;

    @FXML
    private ImageView firstGifImageView;

    @FXML
    private ImageView secondGifImageView;

    @FXML
    private Label floatingMessage;

    private boolean triAscendant = true;

    @FXML
    private TextField rechercheTextField;

    private Hotelservices hotelService = new Hotelservices();
    private ObservableList<hotel> hotels = FXCollections.observableArrayList();
    @FXML
    void showChatBubble() {
        hideFloatingMessage();
        chatBubble.setVisible(true);

        // Changer la visibilité des images
        firstGifImageView.setVisible(false);
        secondGifImageView.setVisible(true);
    }
    @FXML
    private void handleQuestion1() {
        showResponse("Nos horaires sont de 08:00 à 17:00.");
    }

    @FXML
    private void handleQuestion2() {
        showResponse("Pour prendre une reservation allez vers espace reservation puis cliquez sur Prendre un reserver et remplissez le formulaire");
    }

    @FXML
    private void handleQuestion3() {
        showResponse("Les créateurs de Tech-Voyages sont un groupe de jeunes ingénieurs motivés à aider les gens à améliorers leurs voyages");
    }

    @FXML
    private void handleQuestion4() {
        showResponse("Si aucune de ses questions ne correspond à votre besoin, veuillez nous contacter sur notre Numero : +216 223030620");
    }
    private void showResponseWithTypingEffect(String response) {
        // Initialiser le texte de la réponse avec une chaîne vide
        responseLabel.setText("");

        // Durée de la transition pour chaque lettre (ms)
        int letterTransitionDuration = 100;

        // Afficher chaque lettre avec un effet de dactylographie
        Timeline timeline = new Timeline();
        for (int i = 0; i <= response.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(i * letterTransitionDuration), event -> {
                // Ajouter une lettre de plus à chaque itération
                responseLabel.setText(response.substring(0, finalI));
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }


    private void showFloatingMessage() {
        floatingMessage.setVisible(true);
        // Le message à afficher
        String message = "Besoin d'aide ?";

        // Initialiser le message avec une chaîne vide
        floatingMessage.setText("");

        // Définir la position initiale en dehors de la zone visible
        floatingMessage.setTranslateX(400);

        // Créer une transition de translation pour faire apparaître le message de la droite vers la gauche
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), floatingMessage);
        translateTransition.setToX(0);

        // Ajouter une transition de rotation pour un effet plus flottant
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0), floatingMessage);
        rotateTransition.setByAngle(10); // Angle de rotation (en degrés)
        rotateTransition.setCycleCount(TranslateTransition.INDEFINITE); // Répéter indéfiniment

        // Définir le texte du message après l'animation
        translateTransition.setOnFinished(event -> {
            floatingMessage.setText(message);

            // Lancer la transition de rotation
            rotateTransition.play();

            // Ajouter l'effet de dactilographie (typing effect)
            showFloatingMessageWithTyping();

            // Ajouter l'effet de flottement vertical
            showFloatingVertical();
        });

        // Lancer la transition de translation
        translateTransition.play();
    }

    @FXML
    void hideFloatingMessage() {
        // Masquer le message flottant
        floatingMessage.setVisible(false);
    }


    private void showFloatingVertical() {
        // Créer une transition de translation verticale
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), floatingMessage);
        translateTransition.setFromY(0);
        translateTransition.setToY(-20); // Déplacement de -20 pixels vers le haut
        translateTransition.setAutoReverse(true); // Pour un effet de retour
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE); // Pour répéter indéfiniment

        // Lancer la transition
        translateTransition.play();
    }

    public void initialize() {
        // Charger les hôtels depuis le service
        secondGifImageView.setVisible(false);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(1500), event -> showFloatingMessage())
        );
        timeline.play();
        hotels.addAll(hotelService.getAll());

        // Tri par nombre d'étoiles
        trierParNombreEtoiles(triAscendant);

        hotelListView.setItems(hotels);

        // Définir le comportement lorsque la sélection de la liste change
        hotelListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                afficherDetailsHotel(newValue);
            }
        });

        // Mettre en place la recherche par nom
        setupRecherche();

        question1Button.setOnAction(event -> handleQuestion1());
        question2Button.setOnAction(event -> handleQuestion2());
        question3Button.setOnAction(event -> handleQuestion3());
        question4Button.setOnAction(event -> handleQuestion4());
    }
    @FXML
    private void showResponse(String response) {
        showResponseWithTypingEffect(response);
        // Afficher la réponse et cacher les boutons des questions
        responseLabel.setText(response);
        responseLabel.setVisible(true);
        responseLabel.setWrapText(true); // Permettre le retour à la ligne
        responseLabel.setMaxWidth(chatBubble.getPrefWidth()); // Définir la largeur maximale sur celle de la bulle de discussion
        question1Button.setVisible(false);
        question2Button.setVisible(false);
        question3Button.setVisible(false);
        question4Button.setVisible(false);
    }

    @FXML
    void hideChatBubble() {
        chatBubble.setVisible(false);
        // Afficher les questions et cacher la réponse lors de la fermeture de la bulle de discussion
        showQuestions();
    }
    @FXML
    private void showQuestions() {
        responseLabel.setText(""); // Effacer le texte de la réponse
        responseLabel.setVisible(false); // Cacher la réponse
        // Afficher les boutons des questions
        question1Button.setVisible(true);
        question2Button.setVisible(true);
        question3Button.setVisible(true);
        question4Button.setVisible(true);
    }

    @FXML
    void showFirstGif() {
        firstGifImageView.setVisible(true);
        secondGifImageView.setVisible(false);
        hideChatBubble();
    }

    private void showFloatingMessageWithTyping() {
        // Le message à afficher
        String message = "Besoin d'aide ?";

        // Durée de la transition pour chaque lettre (ms)
        int letterTransitionDuration = 100;

        // Initialiser le message avec une chaîne vide
        floatingMessage.setText("");

        // Afficher chaque lettre avec un effet "typing"
        Timeline timeline = new Timeline();
        for (int i = 0; i <= message.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(i * letterTransitionDuration), event -> {
                // Ajouter une lettre de plus à chaque itération
                floatingMessage.setText(message.substring(0, finalI));
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }




    public void showChatBubbleWithAnimation() {
        // Créer une transition de translation
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), chatBubble);
        translateTransition.setFromX(800);
        translateTransition.setToX(800);
        translateTransition.setFromY(630);
        translateTransition.setToY(480);

        // Créer une transition de fondu
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), chatBubble);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        // Inverser la visibilité des images pour afficher le deuxième GIF
        firstGifImageView.setVisible(true);
        secondGifImageView.setVisible(false);

        // Exécuter les transitions en séquence
        translateTransition.play();
        fadeTransition.play();
    }

    @FXML
    private void trierParEtoiles() {
        triAscendant = !triAscendant;
        trierParNombreEtoiles(triAscendant);
    }

    private void trierParNombreEtoiles(boolean ascendant) {
        List<hotel> sortedHotels = hotelService.getAll().stream()
                .sorted(ascendant ? Comparator.comparingInt(h -> Integer.parseInt(h.getNbretoile())) :
                        (h1, h2) -> Integer.parseInt(h2.getNbretoile()) - Integer.parseInt(h1.getNbretoile()))
                .collect(Collectors.toList());
        hotels.setAll(sortedHotels);
    }

    @FXML
    private void setupRecherche() {
        rechercheTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                // Si le champ de recherche est vide, afficher tous les hôtels
                hotelListView.setItems(hotels);
            } else {
                // Filtrer les hôtels selon le texte de recherche
                List<hotel> filteredHotels = hotelService.getAll().stream()
                        .filter(h -> h.getNom().toLowerCase().contains(newValue.toLowerCase()))
                        .collect(Collectors.toList());
                hotelListView.setItems(FXCollections.observableArrayList(filteredHotels));
            }
        });
    }

    private void afficherDetailsHotel(hotel hotel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/hotelAfficherF.fxml"));
            Parent root = loader.load();

            // Passer l'hôtel sélectionné au contrôleur de la vue hotelAfficherF.fxml
            hotelAfficherF controller = loader.getController();
            controller.setSelectedHotelF(hotel);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Ajouter votre logique d'affichage des détails de l'hôtel ici
    }

}
