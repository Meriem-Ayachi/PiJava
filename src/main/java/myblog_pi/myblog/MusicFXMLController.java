/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myblog_pi.myblog;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

import java.io.File;
import javafx.scene.control.Button;
/**
 * FXML Controller class
 *
 * @author ghada
 */
public class MusicFXMLController {

    @FXML
    private Label chooseMusic;

    private MediaPlayer mediaPlayer;
    @FXML
    private Button play;
    @FXML
    private Button pause;
    @FXML
    private Button stop;

    @FXML
    void chooseMusic(MouseEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select your music");
        File file = chooser.showOpenDialog(null);
        if(file != null){
            String selectedFile = file.toURI().toString();
            Media media = new Media(selectedFile);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(() -> chooseMusic.setText(file.getName()));
        }
    }

    @FXML
    void pause(MouseEvent event) {
        mediaPlayer.pause();
    }

    @FXML
    void play(MouseEvent event) {
        mediaPlayer.play();
    }

    @FXML
    void stop(MouseEvent event) {
        mediaPlayer.stop();
    }

}