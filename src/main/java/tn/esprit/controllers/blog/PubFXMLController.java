package tn.esprit.controllers.blog;

import tn.esprit.Model.Publication;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PubFXMLController implements Initializable {

    @FXML
    private HBox pub;
    @FXML
    private HBox com;
    @FXML
    private HBox music;
    @FXML
    private GridPane postGrid;
    @FXML
    private Hyperlink addPostLink;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Mettre en place un gestionnaire d'événements pour le clic sur le lien "Add Post"
        addPostLink.setOnAction(this::handleAddPostLink);
        
        // Autres initialisations si nécessaires
    }

    @FXML
    private void handlePublicationClick(MouseEvent event) {
    }

    @FXML
    private void handleComClick(MouseEvent event) {
    }

    @FXML
    private void handleMusicClick(MouseEvent event) {
    }

   @FXML
    private void handleAddPostLink(ActionEvent event) {
        try {
          
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/publication/addPostFXML.fxml"));
            Parent root = loader.load();

            
            Scene scene = new Scene(root);

           
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Add Post");

           
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


void addContent(String shareContent) {
    
    Label sharedLabel = new Label(shareContent);

   
    int rowCount = 0;
    for (Node node : postGrid.getChildren()) {
        if (GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > rowCount) {
            rowCount = GridPane.getRowIndex(node);
        }
    }

   
    postGrid.add(sharedLabel, 0, rowCount + 1);
}

   /* void addPublication(Publication newPublication) {
    // Créer un Label pour afficher le titre de la publication
    Label titleLabel = new Label(newPublication.getTitle());

    // Créer un ImageView pour afficher l'image de la publication
    ImageView imageView = new ImageView(new Image(newPublication.getImage()));
    imageView.setFitWidth(100); // Ajustez la largeur de l'image si nécessaire
    imageView.setPreserveRatio(true); // Conservez le ratio hauteur/largeur de l'image

    // Créer un Label pour afficher le contenu de la publication
    Label contentLabel = new Label(newPublication.getContent());

    // Créer un Label pour afficher la date de création de la publication
    Label dateLabel = new Label("Date de création : " + newPublication.getCreatedAt().toString());

    // Créer un VBox pour organiser les éléments de la publication
    VBox publicationBox = new VBox(10); // Espacement de 10 pixels entre les éléments
    publicationBox.getChildren().addAll(titleLabel, imageView, contentLabel, dateLabel);

   // Créez une variable pour suivre le nombre de lignes dans la grille
int rowCount = 0;

// Parcourez les enfants de la grille pour obtenir le nombre de lignes actuel
for (Node node : postGrid.getChildren()) {
    if (node instanceof VBox) {
        rowCount++;
    }
}

// Ajoutez la VBox à la grille en utilisant le rowCount calculé
postGrid.addRow(rowCount, publicationBox);

 

}*/
    
    void addPublication(Publication newPublication) {
    // Ajouter la nouvelle publication à la grille dans PubFXMLController
    Label titleLabel = new Label(newPublication.getTitle());
    ImageView imageView = new ImageView(newPublication.getImage_file_path());
    Label contentLabel = new Label(newPublication.getContent());
    Label dateLabel = new Label("Date de création : " + newPublication.getCreatedAt().toString());

    VBox publicationBox = new VBox(10);
    publicationBox.getChildren().addAll(titleLabel, imageView, contentLabel, dateLabel);

    int rowCount = 0;
    for (Node node : postGrid.getChildren()) {
        if (node instanceof VBox) {
            rowCount++;
        }
    }
    postGrid.addRow(rowCount, publicationBox);
}

}



/*************************************************************/
//@FXML
//    private Hyperlink GoBackLink;
//
//    @FXML
//private void handleBackButton(ActionEvent event) {
//    try {
//        Parent root = FXMLLoader.load(getClass().getResource("/publication/PostCRUD.fxml"));
//        Scene scene = new Scene(root);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(scene);
//        stage.show();
//    } catch (IOException e) {
//        e.printStackTrace();
//    }

