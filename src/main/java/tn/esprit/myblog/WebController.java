/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.myblog;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author ghada
 */
public class WebController implements Initializable {

   @FXML
    private WebView viewweb;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final WebEngine web=viewweb.getEngine();
       String urlweb="http://google.com";
       web.load(urlweb);
    }    

    @FXML
    private void fb(ActionEvent event) {
         final WebEngine web=viewweb.getEngine();
       String urlweb="https://www.facebook.com/";
       web.load(urlweb);
    }

    @FXML
    private void yt(ActionEvent event) {
         final WebEngine web=viewweb.getEngine();
       String urlweb="https://www.youtube.com/";
       web.load(urlweb);
    }

    @FXML
    private void w(ActionEvent event) {
       final WebEngine web=viewweb.getEngine();
       String urlweb="https://www.instagram.com/";
       web.load(urlweb);
    }
    
}
