package tn.esprit.controllers.blog;

import tn.esprit.Model.Publication;
import tn.esprit.Service.PublicationService;
import java.io.IOException;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PublicationFXMLController implements Initializable {

    @FXML
    private Hyperlink addPostLink;
    @FXML
    private ImageView image;

  
   
    @FXML
    private GridPane postGrid;

    private ObservableList<Publication> publicationList;
   PublicationService SC=new PublicationService();
   List<Publication> list = new ArrayList();



  

    // Méthode pour afficher tous les posts dans le postGrid
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        afficherPublications();
    }

    private void afficherPublications() {

        list = SC.getAllPublications();
    

        int column = 0;
        int row = 1;

                try {
                    for (int q = 0; q < list.size(); q++) {
                    FXMLLoader load = new FXMLLoader();
                    load.setLocation(getClass().getResource("/publication/postFXML.fxml"));
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




    

}
