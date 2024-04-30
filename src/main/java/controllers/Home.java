package controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.models.Offres;
import tn.esprit.services.OffresService;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import controllers.DetailsPage;
public class Home implements Initializable {

    private final OffresService os = new OffresService();
    @FXML
    private ListView<Offres> listview;
    @FXML
    private Button details;
    @FXML
    private ComboBox<String> combobox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

      try {
          List<Offres> offres = os.getAll();
          listview.getItems().addAll(offres);
          // Ajouter les critères de tri à la ComboBox
          combobox.getItems().addAll( "prix","created_at");

          // Sélectionner par défaut le premier élément dans la ComboBox
          combobox.getSelectionModel().selectFirst();


          }catch (SQLException e) {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("error");
          alert.setContentText(e.getMessage());
          alert.showAndWait();
      }
    }

    @FXML
    void afficherDetails(ActionEvent event) throws Exception {
        Offres selectedOffre = listview.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsPage.fxml"));
            Parent root = loader.load();
            DetailsPage controller = loader.getController();
            controller.initialize(selectedOffre);  // Passer l'offre sélectionnée au contrôleur DetailsPage
            Stage stage = new Stage();  // Créer une nouvelle fenêtre pour afficher les détails
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
    @FXML
    void tri(ActionEvent event) {
        try {
            List<Offres> offres = os.triParCritere(combobox.getValue());
            ObservableList<Offres> observableOffres = FXCollections.observableList(offres);
            listview.setItems(observableOffres);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}