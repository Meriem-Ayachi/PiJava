package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Offres;

import java.sql.Date;
import java.sql.SQLException;

public class ModiffierOffre {
    @FXML
    private Button btnretour;
    @FXML
    private TextField dateTFO;

    @FXML
    private TextField descriptionTFO;

    @FXML
    private TextField imageTFO;

    @FXML
    private TextField lieuTFO;

    @FXML
    private TextField prixTFO;

    @FXML
    private TextField publishedTFO;

    @FXML
    private TextField titleTFO;


    @FXML
    void ModifierO() throws Exception {


    }

    @FXML
    void naviguer(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/AfficherOffre.fxml"));
        Stage stage = (Stage) btnretour.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }

}
