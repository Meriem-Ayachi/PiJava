package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {

      try {
          List<Offres> offres = os.getAll();
          listview.getItems().addAll(offres);


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
}