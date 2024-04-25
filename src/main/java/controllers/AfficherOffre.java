package controllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.models.Offres;
import tn.esprit.services.OffresService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class AfficherOffre {

    @FXML
    private Button commentaires;

    @FXML
    private Button modifierO;

    @FXML
    private TextField titleTFO;
    @FXML
    private TextField descriptionTFO;
    @FXML
    private TextField dateTFO;

    @FXML
    private TableColumn<Offres, Date> dateColO;

    @FXML
    private TableColumn<Offres, String> descriptionColO;

    @FXML
    private TableColumn<Offres, String> imageColO;

    @FXML
    private TableColumn<Offres, String> lieuColO;

    @FXML
    private TableColumn<Offres, Double> prixColO;

    @FXML
    private TableColumn<Offres, Boolean> publishedColO;

    @FXML
    private TableView<Offres> tableview;


    @FXML
    private TableColumn<Offres, String> titleColO;

    private final OffresService os = new OffresService();

    @FXML
    void initialize() {

        try {
            List<Offres> offres = os.getAll();
            ObservableList<Offres> observableList = FXCollections.observableList(offres);
            tableview.setItems(observableList);
            titleColO.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColO.setCellValueFactory(new PropertyValueFactory<>("description"));
            publishedColO.setCellValueFactory(new PropertyValueFactory<>("published"));
            prixColO.setCellValueFactory(new PropertyValueFactory<>("prix"));
            lieuColO.setCellValueFactory(new PropertyValueFactory<>("lieu"));
            imageColO.setCellValueFactory(new PropertyValueFactory<>("image"));
            dateColO.setCellValueFactory(new PropertyValueFactory<>("created_at"));

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }
    @FXML
    void naviguer(ActionEvent event) throws Exception  {

            Offres offres = tableview.getSelectionModel().getSelectedItem();
            if (offres != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModiffierOffre.fxml"));
                Parent root = loader.load();

                ModiffierOffre controllers = loader.getController();
                controllers.setOffres(offres); // Pass the Offres object to the edit controller

                Stage stage = (Stage) modifierO.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                Alert noSelectionAlert = new Alert(Alert.AlertType.WARNING);
                noSelectionAlert.setTitle("Aucune sélection");
                noSelectionAlert.setHeaderText(null);
                noSelectionAlert.setContentText("Veuillez sélectionner un offre à modifier.");
                noSelectionAlert.showAndWait();

        }


    }




    //
    @FXML
    void SupprimerO(ActionEvent event) {
        Offres offres = tableview.getSelectionModel().getSelectedItem();

        if (offres != null) {
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation de suppression");
            confirmationDialog.setHeaderText(null);
            confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer ce module ?");
            confirmationDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        os.delete(offres.getId());
                        tableview.getItems().remove(offres);
                        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                        confirmation.setTitle("Suppression réussie");
                        confirmation.setHeaderText(null);
                        confirmation.setContentText("L'offre a été supprimé avec succès.");
                        confirmation.showAndWait();
                    } catch (SQLException e) {
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Erreur lors de la suppression");
                        error.setHeaderText(null);
                        error.setContentText("Une erreur s'est produite lors de la suppression du offre : " + e.getMessage());
                        error.showAndWait();
                    }
                }
            });
        } else {
            Alert noSelectionAlert = new Alert(Alert.AlertType.WARNING);
            noSelectionAlert.setTitle("Aucune sélection");
            noSelectionAlert.setHeaderText(null);
            noSelectionAlert.setContentText("Veuillez sélectionner un offre à supprimer.");
            noSelectionAlert.showAndWait();
        }


    }
    @FXML
    void commentaires(ActionEvent event) throws Exception {

            Offres selectedOffre = tableview.getSelectionModel().getSelectedItem();
            if (selectedOffre != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCommentaires.fxml"));
                Parent root = loader.load();
                AfficherCommentaires controller = loader.getController();
                controller.intialize(selectedOffre);
                Stage stage = (Stage) commentaires.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            }

    }


}

