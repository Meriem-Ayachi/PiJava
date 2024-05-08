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
import tn.esprit.models.Offre_Commentaire;
import tn.esprit.models.Offres;
import tn.esprit.services.OffresService;
import tn.esprit.services.Offre_CommentaireService;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class AfficherCommentaires {
    @FXML
    private TableColumn<Offre_Commentaire, String> AvisColO;

    @FXML
    private TableColumn<Offre_Commentaire, Date> created_atColO;

    @FXML
    private TableColumn<Offre_Commentaire, String> offres_idCol;

    @FXML
    private TableView<Offre_Commentaire> tableview;

    @FXML
    private Button btnretour;

    private final Offre_CommentaireService os = new Offre_CommentaireService();
    private final OffresService em = new OffresService();

    @FXML
    void initialize(Offres offres) {
        try {
            List<Offre_Commentaire> cm = os.getAll_byOffreId(offres.getId());
            ObservableList<Offre_Commentaire> observableList = FXCollections.observableList(cm);
            tableview.setItems(observableList);
            AvisColO.setCellValueFactory(new PropertyValueFactory<>("avis"));
            created_atColO.setCellValueFactory(new PropertyValueFactory<>("created_at"));
            offres_idCol.setCellValueFactory((cellData -> {
                Offre_Commentaire offre_commentaires = cellData.getValue();
                int id = offre_commentaires.getOffres_id();
                int offre_id = em.getOne(id).getId();
                return new ReadOnlyStringWrapper(String.valueOf(offre_id));
            }));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void Supprimercommentaire(ActionEvent event) {
        Offre_Commentaire offre_commentaire = tableview.getSelectionModel().getSelectedItem();

        if (offre_commentaire != null) {
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation de suppression");
            confirmationDialog.setHeaderText(null);
            confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer ce module ?");
            confirmationDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    os.delete(offre_commentaire.getId());
                    tableview.getItems().remove(offre_commentaire);
                    Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                    confirmation.setTitle("Suppression réussie");
                    confirmation.setHeaderText(null);
                    confirmation.setContentText("Commentaire a été supprimé avec succès.");
                    confirmation.showAndWait();
                }
            });
        } else {
            Alert noSelectionAlert = new Alert(Alert.AlertType.WARNING);
            noSelectionAlert.setTitle("Aucune sélection");
            noSelectionAlert.setHeaderText(null);
            noSelectionAlert.setContentText("Veuillez sélectionner un commentaire à supprimer.");
            noSelectionAlert.showAndWait();
        }


    }


    @FXML
    void naviguer(ActionEvent event) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/AfficherOffre.fxml"));
        Stage stage = (Stage) btnretour.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void setOffre(Offres selectedOffre) {

    }
}
