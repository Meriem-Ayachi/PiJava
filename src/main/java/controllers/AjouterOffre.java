package controllers;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.models.Offres;
import tn.esprit.services.OffresService;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
public class AjouterOffre {

    @FXML
    private TextField titleTFO;

    @FXML
    private TextField descriptionTFO;

    @FXML
    private TextField publishedTFO;

    @FXML
    private TextField prixTFO;
    @FXML
    private StackPane notificationPane;

    @FXML
    private TextField lieuTFO;

    @FXML
    private ImageView imageIV; // Updated to ImageView
    @FXML
    private Button liste;


    private final OffresService os = new OffresService();


    // Cloudinary initialization
    private final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dtnhymuza",
            "api_key", "841544276497521",
            "api_secret", "Xcv3hd_-wqTsBvpk3yNvBc5fPfw"));


    @FXML
    void AjouterO(ActionEvent event) {
        try {
            validateFields(); // Validate input fields
            String imageUrl = uploadImageToCloudinary(imageIV.getImage());
            LocalDate today = LocalDate.now();
            Date date = Date.valueOf(today);
            os.add(new Offres(
                    titleTFO.getText(),
                    descriptionTFO.getText(),
                    Boolean.parseBoolean(publishedTFO.getText()),
                    Double.parseDouble(prixTFO.getText()),
                    lieuTFO.getText(),
                    imageUrl,
                    date
            ));
            showNotification("Offre ajoutée avec succès");
        } catch (SQLException | IOException | IllegalArgumentException e) {
            showAlert("Erreur", e.getMessage());
        }
    }

    private void validateFields() throws SQLException {
        if (descriptionTFO.getText().isEmpty()) {
            throw new SQLException("La description est vide.");
        }
        if (descriptionTFO.getText().length() > 80 ) {
            throw new SQLException("La description ne doit pas dépasser 30 caractères.");
        }
        if (titleTFO.getText().isEmpty()) {
            throw new SQLException("Le titre est vide.");
        }
        if (prixTFO.getText().isEmpty()) {
            throw new SQLException("Le prix est vide.");
        }
        if (Double.parseDouble(prixTFO.getText()) == 0.0) {
            throw new SQLException("Le prix ne peut pas être égal à zéro.");
        }
        if (publishedTFO.getText().isEmpty()) {
            throw new SQLException("Le champ 'published' est vide.");
        }
        if (lieuTFO.getText().isEmpty()) {
            throw new SQLException("Le lieu est vide.");
        }
        if (imageIV.getImage() == null) {
            throw new IllegalArgumentException("L'image est nulle.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Timeline notificationTimeline;

    private void showNotification(String message) {
        if (notificationPane != null) { // Vérifiez que notificationPane n'est pas null avant de l'utiliser
            notificationPane.setVisible(true);
            notificationPane.getChildren().clear();
            Label label = new Label(message);
            notificationPane.getChildren().add(label);

            notificationTimeline = new Timeline(new KeyFrame(Duration.minutes(5), event -> {
                notificationPane.setVisible(false);
                notificationTimeline.stop();
            }));
            notificationTimeline.setCycleCount(1);
            notificationTimeline.play();
        } else {
            showAlert("Erreur", "notificationPane est null. Vérifiez votre FXML.");
        }



    // Créer une Timeline pour gérer la durée d'affichage de la notification (5 minutes)
        notificationTimeline = new Timeline(new KeyFrame(Duration.minutes(15), event -> {
            notificationPane.setVisible(false); // Masquer la notification après 5 minutes
            notificationTimeline.stop(); // Arrêter la Timeline
        }));
        notificationTimeline.setCycleCount(1); // Exécuter une seule fois
        notificationTimeline.play(); // Démarrer la Timeline
    }
    private void sendNotificationToHome(String message) {
        try {
            // Charger le fichier FXML de la vue Home
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur de la vue Home
            Home homeController = loader.getController();

            // Appeler la méthode setNotification du contrôleur Home pour afficher la notification
            homeController.setNotification(message);

            // Créer une nouvelle fenêtre pour afficher la vue Home avec la notification
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors de l'affichage de la notification");
        }
    }

    //télécharger une image vers Cloudinary.
    private String uploadImageToCloudinary(Image image) throws IOException {
        if (image == null) {
            throw new IllegalArgumentException("L'image est nulle.");
        }
        // Convert JavaFX Image to BufferedImage
        Image fxImage = imageIV.getImage();
        java.awt.image.BufferedImage bufferedImage = SwingFXUtils.fromFXImage(fxImage, null);
        // Create temporary file to store image
        File tempFile = File.createTempFile("temp", ".png");
        ImageIO.write(bufferedImage, "png", tempFile);

        // Upload image to Cloudinary
        Map uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.emptyMap());

        // Return Cloudinary image URL
        return (String) uploadResult.get("secure_url");
    }

    @FXML
    void naviguer(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherOffre.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors du chargement du fichier FXML", e);
        }
    }


    @FXML
    public void choisirImage(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                imageIV.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
                // Gérer l'erreur de chargement de l'image
            }
        }
    }


}