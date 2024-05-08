package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.User;
import tn.esprit.services.UserService;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GestionUtilisateurs {
    @FXML
    private TableColumn<User, String> nomColumn;

    @FXML
    private TableColumn<User, String> prenomColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, Integer> numtelColumn;

    @FXML
    private TableView<User> userTableView;

    private User selectedUser;

    private String nom, prenom;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;

    private final UserService userService = new UserService();

    private void refreshTable() {
        userTableView.setItems(FXCollections.observableArrayList(userService.getAll()));
    }

    @FXML
    void initialize() {
        try{
        List<User> user = userService.getAll();
        ObservableList<User> observableList = FXCollections.observableList(user);
        userTableView.setItems(observableList);
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        numtelColumn.setCellValueFactory(new PropertyValueFactory<>("num_tel"));

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
        }
        refreshTable();
    }

    @FXML
    void supprimerSelectedUser() {
        if (selectedUser != null) {
            userService.delete(selectedUser.getId());
            refreshTable();
            selectedUser = null;
        }else{
            showError("Vous devez sélectionner un user");
        }
    }

    @FXML
    void goToModifier(ActionEvent event) {
        if (selectedUser != null) {
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
            showError("Vous devez sélectionner un user");
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


    @FXML
    void recherche() {
        List<User> locationVoitureList = userService.recherche(nom, prenom);
        userTableView.getItems().clear();
        userTableView.getItems().addAll(locationVoitureList);
    }

    @FXML
    void prenomChanged(KeyEvent event) {
        prenom= prenomTF.getText();
        recherche();
    }

    @FXML
    void nomChanged(KeyEvent event) {
        nom = nomTF.getText();
        recherche();
    }

}