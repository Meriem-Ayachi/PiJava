package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Offres;
import tn.esprit.services.OffresService;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class AjouterOffre {

    @FXML
    private TextField titleTFO;

    @FXML
    private TextField descriptionTFO;

    @FXML
    private TextField publishedTFO;

    @FXML
    private TextField prixTFO;

    @FXML
    private TextField lieuTFO;


    @FXML
    private TextField imageTFO;

    @FXML
    private TextField dateTFO;
    private final OffresService os = new OffresService();

    @FXML
    void AjouterO(ActionEvent event) {

        try {
            os.add(new Offres(titleTFO.getText(), descriptionTFO.getText(),Boolean.parseBoolean(publishedTFO.getText()),Double.parseDouble(prixTFO.getText()),lieuTFO.getText(),imageTFO.getText(), Date.valueOf(dateTFO.getText())));
        } catch (SQLException e) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("error");
           alert.setContentText(e.getMessage());
           alert.showAndWait();

        }

    }

    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root= FXMLLoader.load(getClass().getResource("/AfficherOffre.fxml"));
            titleTFO.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
