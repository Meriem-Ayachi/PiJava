//package tn.esprit.controllers;
//
//import javafx.fxml.FXML;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.web.WebEngine;
//import javafx.scene.web.WebView;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//
//public class reservationMap {
//
//    @FXML
//    private WebView mapView;
//
//    private String destination;
//
//    public void setDestination(String destination) {
//        this.destination = destination;
//    }
//
//    public void initialize() {
//        // Initialiser la carte avec la destination sélectionnée
//        WebEngine webEngine = mapView.getEngine();
//        webEngine.load("https://www.google.com/maps/search/" + destination);
//    }
//
//    public void showMapModal(Parent root) {
//        // Créer une nouvelle fenêtre modale pour afficher la carte
//        Stage stage = new Stage();
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setTitle("Carte - " + destination);
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.showAndWait();
//    }
//
//    public void showErrorAlert(String title, String headerText, String contentText) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle(title);
//        alert.setHeaderText(headerText);
//        alert.setContentText(contentText);
//        alert.showAndWait();
//    }
//}
