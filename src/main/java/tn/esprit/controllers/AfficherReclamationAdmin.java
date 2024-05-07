package tn.esprit.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tn.esprit.interfaces.RefreshCallBack;
import tn.esprit.models.Reclamation;
import tn.esprit.models.User;
import tn.esprit.models.session;
import tn.esprit.services.ReclamationService;
import tn.esprit.services.UserService;
import tn.esprit.util.Navigator;
import javafx.event.EventHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class AfficherReclamationAdmin implements RefreshCallBack {

    @FXML
    private TableColumn<Reclamation, Timestamp> DateSoumissionCol;

    @FXML
    private TableColumn<Reclamation, String> DescriptionCol;

    @FXML
    private TableColumn<Reclamation, String> EstTraiteCol;

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
    void initialize() {

        ObservableList<String> typeList = FXCollections.observableArrayList(
                "Toutes"
                , "Traitées"
                , "Non traitées"
        );
        estTraiteCB.setItems(typeList);
        estTraiteCB.setValue("Toutes");

        try {

            List<Reclamation> reclamations = rs.getAll();
            ObservableList<Reclamation> observableList = FXCollections.observableList(reclamations);
            tableview.setItems(observableList);
            DateSoumissionCol.setCellValueFactory(new PropertyValueFactory<>("datesoummission"));
            DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            EstTraiteCol.setCellValueFactory(cellData -> {
                Reclamation reclamation = cellData.getValue();
                Byte estTraite = reclamation.getEst_traite();
                String estTraiteStr = (estTraite != null && estTraite == 1) ? "Oui" : "Non";
                return new SimpleStringProperty(estTraiteStr);
            });

            NomPrenomCol.setCellValueFactory(cellData -> {
                UserService us = new UserService();
                Reclamation reclamation = cellData.getValue();
                if (reclamation.getUser_id() == 0) {
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
                AnchorPane detailsReclamationPane = loader.load();
                ModifierReclamationAdmin controller = loader.getController();

                // Appeler la méthode pour initialiser les détails de la réclamation
                controller.UpdateCallBack(this);
                controller.initialize(reclamation);

                Stage stage = new Stage();

                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        event.consume(); // Consume the event to prevent the window from closing automatically
    
                        // Execute your function here
                        handleCloseRequest(stage);
                    }
                });

                // Afficher l'interface dans une nouvelle fenêtre

                stage.setScene(new Scene(detailsReclamationPane));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
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
        } else {
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

    void recherche() {
        List<Reclamation> reclamations = rs.recherche(nom, prenom, estTraite);
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
        if (search.equals("Traitées"))
            estTraite = 1;
        else if (search.equals("Non traitées")) {
            estTraite = 0;
        } else
            estTraite = null;

        recherche();

    }

    @FXML
    private void calculerEtAfficherStatistiques() {
        List<Reclamation> reclamations = rs.getAll();
        int totalReclamations = reclamations.size();
        int reclamationsTraitees = 0;
        int reclamationsNonTraitees = 0;

        // Parcourir les réclamations et compter le nombre de réclamations traitées et non traitées
        for (Reclamation r : reclamations) {
            byte estTraite = r.getEst_traite();
            if (estTraite == 1) {
                reclamationsTraitees++;
            } else {
                reclamationsNonTraitees++;
            }
        }

        // Créer les données pour le graphique en barres
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Statistiques des réclamations");
        series.getData().add(new XYChart.Data<>("Réclamations traitées", reclamationsTraitees));
        series.getData().add(new XYChart.Data<>("Réclamations non traitées", reclamationsNonTraitees));

        barChart.getData().add(series);

        // Vérifier si les données de la série sont non nulles avant de modifier le style des barres
        if (!series.getData().isEmpty()) {
            // Définir la couleur des barres
            for (XYChart.Data<String, Number> data : series.getData()) {
                Node node = data.getNode();
                if (node != null) {
                    if (data.getXValue().equals("Réclamations traitées")) {
                        node.setStyle("-fx-bar-fill: #27a227;");
                    } else {
                        node.setStyle("-fx-bar-fill: #ff1a1a;");
                    }
                }
            }
        }

        // Afficher les statistiques dans le graphique en barres
        barChart.setTitle("Statistiques des réclamations");
        xAxis.setLabel("Type de réclamation");
        yAxis.setLabel("Nombre de réclamations");

        // Créer une nouvelle fenêtre pour afficher le graphique en barres
        Stage stage = new Stage();
        Scene scene = new Scene(barChart);
        stage.setScene(scene);
        stage.setTitle("Statistiques des réclamations");
        stage.show();
    }


    @Override
    public void onRefreshComplete() {
        Refresh();
    }


    private void handleCloseRequest(Stage stage) {
        Refresh();
        stage.close();

    }
}




