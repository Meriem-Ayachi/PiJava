package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.models.Offres;
import tn.esprit.services.OffresService;

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
}
