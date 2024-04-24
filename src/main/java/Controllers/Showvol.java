package Controllers;

import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.Vols;
import tn.esprit.services.VolService;
import javafx.fxml.Initializable;

import java.util.*;

import javafx.scene.control.ListCell;
import javafx.scene.Scene;

import javafx.util.Callback;
import java.net.URL;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.models.Vols;
import tn.esprit.services.VolService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tn.esprit.models.Vols;


public class Showvol implements Initializable {

    @FXML
    private ListView<Vols> listview;

    private final VolService vs = new VolService() {

    };

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Vols> vols = vs.getAll();
        listview.getItems().addAll(vols);

        listview.setCellFactory(param -> new ListCell<Vols>() {
            @Override
            protected void updateItem(Vols item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Load the images
                    String[] imagePaths = {
                            "file:C:/Users/melek/Downloads/tunisair-vector-logo.png",
                            "file:C:/Users/melek/Downloads/541_qatarairways.jpg",
                            "file:C:/Users/melek/Downloads/west-java-indonesia-oktober-21-600nw-2378341355.jpg"
                    };

                    // Select a random image path
                    Random random = new Random();
                    String randomImagePath = imagePaths[random.nextInt(imagePaths.length)];

                    // Load the image
                    Image image = new Image(randomImagePath);

                    // Create the ImageView with the loaded image
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(100); // Set width as per your requirement
                    imageView.setPreserveRatio(true); // Preserve aspect ratio

                    // Create labels for destination, departure date, and arrival date
                    Label destinationLabel = new Label("Destination: " + item.getDestination());
                    Label departureLabel = new Label("Departure: " + item.getDatedepart().toString());
                    Label arrivalLabel = new Label("Arrival: " + item.getDatearrive().toString());

                    // Create label for price
                    Label priceLabel = new Label("Price: TND" + item.getPrix());
                    // Set styles for labels
                    destinationLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");
                    departureLabel.setStyle("-fx-font-size: 12pt;");
                    arrivalLabel.setStyle("-fx-font-size: 12pt;");
                    priceLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt; -fx-text-fill: #518c65;");

                    // Create "Show More" button
                    Button showMoreButton = new Button("Show More");

                    // Add action handler for the show more button
                    showMoreButton.setOnAction(event -> showMoreAction(item));

                    // Create a VBox to hold all labels vertically
                    VBox labelsVBox = new VBox();
                    labelsVBox.getChildren().addAll(destinationLabel, departureLabel, arrivalLabel);
                    labelsVBox.setAlignment(Pos.CENTER_LEFT); // Align children to the left
                    labelsVBox.setSpacing(5); // Adjust spacing between elements

                    // Create a VBox to hold price label and "Show More" button
                    VBox priceButtonVBox = new VBox();
                    priceButtonVBox.getChildren().addAll(priceLabel, showMoreButton);
                    priceButtonVBox.setAlignment(Pos.CENTER_RIGHT); // Align to the right
                    priceButtonVBox.setSpacing(5); // Adjust spacing between elements

                    // Create an AnchorPane to hold all elements
                    AnchorPane anchorPane = new AnchorPane();

                    // Set image position
                    AnchorPane.setTopAnchor(imageView, 5.0);
                    AnchorPane.setLeftAnchor(imageView, 5.0);

                    // Set labels position
                    AnchorPane.setTopAnchor(labelsVBox, 5.0);
                    AnchorPane.setLeftAnchor(labelsVBox, 120.0); // Adjust left offset as needed

                    // Set price and button position
                    AnchorPane.setTopAnchor(priceButtonVBox, 5.0);
                    AnchorPane.setRightAnchor(priceButtonVBox, 5.0);

                    // Add all elements to the AnchorPane
                    anchorPane.getChildren().addAll(imageView, labelsVBox, priceButtonVBox);

                    // Set the layout as the graphic for the ListCell
                    setGraphic(anchorPane);
                }
            }
        });
    }

    private void showMoreAction(Vols vol) {
        // Load the detailed page for the selected flight
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailedPage.fxml"));
        try {
            Parent root = loader.load();
            // Pass the selected flight to the detailed page controller
            DetailedPageController controller = loader.getController();
            controller.initData(vol);

            // Display the detailed page
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            // Load the Addvol.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Addvol.fxml"));
            Parent root = loader.load();

            // Get the stage of the current scene
            Stage stage = (Stage) listview.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void mettreenpromo(ActionEvent event) {
        try {
            // Load the AddPromo.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddPromo.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage promoStage = new Stage();

            // Set the scene with the content from AddPromo.fxml
            Scene scene = new Scene(root);
            promoStage.setScene(scene);

            // Show the new stage
            promoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}