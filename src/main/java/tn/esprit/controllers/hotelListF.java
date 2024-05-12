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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
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
        firstGifImageView.setVisible(false);
        secondGifImageView.setVisible(true);
    }

    @FXML
    void gotoHotelReserver(){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/hotelListReserver.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) secondGifImageView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        responseLabel.setText("");
        int letterTransitionDuration = 50;
        Timeline timeline = new Timeline();
        for (int i = 0; i <= response.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(i * letterTransitionDuration), event -> {
                responseLabel.setText(response.substring(0, finalI));
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }

    private void showFloatingMessage() {
        floatingMessage.setVisible(true);
        String message = "Besoin d'aide ?";
        floatingMessage.setText("");
        floatingMessage.setTranslateX(400);
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), floatingMessage);
        translateTransition.setToX(0);
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0), floatingMessage);
        rotateTransition.setByAngle(10);
        rotateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        translateTransition.setOnFinished(event -> {
            floatingMessage.setText(message);
            rotateTransition.play();
            showFloatingMessageWithTyping();
            showFloatingVertical();
        });
        translateTransition.play();
    }

    @FXML
    void hideFloatingMessage() {
        floatingMessage.setVisible(false);
    }

    private void showFloatingVertical() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), floatingMessage);
        translateTransition.setFromY(0);
        translateTransition.setToY(-20);
        translateTransition.setAutoReverse(true);
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        translateTransition.play();
    }

    public void initialize() {
        secondGifImageView.setVisible(false);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1500), event -> showFloatingMessage()));
        timeline.play();
        hotels.addAll(hotelService.getAll());
        trierParNombreEtoiles(triAscendant);
        hotelListView.setItems(hotels);
        hotelListView.setCellFactory(new Callback<ListView<hotel>, ListCell<hotel>>() {
            @Override
            public ListCell<hotel> call(ListView<hotel> param) {
                return new ListCell<hotel>() {
                    @Override
                    protected void updateItem(hotel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            Label nomText = new Label("Nom: ");
                            nomText.setStyle("-fx-font-weight: bold");
                            Label nomValue = new Label(item.getNom());
                            nomValue.setStyle("-fx-font-style: italic");

                            Label nbreEtoilesText = new Label("Nombre d'étoiles: ");
                            nbreEtoilesText.setStyle("-fx-font-weight: bold");
                            Label nbreEtoilesValue = new Label(item.getNbretoile());
                            nbreEtoilesValue.setStyle("-fx-font-style: italic");

                            Label emplacementText = new Label("Emplacement: ");
                            emplacementText.setStyle("-fx-font-weight: bold");
                            Label emplacementValue = new Label(item.getEmplacement());
                            emplacementValue.setStyle("-fx-font-style: italic");

                            Label avisText = new Label("Avis: ");
                            avisText.setStyle("-fx-font-weight: bold");
                            Label avisValue = new Label(item.getAvis());
                            avisValue.setStyle("-fx-font-style: italic");

                            HBox hbox = new HBox(nomText, nomValue, new Label(), nbreEtoilesText, nbreEtoilesValue,
                                    new Label(), emplacementText, emplacementValue, new Label(), avisText, avisValue);
                            hbox.setSpacing(5);

                            setGraphic(hbox);
                        }
                    }
                };
            }
        });
        hotelListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                afficherDetailsHotel(newValue);
            }
        });
        setupRecherche();
        question1Button.setOnAction(event -> handleQuestion1());
        question2Button.setOnAction(event -> handleQuestion2());
        question3Button.setOnAction(event -> handleQuestion3());
        question4Button.setOnAction(event -> handleQuestion4());
    }

    @FXML
    private void showResponse(String response) {
        showResponseWithTypingEffect(response);
        responseLabel.setText(response);
        responseLabel.setVisible(true);
        responseLabel.setWrapText(true);
        responseLabel.setMaxWidth(chatBubble.getPrefWidth());
        question1Button.setVisible(false);
        question2Button.setVisible(false);
        question3Button.setVisible(false);
        question4Button.setVisible(false);
    }

    @FXML
    void hideChatBubble() {
        chatBubble.setVisible(false);
        showQuestions();
    }

    @FXML
    private void showQuestions() {
        responseLabel.setText("");
        responseLabel.setVisible(false);
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
        String message = "Besoin d'aide ?";
        int letterTransitionDuration = 100;
        floatingMessage.setText("");
        Timeline timeline = new Timeline();
        for (int i = 0; i <= message.length(); i++) {
            int finalI = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(i * letterTransitionDuration), event -> {
                floatingMessage.setText(message.substring(0, finalI));
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }

    public void showChatBubbleWithAnimation() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), chatBubble);
        translateTransition.setFromX(800);
        translateTransition.setToX(800);
        translateTransition.setFromY(630);
        translateTransition.setToY(480);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), chatBubble);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        firstGifImageView.setVisible(true);
        secondGifImageView.setVisible(false);
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
                hotelListView.setItems(hotels);
            } else {
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
            hotelAfficherF controller = loader.getController();
            controller.setSelectedHotelF(hotel);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
