package Controllers;

import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import tn.esprit.models.Vols;
import tn.esprit.services.VolService;
import javafx.fxml.Initializable;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.*;

import javafx.scene.Scene;

import javafx.util.Callback;
import java.net.URL;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.models.Vols;
import tn.esprit.services.VolService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tn.esprit.models.Vols;


public class Showvolforuser implements Initializable {

    @FXML
    private ListView<Vols> listview;



    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<String> classComboBox;

    @FXML
    private TextField minPriceField;

    @FXML
    private TextField maxPriceField;

    @FXML
    private ComboBox<String> destinationComboBox;

    @FXML
    private ComboBox<String> countryComboBox;




    private final VolService vs = new VolService() {

    };

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<String> destinations = vs.getAllDestinations();
        destinationComboBox.setItems(FXCollections.observableArrayList(destinations));
        List<Vols> vols = vs.getAll();
        listview.getItems().addAll(vols);

        listview.setCellFactory(param -> new ListCell<Vols>() {
            @Override
            protected void updateItem(Vols item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Load the images
                    String[] imagePaths = {
                            "file:C:/Users/melek/Downloads/tunisair-vector-logo.png",
                            "file:C:/Users/melek/Downloads/541_qatarairways.jpg",
                            "file:C:/Users/melek/Downloads/west-java-indonesia-oktober-21-600nw-2378341355.jpg"
                    };

                    // Select a random image path
                    Random random = new Random();
                    String randomImagePath = imagePaths[random.nextInt(imagePaths.length)];

                    // Load the image
                    Image image = new Image(randomImagePath);

                    // Create the ImageView with the loaded image
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(100); // Set width as per your requirement
                    imageView.setPreserveRatio(true); // Preserve aspect ratio

                    // Create labels for destination, departure date, and arrival date
                    Label destinationLabel = new Label("Destination: " + item.getDestination());
                    Label departureLabel = new Label("Departure: " + item.getDatedepart().toString());
                    Label arrivalLabel = new Label("Arrival: " + item.getDatearrive().toString());

                    // Create label for price
                    Label priceLabel = new Label("Price: TND" + item.getPrix());
                    // Set styles for labels
                    destinationLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");
                    departureLabel.setStyle("-fx-font-size: 12pt;");
                    arrivalLabel.setStyle("-fx-font-size: 12pt;");
                    priceLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt; -fx-text-fill: #518c65;");

                    // Create "Show More" button

                    Button showMoreButton = new Button("Show More");
                    showMoreButton.setStyle("-fx-background-color: transparent;-fx-text-fill: #233b9a;-fx-font-size: 14px;-fx-padding: 0 5px;");

                    // Add action handler for the show more button
                    showMoreButton.setOnAction(event -> showMoreAction(item));

                    // Create a VBox to hold all labels vertically
                    VBox labelsVBox = new VBox();
                    labelsVBox.getChildren().addAll(destinationLabel, departureLabel, arrivalLabel);
                    labelsVBox.setAlignment(Pos.CENTER_LEFT); // Align children to the left
                    labelsVBox.setSpacing(5); // Adjust spacing between elements

                    // Create a VBox to hold price label and "Show More" button
                    VBox priceButtonVBox = new VBox();
                    priceButtonVBox.getChildren().addAll(priceLabel, showMoreButton);
                    priceButtonVBox.setAlignment(Pos.CENTER_RIGHT); // Align to the right
                    priceButtonVBox.setSpacing(5); // Adjust spacing between elements

                    // Create an AnchorPane to hold all elements
                    AnchorPane anchorPane = new AnchorPane();

                    // Set image position
                    AnchorPane.setTopAnchor(imageView, 5.0);
                    AnchorPane.setLeftAnchor(imageView, 5.0);

                    // Set labels position
                    AnchorPane.setTopAnchor(labelsVBox, 5.0);
                    AnchorPane.setLeftAnchor(labelsVBox, 120.0); // Adjust left offset as needed

                    // Set price and button position
                    AnchorPane.setTopAnchor(priceButtonVBox, 5.0);
                    AnchorPane.setRightAnchor(priceButtonVBox, 5.0);

                    // Add all elements to the AnchorPane
                    anchorPane.getChildren().addAll(imageView, labelsVBox, priceButtonVBox);

                    // Set the layout as the graphic for the ListCell
                    setGraphic(anchorPane);
                }
            }
        });
    }

    private void showMoreAction(Vols vol) {
        // Load the detailed page for the selected flight
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailedPageforuser.fxml"));
        try {
            Parent root = loader.load();
            // Pass the selected flight to the detailed page controller
            DetailedPageforuserController controller = loader.getController();
            controller.initData(vol);
            
            Stage currentStage = (Stage) endDatePicker.getScene().getWindow();
            controller.setPrimaryStage(currentStage);

            // Display the detailed page
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            // Load the Addvol.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Addvol.fxml"));
            Parent root = loader.load();

            // Get the stage of the current scene
            Stage stage = (Stage) listview.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void mettreenpromo(ActionEvent event) {
        try {
            // Load the AddPromo.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddPromo.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage promoStage = new Stage();

            // Set the scene with the content from AddPromo.fxml
            Scene scene = new Scene(root);
            promoStage.setScene(scene);

            // Show the new stage
            promoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void navigateToPromoVolsList(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PromoVolsListforuser.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) listview.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }




    @FXML
    private void handleFilterChange(ActionEvent event) {
        // Retrieve filter criteria
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String selectedClass = classComboBox.getValue();
        double minPrice = 0;
        double maxPrice = 0;

        // Check if minPriceField and maxPriceField are not empty before parsing
        if (!minPriceField.getText().isEmpty()) {
            minPrice = Double.parseDouble(minPriceField.getText());
        }
        if (!maxPriceField.getText().isEmpty()) {
            maxPrice = Double.parseDouble(maxPriceField.getText());
        }

        String destination = destinationComboBox.getValue();

        // Filter the flight list based on criteria
        List<Vols> filteredFlights = filterFlights(startDate, endDate, selectedClass, minPrice, maxPrice, destination);

        // Update the ListView with filtered flights
        listview.getItems().clear();
        listview.getItems().addAll(filteredFlights);
    }

    // Method to filter flights based on criteria
    private List<Vols> filterFlights(LocalDate startDate, LocalDate endDate, String selectedClass, double minPrice, double maxPrice, String destination) {
        List<Vols> allFlights = vs.getAll();
        List<Vols> filteredFlights = new ArrayList<>();

        for (Vols flight : allFlights) {
            // Apply filter conditions

            LocalDate departureDate = LocalDate.parse(flight.getDatedepart());

            // Check if the flight meets all other criteria
            if ((startDate == null || departureDate.isAfter(startDate)) &&
                    (endDate == null || departureDate.isBefore(endDate)) &&
                    (selectedClass == null || flight.getClasse().equals(selectedClass)) &&
                    (destination == null || flight.getDestination().equals(destination))) {

                // Check if the flight's price is within the specified range (if provided)
                if ((minPrice == 0 || flight.getPrix() >= minPrice) &&
                        (maxPrice == 0 || flight.getPrix() <= maxPrice)) {
                    // Flight meets all filter criteria
                    filteredFlights.add(flight);
                }
            }
        }

        return filteredFlights;
    }




    @FXML
    private void showCountryInfo(ActionEvent event) {
        // Get the selected country from the ComboBox
        String selectedCountry = countryComboBox.getValue();

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
                        "    <p>Official Name: " + countryInfo.getJSONObject("name").getString("official") + "</p>\n" +

                        "    <h2>Top-Level Domain:</h2>\n" +
                        "    <p>" + countryInfo.getJSONArray("tld").getString(0) + "</p>\n" +
                        "    <h2>Country Codes:</h2>\n" +
                        "    <p>Alpha-2 Code: " + countryInfo.getString("cca2") + "</p>\n" +
                        "    <p>Numeric Code: " + countryInfo.getString("ccn3") + "</p>\n" +
                        "    <p>Alpha-3 Code: " + countryInfo.getString("cca3") + "</p>\n" +
                        "    <p>IOC Code: " + countryInfo.getString("cioc") + "</p>\n" +
                        "    <h2>Independence and Status:</h2>\n" +
                        "    <p>Independent: " + (countryInfo.getBoolean("independent") ? "Yes" : "No") + "</p>\n" +
                        "    <p>Status: " + countryInfo.getString("status") + "</p>\n" +
                        "    <p>UN Member: " + (countryInfo.getBoolean("unMember") ? "Yes" : "No") + "</p>\n" +
                        "    <h2>Currency</h2>\n" +
                        "    <p><strong>Currency:</strong> " + countryInfo.getJSONObject("currencies").keys().next() + "</p>\n" +
                        "    <h2>International Dialing:</h2>\n" +
                        "    <p>Root: " + countryInfo.getJSONObject("idd").getString("root") + "</p>\n" +
                        "    <p>Suffixes: " + countryInfo.getJSONObject("idd").getJSONArray("suffixes").join(", ") + "</p>\n" +
                        "    <h2>Capital:</h2>\n" +
                        "    <p>" + countryInfo.getJSONArray("capital").getString(0) + "</p>\n" +
                        "    <h2>Alternate Spellings:</h2>\n" +
                        "    <p>" + countryInfo.getJSONArray("altSpellings").join(", ") + "</p>\n" +
                        "    <h2>Languages:</h2>\n" +
                        "    <ul>\n";

// Add languages dynamically
                JSONArray languages = countryInfo.getJSONObject("languages").names();
                if (languages != null) {
                    for (int i = 0; i < languages.length(); i++) {
                        String languageCode = languages.getString(i);
                        String languageName = countryInfo.getJSONObject("languages").getString(languageCode);
                        htmlContent += "        <li>" + languageCode + ": " + languageName + "</li>\n";
                    }
                }

                htmlContent += "    </ul>\n" +
                        "    <h2>Geographical Information:</h2>\n" +
                        "    <p>Region: " + countryInfo.getString("region") + "</p>\n" +
                        "    <p>Subregion: " + countryInfo.getString("subregion") + "</p>\n" +
                        "    <p>Latlng: " + countryInfo.getJSONArray("latlng").join(", ") + "</p>\n" +
                        "    <p>Landlocked: " + (countryInfo.getBoolean("landlocked") ? "Yes" : "No") + "</p>\n" +
                        "    <p>Borders: " + countryInfo.getJSONArray("borders").join(", ") + "</p>\n" +
                        "    <p>Area: " + countryInfo.getDouble("area") + " square kilometers</p>\n" +
                        "    <h2>Flag and Maps:</h2>\n" +
                        "    <img src=\"" + countryInfo.getJSONArray("flags").getString(0) + "\" alt=\"Country Flag\">\n" +
                        "    <p>Google Maps: <a href=\"" + countryInfo.getJSONObject("maps").getString("googleMaps") + "\" target=\"_blank\">Link</a></p>\n" +
                        "    <p>OpenStreet Maps: <a href=\"" + countryInfo.getJSONObject("maps").getString("openStreetMaps") + "\" target=\"_blank\">Link</a></p>\n" +
                        "    <h2>Population and Other Details:</h2>\n" +
                        "    <p>Population: " + countryInfo.getLong("population") + "</p>\n" +
                        "    <p>FIFA Code: " + countryInfo.getString("fifa") + "</p>\n" +
                        "    <p>Timezones: " + countryInfo.getJSONArray("timezones").join(", ") + "</p>\n" +
                        "    <p>Continents: " + countryInfo.getJSONArray("continents").join(", ") + "</p>\n" +


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





}