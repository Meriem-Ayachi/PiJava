/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.myblog;


import tn.esprit.Model.Publication;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;

import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author HP-Gaming
 */
public class PostFXMLController  {
   
    @FXML
    private Label dateC;
    @FXML
    private AnchorPane menupane;
    
    @FXML
    private Label description;

    @FXML
    private Label postTitle;
    
    @FXML
    private Label idP;
    @FXML
    private ImageView postimage;
    
    @FXML
    private Label jador;
    @FXML
    private Label comment;

    
 public void setData(Publication postdata) {
    idP.setText(String.valueOf(postdata.getId()));
    postTitle.setText(postdata.getTitle());
    description.setText(postdata.getContent());
    // Charger l'image à partir du chemin d'accès stocké dans postdata.getImage()
    if (postdata.getImage() != null && !postdata.getImage().isEmpty()) {
        try {
            FileInputStream inputStream = new FileInputStream(postdata.getImage());
            Image image = new Image(inputStream);
            postimage.setImage(image);
            postdata.setImageObject(image); // Mettre à jour la propriété imageProperty
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // Gérer l'erreur si l'image n'est pas trouvée
        }
      } else {
        // Gérer le cas où aucun chemin d'accès d'image n'est spécifié
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDate = postdata.getCreatedAt().format(formatter);
    dateC.setText(formattedDate); 
} 
}
