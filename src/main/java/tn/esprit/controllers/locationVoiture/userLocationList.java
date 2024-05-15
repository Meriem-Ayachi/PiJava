package tn.esprit.controllers.locationVoiture;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.MainFX;
import tn.esprit.models.Location_Voiture;
import tn.esprit.models.Voiture;
import tn.esprit.services.LocationVoitureService;
import tn.esprit.services.VoitureService;
import tn.esprit.util.Navigator;

public class userLocationList {
    
    @FXML
    private ListView<Location_Voiture> listview;

    @FXML
    private ComboBox<String> typeComboBox;
    
    private final LocationVoitureService LocationService = new LocationVoitureService();
    private final VoitureService voitureService = new VoitureService();

    @FXML
    void initialize() {
        //initialize the values of the type comboBox
        ObservableList<String> typeList = FXCollections.observableArrayList(
            "Sports"
            , "Famille"
            , "Minivan"
            , "Luxe"
        );
        typeComboBox.setItems(typeList);

        //prepare the listView
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
                    Voiture v = voitureService.getOne(item.getVoiture_id());
                    
                    // Create labels for destination, departure date, and arrival date
                    Label voitureLabel = new Label(v.getModel());
                    Label marqueLabel = new Label("Marque: " + v.getMarque());
                    Label typeLabel = new Label("Type: " + item.getType());
                    Label prixLabel = new Label("Prix (par jour): " + item.getPrix());
                    
                    // Set styles for labels
                    voitureLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");
                    marqueLabel.setStyle("-fx-font-size: 12pt;");
                    typeLabel.setStyle("-fx-font-size: 12pt;");
                    prixLabel.setStyle("-fx-font-size: 12pt;");

                    // Create "Show More" button
                    Button DetailsButton = new Button("Details");
                    DetailsButton.setStyle("-fx-background-color: transparent;-fx-text-fill: #233b9a;-fx-font-size: 14px;-fx-padding: 0 5px;");

                    // Add action handler for the show more button
                    DetailsButton.setOnAction(event -> {
                        goToVoitureDetails(item);
                    });

                    // Create a VBox to hold all labels vertically
                    VBox labelsVBox = new VBox();
                    labelsVBox.getChildren().addAll(voitureLabel, marqueLabel,typeLabel, prixLabel);
                    labelsVBox.setAlignment(Pos.CENTER_LEFT); // Align children to the left
                    labelsVBox.setSpacing(5); // Adjust spacing between elements

                    // Create a VBox to hold price label and "Show More" button
                    VBox ButtonsVBox = new VBox();
                    ButtonsVBox.getChildren().addAll(DetailsButton);
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


    //recherche Code
    private String typeSearch, marqueSearch, modelSearch;
    private int minPrixSearch, maxPrixSearch;

    @FXML
    void recherche() {
        List<Location_Voiture> locationVoitureList = LocationService.rechercher(modelSearch, marqueSearch, typeSearch, maxPrixSearch, minPrixSearch);
        listview.getItems().clear(); 
        listview.getItems().addAll(locationVoitureList);
    }

    @FXML
    void typeChanged(ActionEvent event) {
        typeSearch = typeComboBox.getValue();
        recherche();
    }

    @FXML
    void modelChanged(KeyEvent event) {
        modelSearch = ((TextField) event.getSource()).getText();
        recherche();
    }

    @FXML
    void marqueChanged(KeyEvent event) {
        marqueSearch = ((TextField) event.getSource()).getText();
        recherche();
    }

    @FXML
    void maxPrixChanged(KeyEvent event) {
        try {
            String searchText = ((TextField) event.getSource()).getText();
            int prix = Integer.parseInt(searchText);
            maxPrixSearch = prix;
        } catch (NumberFormatException e) {
            maxPrixSearch = 0;
            return;
        }
        recherche();
    }

    @FXML
    void minPrixChanged(KeyEvent event) {
        try {
            String searchText = ((TextField) event.getSource()).getText();
            int prix = Integer.parseInt(searchText);
            minPrixSearch = prix;
        } catch (NumberFormatException e) {
            minPrixSearch = 0;
            return;
        }
        recherche();
    }
}
