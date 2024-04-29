package tn.esprit.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.models.Reclamation;
import tn.esprit.models.User;
import tn.esprit.models.session;
import tn.esprit.services.ReclamationService;
import tn.esprit.services.UserService;
import tn.esprit.util.Navigator;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class AfficherReclamationAdmin {

    @FXML
    private TableColumn<Reclamation, Timestamp> DateSoumissionCol;

    @FXML
    private TableColumn<Reclamation, String> DescriptionCol;

    @FXML
    private TableColumn<Reclamation, Byte> EstTraiteCol;

    @FXML
    private TableColumn<Reclamation, String> NomPrenomCol;

    @FXML
    private TableColumn<Reclamation, String> SujetCol;

    @FXML
    private TableView<Reclamation> tableview;

    @FXML
    private ComboBox<String> estTraiteCB;


    private final ReclamationService rs = new ReclamationService();
    UserService us = new UserService();

    private Reclamation selectedReclamation;


    @FXML
    void initialize(){

        ObservableList<String> typeList = FXCollections.observableArrayList(
                "Toutes"
                , "Traitées"
                , "Non traitées"
        );
        estTraiteCB.setItems(typeList);
        estTraiteCB.setValue("Toutes");

        try{

            List<Reclamation> reclamations = rs.getAll();
            ObservableList<Reclamation> observableList = FXCollections.observableList(reclamations);
            tableview.setItems(observableList);
            DateSoumissionCol.setCellValueFactory(new PropertyValueFactory<>("datesoummission"));
            DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            EstTraiteCol.setCellValueFactory(new PropertyValueFactory<>("est_traite"));
            NomPrenomCol.setCellValueFactory(cellData -> {
                UserService us = new UserService();
                Reclamation reclamation = cellData.getValue();
                if (reclamation.getUser_id() == 0 )
                {
                    return new SimpleStringProperty("");
                }
                User user = us.getOne(reclamation.getUser_id()); // Supposons que la méthode getUserId() récupère l'ID de l'utilisateur
                String nom = user.getNom();
                String prenom = user.getPrenom();
                return new SimpleStringProperty(nom + " " + prenom);
            });
            SujetCol.setCellValueFactory(new PropertyValueFactory<>("sujet"));

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }

    @FXML
    void Refresh() {
        tableview.setItems(FXCollections.observableArrayList(rs.getAll()));

    }

    @FXML
    void modifierBTN(ActionEvent event) {

        if (selectedReclamation != null) {
            Reclamation reclamation = rs.getOne(selectedReclamation.getId());

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReclamationAdmin.fxml"));
                Parent root = loader.load();

                // pass param
                ModifierReclamationAdmin controller = loader.getController();
                controller.initialize(reclamation);

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            showError("Vous devez sélectionner une réclamation");
        }

    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void supprimerBTN(ActionEvent event) {

        if (selectedReclamation != null) {
            rs.delete(selectedReclamation.getId());
            Refresh();
            selectedReclamation = null;
        }else{
            showError("Vous devez sélectionner une réclamation");
        }
    }
    @FXML
    void onTableRowClicked(MouseEvent event) throws IOException {
        selectedReclamation = tableview.getSelectionModel().getSelectedItem();

        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {

            if (selectedReclamation != null) {
                // Charger le fichier FXML de l'interface des détails de la réclamation
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsReclamationAdmin.fxml"));
                AnchorPane detailsReclamationPane = loader.load();

                // Obtenir le contrôleur associé à l'interface
                DetailsReclamationAdmin controller = loader.getController();

                // Appeler la méthode pour initialiser les détails de la réclamation
                controller.initializeDetails(selectedReclamation);

                // Afficher l'interface dans une nouvelle fenêtre
                Stage stage = new Stage();
                stage.setScene(new Scene(detailsReclamationPane));
                stage.show();
            }
        }
    }




    String nom, prenom;
    Byte estTraite;
    void recherche ()
    {
        List <Reclamation> reclamations = rs.recherche(nom , prenom , estTraite);
        tableview.setItems(FXCollections.observableArrayList(reclamations));
    }

    @FXML
    void NomTF(KeyEvent event) {
        nom = ((TextField) event.getSource()).getText();
        recherche();
    }

    @FXML
    void PrenomTF(KeyEvent event) {
        prenom = ((TextField) event.getSource()).getText();
        recherche();

    }

    @FXML
    void estTraiteChanged(ActionEvent event) {
        String search = estTraiteCB.getValue();
        if(search . equals("Traitées"))
            estTraite = 1;
        else if (search . equals("Non traitées")) {
            estTraite = 0;
        }
        else
            estTraite = null;

        recherche();

    }
}




