package tn.esprit.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.controllers.locationVoiture.ModifierVoiture;
import tn.esprit.models.Voiture;

import java.io.IOException;

public class Navigator {

    private final Stage primaryStage;

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
    
}