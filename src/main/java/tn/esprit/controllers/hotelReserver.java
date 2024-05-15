package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.hotel;
import tn.esprit.models.hotelReserverModel;
import tn.esprit.models.session;
import tn.esprit.services.Hotelservices;

public class hotelReserver {

    @FXML
    private ComboBox<String> classeComboBox;

    @FXML
    private DatePicker dateDepartPicker;

    @FXML
    private DatePicker dateRetourPicker;

    @FXML
    private TextField nbrPersonnesTextField;

    private hotel currentHotel;
    public void initialize(hotel h){
        currentHotel = h;
    }
    
    @FXML
    void sauvegarderInformations(ActionEvent event) {
        int hotelId = currentHotel.getId();
        int userId = session.id_utilisateur;
        String sejour = classeComboBox.getValue();
        String dateDepart = dateDepartPicker.getValue().toString();
        String dateRetour = dateRetourPicker.getValue().toString();
        int nbrPersonne = Integer.parseInt(nbrPersonnesTextField.getText());
        hotelReserverModel h = new hotelReserverModel(sejour, dateDepart, dateRetour, nbrPersonne, userId, hotelId);

        Hotelservices hs = new Hotelservices();
        hs.reserveHotel(h);
        //redirect
        Stage stage = (Stage) dateRetourPicker.getScene().getWindow();
        stage.close();
    }

}
