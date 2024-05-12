package myblog_pi.myblog;

import myblog_pi.Service.LikeService;
import myblog_pi.Service.PublicationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import myblog_pi.Model.Forum_Commentaire;
import myblog_pi.Model.Like;
import myblog_pi.Model.Publication;
import myblog_pi.Service.CommentaireService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AddPostFXMLController implements Initializable {

    private PubFXMLController pubController;
    @FXML
    private ImageView likes;
    @FXML
    private Label nb_likes;
    @FXML
    private Label nb_comment;

    public void setPubController(PubFXMLController pubController) {
        this.pubController = pubController;
    }

    @FXML
    private ImageView postImage;
    @FXML
    private ImageView upload_image;
    @FXML
    private Button btn_commenter;
    @FXML
    private Hyperlink shareLink;
    @FXML
    private TextField ta_comment;
    @FXML
    private TextFlow textFlow_comment;
    
    private int likeCount = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        upload_image.setOnMouseClicked(event -> handleUploadImage());
        btn_commenter.setOnAction(event -> handleCommenter());
        shareLink.setOnAction(event -> handleShareLink());
        likes.setOnMouseClicked(event -> handleLikeClick());
         nb_comment.setText("0");
    }

    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File file = fileChooser.showOpenDialog(upload_image.getScene().getWindow());

        if (file != null) {
            
            Image newImage = new Image(file.toURI().toString());
            postImage.setImage(newImage);
        }
    }
    
   /* private void handleUploadImage() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Choisir une image");
    File file = fileChooser.showOpenDialog(upload_image.getScene().getWindow());

    if (file != null) {
        try {
            // Convertir l'image en tableau d'octets
            byte[] imageData = convertImageToByteArray(file);

            // Convertir le tableau d'octets en chaîne encodée en base64
            String base64Image = Base64.getEncoder().encodeToString(imageData);

            // Enregistrer la publication dans la base de données avec l'image encodée en base64
            savePublicationWithImage(base64Image);

            // Afficher l'image dans postImage
            Image newImage = new Image(file.toURI().toString());
            postImage.setImage(newImage);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            // Ajoutez ici la logique pour gérer l'erreur, par exemple afficher un message à l'utilisateur
        }
    }
}

private byte[] convertImageToByteArray(File file) throws IOException {
    try (FileInputStream fis = new FileInputStream(file);
         ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        return bos.toByteArray();
    }
}

private void savePublicationWithImage(String base64Image) throws SQLException {
    // Créer une nouvelle publication avec l'image encodée en base64
    Publication newPublication = new Publication();
    newPublication.setTitle("Titre de la publication");
    newPublication.setContent("Contenu de la publication");
    newPublication.setImage(base64Image);

    // Utiliser le service de publication pour enregistrer la nouvelle publication dans la base de données
    PublicationService publicationService = new PublicationService();
    publicationService.addPublication(newPublication);
}*/




    
    @FXML
private void handleCommenter() {
    // Récupérer le texte du commentaire et le nettoyer
    String commentText = ta_comment.getText().trim();
    if (commentText.isEmpty()) {
        return;
    }

    // Vérifier s'il y a des mots inappropriés dans le commentaire
    if (containsInappropriateWords(commentText)) {
        // Afficher une alerte et bloquer l'ajout du commentaire
        showAlert("Mot Irrespectueux", "Le commentaire contient des mots inappropriés.");
        return;
    }

    // Obtenez la date et l'heure actuelles
    LocalDateTime now = LocalDateTime.now();

    // Créer un objet Forum_Commentaire avec le contenu et la date actuels
    Forum_Commentaire nouveauCommentaire = new Forum_Commentaire();
    nouveauCommentaire.setContent(commentText);
    nouveauCommentaire.setCreatedAt(now);
    nouveauCommentaire.setUpdatedAt(now);

    // Utiliser le service de commentaire pour ajouter ce commentaire à la base de données
    CommentaireService commentaireService = new CommentaireService();
    commentaireService.insertCommentaire(nouveauCommentaire);

    // Ajouter le commentaire au TextFlow
    Text commentLabel = new Text(commentText + "\n");
    textFlow_comment.getChildren().add(commentLabel);

    // Effacer le contenu de votre TextArea après avoir ajouté le commentaire
    ta_comment.clear();

    // Incrémenter le nombre de commentaires
    incrementCommentCount();

    // Afficher un message de confirmation (facultatif)
    System.out.println("Commentaire inséré avec succès dans la base de données.");
}

private boolean containsInappropriateWords(String text) {
    // Liste des mots inappropriés à vérifier
    String[] inappropriateWords = {"fuck", "hate", "loser", "fat", "ugly"};

    // Vérifier si le texte contient l'un des mots inappropriés
    for (String word : inappropriateWords) {
        if (text.toLowerCase().contains(word)) {
            return true;
        }
    }
    return false;
}

private void showAlert(String title, String content) {
    Alert alert = new Alert(AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
}


private void incrementCommentCount() {
    // Incrémenter le nombre de commentaires
    int currentCommentCount = Integer.parseInt(nb_comment.getText());
    currentCommentCount++;
    nb_comment.setText(String.valueOf(currentCommentCount));
}



/*private void handleShareLink() {
    try {
        // Charger la vue pubFXML.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("pubFXML.fxml"));
        Parent root = loader.load();

        // Obtenir le contrôleur PubFXMLController
        PubFXMLController pubController = loader.getController();

        // Créer une nouvelle publication avec les données
        Publication newPublication = new Publication();
        newPublication.setTitle("Titre de la publication");
        newPublication.setContent("Contenu de la publication");
        newPublication.setImage("Chemin de l'image");

        // Insérer la nouvelle publication dans la base de données
        PublicationService publicationService = new PublicationService();
        publicationService.insertPublication(newPublication);

        // Ajouter la nouvelle publication à la grille dans PubFXMLController
        pubController.addPublication(newPublication);

        // Fermer la fenêtre actuelle (AddPostFXMLController)
        ((Stage) shareLink.getScene().getWindow()).close();

        // Afficher la nouvelle fenêtre (pubFXML.fxml)
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Publication");
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}*/
private void handleShareLink() {
    try {
        // Charger la vue pubFXML.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("pubFXML.fxml"));
        Parent root = loader.load();

        // Obtenir le contrôleur PubFXMLController
        PubFXMLController pubController = loader.getController();

        // Créer une nouvelle publication avec les données
        Publication newPublication = new Publication();
        newPublication.setTitle("Titre de la publication");
        newPublication.setContent("Contenu de la publication");
        newPublication.setImage("Chemin de l'image");

        // Insérer la nouvelle publication dans la base de données
        PublicationService publicationService = new PublicationService();
        publicationService.insertPublication(newPublication);

        // Ajouter la nouvelle publication à la grille dans PubFXMLController
        pubController.addPublication(newPublication);

        // Fermer la fenêtre actuelle (AddPostFXMLController)
        ((Stage) shareLink.getScene().getWindow()).close();

        // Afficher la nouvelle fenêtre (pubFXML.fxml)
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Publication");
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}



private void handleLikeClick() {
    // Incrémenter le nombre de likes dans l'interface graphique
    likeCount++;
    nb_likes.setText(String.valueOf(likeCount)); // Mettre à jour l'affichage du nombre de likes dans le Label

    // Créer un objet Like avec le nom et le nombre de likes actuels
    Like newLike = new Like();
    newLike.setNom("like"); // Remplacez "NomDeVotreLike" par le nom que vous souhaitez utiliser
    newLike.setLikes(likeCount); // Utilisez le nombre de likes actuel

    // Appeler la méthode du service pour ajouter le like à la base de données
    LikeService likeService = new LikeService();
    likeService.insertLike(newLike); // Vous devez ajouter cette méthode dans LikeService
}

   
     private PubFXMLController parentController; // Référence au contrôleur parent
    
    public void setParentController(PubFXMLController parentController) {
        this.parentController = parentController;
    }
    
    // Méthode appelée lorsque vous souhaitez ajouter le contenu à la grille dans le contrôleur parent
    private void addContent(String shareContent) {
        parentController.addContent(shareContent);
    }

}
