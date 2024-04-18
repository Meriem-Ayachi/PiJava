package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.models.Offres;
import tn.esprit.services.OffresService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class AfficherOffre {

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
    void initialize(){

        try {
            List<Offres> offres= os.getAll();
            ObservableList<Offres>observableList=FXCollections.observableList(offres);
            tableview.setItems(observableList);
            titleColO.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColO.setCellValueFactory(new PropertyValueFactory<>("description"));
            publishedColO.setCellValueFactory(new PropertyValueFactory<>("published"));
            prixColO.setCellValueFactory(new PropertyValueFactory<>("prix") );
            lieuColO.setCellValueFactory(new PropertyValueFactory<>("lieu"));
            imageColO.setCellValueFactory(new PropertyValueFactory<>("image"));
            dateColO.setCellValueFactory(new PropertyValueFactory<>("date"));

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }

    @FXML
    void ModifierO() {

    }
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
}
