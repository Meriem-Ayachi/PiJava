package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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


public class DetailsReclamation {

    @FXML
    private TextFlow commentsTextFlow;

    private static final double MAX_TEXT_WIDTH = 300;


    @FXML
    private Label descriptionLabel;

    @FXML
    private Label sujetLabel;
    private Reclamation reclamation;
    private ReclamationService reclamationService = new ReclamationService();
    private Reclamation_CommentaireService rcs = new Reclamation_CommentaireService();

    private UserService us = new UserService();





    // Méthode pour initialiser les détails de la réclamation dans l'interface
    public void initializeDetails(Reclamation reclamation) {
        this.reclamation = reclamation;
        sujetLabel.setText("Sujet : " + reclamation.getSujet());
        descriptionLabel.setText("Description : " + reclamation.getDescription());
        refresh();
    }

    public void AddCommBTN(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCommentaireReclamationUser.fxml"));
        Parent root = loader.load();

        // Obtenir le contrôleur associé à l'interface
        AjouterCommentaireReclamationUser controller = loader.getController();

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
                addComment_withDelete(username, commentaire.getContenu(), commentaire.getId());
            } else {
                addComment(username, commentaire.getContenu());
            }
        }
    }

    @FXML
    private void addComment(String username, String comment) {
        Text usernameText = new Text(username + ": ");
        usernameText.setStyle("-fx-font-weight: bold; -fx-fill: blue;");

        Text commentContent = new Text(comment);
        commentContent.setStyle("-fx-fill: black;");
        commentContent.setFont(Font.font("Arial", Font.getDefault().getSize()));
        commentContent.setWrappingWidth(MAX_TEXT_WIDTH);

        TextFlow commentFlow = new TextFlow(usernameText, new Text("\n"), commentContent);
        commentFlow.setMaxWidth(MAX_TEXT_WIDTH);

        commentsTextFlow.getChildren().addAll(commentFlow, new Text("\n\n"));
    }

    private void addComment_withDelete(String username, String comment, int commentId) {
        Text usernameText = new Text(username + ": ");
        usernameText.setStyle("-fx-font-weight: bold; -fx-fill: blue;");

        Text commentContent = new Text(comment);
        commentContent.setStyle("-fx-fill: black;");
        commentContent.setFont(Font.font("Arial", Font.getDefault().getSize()));
        commentContent.setWrappingWidth(MAX_TEXT_WIDTH);

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            rcs.delete(commentId);
            refresh();
        });
        Button updateButton = new Button("Update");
        updateButton.setOnAction(event -> {
            try {
                GoToUpdate(event , commentId);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        TextFlow commentFlow = new TextFlow(usernameText, new Text("\n"), commentContent, new Text("\n"), deleteButton , updateButton);
        commentFlow.setMaxWidth(MAX_TEXT_WIDTH);

        commentsTextFlow.getChildren().addAll(commentFlow, new Text("\n\n"));
    }

    private void GoToUpdate (ActionEvent event ,int commentID) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCommentaireReclamationUser.fxml"));
        Parent root = loader.load();

        // Obtenir le contrôleur associé à l'interface
        ModifierCommentaireReclamationUser controller = loader.getController();

        // Appeler la méthode pour initialiser les détails de la réclamation
        controller.initialize(commentID);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
