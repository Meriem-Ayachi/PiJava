package myblog_pi.myblog;

import myblog_pi.Model.Publication;
import myblog_pi.Service.PublicationService;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PublicationFXMLController implements Initializable {

    @FXML
    private ImageView music1;
    @FXML
    private ImageView media1;
    @FXML
    private Hyperlink addPostLink;
    
     @FXML
    private Hyperlink GoBackLink;

  
   
    @FXML
    private GridPane postGrid;

    private ObservableList<Publication> publicationList;
   PublicationService SC=new PublicationService();
   List<Publication> list = new ArrayList();
    @FXML
    private ImageView game;
    @FXML
    private ImageView web;



  

    // Méthode pour afficher tous les posts dans le postGrid
 @Override
public void initialize(URL location, ResourceBundle resources) {
    afficherPublications();
    addPostLink.setOnAction(this::handleAddPostLink);
        music1.setOnMouseClicked(event -> handleMusicClick());
        media1.setOnMouseClicked(event -> handleMediaClick());
      game.setOnMouseClicked(event -> handleGameClick());
       web.setOnMouseClicked(event -> handleWebClick());
       
}

private void afficherPublications() {

    list = SC.getAllPublications();
   

    int column = 0;
    int row = 1;

            try {
                 for (int q = 0; q < list.size(); q++) {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/myblog_pi/postFXML.fxml"));
                AnchorPane pane = load.load();
                PostFXMLController cardC = load.getController();
                cardC.setData(list.get(q));
                
                if (column == 3) {
                    column = 0;
                    row += 1;
                }
                
                postGrid.add(pane, column++, row);
                
                GridPane.setMargin(pane, new Insets(10));
                 }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
    }


    @FXML
    private void handleAddPostLink(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addPostFXML.fxml"));
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

    @FXML
    private void handleMusicClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("musicFXML.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Music");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMediaClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mediaFXML.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Media");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading FXML: " + e.getMessage());
        }
    }

    @FXML
    private void handleGameClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Guessing_Game.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Game");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading FXML: " + e.getMessage());
        }
    }

    @FXML
    private void handleWebClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Web.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Web");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading FXML: " + e.getMessage());
        }
    }

    @FXML
    private void GoBackLink(ActionEvent event) {
         try {
        Parent root = FXMLLoader.load(getClass().getResource("PostCRUD.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
        
    }


    

}
