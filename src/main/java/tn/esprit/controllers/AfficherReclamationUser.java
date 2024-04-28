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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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

public class AfficherReclamationUser implements Initializable {

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
                    // Create labels for destination, departure date, and arrival date
                    Label NomPrenomLabel = new Label("Nom et Prénom: " + user.getNom() + " " + user.getPrenom());
                    Label sujetLabel = new Label("Sujet: " + item.getSujet());
                    Label descriptionLabel = new Label("Description: " + item.getDescription());
                    Label dateSoumissionLabel = new Label("Date de soummission: " + item.getDatesoummission().toString());

                    // Set styles for labels
                    NomPrenomLabel.setStyle("-fx-font-size: 12pt;");
                    sujetLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");
                    descriptionLabel.setStyle("-fx-font-size: 12pt;");
                    dateSoumissionLabel.setStyle("-fx-font-size: 12pt;");

                    // Create "Show More" button
                    Button ModifierButton = new Button("Modifier");
                    Button SupprimerButton = new Button("Supprimer");
                    Button DetailsButton = new Button("Details");

                    ModifierButton.getStyleClass().add("modifierButton");
                    SupprimerButton.getStyleClass().add("deleteButton");

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

                    // Create a VBox to hold all labels vertically
                    VBox labelsVBox = new VBox();
                    labelsVBox.getChildren().addAll(sujetLabel,NomPrenomLabel, descriptionLabel, dateSoumissionLabel);
                    labelsVBox.setAlignment(Pos.CENTER_LEFT); // Align children to the left
                    labelsVBox.setSpacing(5); // Adjust spacing between elements

                    // Create a VBox to hold price label and "Show More" button
                    VBox ButtonsVBox = new VBox();
                    ButtonsVBox.getChildren().addAll(ModifierButton,SupprimerButton,DetailsButton);
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
            controller.initialize(reclamation);

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

    }

    @FXML
    void GoToAjouter(ActionEvent event) {
        Navigator nav = new Navigator();
        nav.goToPage_WithEvent("/AjouterReclamationUser.fxml",event);

    }


    @FXML
    void refresh(ActionEvent event) {

        listview.getItems().clear(); // Efface tous les éléments actuels de la ListView
        listview.getItems().addAll(rs.getAllByUserId(session.id_utilisateur)); // Ajoute de nouveaux éléments


    }

}




