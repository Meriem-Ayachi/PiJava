package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;


import javafx.stage.Stage;
import java.io.*;

public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger le fichier FXML
            Parent root = FXMLLoader.load(getClass().getResource("/Fxml/hotelAdd.fxml"));

            // Créer la scène
            Scene scene = new Scene(root);

            // Ajuster la taille de la scène
            primaryStage.setWidth(800); // Définir la largeur souhaitée
            primaryStage.setHeight(600); // Définir la hauteur souhaitée

            // Lire la musique
            //playMusic();

            // Définir le titre de la fenêtre principale
            primaryStage.setTitle("Tech-Voyage");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     //Méthode pour jouer de la musique
//    private void playMusic() {
//        try {
//            // Obtenir le chemin absolu du fichier audio
//            String musicFilePath = new File("music.mp3").getAbsolutePath();
//
//            // Créer un objet Media à partir du chemin absolu du fichier audio
//            Media sound = new Media(new File(musicFilePath).toURI().toString());
//
//            // Créer un lecteur de média et jouer le son
//            MediaPlayer mediaPlayer = new MediaPlayer(sound);
//            mediaPlayer.play();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        launch(args);
    }
}
