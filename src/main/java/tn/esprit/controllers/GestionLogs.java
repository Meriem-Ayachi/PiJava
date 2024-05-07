package tn.esprit.controllers;

import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import tn.esprit.models.Log;
import tn.esprit.models.LogType;
import tn.esprit.services.LogService;

public class GestionLogs {

    @FXML
    private TableView<Log> logsTableView;

    private Log selectedLog;


    private String nom, prenom, logType;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField prenomTF;

    @FXML
    private ComboBox<String> logTypeComboBox;

    private final LogService logService = new LogService();

    private void refreshTable() {
        logsTableView.setItems(FXCollections.observableArrayList(logService.getAll()));
    }

    @FXML
    void initialize() {
        //initialize the values of the type comboBox
        ObservableList<String> typeList = FXCollections.observableArrayList();
        for (LogType type : LogType.values()) {
            typeList.add(type.name()); // Add the enum value as a string
        }
        logTypeComboBox.setItems(typeList);

        TableColumn<Log, String> timeColumn = new TableColumn<>("Timestamp");
        TableColumn<Log, String> typeColumn = new TableColumn<>("Type");
        TableColumn<Log, String> descriptionColumn = new TableColumn<>("Description");
        TableColumn<Log, String> userIdColumn = new TableColumn<>("user");

        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        // add columns
        List<TableColumn<Log, ?>> columns = Arrays.asList(
                timeColumn,
                typeColumn,
                descriptionColumn,
                userIdColumn);
        logsTableView.getColumns().addAll(columns);

        // fill table
        refreshTable();
    }

    @FXML
    void supprimerSelectedLog() {
        if (selectedLog != null) {
            logService.delete((int) selectedLog.getLogId());
            refreshTable();
            selectedLog = null;
        }else{
            showError("Vous devez sélectionner une log");
        }
    }

    @FXML
    void onTableRowClicked() {
        selectedLog = logsTableView.getSelectionModel().getSelectedItem();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    void recherche() {
        List<Log> locationVoitureList = logService.recherche(nom, prenom, logType);
        logsTableView.getItems().clear();
        logsTableView.getItems().addAll(locationVoitureList);
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

    @FXML
    void logTypeChanged(ActionEvent event) {
        logType = logTypeComboBox.getValue();
        recherche();
    }
}