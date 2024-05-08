package controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tn.esprit.util.MaConnexion;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Statistique{



    @FXML
    private PieChart piechart;
    ObservableList<PieChart.Data> data= FXCollections.observableArrayList();





    @FXML
    public void initialize() {
        try {
            stat();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void stat() throws SQLException {
        String query="SELECT title,COUNT(id) AS number_of_offres FROM offres GROUP BY title;";
        Statement st= MaConnexion.getInstance().getCnx().createStatement();
        ResultSet rs=st.executeQuery(query);
        while (rs.next()){
            data.add(new PieChart.Data("title:"+rs.getString("title"),rs.getInt("number_of_offres")));
        }
        piechart.setData(data);
    }

}