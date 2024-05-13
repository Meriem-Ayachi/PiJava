/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.myblog;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ghada
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Hyperlink addPostLink;
    @FXML
    private GridPane postGrid;
    @FXML
    private ImageView music1;
    @FXML
    private ImageView media1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleMusicClick(MouseEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("musicFXML.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Music");

        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void handleMediaClick(MouseEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mediaFXML.fxml"));
        Parent root = loader.load();

        // Create a new stage and set the scene
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Media");

        // Show the stage
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error loading FXML: " + e.getMessage());
    }
    }

    @FXML
    private void handleAddPostLink(ActionEvent event) {
    }
    
}
