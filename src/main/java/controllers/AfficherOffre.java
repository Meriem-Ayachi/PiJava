package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.Offres;
import tn.esprit.services.OffresService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherOffre implements Initializable {

    private final OffresService os = new OffresService();
    @FXML
    private Button commentaires;
    @FXML
    private Button ajouter;

    @FXML
    private Button modifierO;

    @FXML
    private ListView<Offres> listview;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Offres> offres = os.getAll();
        listview.getItems().addAll(offres);

        // Charger le fichier CSS pour le style des cellules
        URL cssFileUrl = getClass().getResource("/styles-offre.css");
        if (cssFileUrl != null) {
            String cssFile = cssFileUrl.toExternalForm();
            listview.getStylesheets().add(cssFile);
        } else {
            System.err.println("Erreur : Le fichier CSS n'a pas été trouvé.");
        }

        listview.setCellFactory(param -> new ListCell<Offres>() {
            private final HBox content = new HBox();
            private final ImageView imageView = new ImageView();
            private final VBox infoPane = new VBox();
            private final Label titleLabel = new Label();
            private final Label descriptionLabel = new Label();
            private final Label lieuLabel = new Label();
            private final Label prixLabel = new Label();
            private final Label dateLabel = new Label();

            {
                content.getChildren().addAll(imageView, infoPane);
                content.setSpacing(20);
                infoPane.setSpacing(5);
                titleLabel.getStyleClass().add("offer-title");
                descriptionLabel.getStyleClass().add("offer-description");
                lieuLabel.getStyleClass().add("offer-lieu");
                prixLabel.getStyleClass().add("offer-price");
                dateLabel.getStyleClass().add("offer-date");
                infoPane.getChildren().addAll(titleLabel, descriptionLabel, lieuLabel, prixLabel,dateLabel);
            }

            @Override
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
                        titleLabel.setText(offres.getTitle());
                        descriptionLabel.setText(offres.getDescription());
                        lieuLabel.setText(offres.getLieu());
                        prixLabel.setText(String.valueOf(offres.getPrix()));
                        dateLabel.setText(offres.getCreated_at().toString());

                        setGraphic(content);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @FXML
    void SupprimerO(ActionEvent event) {
        Offres offres = listview.getSelectionModel().getSelectedItem();
        if (offres != null) {
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation de suppression");
            confirmationDialog.setHeaderText(null);
            confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer cette offre ?");
            confirmationDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    os.delete(offres.getId());
                    listview.getItems().remove(offres);
                    if (offres.getImage() != null && !offres.getImage().isEmpty()) {
                        new Image(offres.getImage()).cancel();
                    }

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Suppression réussie");
                    alert.setContentText("L'offre a été supprimée avec succès.");
                    alert.showAndWait();
                }
            });
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner une offre à supprimer.");
        }
    }

    @FXML
    void naviguer(ActionEvent event) {
        Offres offres = listview.getSelectionModel().getSelectedItem();
        if (offres != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModiffierOffre.fxml"));
                Parent root = loader.load();

                ModiffierOffre controller = loader.getController();
                controller.setOffres(offres);

                Stage stage = (Stage) ajouter.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                showAlert("Erreur de chargement", "Une erreur s'est produite lors du chargement de la vue de modification.");
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner une offre à modifier.");
        }
    }

    @FXML
    void commentaires(ActionEvent event) {
        Offres selectedOffre = listview.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCommentaires.fxml"));
                Parent root = loader.load();

                AfficherCommentaires controller = loader.getController();
                controller.initialize(selectedOffre);

                Stage stage = (Stage) ajouter.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                showAlert("Erreur de chargement", "Une erreur s'est produite lors du chargement de la vue des commentaires.");
            }
        }
    }

    @FXML
    void gostat(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Statistique.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
            showAlert("Erreur de chargement", "Une erreur s'est produite lors du chargement de la vue des statistiques.");
        }
    }

    @FXML
    void ajouter(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/AjouterOffre.fxml"));
        Stage stage = (Stage) ajouter.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
