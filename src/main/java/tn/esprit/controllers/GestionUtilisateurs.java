package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import tn.esprit.models.User;
import tn.esprit.services.UserService;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GestionUtilisateurs {

    @FXML
    private TableView<User> userTableView;

    private User selectedUser;

    private final UserService userService = new UserService();

    private void refreshTable() {
        userTableView.setItems(FXCollections.observableArrayList(userService.getAll()));
    }

    @FXML
    void initialize() {
        TableColumn<User, String> nomColumn = new TableColumn<>("nom");
        TableColumn<User, String> prenomColumn = new TableColumn<>("prenom");
        TableColumn<User, String> emailColumn = new TableColumn<>("email");
        TableColumn<User, String> numtelColumn = new TableColumn<>("numtel");

        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        numtelColumn.setCellValueFactory(new PropertyValueFactory<>("num_tel"));

        // add columns
        List<TableColumn<User, ?>> columns = Arrays.asList(
                nomColumn,
                prenomColumn,
                emailColumn,
                numtelColumn);
        userTableView.getColumns().addAll(columns);

        // fill table
        refreshTable();
    }





    @FXML
    void supprimerSelectedLocation() {
        if (selectedUser != null) {
            userService.delete(selectedUser.getId());
            refreshTable();
            selectedUser = null;
        }else{
            showError("Vous devez sélectionner une location");
        }
    }

    @FXML
    void goToModifier(ActionEvent event) {
        if (selectedUser != null) {
            User locationVoiture = userService.getOne(selectedUser.getId());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierUserparAdmin.fxml"));
                Parent root = loader.load();
                ModifierUserparAdmin controller = loader.getController();
                controller.initialize(selectedUser);
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            showError("Vous devez sélectionner une location");
        }
    }

    @FXML
    void onTableRowClicked() {
        selectedUser = userTableView.getSelectionModel().getSelectedItem();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
