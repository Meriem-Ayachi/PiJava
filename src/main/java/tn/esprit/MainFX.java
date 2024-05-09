package tn.esprit;

import javafx.application.Application;
import javafx.stage.Stage;
import tn.esprit.util.Navigator;


public class MainFX extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        Navigator nav = new Navigator(stage);
        nav.goToPage("/AfficherReclamationUser.fxml");
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}