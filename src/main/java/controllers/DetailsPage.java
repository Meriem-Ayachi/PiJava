package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.models.Offres;
import tn.esprit.services.OffresService;

import java.io.IOException;

public class DetailsPage {


    @FXML
    private ListView<Offres> listview;
    @FXML
    private Button commenter;
    @FXML
    private Offres offres;


    @FXML
    private Button reserver;


    private final OffresService os = new OffresService();

    public void Commenter(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCommentairefront.fxml"));
            AnchorPane detailsOffrePane = loader.load();
            AjouterCommentairefront controller = loader.getController();

            controller.initialize(offres);

            Stage stage = new Stage();
            stage.setScene(new Scene(detailsOffrePane));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void Reserver(ActionEvent event) {

    }


    @FXML
    public void initialize(Offres selectedOffre) {
        if (selectedOffre != null) {
            offres=selectedOffre;
            Offres loadedOffre = os.getOne(selectedOffre.getId());
            if (loadedOffre != null) {
                listview.getItems().add(loadedOffre);
            }
        }
    }
}






