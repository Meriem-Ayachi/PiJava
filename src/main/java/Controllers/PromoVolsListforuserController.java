package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tn.esprit.models.Promo_Vols;
import tn.esprit.models.Vols;
import tn.esprit.services.Promo_VolsService;
import tn.esprit.services.VolService;

import java.io.IOException;
import java.util.List;

public class PromoVolsListforuserController {

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

                    String imagePath= "file:C:/Users/melek/Downloads/depositphotos_42167223-stock-photo-promo.jpg";
                    Image image = new Image(imagePath);

                    ImageView imageView=new ImageView(image);
                    imageView.setFitWidth(100);
                    imageView.setPreserveRatio(true);










                    // Create labels for Promo_Vols information
                    Label promoInfoLabel = new Label( "Start Date: " + item.getDate_debut_promo().toString() +
                            "\nEnd Date: " + item.getDate_fin_promo().toString() + "\nPourcentage: " + Double.toString(item.getPourcentage()) + "%");
                    promoInfoLabel.setWrapText(true);

                    promoInfoLabel.setStyle("-fx-font-size: 12pt;");

                    // Fetch corresponding flight (Vols) details using the ID from Promo_Vols
                    Vols correspondingFlight = getCorrespondingFlight(item.getId());

                    // Calculate new price after discount
                    double originalPrice = correspondingFlight.getPrix();
                    double discountPercentage = item.getPourcentage();
                    double newPrice = originalPrice - (originalPrice * (discountPercentage / 100));

                    // Create labels for Vols information
                    Label volsInfoLabel = new Label("Destination: " + correspondingFlight.getDestination());
                    volsInfoLabel.setWrapText(true);
                    Label newpricelabel = new Label("Prix Promo:"+String.format("%.2f", newPrice));

                    // Set styles for labels
                    promoInfoLabel.setStyle("-fx-font-size: 12pt;");
                    volsInfoLabel.setStyle("-fx-font-weight: bold;-fx-font-size: 14pt;");
                    newpricelabel.setStyle("-fx-font-weight: bold;-fx-font-size: 14pt;-fx-text-fill: #518c65;");

                    // Create a VBox to hold all labels vertically
                    VBox infoVBox = new VBox();
                    infoVBox.getChildren().addAll(volsInfoLabel,promoInfoLabel);
                    infoVBox.setAlignment(Pos.CENTER_LEFT);
                    infoVBox.setSpacing(5);

                    VBox pricevbox=new VBox();
                    pricevbox.getChildren().addAll(newpricelabel);
                    pricevbox.setAlignment(Pos.CENTER_RIGHT);
                    pricevbox.setSpacing(5);

                    AnchorPane anchorPane=new AnchorPane();
                    AnchorPane.setTopAnchor(imageView,5.0);
                    AnchorPane.setLeftAnchor(imageView,5.0);
                    AnchorPane.setTopAnchor(infoVBox,5.0);
                    AnchorPane.setLeftAnchor(infoVBox,120.0);

                    AnchorPane.setTopAnchor(pricevbox,5.0);
                    AnchorPane.setRightAnchor(pricevbox,5.0);

                    anchorPane.getChildren().addAll(imageView,infoVBox,pricevbox);

                    // Set the layout as the graphic for the ListCell
                    setGraphic(anchorPane);
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
            root = FXMLLoader.load(getClass().getResource("/Showvolforuser.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        promoListView.getScene().setRoot(root);
    }
}
