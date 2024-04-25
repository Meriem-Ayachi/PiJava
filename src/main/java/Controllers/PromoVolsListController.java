package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import tn.esprit.models.Promo_Vols;
import tn.esprit.models.Vols;
import tn.esprit.services.Promo_VolsService;
import tn.esprit.services.VolService;

import java.io.IOException;
import java.util.List;

public class PromoVolsListController {

    @FXML
    private ListView<Promo_Vols> promoListView;

    private final Promo_VolsService promoService = new Promo_VolsService();
    private final VolService volService = new VolService(){}; // Assuming you have a service for managing flights

    @FXML
    public void initialize() {
        // Populate the ListView with Promo_Vols data
        List<Promo_Vols> promoVols = promoService.getAll();
        promoListView.getItems().addAll(promoVols);

        // Set up cell factory to customize the appearance of each item
        promoListView.setCellFactory(param -> new ListCell<Promo_Vols>() {
            @Override
            protected void updateItem(Promo_Vols item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Create labels for Promo_Vols information
                    Label promoInfoLabel = new Label("Promo ID: " + item.getId() + "\nStart Date: " + item.getDate_debut_promo().toString() +
                            "\nEnd Date: " + item.getDate_fin_promo().toString() + "\nPourcentage: " + Double.toString(item.getPourcentage()) + "%");
                    promoInfoLabel.setWrapText(true);

                    // Fetch corresponding flight (Vols) details using the ID from Promo_Vols
                    Vols correspondingFlight = getCorrespondingFlight(item.getId());

                    // Calculate new price after discount
                    double originalPrice = correspondingFlight.getPrix();
                    double discountPercentage = item.getPourcentage();
                    double newPrice = originalPrice - (originalPrice * (discountPercentage / 100));

                    // Create labels for Vols information
                    Label volsInfoLabel = new Label("Destination: " + correspondingFlight.getDestination() + "\nPoint Depart: " + correspondingFlight.getPointdepart() +
                            "\nDate Arrivee: " + correspondingFlight.getDatearrive() + "\nDate Depart: " + correspondingFlight.getDatedepart() +
                            "\nPrix: " + correspondingFlight.getPrix() + "\nNbrescale: " + correspondingFlight.getNbrescale() +
                            "\nNbrplace: " + correspondingFlight.getNbrplace() + "\nDuree: " + correspondingFlight.getDuree() +
                            "\nClasse: " + correspondingFlight.getClasse() + "\nNew Price after Discount: " + String.format("%.2f", newPrice));
                    volsInfoLabel.setWrapText(true);

                    // Set styles for labels
                    promoInfoLabel.setStyle("-fx-font-weight: bold;");
                    volsInfoLabel.setStyle("-fx-font-weight: bold;");

                    // Create a VBox to hold all labels vertically
                    VBox infoVBox = new VBox();
                    infoVBox.getChildren().addAll(promoInfoLabel, volsInfoLabel);
                    infoVBox.setSpacing(5);

                    // Set the layout as the graphic for the ListCell
                    setGraphic(infoVBox);
                }
            }

            // Method to fetch corresponding flight details based on promo ID
            private Vols getCorrespondingFlight(int promoId) {
                return volService.getOne(promoId); // Assuming the promo ID corresponds to vol ID
            }
        });
    }

    @FXML
    void goBack(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/addvol.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        promoListView.getScene().setRoot(root);
    }
}
