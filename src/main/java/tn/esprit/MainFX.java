package tn.esprit;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {


   // private static final int WINDOW_WIDTH = 960;
   // private static final int WINDOW_HEIGHT = 540; // Set your desired height


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserOrAdmin.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Application");
            primaryStage.setScene(scene);
           // primaryStage.setMinWidth(WINDOW_WIDTH);
            //primaryStage.setMaxWidth(WINDOW_WIDTH);
            //primaryStage.setMinHeight(WINDOW_HEIGHT);
           // primaryStage.setMaxHeight(WINDOW_HEIGHT);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
