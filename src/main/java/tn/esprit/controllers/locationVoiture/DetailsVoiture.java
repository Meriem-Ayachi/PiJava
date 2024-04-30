package tn.esprit.controllers.locationVoiture;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.MainFX;
import tn.esprit.interfaces.RefreshCallBack;
import tn.esprit.models.Voiture;
import tn.esprit.services.VoitureService;
import tn.esprit.util.Navigator;

import java.io.IOException;

import javafx.event.ActionEvent;

public class DetailsVoiture {

    @FXML
    private Label CapaciteDetail;

    @FXML
    private Label CouleurDetail;

    @FXML
    private Label EnergyDetail;

    @FXML
    private ImageView ImageDetail;

    @FXML
    private Label MarqueDetail;

    @FXML
    private Label ModelDetail;

    private Voiture currentVoiture;

    private final VoitureService voitureService = new VoitureService();
    private RefreshCallBack callBack;

    void initializeCallback(RefreshCallBack callBack){
        this.callBack = callBack;
    }

    void initialize(Voiture voiture) {
        currentVoiture = voiture;

        CapaciteDetail.setText("Capacité: " + String.valueOf(voiture.getCapacite()));
        CouleurDetail.setText("Couleur: " + String.valueOf(voiture.getCouleur()));
        EnergyDetail.setText("Energy: " + String.valueOf(voiture.getEnergy()));
        MarqueDetail.setText("Marque: " + String.valueOf(voiture.getMarque()));
        ModelDetail.setText("Model: " + String.valueOf(voiture.getModel()));

        Image image = new Image("file:" + voiture.getImage_file_name());
        ImageDetail.setImage(image);
    }

    @FXML
    void gotoModifier(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierVoiture.fxml"));
        Parent root = loader.load();

        ModifierVoiture controller = loader.getController();
        controller.initialize(currentVoiture);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void supprimer(ActionEvent event) {
        // confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Voulez-vous supprimer cette réclamation");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        // check if user has declined
        if (result != ButtonType.OK) {
            return;
        }

        voitureService.delete(currentVoiture.getId());

        // Afficher un message de confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
        confirmationAlert.setTitle("Suppression réussie");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("La réclamation a été supprimée avec succès.");
        confirmationAlert.showAndWait();

        ((Stage) ImageDetail.getScene().getWindow()).close();
        this.callBack.onRefreshComplete();

    }
}
