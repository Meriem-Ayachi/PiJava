package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tn.esprit.models.Offres;
import tn.esprit.services.OffresService;

import java.io.IOException;

public class DetailsPage {


    @FXML
    private GridPane gridpane;
    @FXML
    private Button commenter;
    @FXML
    private Offres offres;


    @FXML
    private Button reserver;


    private final OffresService os = new OffresService();

    public void Commenter(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCommentairefront.fxml"));
            AnchorPane detailsOffrePane = loader.load();
            AjouterCommentairefront controller = loader.getController();

            controller.initialize(offres);

            Stage stage = new Stage();
            stage.setScene(new Scene(detailsOffrePane));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void Reserver(ActionEvent event) {

    }


    @FXML
    private ImageView imageView; // L'image sera affichée ici


    @FXML
    public void initialize(Offres selectedOffre) {


        if (selectedOffre != null) {
            Offres loadedOffre = os.getOne(selectedOffre.getId());
            if (loadedOffre != null) {

                gridpane.getChildren().clear();

                // Add labels to the grid pane
                gridpane.add(new Label(loadedOffre.getLieu()), 1, 0);
                gridpane.add(new Label(loadedOffre.getTitle()), 0, 0);
                gridpane.add(new Label(loadedOffre.getDescription()), 0, 1);
                gridpane.add(new Label(String.valueOf(loadedOffre.getCreated_at())), 1, 2);
                gridpane.add(new Label(String.valueOf(loadedOffre.getPrix())), 0, 2);
                // Add more labels to display other offer details
                // Load the image from the URL
                String imageUrl = loadedOffre.getImage(); // Assume getImage() returns the image URL
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Image image = new Image(imageUrl);
                    if (image.isError()) {
                        System.err.println("Error loading image from URL: " + imageUrl);
                        // Handle the error, e.g., display a default image
                    } else {
                        // Display the image
                        imageView.setImage(image);
                    }
                } else {
                    System.err.println("Empty image URL for offer with ID: " + loadedOffre.getId());
                    // Handle the case where the image URL is empty
                }

                // You can also display other information about the offer here without adding to the grid pane
            }
        }
    }

}








