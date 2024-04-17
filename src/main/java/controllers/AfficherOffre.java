package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.models.Offres;
import tn.esprit.services.OffresService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class AfficherOffre {

    @FXML
    private TableColumn<Offres, Date> dateColO;

    @FXML
    private TableColumn<Offres, String> descriptionColO;

    @FXML
    private TableColumn<Offres, String> imageColO;

    @FXML
    private TableColumn<Offres, String> lieuColO;

    @FXML
    private TableColumn<Offres, Double> prixColO;

    @FXML
    private TableColumn<Offres, Boolean> publishedColO;

    @FXML
    private TableView<Offres> tableview;

    @FXML
    private TableColumn<Offres, String> titleColO;

    private final OffresService os = new OffresService();

    @FXML
    void initialize(){

        try {
            List<Offres> offres= os.getAll();
            ObservableList<Offres>observableList=FXCollections.observableList(offres);
            tableview.setItems(observableList);
            titleColO.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColO.setCellValueFactory(new PropertyValueFactory<>("description"));
            publishedColO.setCellValueFactory(new PropertyValueFactory<>("published"));
            prixColO.setCellValueFactory(new PropertyValueFactory<>("prix") );
            lieuColO.setCellValueFactory(new PropertyValueFactory<>("lieu"));
            imageColO.setCellValueFactory(new PropertyValueFactory<>("image"));
            dateColO.setCellValueFactory(new PropertyValueFactory<>("date"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
