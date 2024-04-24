package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Vols;
import tn.esprit.services.VolService;
import tn.esprit.models.Promo_Vols;
import tn.esprit.services.Promo_VolsService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class AddPromoController {

    @FXML
    private ComboBox<Integer> flightIdComboBox;

    @FXML
    private TextField pourcentageField;

    @FXML
    private DatePicker dateDebutField;

    @FXML
    private DatePicker dateFinField;

    private final VolService volService = new VolService(){};
    private final Promo_VolsService promoService = new Promo_VolsService();

    @FXML
    private void initialize() {
        // Populate the ComboBox with flight IDs
        List<Vols> flights = volService.getAll();
        for (Vols flight : flights) {
            flightIdComboBox.getItems().add(flight.getId());
        }
    }

    @FXML
    private void addPromo() {
        try {
            // Parse input fields
            int flightId = flightIdComboBox.getValue();
            double pourcentage = Double.parseDouble(pourcentageField.getText());
            LocalDate dateDebut = dateDebutField.getValue();
            LocalDate dateFin = dateFinField.getValue();

            // Create Promo_Vols object
            Promo_Vols promo = new Promo_Vols(flightId, pourcentage, Date.valueOf(dateDebut), Date.valueOf(dateFin));

            // Add promo to database
            promoService.add(promo);

            // Close the window
            closeAddPromoPage();
        } catch (NumberFormatException e) {
            // Handle invalid input (e.g., non-numeric input for percentage)
            // You can display an error message to the user
            e.printStackTrace();
        }
    }

    private void closeAddPromoPage() {
        // Close the add promo window
        Stage stage = (Stage) flightIdComboBox.getScene().getWindow();
        stage.close();
    }
}
