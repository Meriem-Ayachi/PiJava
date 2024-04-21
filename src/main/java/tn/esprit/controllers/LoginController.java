package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
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
        System.out.println(" connectéé"+authentifie);
        // Ajouter le reste de la logique selon le résultat de l'authentification
        if (authentifie) {
            // Authentification réussie, vérifier si l'utilisateur est bloqué
            int userId = userService.getUtilisateurid(emailTF.getText());
            if (userService.isBlocked(userId)) {
                // Si l'utilisateur est bloqué, afficher une alerte
                afficherAlerteErreur("account blocked", "BLOCKED");
            } else {
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

                System.out.println("User connectéé" + p);
            }
        } else {
            // L'authentification a échoué, afficher une alerte d'erreur
            afficherAlerteErreur("Authentification échouée", "Veuillez vérifier vos identifiants.");
        }
    }

    private void redirectToUserPage(ActionEvent event) {
        nav.goToPage_WithEvent("/ProfileUser.fxml", event);
    }

    private void redirectToAdminPage(ActionEvent event) {
        nav.goToPage_WithEvent("/ProfileAdmin.fxml", event);
    }
    
    public void redirectToInscription(ActionEvent event){
        nav.goToPage_WithEvent("/inscription.fxml", event);
    }


    private void afficherAlerteErreur(String titre, String contenu) {
        Alert alerte = new Alert(Alert.AlertType.ERROR);
        alerte.setTitle(titre);
        alerte.setHeaderText(null);
        alerte.setContentText(contenu);
        alerte.showAndWait();
    }

}
