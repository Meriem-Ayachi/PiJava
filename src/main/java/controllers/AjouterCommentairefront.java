package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import tn.esprit.models.Offre_Commentaire;

import tn.esprit.services.Offre_CommentaireService;
import tn.esprit.services.OffresService;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class AjouterCommentairefront {

    @FXML
    private TextField AvisTFO;

    @FXML
    private TextField dateTFO;

    @FXML
    private ListView<Offre_Commentaire> listview;

    private final Offre_CommentaireService oc = new Offre_CommentaireService();


    @FXML
    void Ajoutercommentaire(ActionEvent event) {
        try {
            // Check if the dateTFO text is in the correct format
            String dateString = dateTFO.getText();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            Date date = new Date(dateFormat.parse(dateString).getTime());
            // Proceed with adding the Offre_Commentaire object
            oc.add(new Offre_Commentaire(
                    AvisTFO.getText(),
                    date
            ));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Offre ajoutée");
            alert.showAndWait();
        } catch (ParseException | SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
