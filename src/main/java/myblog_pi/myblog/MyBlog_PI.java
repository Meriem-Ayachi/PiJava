/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myblog_pi.myblog;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ghada
 */
public class MyBlog_PI extends Application {
    
     @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PostCRUD.fxml"));
        stage.setTitle("Forum");
        stage.setScene(new Scene(root, 1250, 780)); 
        stage.getScene().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm()); 
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
