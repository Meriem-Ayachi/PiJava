package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import tn.esprit.models.Reservation;
import tn.esprit.models.hotel;
import tn.esprit.services.Reservationservices;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.*;

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

    public void fillReservationInputs(String depart, String retour, String classe, String nbrPsersonne, String dateDepart, String dateArrive){
        nbrPersonnesTextField.setText(nbrPsersonne);
        classeComboBox.setValue(classe);
        destinationDepartComboBox.setValue(depart);
        destinationRetourComboBox.setValue(retour);
        
        LocalDate dateDepartTime = LocalDate.parse(dateDepart);
        LocalDate dateArriveTime = LocalDate.parse(dateArrive);
        dateDepartPicker.setValue(dateDepartTime);
        dateRetourPicker.setValue(dateArriveTime);
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
                "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Italy", "Jamaica",
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
    private void showCountryInfo(ActionEvent event) {
        // Get the selected country from the ComboBox
        String selectedCountry = destinationRetourComboBox.getValue();

        // Define a map to associate country names with their country codes
        Map<String, String> countryCodes = new HashMap<>();
        countryCodes.put("France", "FR");
        countryCodes.put("Spain", "ES");
        countryCodes.put("Italy", "IT");
        countryCodes.put("Tunisia","Tn");
        countryCodes.put("Germany", "DE");
        countryCodes.put("United Kingdom", "GB");
        countryCodes.put("United States", "US");
        countryCodes.put("Canada", "CA");
        countryCodes.put("Australia", "AU");
        countryCodes.put("Japan", "JP");
        countryCodes.put("China", "CN");
        countryCodes.put("India", "IN");
        countryCodes.put("Brazil", "BR");
        countryCodes.put("Mexico", "MX");
        countryCodes.put("Argentina", "AR");
        countryCodes.put("South Africa", "ZA");
        countryCodes.put("Russia", "RU");
        countryCodes.put("Sweden", "SE");
        countryCodes.put("Norway", "NO");
        countryCodes.put("Finland", "FI");
        countryCodes.put("Denmark", "DK");
        countryCodes.put("Netherlands", "NL");
        countryCodes.put("Belgium", "BE");
        countryCodes.put("Switzerland", "CH");
        countryCodes.put("Austria", "AT");
        countryCodes.put("Greece", "GR");
        countryCodes.put("Turkey", "TR");
        countryCodes.put("Egypt", "EG");
        countryCodes.put("Saudi Arabia", "SA");
        countryCodes.put("United Arab Emirates", "AE");
        countryCodes.put("Afrique Du Sud", "ZA");
        // Add more countries and their codes as needed

        // Get the country code for the selected country
        String countryCode = countryCodes.get(selectedCountry);

        // Check if the country code is not null
        if (countryCode != null) {
            // Construct the URL for fetching country information
            String apiUrl = "https://restcountries.com/v3/alpha/" + countryCode.toLowerCase();

            // Fetch country information from the API
            JSONObject countryInfo = fetchCountryInfo(apiUrl);

            // Check if countryInfo is not null and contains necessary data
            if (countryInfo != null && countryInfo.has("name")) {
                // Construct the HTML content with dynamic data
                String googleMapsUrl = countryInfo.getJSONObject("maps").getString("googleMaps");

                String htmlContent = "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    <title>Country Information</title>\n" +
                        "    <style>\n" +
                        "        /* Paste your CSS styles here */\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<main>\n" +
                        "    <h1>Country Information</h1>\n" +
                        "    <h2>Names:</h2>\n" +
                        "    <p>Common Name: " + countryInfo.getJSONObject("name").getString("common") + "</p>\n" +


                        "    <h2>Flag and Maps:</h2>\n" +
                        "    <img src=\"" + countryInfo.getJSONArray("flags").getString(0) + "\" alt=\"Country Flag\">\n" +
                        "    <p>Google Maps: <a href=\"" + countryInfo.getJSONObject("maps").getString("googleMaps") + "\" target=\"_blank\">Link</a></p>\n" +
                        "    <p>OpenStreet Maps: <a href=\"" + countryInfo.getJSONObject("maps").getString("openStreetMaps") + "\" target=\"_blank\">Link</a></p>\n" +
                      

                        "</main>\n" +
                        "</body>\n" +
                        "</html>";

                // Create a WebView to display the country information
                WebView webView = new WebView();
                WebEngine webEngine = webView.getEngine();

                // Load HTML content into the WebView
                webEngine.loadContent(htmlContent);

                // Create a new stage to display the WebView
                Stage stage = new Stage();
                stage.setTitle("Country Information: " + selectedCountry);
                stage.setScene(new Scene(webView, 960, 540));
                stage.show();
            } else {
                // Handle the case where country information is not available
                System.out.println("Country information not available for: " + selectedCountry);
            }
        } else {
            // Handle the case where the country code is null (selected country not found)
            System.out.println("Country code not found for selected country: " + selectedCountry);
        }
    }

    // Method to fetch country information from the API
    private JSONObject fetchCountryInfo(String apiUrl) {
        try {
            // Send HTTP GET request to the API URL
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the JSON response array
            JSONArray jsonArray = new JSONArray(response.body());

            // Extract the JSON object from the array
            JSONObject countryInfo = jsonArray.getJSONObject(0);

            // Print the extracted country information
            System.out.println("Country Information: " + countryInfo.toString());

            // Return the country information
            return countryInfo;
        } catch (Exception e) {
            // Handle exceptions (e.g., network errors, JSON parsing errors)
            e.printStackTrace();
            return null;
        }
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