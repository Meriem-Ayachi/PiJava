package tn.esprit.controllers;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntriesFilter;
import io.github.cdimascio.dotenv.DotenvEntry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tn.esprit.models.Reclamation;
import tn.esprit.models.User;
import tn.esprit.models.session;
import tn.esprit.services.ReclamationService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import tn.esprit.services.UserService;
import tn.esprit.util.Navigator;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;


public class ModifierReclamationAdmin {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String ACCOUNT_SID = dotenv.get("ACCOUNT_SID");
    private static final String AUTH_TOKEN = dotenv.get("AUTH_TOKEN");

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    @FXML
    private CheckBox estTraiteCB;

    private Reclamation currentRec ;
    public void initialize(Reclamation reclamation) {
        currentRec = reclamation;
        estTraiteCB.setSelected(reclamation.getEst_traite() == 1 ? true : false);
    }

    @FXML
    void modifierReclamation(ActionEvent event) {

        ReclamationService reclamationService = new ReclamationService();
        Reclamation reclamation = currentRec;
        reclamation.setEst_traite(estTraiteCB.isSelected()? (byte) 1 : (byte) 0);
        System.out.println(reclamation);
        {
            reclamationService.update(reclamation);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("Réclamation modifiée avec succés");
            alert.showAndWait();
        }
        if (estTraiteCB.isSelected() == true ) {

            UserService us = new UserService();
            User user = us.getOne(reclamation.getUser_id());

            String numeroTelephone = "+216" + user.getNum_tel();

            Message message = Message.creator(
                    new PhoneNumber(numeroTelephone),
                    new PhoneNumber("+14013714143"),
                    "Votre réclamation a été modifiée avec succès."
            ).create();
            System.out.println("Message SID: " + message.getSid());
        }
        Navigator nav =new Navigator();
        nav.goToPage_WithEvent("/AfficherReclamationAdmin.fxml" , event);
    }

    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void back(ActionEvent event) {
        Navigator nav =new Navigator();
        nav.goToPage_WithEvent("/AfficherReclamationAdmin.fxml" , event);
    }

}

