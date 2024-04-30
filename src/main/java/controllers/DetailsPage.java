package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import tn.esprit.models.Offres;
import tn.esprit.services.OffresService;

public class DetailsPage {


    @FXML
    private ListView<Offres> listview;
    @FXML
    private Button commenter;

    @FXML
    private Button reserver;


    private final OffresService os = new OffresService();
    @FXML
    void Commenter(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/AjouterCommentairefront.fxml"));
        Stage stage = (Stage) commenter.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    void Reserver(ActionEvent event) {

    }


    @FXML
    public void initialize(Offres selectedOffre) {
        if (selectedOffre != null) {
            Offres loadedOffre = os.getOne(selectedOffre.getId());
            if (loadedOffre != null) {
                listview.getItems().add(loadedOffre);
            }
        }
    }
}






