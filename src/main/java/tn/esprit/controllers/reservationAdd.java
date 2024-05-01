package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.esprit.models.Reservation;
import tn.esprit.models.hotel;
import tn.esprit.services.Reservationservices;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class reservationAdd implements Initializable {

    @FXML
    private DatePicker dateDepartPicker;
    @FXML
    private DatePicker dateRetourPicker;
    @FXML
    private ComboBox<String> classeComboBox;
    @FXML
    private ComboBox<String> destinationDepartComboBox;
    @FXML
    private ComboBox<String> destinationRetourComboBox;
    @FXML
    private TextField nbrPersonnesTextField;

    private final Reservationservices reservationService = new Reservationservices() {
        @Override
        public void generatePDF(List<Reservation> reservations, String filePath) {

        }

        @Override
        public List<hotel> rechercherParNom(String nom) {
            return null;
        }

        @Override
        public void delete(Reservation reservation) {

        }

        @Override
        public void delete(int id) {
            // Implémentation de la méthode delete
        }
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDestinationComboBoxes();
    }

    private void initializeDestinationComboBoxes() {
        // Vous pouvez charger les destinations disponibles à partir de votre source de données
        // ou les définir manuellement ici
        List<String> destinations = Arrays.asList("Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina",
                "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados",
                "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana",
                "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon",
                "Canada", "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo",
                "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica",
                "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia",
                "Eswatini", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany",
                "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras",
                "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica",
                "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos",
                "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg",
                "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania",
                "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco",
                "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua",
                "Niger", "Nigeria", "North Korea", "North Macedonia", "Norway", "Oman", "Pakistan", "Palau",
                "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal",
                "Qatar", "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia",
                "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia",
                "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia",
                "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka",
                "Sudan", "Suriname", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania",
                "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey",
                "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom",
                "United States of America", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela",
                "Vietnam", "Yemen", "Zambia", "Zimbabwe");

        // Remplissage des ComboBox avec les destinations disponibles
        destinationDepartComboBox.setItems(FXCollections.observableArrayList(destinations));
        destinationRetourComboBox.setItems(FXCollections.observableArrayList(destinations));
    }
    @FXML
    public void openReservationList(ActionEvent event) {
        try {
            // Charger le fichier FXML de la liste des hôtels
            Parent root = FXMLLoader.load(getClass().getResource("/Fxml/reservationList.fxml"));
            // Modifier la racine de la scène actuelle pour afficher la liste des hôtels
            nbrPersonnesTextField.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    public void add(javafx.event.ActionEvent actionEvent) {
        Reservation reservation = new Reservation();

        LocalDate dateDepart = dateDepartPicker.getValue();
        LocalDate dateRetour = dateRetourPicker.getValue();

        if (dateDepart == null || dateDepart.isBefore(LocalDate.now())) {
            showErrorAlert("Erreur", "Date de départ invalide", "La date de départ ne peut pas être antérieure à aujourd'hui.");
            return;
        }

        if (dateRetour != null && dateRetour.isBefore(dateDepart)) {
            showErrorAlert("Erreur", "Date de retour invalide", "La date de retour doit être postérieure à la date de départ.");
            return;
        }

        reservation.setDatedepart(dateDepart.toString());
        reservation.setDateretour(dateRetour == null ? null : dateRetour.toString());
        reservation.setClasse(classeComboBox.getValue());
        reservation.setDestinationdepart(destinationDepartComboBox.getValue());
        reservation.setDestinationretour(destinationRetourComboBox.getValue());

        try {
            int nbrPersonnes = Integer.parseInt(nbrPersonnesTextField.getText());
            if (nbrPersonnes < 0) {
                showErrorAlert("Erreur", "Erreur de saisie", "Le nombre de personnes ne peut pas être négatif.");
                return;
            }
            reservation.setNbrdepersonne(nbrPersonnes);
        } catch (NumberFormatException e) {
            showErrorAlert("Erreur", "Erreur de saisie", "Veuillez entrer un nombre valide pour le nombre de personnes.");
            return;
        }

        reservationService.add(reservation);

        showSuccessAlert("Succès", null, "Réservation ajoutée avec succès!");

        clearFields();
    }

    private void clearFields() {
        dateDepartPicker.setValue(null);
        dateRetourPicker.setValue(null);
        classeComboBox.getSelectionModel().clearSelection();
        destinationDepartComboBox.getSelectionModel().clearSelection();
        destinationRetourComboBox.getSelectionModel().clearSelection();
        nbrPersonnesTextField.clear();
    }

    private void showErrorAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private void showSuccessAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }


}
