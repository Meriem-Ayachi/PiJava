package tn.esprit.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.controllers.locationVoiture.ModifierLocation;
import tn.esprit.controllers.locationVoiture.ModifierVoiture;
import tn.esprit.models.Location_Voiture;
import tn.esprit.models.Voiture;

import java.io.IOException;

public class Navigator {

    private Stage primaryStage;

    public Navigator(){

    }

    public Navigator(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void goToPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goTo_ModifierVoiture(String fxmlPath, Voiture v) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // pass param
            ModifierVoiture controller = loader.getController();
            controller.initialize(v);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goTo_ModifierLocation(String fxmlPath, Location_Voiture l) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // pass param
            ModifierLocation controller = loader.getController();
            controller.initialize(l);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void goToPage_WithEvent(String fxmlPath, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}