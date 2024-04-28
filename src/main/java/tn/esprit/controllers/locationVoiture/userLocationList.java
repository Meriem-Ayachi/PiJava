package tn.esprit.controllers.locationVoiture;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.MainFX;
import tn.esprit.models.Location_Voiture;
import tn.esprit.services.LocationVoitureService;
import tn.esprit.services.VoitureService;
import tn.esprit.util.Navigator;

public class userLocationList {
    
    @FXML
    private ListView<Location_Voiture> listview;
    
    private final LocationVoitureService LocationService = new LocationVoitureService();
    private final VoitureService voitureService = new VoitureService();

    @FXML
    void initialize() {
        List<Location_Voiture> locationsVoiture = LocationService.getAll_unreserved();
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

                    Label voitureLabel = new Label("Voiture: " + voitureService.getOne(item.getVoiture_id()));
                    Label typeLabel = new Label("Type: " + item.getType());
                    Label prixLabel = new Label("Prix: " + item.getPrix());
                    Label data_debutLabel = new Label("Date debut: " + item.getDate_debut());
                    Label date_finLabel = new Label("Date fin: " + item.getDatefin());
                    
                    // Set styles for labels
                    voitureLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");
                    typeLabel.setStyle("-fx-font-size: 12pt;");
                    prixLabel.setStyle("-fx-font-size: 12pt;");
                    data_debutLabel.setStyle("-fx-font-size: 12pt;");
                    date_finLabel.setStyle("-fx-font-size: 12pt;");

                    // Create "Show More" button
                    Button DetailsButton = new Button("Details");

                    // Add action handler for the show more button
                    DetailsButton.setOnAction(event -> {
                        goToVoitureDetails(item);
                    });

                    // Create a VBox to hold all labels vertically
                    VBox labelsVBox = new VBox();
                    labelsVBox.getChildren().addAll(voitureLabel,typeLabel, prixLabel, data_debutLabel, date_finLabel);
                    labelsVBox.setAlignment(Pos.CENTER_LEFT); // Align children to the left
                    labelsVBox.setSpacing(5); // Adjust spacing between elements

                    // Create a VBox to hold price label and "Show More" button
                    VBox ButtonsVBox = new VBox();
                    ButtonsVBox.getChildren().addAll(DetailsButton);
                    ButtonsVBox.setAlignment(Pos.CENTER_RIGHT); // Align to the right
                    ButtonsVBox.setSpacing(5); // Adjust spacing between elements

                    // Create an AnchorPane to hold all elements
                    AnchorPane anchorPane = new AnchorPane();

                    // Set labels position
                    AnchorPane.setTopAnchor(labelsVBox, 5.0);
                    AnchorPane.setLeftAnchor(labelsVBox, 5.0); // Adjust left offset as needed

                    // Set price and button position
                    AnchorPane.setTopAnchor(ButtonsVBox, 5.0);
                    AnchorPane.setRightAnchor(ButtonsVBox, 5.0);

                    // Add all elements to the AnchorPane
                    anchorPane.getChildren().addAll(labelsVBox, ButtonsVBox);

                    // Set the layout as the graphic for the ListCell
                    setGraphic(anchorPane);
                }
            }
        });
    }

    
    @FXML
    void goToVoitureDetails(Location_Voiture location_Voiture) {
        Stage stage = MainFX.getPrimaryStage();
        Navigator nav = new Navigator(stage);
        nav.goTo_LocationVoitureDetails("/userLocation_ShowVoiture.fxml", location_Voiture);
    }
    
    @FXML
    void goToLocationReserver(ActionEvent event) {
        Stage stage = MainFX.getPrimaryStage();
        Navigator nav = new Navigator(stage);
        nav.goToPage("/userLocationList_reserver.fxml");
    }

}
