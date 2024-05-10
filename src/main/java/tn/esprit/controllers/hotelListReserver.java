package tn.esprit.controllers;

import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tn.esprit.models.hotelReserverModel;
import tn.esprit.services.Hotelservices;

public class hotelListReserver {


    @FXML
    private ListView<hotelReserverModel> hotelListView;

    private Hotelservices hs = new Hotelservices();
    public void initialize(){


        //prepare the listView
        List<hotelReserverModel> reservedHotel = hs.getAllReserved();
        hotelListView.getItems().addAll(reservedHotel);

        hotelListView.setCellFactory(param -> new ListCell<hotelReserverModel>() {
            @Override
            protected void updateItem(hotelReserverModel item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Create labels for each piece of information
                    Label sejourLabel = new Label("Sejour: " + item.getSejour());
                    Label dateDebutLabel = new Label("Date de début: " + item.getDate_debut());
                    Label dateFinLabel = new Label("Date de fin: " + item.getDate_retour());
                    // Add more labels for other information as needed

                    // Set styles for labels
                    sejourLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");
                    dateDebutLabel.setStyle("-fx-font-size: 12pt;");
                    dateFinLabel.setStyle("-fx-font-size: 12pt;");
                    // Set alignment for labels
                    sejourLabel.setAlignment(Pos.CENTER_LEFT);
                    dateDebutLabel.setAlignment(Pos.CENTER_LEFT);
                    dateFinLabel.setAlignment(Pos.CENTER_LEFT);
                    // Create a VBox to hold all labels vertically
                    VBox labelsVBox = new VBox();
                    labelsVBox.getChildren().addAll(sejourLabel, dateDebutLabel, dateFinLabel);
                    labelsVBox.setAlignment(Pos.CENTER_LEFT); // Align children to the left
                    labelsVBox.setSpacing(5); // Adjust spacing between elements

                    // Create an AnchorPane to hold all elements
                    AnchorPane anchorPane = new AnchorPane();

                    // Add all elements to the AnchorPane
                    anchorPane.getChildren().addAll(labelsVBox);

                    // Set the layout as the graphic for the ListCell
                    setGraphic(anchorPane);
                }
            }
        });

    }

}