package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tn.esprit.interfaces.RefreshCallBack;
import tn.esprit.models.Reclamation;
import tn.esprit.models.User;
import tn.esprit.models.session;
import tn.esprit.services.ReclamationService;
import tn.esprit.services.UserService;
import tn.esprit.util.Navigator;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.shape.Line;




public class AfficherReclamationUser implements Initializable, RefreshCallBack {

    @FXML
    private ListView<Reclamation> listview;

    private final ReclamationService rs = new ReclamationService() {
    };
    private final UserService us = new UserService() {
    };

    public void initialize(URL location, ResourceBundle resources) {
        int userId = session.id_utilisateur;
        List<Reclamation> reclamations = rs.getAllByUserId(userId);
        listview.getItems().addAll(reclamations);

        listview.setCellFactory(param -> new ListCell<Reclamation>() {
            @Override
            protected void updateItem(Reclamation item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setOnMouseClicked(event -> {
                        // Votre logique pour afficher les détails de la réclamation
                        try {
                            afficherDetailsReclamation(item);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });


                    User user = us.getOne(item.getUser_id());

                    Label sujetTitreLabel = new Label("Sujet:");
                    Label descriptionTitreLabel = new Label("Description:");
                    Label dateSoumissionTitreLabel = new Label("Date de soumission:");

                    sujetTitreLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair
                    descriptionTitreLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair
                    dateSoumissionTitreLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #387296; -fx-font-size: 14;"); // Bleu clair


                    Label sujetLabel = new Label(item.getSujet());
                    sujetLabel.setStyle("-fx-text-fill: black; -fx-font-size: 15;"); // Noir

                    Label descriptionLabel = new Label(item.getDescription());
                    descriptionLabel.setStyle("-fx-text-fill: black; -fx-font-size: 15;"); // Noir

                    Label dateSoumissionLabel = new Label(item.getDatesoummission().toString());
                    dateSoumissionLabel.setStyle("-fx-text-fill: black; -fx-font-size: 15;"); // Noir

                    // Add action handler for the show more button


                    // Create a GridPane to hold all labels in a tabular layout
                    GridPane gridPane = new GridPane();
                    gridPane.setHgap(10); // Set horizontal gap between cells
                    gridPane.setVgap(5); // Set vertical gap between cells
                    // Ajouter les étiquettes de titre au GridPane
                    gridPane.addRow(0, sujetTitreLabel, sujetLabel);
                    gridPane.addRow(1, descriptionTitreLabel, descriptionLabel);
                    gridPane.addRow(2, dateSoumissionTitreLabel, dateSoumissionLabel);

                    // Créer une ligne horizontale de séparation
                    Separator separator = new Separator();
                    separator.setOrientation(Orientation.HORIZONTAL);

                    // Créer un VBox pour contenir les éléments avec un padding et un espacement spécifiques
                    VBox rootVBox = new VBox(gridPane, separator);
                    rootVBox.setAlignment(Pos.CENTER_LEFT); // Alignement à gauche
                    rootVBox.setSpacing(10); // Espace vertical entre les éléments
                    rootVBox.setPadding(new Insets(10)); // Padding autour du VBox

                    // Set the layout as the graphic for the ListCell
                    setGraphic(rootVBox);
                }
            }
        });
    }

    // Méthode appelée lorsque l'utilisateur clique sur une réclamation dans la TableView
    @FXML
    private void afficherDetailsReclamation(Reclamation reclamation) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsReclamationUser.fxml"));
            AnchorPane detailsReclamationPane = loader.load();
            DetailsReclamationUser controller = loader.getController();

            // Appeler la méthode pour initialiser les détails de la réclamation
            controller.initializeDetails(reclamation);
            controller.UpdateCallBack(this);


            // Afficher l'interface dans une nouvelle fenêtre
            Stage stage = new Stage();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    event.consume(); // Consume the event to prevent the window from closing automatically

                    // Execute your function here
                    handleCloseRequest(stage);
                }
            });

            stage.setScene(new Scene(detailsReclamationPane));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void GoToAjouter(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReclamationUser.fxml"));
            AnchorPane detailsReclamationPane = loader.load();
            AjouterReclamationUser controller = loader.getController();

            // Appeler la méthode pour initialiser les détails de la réclamation
            controller.UpdateCallBack(this);

            // Afficher l'interface dans une nouvelle fenêtre
            Stage stage = new Stage();

            stage.setScene(new Scene(detailsReclamationPane));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void refresh() {

        listview.getItems().clear(); // Efface tous les éléments actuels de la ListView
        listview.getItems().addAll(rs.getAllByUserId(session.id_utilisateur)); // Ajoute de nouveaux éléments
    }

    @Override
    public void onRefreshComplete() {
        refresh();

    }


    private void handleCloseRequest(Stage stage) {
        refresh();
        stage.close();

    }
}
