package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.esprit.models.Reservation;
import tn.esprit.models.hotel;
import tn.esprit.services.Hotelservices;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.List;

public class hotelAdd {
    @FXML
    private TextField Nomf ;
    @FXML
    private ListView<hotel> listView;


    @FXML
    private TextField nbetoilesf;
    @FXML
    private TextField emplacementf;
    @FXML
    private TextField avisf ;



    private final Hotelservices hotelservice = new Hotelservices() {
        @Override
        public void generatePDF(List<Reservation> reservations, String filePath) {

        }

        @Override
        public void delete(hotel hotel) {

        }
    };
    @FXML
    public void add(ActionEvent actionEvent) {
        hotel hotel1 = new hotel();

        hotel1.setNom(Nomf.getText());
        hotel1.setEmplacement(emplacementf.getText());
        hotel1.setAvis(avisf.getText());

        try {
            int nbetoiles = Integer.parseInt(nbetoilesf.getText());
            // Si la conversion réussit, vous pouvez définir le nombre d'étoiles
            hotel1.setNbretoile(String.valueOf(nbetoiles));
        } catch (NumberFormatException e) {
            System.out.println("La valeur du champ nbetoilesf n'est pas un nombre valide.");
            // Vous pouvez afficher un message d'erreur à l'utilisateur ou prendre une autre action appropriée
            // Par exemple, afficher une boîte de dialogue d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur de saisie");
            alert.setContentText("Veuillez entrer un nombre valide pour le nombre d'étoiles.");
            alert.showAndWait();
            // Sortir de la méthode pour éviter d'ajouter l'hôtel avec une valeur invalide pour le nombre d'étoiles
            return;
        }

        hotelservice.add(hotel1);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Hotel ajouté avec succès!");
        alert.showAndWait();

        // Effacer les champs après l'ajout
        Nomf.clear();
        emplacementf.clear();
        nbetoilesf.clear();
        avisf.clear();
    }
    @FXML
    public void goToHotelList(ActionEvent event) {
        Parent root =null;
        try {
            root = FXMLLoader.load(getClass().getResource("/hotelList.fxml"));
          //  FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/hotelList.fxml"));
           // Parent root = loader.load();

            //Scene scene = new Scene(root);

            // Récupérer la scène actuelle
           // Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Afficher la nouvelle scène
            //stage.setScene(scene);
           // stage.show();
        } catch (IOException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
}
Nomf.getScene().setRoot(root);
}

    @FXML
    public void deleteHotel(ActionEvent actionEvent) {
        hotel selectedHotel = listView.getSelectionModel().getSelectedItem();
        if (selectedHotel != null) {
            // Call the delete method of your Hotelservices class to delete the selected hotel
            hotelservice.delete(selectedHotel);

            // Remove the deleted hotel from the list view
            listView.getItems().remove(selectedHotel);
        } else {
            // If no hotel is selected, display an error message or take appropriate action
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun hotel selectionne");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez selectionner un hotel a supprimer.");
            alert.showAndWait();
        }
    }

}