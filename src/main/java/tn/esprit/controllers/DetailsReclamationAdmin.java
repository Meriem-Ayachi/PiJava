package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import tn.esprit.interfaces.RefreshCallBack;
import tn.esprit.models.Reclamation;
import tn.esprit.models.Reclamation_Commentaire;
import tn.esprit.models.User;
import tn.esprit.models.session;
import tn.esprit.services.ReclamationService;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import tn.esprit.services.Reclamation_CommentaireService;
import tn.esprit.services.UserService;

import java.io.IOException;
import java.util.List;


public class DetailsReclamationAdmin implements RefreshCallBack {

    @FXML
    private TextFlow commentsTextFlow;

    private static final double MAX_TEXT_WIDTH = 500;


    @FXML
    private Label descriptionLabel;

    @FXML
    private Label sujetLabel;
    private Reclamation reclamation;
    private ReclamationService reclamationService = new ReclamationService();
    private Reclamation_CommentaireService rcs = new Reclamation_CommentaireService();

    private UserService us = new UserService();


    RefreshCallBack callback ;


    // Méthode pour initialiser les détails de la réclamation dans l'interface
    public void initializeDetails(Reclamation reclamation ) {

        this.reclamation = reclamation;
        sujetLabel.setText("Sujet : " + reclamation.getSujet());
        descriptionLabel.setText("Description : " + reclamation.getDescription());
        refresh();
    }

    public void UpdateCallBack (RefreshCallBack callback)
    {
        this.callback = callback;
    }

    public void AddCommBTN(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCommentaireReclamationAdmin.fxml"));
        Parent root = loader.load();

        // Obtenir le contrôleur associé à l'interface
        AjouterCommentaireReclamationAdmin controller = loader.getController();

        // Appeler la méthode pour initialiser les détails de la réclamation
        controller.initialize(reclamation);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    void refresh() {
        commentsTextFlow.getChildren().clear();
        List<Reclamation_Commentaire> reclamationCommentaireList = rcs.getAllByReclamationId(reclamation.getId());

        for (Reclamation_Commentaire commentaire : reclamationCommentaireList) {
            User user = us.getOne(commentaire.getUser_id());
            String username = user.getNom() + " " + user.getPrenom();
            if (session.id_utilisateur == user.getId()) {
                addComment_withDeleteAndUpdate(username, commentaire.getContenu(), commentaire.getId());
            } else {
                addComment_withDelete(username, commentaire.getContenu(), commentaire.getId());
            }
        }
    }

    private void addComment_withDelete(String username, String comment, int commentId) {
        Text usernameText = new Text(username + ": ");
        usernameText.setStyle("-fx-font-weight: bold; -fx-fill: #387296; -fx-font-size: 17px;\n"); // Bleu avec une taille de police de 17px

        Text commentContent = new Text(comment);
        commentContent.setStyle("-fx-font-weight: bold; -fx-fill: black; -fx-font-size: 14px;\n"); // Noir avec une taille de police de 14px
        commentContent.setFont(Font.font("Arial", FontWeight.NORMAL, 12)); // Définir une taille de police normale avec une taille de 12px
        commentContent.setWrappingWidth(MAX_TEXT_WIDTH);

        Button deleteButton = new Button("");

        String imagePath = "/images/deleteComm.png"; // Chemin de votre image locale
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);

        // Ajustez la taille de l'image au besoin
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        // Définir l'image comme le graphique du bouton
        deleteButton.setGraphic(imageView);
        deleteButton.setStyle("-fx-background-color: transparent; "); // Rouge, en gras et taille de police de 14px
        deleteButton.setOnAction(event -> {
            rcs.delete(commentId);
            refresh();
        });

        HBox buttonsHBox = new HBox(deleteButton);
        buttonsHBox.setSpacing(10);

        Separator separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL); // Définir l'orientation de la séparatrice comme horizontale

        VBox commentVBox = new VBox(usernameText, commentContent);
        commentVBox.setSpacing(10);

        HBox commentBox = new HBox(commentVBox, buttonsHBox);
        commentBox.setSpacing(20);

        VBox commentContainer = new VBox(commentBox, separator); // Ajouter la séparatrice sous le commentaire
        commentContainer.setSpacing(20);

        commentsTextFlow.getChildren().addAll(commentContainer);

    }

    private void addComment_withDeleteAndUpdate(String username, String comment, int commentId) {
        Text usernameText = new Text(username + ": ");
        usernameText.setStyle("-fx-font-weight: bold; -fx-fill: #387296; -fx-font-size: 17px;\n"); // Bleu avec une taille de police de 17px

        Text commentContent = new Text(comment);
        commentContent.setStyle("-fx-font-weight: bold; -fx-fill: black; -fx-font-size: 14px;\n"); // Noir avec une taille de police de 14px
        commentContent.setFont(Font.font("Arial", FontWeight.NORMAL, 12)); // Définir une taille de police normale avec une taille de 12px
        commentContent.setWrappingWidth(MAX_TEXT_WIDTH);

        Button deleteButton = new Button("");

        String imagePath = "/images/deleteComm.png"; // Chemin de votre image locale
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);

        // Ajustez la taille de l'image au besoin
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        // Définir l'image comme le graphique du bouton
        deleteButton.setGraphic(imageView);
        deleteButton.setStyle("-fx-background-color: transparent; "); // Rouge, en gras et taille de police de 14px
        deleteButton.setOnAction(event -> {
            rcs.delete(commentId);
            refresh();
        });

        Button updateButton = new Button("");

        String imagePath2 = "/images/editComm.png"; // Chemin de votre image locale
        Image image2 = new Image(getClass().getResourceAsStream(imagePath2));
        ImageView imageView2 = new ImageView(image2);

        // Ajustez la taille de l'image au besoin
        imageView2.setFitWidth(20);
        imageView2.setFitHeight(20);
        updateButton.setGraphic(imageView2);
        updateButton.setStyle("-fx-background-color: transparent;"); // Bleu acier, en gras et taille de police de 14px
        updateButton.setOnAction(event -> {
            try {
                GoToUpdate(event , commentId);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        HBox buttonsHBox = new HBox(deleteButton, updateButton);
        buttonsHBox.setSpacing(10);

        Separator separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL); // Définir l'orientation de la séparatrice comme horizontale

        VBox commentVBox = new VBox(usernameText, commentContent);
        commentVBox.setSpacing(10);

        HBox commentBox = new HBox(commentVBox, buttonsHBox);
        commentBox.setSpacing(20);

        VBox commentContainer = new VBox(commentBox, separator); // Ajouter la séparatrice sous le commentaire
        commentContainer.setSpacing(20);

        commentsTextFlow.getChildren().addAll(commentContainer);
    }


    private void GoToUpdate (ActionEvent event ,int commentID) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCommentaireReclamationAdmin.fxml"));
        Parent root = loader.load();

        // Obtenir le contrôleur associé à l'interface
            ModifierCommentaireReclamationAdmin controller = loader.getController();

        // Appeler la méthode pour initialiser les détails de la réclamation
        controller.initialize(commentID);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void onRefreshComplete() {
        this.reclamation = reclamationService.getOne(this.reclamation.getId());
        sujetLabel.setText("Sujet : " + reclamation.getSujet());
        descriptionLabel.setText("Description : " + reclamation.getDescription());

    }

}
