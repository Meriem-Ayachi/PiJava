package tn.esprit.controllers ;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.mapping.ArcGISMap;

import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.Viewpoint;

import com.esri.arcgisruntime.mapping.view.MapView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    private MapView mapView;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        // set the title and size of the stage and show it
        stage.setTitle("Display a map tutorial");
        stage.setWidth(800);
        stage.setHeight(700);
        stage.show();

        // create a JavaFX scene with a stack pane as the root node, and add it to the scene
        StackPane stackPane = new StackPane();
        Scene scene = new Scene(stackPane);
        stage.setScene(scene);

        // create a map view to display the map and add it to the stack pane
        mapView = new MapView();
        stackPane.getChildren().add(mapView);

    }

}