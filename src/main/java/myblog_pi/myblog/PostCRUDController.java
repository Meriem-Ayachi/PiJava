package myblog_pi.myblog;
import myblog_pi.Model.ImageCell;
import myblog_pi.Model.Publication;
import myblog_pi.Service.PublicationService;
import java.awt.image.BufferedImage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javax.imageio.ImageIO;
import javafx.scene.image.Image;


public class PostCRUDController implements Initializable {

    @FXML
    private ImageView imageView;
    @FXML
    private Button btn_uploadImage;
    private Hyperlink FrontLink;
    @FXML
    private TextField tf_title;
    @FXML
    private TextField tf_shortDescription;
    @FXML
    private TextArea ta_content;
    @FXML
    private TableView<Publication> tableView;
    private TableColumn<Publication, Integer> idColumn;
    private TableColumn<Publication, String> titleColumn;
    private TableColumn<Publication, String> shortDescColumn;
    private TableColumn<Publication, String> contentColumn;
    private ObservableList<Publication> publicationList;
    
    private TableColumn<Publication, Image> imageColumn;
    @FXML
    private TextField tf_search;
    @FXML
    private Button btn_search;
    @FXML
    private HBox pub;
    @FXML
    private Button btn_add;
    @FXML
    private Button btn_update;
    @FXML
    private Button btn_clear;
    @FXML
    private Button btn_delete;
    @FXML
    private HBox com;
    @FXML
    private HBox music;
    @FXML
    private HBox media;
    @FXML
    private HBox setting;
    @FXML
    private HBox profil1;
    
    List<Publication> list = new ArrayList();
    PublicationService SC=new PublicationService();
    @FXML
    private Hyperlink GoFrontLink;

    /*@Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize publicationList and bind it to the TableView
        publicationList = FXCollections.observableArrayList();
        tableView.setItems(publicationList);

        // Initialize TableColumn instances
        idColumn = new TableColumn<>("ID");
        titleColumn = new TableColumn<>("Title");
        shortDescColumn = new TableColumn<>("Short Description");
        contentColumn = new TableColumn<>("Content");
        imageColumn = new TableColumn<>("Image"); // Création de la colonne pour l'image

        // Bind TableView columns to Publication properties
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        shortDescColumn.setCellValueFactory(cellData -> cellData.getValue().shortDescriptionProperty());
        contentColumn.setCellValueFactory(cellData -> cellData.getValue().contentProperty());
        imageColumn.setCellValueFactory(cellData -> cellData.getValue().imageProperty());

        // Set cell factory for the image column to use ImageCell
        imageColumn.setCellFactory(column -> new ImageCell());

        // Set preferred widths for columns (you can adjust the values according to your needs)
        idColumn.setPrefWidth(50);
        titleColumn.setPrefWidth(200);
        shortDescColumn.setPrefWidth(250);
        contentColumn.setPrefWidth(300);
        imageColumn.setPrefWidth(200); // Définir la largeur de la colonne de l'image

        // Add columns to the TableView
        tableView.getColumns().addAll(idColumn, titleColumn, shortDescColumn, contentColumn, imageColumn); // Ajout de la colonne de l'image au TableView
        // Associez la méthode handleSearchButton au clic sur le bouton btn_search
         btn_search.setOnAction(this::handleSearchButton);
    }*/
    
  @FXML
private void handlePublicationClick() {
    try {
        // Charger le fichier FXML PublicationFXML.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PublicationFXML.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène avec le contenu du fichier FXML chargé
        Scene scene = new Scene(root);

        // Créer une nouvelle fenêtre pour afficher la scène
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Publication");

        // Afficher la fenêtre
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}



@Override
public void initialize(URL url, ResourceBundle rb) {
    // Initialize publicationList and bind it to the TableView
        list = SC.getAllPublications();
    publicationList = FXCollections.observableArrayList(list);
    tableView.setItems(publicationList);

    // Initialize TableColumn instances
    idColumn = new TableColumn<>("ID");
    titleColumn = new TableColumn<>("Title");
    shortDescColumn = new TableColumn<>("Short Description");
    contentColumn = new TableColumn<>("Content");
    imageColumn = new TableColumn<>("Image");

    // Bind TableView columns to Publication properties
    idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
    titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
    shortDescColumn.setCellValueFactory(cellData -> cellData.getValue().shortDescriptionProperty());
    contentColumn.setCellValueFactory(cellData -> cellData.getValue().contentProperty());
    imageColumn.setCellValueFactory(cellData -> cellData.getValue().imageProperty());

    // Set cell factory for the image column to use ImageCell
    imageColumn.setCellFactory(column -> new ImageCell());

    // Set preferred widths for columns
    idColumn.setPrefWidth(50);
    titleColumn.setPrefWidth(200);
    shortDescColumn.setPrefWidth(250);
    contentColumn.setPrefWidth(300);
    imageColumn.setPrefWidth(200);

    // Add columns to the TableView
    tableView.getColumns().addAll(idColumn, titleColumn, shortDescColumn, contentColumn, imageColumn);

    // Associate the handleSearchButton method with the btn_search button click event
    btn_search.setOnAction(this::handleSearchButton);

    // Add an event handler for the HBox publication
    pub.setOnMouseClicked(event -> handlePublicationClick()); 
    com.setOnMouseClicked(event -> handleComClick());
    music.setOnMouseClicked(event -> handleMusicClick());
    media.setOnMouseClicked(event -> handleMediaClick());
}

    @FXML
    private void handleImportButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png")
        );

        File selectedFile = fileChooser.showOpenDialog(btn_uploadImage.getScene().getWindow());

        if (selectedFile != null) {
            try {
                String imagePath = selectedFile.toURI().toString();
                // Load image into the ImageView
                Image image = new Image(imagePath);
                imageView.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



@FXML
private void handleAddButton(ActionEvent event) {
    try {
        String title = tf_title.getText();
        String shortDescription = tf_shortDescription.getText();
        String content = ta_content.getText();
        Image image = imageView.getImage();

        if (title.isEmpty() || shortDescription.isEmpty() || content.isEmpty() || image == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields and select an image.");
            return;
        }

        String imagePath = saveImageToFile(image);

        Publication newPublication = new Publication();
        newPublication.setTitle(title);
        newPublication.setShortDescription(shortDescription);
        newPublication.setContent(content);
        newPublication.setImage(imagePath);
        newPublication.setImageObject(image); 
        newPublication.setCreatedAt(LocalDateTime.now());
        newPublication.setUpdatedAt(LocalDateTime.now());

        PublicationService publicationService = new PublicationService();
        publicationService.addPublication(newPublication);

        // Ajoutez cette ligne pour récupérer l'ID auto-incrémenté après l'ajout dans la base de données
        int newId = newPublication.getId();

        newPublication.setId(newId); // Mettez à jour l'ID dans l'objet Publication avec l'ID auto-incrémenté

        publicationList.add(newPublication);

        showAlert(Alert.AlertType.INFORMATION, "Success", "Publication added successfully.");

        clearFields();
    } catch (Exception e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while adding the publication.");
    }
}



    private void handleGoFrontLink(ActionEvent event) {
        try {
            Stage stage = (Stage) FrontLink.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   private String saveImageToFile(Image image) {
    String imagePath = ""; 
    try {
       
        String fileName = "image_" + System.currentTimeMillis() + ".png";

        
        String currentDir = System.getProperty("user.dir");

       
        imagePath = currentDir + File.separator + fileName;

        
        File outputFile = new File(imagePath);

       
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(bufferedImage, "png", outputFile);
    } catch (IOException e) {
        e.printStackTrace();
    }
    return imagePath; 
}

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        tf_title.clear();
        tf_shortDescription.clear();
        ta_content.clear();
        imageView.setImage(null);
    }
    
    
    @FXML
    private void handleMouseAction(MouseEvent event) {
        Publication c = tableView.getSelectionModel().getSelectedItem();
        tf_title.setText(c.getTitle());
        tf_shortDescription.setText(c.getShortDescription());
        ta_content.setText(c.getContent());
       
    }
//    @FXML
//    private void handleUpdateButton(ActionEvent event) {
//    Publication selectedPublication = tableView.getSelectionModel().getSelectedItem();
//    if (selectedPublication != null) {
//        try {
//            String title = tf_title.getText();
//            String shortDescription = tf_shortDescription.getText();
//            String content = ta_content.getText();
//            Image image = imageView.getImage();
//
//            if (title.isEmpty() || shortDescription.isEmpty() || content.isEmpty()) {
//                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
//                return;
//            }
//            if (image != null) {
//                String imagePath = saveImageToFile(image);
//                selectedPublication.setImageObject(image); 
//            }
//            selectedPublication.setTitle(title);
//            selectedPublication.setShortDescription(shortDescription);
//            selectedPublication.setContent(content);
//            selectedPublication.setUpdatedAt(LocalDateTime.now());
//
//            PublicationService publicationService = new PublicationService();
//            publicationService.updatePublication(selectedPublication);
//
//            showAlert(Alert.AlertType.INFORMATION, "Success", "Publication updated successfully.");
//
//            clearFields();
//        } catch (Exception e) {
//            e.printStackTrace();
//            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while updating the publication.");
//        }
//    } else {
//        showAlert(Alert.AlertType.ERROR, "Error", "Please select a publication to update.");
//    }
//}
@FXML
private void handleUpdateButton(ActionEvent event) {
    Publication selectedPublication = tableView.getSelectionModel().getSelectedItem();
    if (selectedPublication != null) {
        try {
            String title = tf_title.getText();
            String shortDescription = tf_shortDescription.getText();
            String content = ta_content.getText();
            Image image = imageView.getImage();

            if (title.isEmpty() || shortDescription.isEmpty() || content.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
                return;
            }

            if (image != null) {
                String imagePath = saveImageToFile(image);
                selectedPublication.setImage(imagePath);
                selectedPublication.setImageObject(image);
            }

            selectedPublication.setTitle(title);
            selectedPublication.setShortDescription(shortDescription);
            selectedPublication.setContent(content);
            selectedPublication.setUpdatedAt(LocalDateTime.now());

            PublicationService publicationService = new PublicationService();
            publicationService.updatePublication(selectedPublication);

            // Mise à jour de la vue
            int index = publicationList.indexOf(selectedPublication);
            if (index != -1) {
                publicationList.set(index, selectedPublication);
            }

            clearFields();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Publication updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while updating the publication.");
        }
    } else {
        showAlert(Alert.AlertType.ERROR, "Error", "Please select a publication to update.");
    }
}



    @FXML
    private void handleDeleteButton(ActionEvent event) {
    try {
        Publication selectedPublication = tableView.getSelectionModel().getSelectedItem();
        if (selectedPublication != null) {
           
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this publication?");

        
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
              
                PublicationService publicationService = new PublicationService();
                publicationService.deletePublication(selectedPublication.getId());
                publicationList.remove(selectedPublication);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Publication deleted successfully.");
                clearFields();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a publication to delete.");
        }
    } catch (Exception e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting the publication.");
    }
}


    @FXML
    private void handleClearButton(ActionEvent event) {
   
    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
    confirmAlert.setTitle("Confirmation");
    confirmAlert.setHeaderText(null);
    confirmAlert.setContentText("Are you sure you want to clear the table?");
    
  
    Optional<ButtonType> result = confirmAlert.showAndWait();
    
    if (result.isPresent() && result.get() == ButtonType.OK) {
        try {
            clearFields();
            publicationList.clear(); 
            showAlert(Alert.AlertType.INFORMATION, "Success", "Table cleared successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while clearing the fields.");
        }
    }
}



private void handleSearchButton(ActionEvent event) {
    String searchText = tf_search.getText().trim().toLowerCase(); 
    ObservableList<Publication> searchResults = FXCollections.observableArrayList();

    if (!searchText.isEmpty()) {
        for (Publication publication : publicationList) {
            if (publication.getTitle().toLowerCase().contains(searchText)) {
                searchResults.add(publication); 
            }
        }
    } else {
        searchResults.addAll(publicationList); 
    }

    tableView.setItems(searchResults); 
}


@FXML
private void handleComClick() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("comFXML.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Com");

        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

@FXML
private void handleMusicClick() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("musicFXML.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Music");

        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

  @FXML
   private void handleMediaClick() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mediaFXML.fxml"));
        Parent root = loader.load();

        // Create a new stage and set the scene
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Media");

        // Show the stage
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error loading FXML: " + e.getMessage());
    }
}



    @FXML
    private void handleSettingClick(MouseEvent event) {
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("settingFXML.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Setting");

        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void handleProfilClick(MouseEvent event) {
         try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfilFXML.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Profil");

        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void GoFrontLink(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("PublicationFXML.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
