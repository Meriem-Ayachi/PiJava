package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import tn.esprit.models.LogType;
import tn.esprit.models.User;
import tn.esprit.models.session;
import tn.esprit.services.UserService;
import tn.esprit.util.Navigator;

import java.sql.SQLException;

public class  LoginController {




    @FXML
    private TextField emailTF;

    @FXML
    private PasswordField passwordTF;

    @FXML
    private Button button;

    private Navigator nav = new Navigator();

    @FXML
    private void initialize() {

    }

    @FXML
    private void submit(ActionEvent event) throws SQLException {

        if (emailTF.getText().isEmpty() || passwordTF.getText().isEmpty()) {
            afficherAlerteErreur( "Erreur de saisie", "Le mot de passe doit contenir au moins 8 caractères.");
            return;
        }
        UserService userService=new UserService();

        // Appeler la fonction de vérification dans AuthentificationManager
        boolean authentifie = userService.verifierUtilisateur(emailTF.getText(), passwordTF.getText());
        // Ajouter le reste de la logique selon le résultat de l'authentification
        if (authentifie) {
            // Authentification réussie, vérifier si l'utilisateur est bloqué
            int userId = userService.getUtilisateurid(emailTF.getText());
            User user = userService.getOne(userId);
            // Save Log
            connecterUtilisateur(user);
            // Si l'utilisateur n'est pas bloqué, continuer avec le reste de la logique
            int p = userId;
            session.id_utilisateur = p;
            String userRole = userService.getUtilisateurRole(emailTF.getText());

            switch (userRole) {
                case "ROLE_ADMIN":
                    redirectToAdminPage(event);
                    break;
                case "ROLE_USER":
                    redirectToUserPage(event);
                    break;
            }

            System.out.println("User connecté " + p);
        } else {
            // L'authentification a échoué, afficher une alerte d'erreur
            afficherAlerteErreur("Authentification échouée", "Veuillez vérifier vos identifiants.");
        }
    }

    private void redirectToUserPage(ActionEvent event) {
        nav.goToPage_WithEvent("/home.fxml", event);
    }

    private void redirectToAdminPage(ActionEvent event) {
        nav.goToPage_WithEvent("/DashboardAdmin.fxml", event);
    }

    public void redirectToInscription(ActionEvent event){
        nav.goToPage_WithEvent("/inscription.fxml", event);
    }
    public void redirectToResetpassword(ActionEvent event){
        nav.goToPage_WithEvent("/MotsPassOublierMail.fxml", event);
    }


    private void afficherAlerteErreur(String titre, String contenu) {
        Alert alerte = new Alert(Alert.AlertType.ERROR);
        alerte.setTitle(titre);
        alerte.setHeaderText(null);
        alerte.setContentText(contenu);
        alerte.showAndWait();
    }

    // Logs functions
    public void connecterUtilisateur(User user) {
        LogController.saveLog("Utilisateur " + user.getPrenom() + " " + user.getNom() + " connecté.", LogType.CONNEXION_UTILISATEUR, user.getId());
    }
}