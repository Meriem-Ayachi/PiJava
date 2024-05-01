package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/hotelListF.fxml")));
            Scene scene = new Scene(root);

            // Ajuster la taille de la scène
            primaryStage.setWidth(800); // Définir la largeur souhaitée
            primaryStage.setHeight(600); // Définir la hauteur souhaitée

            primaryStage.setTitle("Tech-Voyage");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
