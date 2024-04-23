package Controllers;


import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tn.esprit.models.Vols;
import tn.esprit.services.VolService;
import javafx.fxml.Initializable;
import java.util.List;
import javafx.scene.control.ListCell;
import javafx.scene.Scene;

import javafx.util.Callback;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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




    private final VolService vs=new VolService() {
        @Override
        public void delete(int id) {

        }
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
                    // Create an HBox to hold destination, price, and show more button
                    HBox hbox = new HBox();
                    Label destinationLabel = new Label("Destination: " + item.getDestination());
                    Label priceLabel = new Label("Price: $" + item.getPrix());
                    Button showMoreButton = new Button("Show More");

                    // Add action handler for the show more button
                    showMoreButton.setOnAction(event -> showMoreAction(item));

                    // Add elements to the HBox
                    hbox.getChildren().addAll(destinationLabel, priceLabel, showMoreButton);
                    setGraphic(hbox);
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
}
