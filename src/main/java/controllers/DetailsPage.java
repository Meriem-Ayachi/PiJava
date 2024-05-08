package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tn.esprit.models.Offres;
import tn.esprit.services.OffresService;
import tn.esprit.util.MaConnexion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetailsPage {
    @FXML
    private Label averageRatingLabel;


    @FXML
    private GridPane gridpane;
    @FXML
    private Button commenter;
    @FXML
    private Offres offres;

    @FXML
    private Button reserver;


    private final OffresService os = new OffresService();

    public void Commenter(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCommentairefront.fxml"));
            AnchorPane detailsOffrePane = loader.load();
            AjouterCommentairefront controller = loader.getController();

            controller.initialize(offres);

            Stage stage = new Stage();
            stage.setScene(new Scene(detailsOffrePane));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void Reserver(ActionEvent event) {

    }

    @FXML
    private Button backtohome;


    @FXML
    private ImageView imageView; // L'image sera affichée ici


    @FXML
    public void initialize(Offres selectedOffre) {


        this.offres=selectedOffre;
        if (selectedOffre != null) {
            Offres loadedOffre = os.getOne(selectedOffre.getId());
            if (loadedOffre != null) {

                gridpane.getChildren().clear();

                // Add labels to the grid pane
                gridpane.add(new Label(loadedOffre.getLieu()), 1, 0);
                gridpane.add(new Label(loadedOffre.getTitle()), 0, 0);
                gridpane.add(new Label(loadedOffre.getDescription()), 0, 1);
                gridpane.add(new Label(String.valueOf(loadedOffre.getCreated_at())), 1, 2);
                gridpane.add(new Label(String.valueOf(loadedOffre.getPrix())), 0, 2);
                // Add more labels to display other offer details
                // Load the image from the URL
                String imageUrl = loadedOffre.getImage(); // Assume getImage() returns the image URL
                if (imageUrl != null && !imageUrl.isEmpty()) {
                    Image image = new Image(imageUrl);
                    if (image.isError()) {
                        System.err.println("Error loading image from URL: " + imageUrl);
                        // Handle the error, e.g., display a default image
                    } else {
                        // Display the image
                        imageView.setImage(image);
                    }
                } else {
                    System.err.println("Empty image URL for offer with ID: " + loadedOffre.getId());
                    // Handle the case where the image URL is empty
                }

                // You can also display other information about the offer here without adding to the grid pane
            }
        }
        double averageRating = calculateAverageRating(selectedOffre.getId());
        averageRatingLabel.setText(String.format("%.2f", averageRating));
    }
    @FXML
    void rateOneStar(ActionEvent event) {
        rateOffer(offres.getId(), getUserId(), 1);
    }

    @FXML
    void rateTwoStars(ActionEvent event) {
        rateOffer(offres.getId(), getUserId(), 2);
    }

    @FXML
    void rateThreeStars(ActionEvent event) {
        rateOffer(offres.getId(), getUserId(), 3);
    }
    private void rateOffer(int offerId, int userId, int ratingValue) {
        try {
            // Utilisez la connexion de OffresService ou passez-la en paramètre
            // Assurez-vous d'avoir une méthode pour récupérer l'ID de l'utilisateur (getUserId par exemple)
            // Connexion à la base de données
            Connection cnx = MaConnexion.getInstance().getCnx();

            // Vérifiez si l'utilisateur a déjà noté cette offre
            String checkQuery = "SELECT * FROM offer_review WHERE offer_list_id = ? AND user_id = ?";
            PreparedStatement checkStmt = cnx.prepareStatement(checkQuery);
            checkStmt.setInt(1, offerId);
            checkStmt.setInt(2, userId);
            ResultSet checkResult = checkStmt.executeQuery();

            if (checkResult.next()) {
                // L'utilisateur a déjà noté cette offre, effectuez la mise à jour de la notation
                String updateQuery = "UPDATE offer_review SET value = ? WHERE offer_list_id = ? AND user_id = ?";
                PreparedStatement updateStmt = cnx.prepareStatement(updateQuery);
                updateStmt.setInt(1, ratingValue);
                updateStmt.setInt(2, offerId);
                updateStmt.setInt(3, userId);
                updateStmt.executeUpdate();
                System.out.println("Note mise à jour avec succès");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText("Note mise à jour avec succès");
                alert.showAndWait();

            } else {
                // L'utilisateur n'a pas encore noté cette offre, ajoutez une nouvelle entrée
                String insertQuery = "INSERT INTO offer_review (offer_list_id, user_id, value) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = cnx.prepareStatement(insertQuery);
                insertStmt.setInt(1, offerId);
                insertStmt.setInt(2, userId);
                insertStmt.setInt(3, ratingValue);
                insertStmt.executeUpdate();
                System.out.println("Nouvelle note ajoutée avec succès");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText("Nouvelle note ajoutée avec succès");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la notation de l'offre : " + e.getMessage());
            e.printStackTrace();
            // Gérez l'erreur selon vos besoins
        }
    }


    // Méthode pour récupérer l'ID de l'utilisateur (à adapter selon votre implémentation)
    private int getUserId() {
        return 1; // Exemple, vous devez récupérer l'ID de l'utilisateur connecté
    }
    private double calculateAverageRating(int offerId) {
        double averageRating = 0.0;
        int totalRatings = 0;

        try {
            Connection conn = MaConnexion.getInstance().getCnx();
            String query = "SELECT value FROM offer_review WHERE offer_list_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, offerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                totalRatings++;
                averageRating += rs.getDouble("value");
            }

            if (totalRatings > 0) {
                averageRating /= totalRatings;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du calcul de la moyenne de notation : " + e.getMessage());
            e.printStackTrace();
            // Gérer l'erreur selon vos besoins
        }

        return averageRating;
    }


    @FXML
    void backtohome(ActionEvent event) throws Exception {

            Parent root = FXMLLoader.load(getClass().getResource("/home.fxml"));
            Stage stage = (Stage) backtohome.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

    }
}
