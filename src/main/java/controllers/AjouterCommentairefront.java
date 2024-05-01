package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import tn.esprit.models.Offre_Commentaire;

import tn.esprit.models.Offres;
import tn.esprit.services.Offre_CommentaireService;
import tn.esprit.services.OffresService;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

public class AjouterCommentairefront {

    @FXML
    private TextField AvisTFO;

    @FXML
    private ListView<Offre_Commentaire> listview;
    @FXML
    private Offres offres;

    private final Offre_CommentaireService oc = new Offre_CommentaireService();


    @FXML
    void Ajoutercommentaire(ActionEvent event) {
        try {
            if (AvisTFO.getText().isEmpty()) {
                throw new SQLException("Avis est vide.");
            }

            // Get today's date
            LocalDate today = LocalDate.now();

// Convert LocalDate to java.sql.Date
            Date date = Date.valueOf(today);

            // Proceed with adding the Offre_Commentaire object
            oc.add(new Offre_Commentaire(
                    AvisTFO.getText(),
                    date,
                    offres.getId(),false
            ));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText("Commentaire ajoutée");
            alert.showAndWait();
            refrech();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void initialize (Offres offres) {
        this.offres=offres;
        refrech();
    }

  //afficher commentaire
    @FXML
    void refrech () {

        try {
            List<Offre_Commentaire> commentaire = null;
            commentaire = oc.getAll_byOffreId(offres.getId());
            listview.getItems().addAll(commentaire);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }





}
