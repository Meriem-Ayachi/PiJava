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
                    Label typeLabel = new Label("Sejour: " + item.getSejour());
                    
                    // Set styles for labels
                    typeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");

                    // Create a VBox to hold all labels vertically
                    VBox labelsVBox = new VBox();
                    labelsVBox.getChildren().addAll(typeLabel);
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
