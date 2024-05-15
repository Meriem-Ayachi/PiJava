package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import tn.esprit.models.Promo_Vols;
import tn.esprit.models.Vols;
import tn.esprit.services.Promo_VolsService;
import tn.esprit.services.VolService;

public class PromoDetailsController {

    @FXML
    private Label promoInfoLabel;

    @FXML
    private Label volsInfoLabel;


    private final VolService volService = new VolService(){}; // Assuming you have a service for managing flights


    public void initData(Promo_Vols promoVols) {
        // Populate labels with promo and vol info
        promoInfoLabel.setText("Promo ID: " + promoVols.getId() + "\nStart Date: " + promoVols.getDate_debut_promo().toString() +
                "\nEnd Date: " + promoVols.getDate_fin_promo().toString() + "\nPourcentage: " + Double.toString(promoVols.getPourcentage()) + "%");

        Vols correspondingFlight = getCorrespondingFlight(promoVols.getId());

        // Calculate new price after discount
        double originalPrice = correspondingFlight.getPrix();
        double discountPercentage = promoVols.getPourcentage();
        double newPrice = originalPrice - (originalPrice * (discountPercentage / 100));

        volsInfoLabel.setText("Destination: " + correspondingFlight.getDestination() + "\nPoint Depart: " + correspondingFlight.getPointdepart() +
                "\nDate Arrivee: " + correspondingFlight.getDatearrive() + "\nDate Depart: " + correspondingFlight.getDatedepart() +
                "\nPrix: " + correspondingFlight.getPrix() + "\nNbrescale: " + correspondingFlight.getNbrescale() +
                "\nNbrplace: " + correspondingFlight.getNbrplace() + "\nDuree: " + correspondingFlight.getDuree() +
                "\nClasse: " + correspondingFlight.getClasse() + "\nDiscount Price: " + String.format("%.2f", newPrice));
    }

    private Vols getCorrespondingFlight(int promoId) {
        return volService.getOne(promoId); // Assuming the promo ID corresponds to vol ID
    }











}
