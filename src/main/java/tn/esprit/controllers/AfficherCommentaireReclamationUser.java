package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import tn.esprit.models.Reclamation;
import tn.esprit.models.Reclamation_Commentaire;
import tn.esprit.models.User;
import tn.esprit.models.session;
import tn.esprit.services.Reclamation_CommentaireService;
import tn.esprit.services.UserService;
import tn.esprit.util.Navigator;

import java.io.IOException;
import java.util.List;

public class AfficherCommentaireReclamationUser {

    @FXML
    private TextFlow commentsTextFlow;

    private static final double MAX_TEXT_WIDTH = 300;

    Reclamation_CommentaireService rcs = new Reclamation_CommentaireService();
    UserService us = new UserService();

    Reclamation currentRec;
    @FXML
    void initialize(Reclamation reclamation) {
        currentRec=reclamation;
        refresh();

    }


    @FXML
    private void addComment(String username, String comment) {
        Text usernameText = new Text(username + ": ");
        usernameText.setStyle("-fx-font-weight: bold; -fx-fill: blue;");

        Text commentContent = new Text(comment);
        commentContent.setStyle("-fx-fill: black;");
        commentContent.setFont(Font.font("Arial", Font.getDefault().getSize()));
        commentContent.setWrappingWidth(MAX_TEXT_WIDTH);

        TextFlow commentFlow = new TextFlow(usernameText, new Text("\n") , commentContent);
        commentFlow.setMaxWidth(MAX_TEXT_WIDTH);

        commentsTextFlow.getChildren().addAll(commentFlow, new Text("\n\n"));
    }

    private void addComment_withDelete(String username, String comment , int commentId) {
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


        TextFlow commentFlow = new TextFlow(usernameText, new Text("\n"), commentContent, new Text("\n"), deleteButton);
        commentFlow.setMaxWidth(MAX_TEXT_WIDTH);

        commentsTextFlow.getChildren().addAll(commentFlow, new Text("\n\n"));
    }
    @FXML
    void AddCommBTN(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCommentaireReclamationUser.fxml"));
        Parent root = loader.load();

        // Obtenir le contrôleur associé à l'interface
        AjouterCommentaireReclamationUser controller = loader.getController();

        // Appeler la méthode pour initialiser les détails de la réclamation
        controller.initialize(currentRec);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();


    }

    void refresh(){
        commentsTextFlow.getChildren().clear();
        List<Reclamation_Commentaire> reclamationCommentaireList = rcs.getAllByReclamationId(currentRec.getId());

        for (Reclamation_Commentaire commentaire : reclamationCommentaireList) {
            User user = us.getOne(commentaire.getUser_id());
            String username = user.getNom() + " " + user.getPrenom();
            if (session.id_utilisateur == user.getId()){
                addComment_withDelete(username,commentaire.getContenu(), commentaire.getId());
            }
            else {
                addComment(username, commentaire.getContenu());
            }
        }
    }
}