package tn.esprit.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.interfaces.RefreshCallBack;
import tn.esprit.models.Reclamation;
import tn.esprit.models.User;
import tn.esprit.models.session;
import tn.esprit.services.ReclamationService;
import tn.esprit.services.UserService;
import tn.esprit.util.Navigator;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;



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

                    // Create "Show More" button
                    Button ModifierButton = new Button("Modifier");
                    Button SupprimerButton = new Button("Supprimer");
                    Button DetailsButton = new Button("Details");

                    ModifierButton.getStyleClass().add("modifierButton");
                    SupprimerButton.getStyleClass().add("deleteButton");

                    ModifierButton.setStyle("-fx-background-color: #387296; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold");
                    SupprimerButton.setStyle("-fx-background-color: #387296; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold");
                    DetailsButton.setStyle("-fx-background-color: #387296; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold");

                    // Ajouter des marges aux boutons
                    ModifierButton.setMinWidth(80);
                    SupprimerButton.setMinWidth(80);
                    DetailsButton.setMinWidth(80);

                    // Add action handler for the show more button
                    DetailsButton.setOnAction(event -> {
                        try {
                            afficherDetailsReclamation(item);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    ModifierButton.setOnAction(event -> {
                        try {
                            GoToModifierReclamation(item);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

                    SupprimerButton.setOnAction(event -> {
                        SupprimerReclamation(item);
                    });

                    // Create a GridPane to hold all labels in a tabular layout
                    GridPane gridPane = new GridPane();
                    gridPane.setHgap(10); // Set horizontal gap between cells
                    gridPane.setVgap(5); // Set vertical gap between cells
                    // Ajouter les étiquettes de titre au GridPane
                    gridPane.addRow(0, sujetTitreLabel, sujetLabel);
                    gridPane.addRow(1, descriptionTitreLabel, descriptionLabel);
                    gridPane.addRow(2, dateSoumissionTitreLabel, dateSoumissionLabel);

                    // Create an HBox to hold buttons horizontally
                    HBox buttonsHBox = new HBox(ModifierButton, SupprimerButton, DetailsButton);
                    buttonsHBox.setAlignment(Pos.CENTER_RIGHT); // Align buttons to the right
                    buttonsHBox.setSpacing(10); // Adjust spacing between buttons

                    // Create a VBox to hold the GridPane and HBox vertically
                    VBox rootVBox = new VBox(gridPane, buttonsHBox);
                    rootVBox.setAlignment(Pos.CENTER_LEFT); // Align elements to the left
                    rootVBox.setSpacing(10); // Adjust spacing between elements

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsReclamation.fxml"));
            AnchorPane detailsReclamationPane = loader.load();
            DetailsReclamation controller = loader.getController();

            // Appeler la méthode pour initialiser les détails de la réclamation
            controller.initializeDetails(reclamation);

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
    private void GoToModifierReclamation(Reclamation reclamation) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReclamationUser.fxml"));
            AnchorPane detailsReclamationPane = loader.load();
            ModifierReclamationUser controller = loader.getController();

            // Appeler la méthode pour initialiser les détails de la réclamation
            controller.initialize(reclamation , this);

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
    private void SupprimerReclamation (Reclamation rec){

        // confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Voulez-vous supprimer cette réclamation");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        // check if user has declined
        if (result != ButtonType.OK) {
            return;
        }

        rs.delete(rec.getId());

        // Afficher un message de confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
        confirmationAlert.setTitle("Suppression réussie");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("La réclamation a été supprimée avec succès.");
        confirmationAlert.showAndWait();

        refresh();
    }

    @FXML
    void GoToAjouter(ActionEvent event) {
        Navigator nav = new Navigator();
        nav.goToPage_WithEvent("/AjouterReclamationUser.fxml",event);

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
}




