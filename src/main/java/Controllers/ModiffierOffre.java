package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Offres;
import tn.esprit.services.OffresService;
import tn.esprit.util.MaConnexion;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModiffierOffre implements Initializable {
    @FXML
    private Button btnretour;
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

    private OffresService os = new OffresService();
    private Connection cnx = MaConnexion.getInstance().getCnx();
    private Offres offres; // Field to store the passed Offres object

    // Method to receive and display the Offres data
    @FXML
    public void setOffres(Offres offres) {
        this.offres = offres;
        if (offres != null) {
            titleTFO.setText(offres.getTitle());
            descriptionTFO.setText(offres.getDescription());
            publishedTFO.setText(String.valueOf(offres.isPublished()));
            prixTFO.setText(String.valueOf(offres.getPrix()));
            lieuTFO.setText(offres.getLieu());
            imageTFO.setText(offres.getImage());

        }
    }

    @FXML
    void ModifierO() throws Exception {
        if (offres != null) {
            offres.setTitle(titleTFO.getText());
            offres.setDescription(descriptionTFO.getText());
            offres.setPublished(Boolean.parseBoolean(publishedTFO.getText()));
            offres.setPrix(Double.parseDouble(prixTFO.getText()));
            offres.setLieu(lieuTFO.getText());
            offres.setImage(imageTFO.getText());

            try {

                if (descriptionTFO.getText().isEmpty()) {
                    throw new SQLException("La description est vide.");
                }
                if (descriptionTFO.getText().length() > 20) {
                    throw new SQLException("La description ne doit pas dépasser 20 caractères.");
                }
                if (titleTFO.getText().isEmpty()) {
                    throw new SQLException("le titre est vide.");
                }
                if (imageTFO.getText().isEmpty()) {
                    throw new SQLException("le image est vide.");
                }
                if (prixTFO.getText().isEmpty()) {
                    throw new SQLException("le prix est vide.");
                }
                if (Double.parseDouble(prixTFO.getText()) == 0.0) {
                    throw new SQLException("Le prix ne peut pas être égal à zéro.");
                }
                if (publishedTFO.getText().isEmpty()) {
                    throw new SQLException("le published est vide.");
                }
                if (lieuTFO.getText().isEmpty()) {
                    throw new SQLException("le lieu est vide.");
                }
                os.update(offres);
                System.out.println("Mise à jour réussie avec succès");

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Succès");
                successAlert.setHeaderText(null);
                successAlert.setContentText("L'offre a été modifiée avec succès.");
                successAlert.showAndWait();

                // Reload the page if needed
                Parent root = FXMLLoader.load(getClass().getResource("/AfficherOffre.fxml"));
                Stage stage = (Stage) btnretour.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(e.getMessage());
                alert.showAndWait();}
        } else {
            System.out.println("Aucune offre à modifier.");
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Aucune offre à modifier.");
            errorAlert.showAndWait();
        }
    }

    @FXML
    void naviguer(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/AfficherOffre.fxml"));
        Stage stage = (Stage) btnretour.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize Offres if needed
        setOffres(offres);
        // Additional initialization code here, if needed
    }
}
