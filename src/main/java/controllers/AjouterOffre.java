package controllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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
                if (descriptionTFO.getText().isEmpty()) {
                    throw new SQLException("La description est vide.");
                }
                if (descriptionTFO.getText().length() > 20) {
                    throw new SQLException("La description ne doit pas dépasser 20 caractères.");
                }
                if (titleTFO.getText().isEmpty()) {
                    throw new SQLException("le titre est vide.");
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

                Image fxImage = imageIV.getImage();
                if (fxImage != null) {
                    // Appeler la méthode uploadImageToCloudinary(image) ici
                    String imageUrl = uploadImageToCloudinary(fxImage);

                    // Get today's date
                    LocalDate today = LocalDate.now();
                    Date date = Date.valueOf(today);

                    os.add(new Offres(
                            titleTFO.getText(),
                            descriptionTFO.getText(),
                            Boolean.parseBoolean(publishedTFO.getText()),
                            Double.parseDouble(prixTFO.getText()),
                            lieuTFO.getText(),
                            imageUrl, // Utiliser l'URL de l'image Cloudinary
                            date
                    ));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setContentText("Offre ajoutée");
                    alert.showAndWait();
                } else {
                    throw new IllegalArgumentException("L'image est nulle.");
                }
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(e.getMessage());
                alert.showAndWait();

            } catch (IOException e) {
                throw new RuntimeException(e);
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