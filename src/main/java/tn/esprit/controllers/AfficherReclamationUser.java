package tn.esprit.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.models.Reclamation;
import tn.esprit.models.User;
import tn.esprit.models.session;
import tn.esprit.services.ReclamationService;
import tn.esprit.services.UserService;
import tn.esprit.util.Navigator;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class AfficherReclamationUser {

    @FXML
    private TableColumn<Reclamation, Timestamp> DateSoumissionCol;

    @FXML
    private TableColumn<Reclamation, String> DescriptionCol;

    @FXML
    private TableColumn<Reclamation, String> NomPrenomCol;

    @FXML
    private TableColumn<Reclamation, String> SujetCol;

    @FXML
    private TableView<Reclamation> tableview;

    private final ReclamationService rs = new ReclamationService();
    @FXML
    void initialize(){

        try{
            int userId = session.id_utilisateur;
            List<Reclamation> reclamations = rs.getAllByUserId(userId);
            ObservableList<Reclamation> observableList = FXCollections.observableList(reclamations);
            tableview.setItems(observableList);
            DateSoumissionCol.setCellValueFactory(new PropertyValueFactory<>("datesoummission"));
            DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            NomPrenomCol.setCellValueFactory(cellData -> {
                UserService us = new UserService();
                Reclamation reclamation = cellData.getValue();
                User user = us.getOne(reclamation.getUser_id()); // puisque la méthode getUserId() récupère l'ID de l'utilisateur
                String nom = user.getNom();
                String prenom = user.getPrenom();
                return new SimpleStringProperty(nom + " " + prenom);
            });            SujetCol.setCellValueFactory(new PropertyValueFactory<>("sujet"));

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

    // Méthode appelée lorsque l'utilisateur clique sur une réclamation dans la TableView
    @FXML
    private void afficherDetailsReclamation() throws IOException {
        Reclamation selectedReclamation = tableview.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            // Charger le fichier FXML de l'interface des détails de la réclamation
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsReclamation.fxml"));
            AnchorPane detailsReclamationPane = loader.load();

            // Obtenir le contrôleur associé à l'interface
            DetailsReclamation controller = loader.getController();

            // Appeler la méthode pour initialiser les détails de la réclamation
            controller.initializeDetails(selectedReclamation);

            // Afficher l'interface dans une nouvelle fenêtre
            Stage stage = new Stage();
            stage.setScene(new Scene(detailsReclamationPane));
            stage.show();
        }
    }
    @FXML
    void Refresh(ActionEvent event) {
        tableview.setItems(FXCollections.observableArrayList(rs.getAllByUserId(session.id_utilisateur)));
    }

    @FXML
    void GoToAjouter(ActionEvent event) {
        Navigator nav = new Navigator();
        nav.goToPage_WithEvent("/AjouterReclamationUser.fxml",event);

    }

}




