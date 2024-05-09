package tn.esprit.controllers.locationVoiture;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.MainFX;
import tn.esprit.models.Location_Voiture;
import tn.esprit.models.Voiture;
import tn.esprit.models.session;
import tn.esprit.services.LocationVoitureService;
import tn.esprit.services.VoitureService;
import tn.esprit.util.Navigator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class userLocationList_reserver {
    
    @FXML
    private ListView<Location_Voiture> listview;

    private final LocationVoitureService LocationService = new LocationVoitureService();
    private final VoitureService voitureService = new VoitureService();

    @FXML
    void initialize() {
        int userid = session.id_utilisateur;
        List<Location_Voiture> locationsVoiture = LocationService.getAll_UserIdReserved(userid);
        listview.getItems().addAll(locationsVoiture);

        listview.setCellFactory(param -> new ListCell<Location_Voiture>() {
            @Override
            protected void updateItem(Location_Voiture item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Create labels for destination, departure date, and arrival date


                    Voiture v = voitureService.getOne(item.getVoiture_id());
                    Label voitureLabel = new Label(v.getModel());
                    Label marqueLabel = new Label("Marque: " + v.getMarque());
                    Label typeLabel = new Label("Type: " + item.getType());
                    Label prixLabel = new Label("Prix (par jour): " + item.getPrix());
                    Label data_debutLabel = new Label("Date debut: " + item.getDate_debut());
                    Label date_finLabel = new Label("Date fin: " + item.getDatefin());
                    
                    // Set styles for labels
                    voitureLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");
                    marqueLabel.setStyle("-fx-font-size: 12pt;");
                    typeLabel.setStyle("-fx-font-size: 12pt;");
                    prixLabel.setStyle("-fx-font-size: 12pt;");
                    data_debutLabel.setStyle("-fx-font-size: 12pt;");
                    date_finLabel.setStyle("-fx-font-size: 12pt;");

                    // Create "Show More" button
                    Button DownloadButton = new Button("Download Pdf");

                    // Add action handler for the show more button
                    DownloadButton.setOnAction(event -> {
                        DownloadPdf(item);
                    });

                    // Create a VBox to hold all labels vertically
                    VBox labelsVBox = new VBox();
                    labelsVBox.getChildren().addAll(voitureLabel,marqueLabel, typeLabel, prixLabel, data_debutLabel, date_finLabel);
                    labelsVBox.setAlignment(Pos.CENTER_LEFT); // Align children to the left
                    labelsVBox.setSpacing(5); // Adjust spacing between elements

                    // Create a VBox to hold price label and "Show More" button
                    VBox ButtonsVBox = new VBox();
                    ButtonsVBox.getChildren().addAll(DownloadButton);
                    ButtonsVBox.setAlignment(Pos.CENTER_RIGHT); // Align to the right
                    ButtonsVBox.setSpacing(5); // Adjust spacing between elements

                    // Create an AnchorPane to hold all elements
                    AnchorPane anchorPane = new AnchorPane();
                    ImageView imageView = new ImageView();

                    String imagePath = v.getImage_file_name();
                    if ( imagePath != null && !imagePath.isEmpty()){
                        // Load the image
                        Image image = new Image("file:" + imagePath);
    
                        // Create the ImageView with the loaded image
                        imageView = new ImageView(image);
                        imageView.setFitWidth(100); // Set width as per your requirement
                        imageView.setPreserveRatio(true); // Preserve aspect ratio
    
                        // Set image position
                        AnchorPane.setTopAnchor(imageView, 5.0);
                        AnchorPane.setLeftAnchor(imageView, 5.0);
                    }

                    // Set labels position
                    AnchorPane.setTopAnchor(labelsVBox, 5.0);
                    AnchorPane.setLeftAnchor(labelsVBox, 120.0);

                    // Set price and button position
                    AnchorPane.setTopAnchor(ButtonsVBox, 5.0);
                    AnchorPane.setRightAnchor(ButtonsVBox, 5.0);

                    // Add all elements to the AnchorPane
                    anchorPane.getChildren().addAll(imageView, labelsVBox, ButtonsVBox);

                    // Set the layout as the graphic for the ListCell
                    setGraphic(anchorPane);
                }
            }
        });
    }

    @FXML
    void DownloadPdf(Location_Voiture location_Voiture) {
        if (location_Voiture != null) {
            try {
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                float textY = 700; // Position verticale initiale
                float leading = -15; // Espacement vertical entre chaque ligne
                contentStream.newLineAtOffset(100, textY);

                Voiture voiture = voitureService.getOne(location_Voiture.getVoiture_id());

                contentStream.showText("Informations de la réservation :");
                textY += leading;
                contentStream.newLineAtOffset(0, leading);
                contentStream.showText("Marque : " + voiture.getMarque());
                textY += leading;
                contentStream.newLineAtOffset(0, leading);
                contentStream.showText("Model : " + voiture.getModel());
                textY += leading;
                contentStream.newLineAtOffset(0, leading);
                contentStream.showText("Prix (par jour) : " + location_Voiture.getPrix());
                textY += leading;
                contentStream.newLineAtOffset(0, leading);
                contentStream.showText("Energy: " + voiture.getEnergy());
                textY += leading;
                contentStream.newLineAtOffset(0, leading);
                contentStream.showText("Capacité: " + voiture.getCapacite());
                contentStream.endText();
                contentStream.close();

                File file = new File("C:\\Users\\MALEK\\Desktop/Reservation.pdf");
                document.save(file);
                document.close();

                afficherPDFGenereAvecSucces();
            } catch (IOException e) {
                e.printStackTrace();
                afficherErreurPDF();
            }
        } else {
            afficherAucuneReservationSelectionneeAlert();
        }
    }

    private void afficherErreurPDF() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Erreur PDF");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText("Une erreur s'est produite lors de la génération du fichier PDF.");
        errorAlert.showAndWait();
    }


 private void afficherPDFGenereAvecSucces() {
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("PDF généré");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Le fichier PDF de la réservation a été généré avec succès.");
        successAlert.showAndWait();
    }

    @FXML
    void GoToLocationList(ActionEvent event) {
        Stage stage = MainFX.getPrimaryStage();
        Navigator nav = new Navigator(stage);
        nav.goToPage("/userLocationList.fxml");
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherAucuneReservationSelectionneeAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aucune réservation sélectionnée");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une réservation pour générer un fichier PDF.");
        alert.showAndWait();
    }
    
}
