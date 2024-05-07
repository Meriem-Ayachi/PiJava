package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.models.Offres;
import tn.esprit.services.OffresService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

    public class AfficherOffre  implements Initializable {

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
            try {


                List<Offres> offres = os.getAll();
                listview.getItems().addAll(offres);
                // Configurer la cellule personnalisée pour afficher l'image dans la ListView
                listview.setCellFactory(param -> new ListCell<Offres>() {
                    private final ImageView imageView = new ImageView();

                    @Override
                    protected void updateItem(Offres offres, boolean empty) {
                        super.updateItem(offres, empty);
                        if (empty || offres == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            // Charger l'image depuis l'URL et l'afficher dans l'ImageView
                            try {
                                Image image = new Image(offres.getImage());
                                imageView.setImage(image);
                                imageView.setFitWidth(70); // Réglez la largeur de l'image
                                imageView.setPreserveRatio(true); // Préservez les proportions de l'image
                                setGraphic(imageView);
                                setText(offres.getTitle() + // Afficher le titre de l'offre comme texte de la cellule
                                        "\n"+ offres.getDescription()+
                                        "\n"+ offres.getLieu()+
                                        "\n"+ String.valueOf(offres.getPrix())+
                                        "\n"+ offres.getCreated_at().toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                                // Gérer les erreurs de chargement de l'image ici
                            }
                        }
                    }
                });
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }


        }

        //
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
                        try {
                            os.delete(offres.getId());
                            listview.getItems().remove(offres);
                            // Vérifier si l'élément supprimé avait une image et libérer les ressources
                            if (offres.getImage() != null && !offres.getImage().isEmpty()) {
                                Image imageToRemove = new Image(offres.getImage());
                                imageToRemove.cancel(); // Libérer les ressources de l'image supprimée
                            }
                            Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                            confirmation.setTitle("Suppression réussie");
                            confirmation.setHeaderText(null);
                            confirmation.setContentText("L'offre a été supprimée avec succès.");
                            confirmation.showAndWait();
                        } catch (SQLException e) {
                            Alert error = new Alert(Alert.AlertType.ERROR);
                            error.setTitle("Erreur lors de la suppression");
                            error.setHeaderText(null);
                            error.setContentText("Une erreur s'est produite lors de la suppression de l'offre : " + e.getMessage());
                            error.showAndWait();
                        }
                    }
                });
            } else {
                Alert noSelectionAlert = new Alert(Alert.AlertType.WARNING);
                noSelectionAlert.setTitle("Aucune sélection");
                noSelectionAlert.setHeaderText(null);
                noSelectionAlert.setContentText("Veuillez sélectionner une offre à supprimer.");
                noSelectionAlert.showAndWait();
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
                    controller.setOffres(offres); // Pass the Offres object to the edit controller

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
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


                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
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
                e.printStackTrace();
                showAlert("Erreur de chargement", "Une erreur s'est produite lors du chargement de la vue des statistiques.");
            }
        }
        @FXML
        void ajouter(ActionEvent event) throws Exception  {
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



