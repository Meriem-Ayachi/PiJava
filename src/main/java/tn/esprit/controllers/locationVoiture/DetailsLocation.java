package tn.esprit.controllers.locationVoiture;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.MainFX;
import tn.esprit.interfaces.RefreshCallBack;
import tn.esprit.models.Location_Voiture;
import tn.esprit.services.LocationVoitureService;
import tn.esprit.util.Navigator;

public class DetailsLocation {

    @FXML
    private Label DateDebutDetail;

    @FXML
    private Label DateFinDetail;

    @FXML
    private Label PrixDetail;

    @FXML
    private Label StatusDetail;

    @FXML
    private Label TypeDetail;

    @FXML
    private Button voitureDetailB;

    private Location_Voiture currentLocation;

    private final LocationVoitureService locationVoitureService = new LocationVoitureService();
    private RefreshCallBack callBack;

    void initializeCallback(RefreshCallBack callBack){
        this.callBack = callBack;
    }

    void initialize(Location_Voiture locationV) {
        currentLocation = locationV;

        StatusDetail.setText("Status: " + String.valueOf(locationV.getStatus()));
        PrixDetail.setText("Prix: " + String.valueOf(locationV.getPrix()));
        TypeDetail.setText("Type: " + String.valueOf(locationV.getType()));
        DateDebutDetail.setText("Date Debut: " + String.valueOf(locationV.getDate_debut().toString()));
        DateFinDetail.setText("Date Fin: " + String.valueOf(locationV.getDatefin().toString()));

        if (locationV.getVoiture_id() != 0){
            voitureDetailB.setVisible(true);;
        }
    }

    @FXML
    void gotoModifier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierLocation.fxml"));
            Parent root;
            root = loader.load();
            ModifierLocation controller = loader.getController();
            controller.initialize(currentLocation);
    
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void gotoVoitureDetail(ActionEvent event) {
        System.out.println("going to voiture" + currentLocation.getVoiture_id());
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

        locationVoitureService.delete(currentLocation.getId());

        // Afficher un message de confirmation
        Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
        confirmationAlert.setTitle("Suppression réussie");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("La réclamation a été supprimée avec succès.");
        confirmationAlert.showAndWait();

        ((Stage) DateFinDetail.getScene().getWindow()).close();
        this.callBack.onRefreshComplete();
    }
    

}
