package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.Offres;
import tn.esprit.services.OffresService;


import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Home implements Initializable {

    private final OffresService os = new OffresService();

    @FXML
    private ListView<Offres> listview;

    @FXML
    private Button details;

    @FXML
    private ComboBox<String> combobox;

    @FXML
    private Label notificationLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Offres> offres = os.getAll();
        ObservableList<Offres> observableOffres = FXCollections.observableList(offres);
        listview.setItems(observableOffres);
        // Charger le fichier CSS pour le style des cellules
        URL cssFileUrl = getClass().getResource("/styles-offre.css");
        if (cssFileUrl != null) {
            String cssFile = cssFileUrl.toExternalForm();
            listview.getStylesheets().add(cssFile);
        } else {
            System.err.println("Erreur : Le fichier CSS n'a pas été trouvé.");
        }

        // Configurer la cellule personnalisée pour afficher l'image dans la ListView
        listview.setCellFactory(param -> new ListCell<Offres>() {
            private final HBox content = new HBox();
            private final ImageView imageView = new ImageView();
            private final VBox infoPane = new VBox();
            private final Label titleLabel = new Label();
            private final Label lieuLabel = new Label();
            private final Label prixLabel = new Label();
            private final Label dateLabel = new Label();


            {

                titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");

                content.getChildren().addAll(imageView, infoPane);
                content.setSpacing(20);
                infoPane.setSpacing(5);
                titleLabel.getStyleClass().add("offer-title");
                lieuLabel.getStyleClass().add("offer-lieu");
                prixLabel.getStyleClass().add("offer-price");
                dateLabel.getStyleClass().add("offer-date");

                infoPane.getChildren().addAll(titleLabel, lieuLabel, prixLabel,dateLabel);


            }

            protected void updateItem(Offres offres, boolean empty) {
                super.updateItem(offres, empty);
                if (empty || offres == null) {
                    setGraphic(null);
                } else {
                    try {
                        Image image = new Image(offres.getImage());
                        imageView.setImage(image);
                        imageView.setFitWidth(150); // Ajustez la largeur de l'image selon vos besoins
                        imageView.setFitHeight(200); // Ajustez la hauteur de l'image selon vos besoins
                        imageView.setPreserveRatio(true);
                        titleLabel.setText(offres.getTitle());
                        lieuLabel.setText(offres.getLieu());
                        prixLabel.setText(String.valueOf(offres.getPrix()));
                        dateLabel.setText(offres.getCreated_at().toString());



                        prixLabel.setText(String.valueOf(offres.getPrix()));
                        setGraphic(content);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Ajouter les critères de tri à la ComboBox
        combobox.getItems().addAll("prix", "created_at");
        // Sélectionner par défaut le premier élément dans la ComboBox
        combobox.getSelectionModel().selectFirst();
    }

    @FXML
    void afficherDetails(ActionEvent event) throws Exception {
        Offres selectedOffre = listview.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsPage.fxml"));
            Parent root = loader.load();
            DetailsPage controller = loader.getController();
            controller.initialize(selectedOffre);  // Passer l'offre sélectionnée au contrôleur DetailsPage
            
            Stage stage = (Stage) combobox.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @FXML
    void tri(ActionEvent event) {
        try {
            List<Offres> offres = os.triParCritere(combobox.getValue());
            ObservableList<Offres> observableOffres = FXCollections.observableList(offres);
            listview.setItems(observableOffres);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }



    // Méthode pour recevoir et afficher la notification
    public void setNotification(String message) {
        notificationLabel.setText(message);
    }
}
