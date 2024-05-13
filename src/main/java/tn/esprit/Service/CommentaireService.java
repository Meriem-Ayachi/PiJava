package tn.esprit.Service;

import tn.esprit.Model.Forum_Commentaire;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CommentaireService {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Integrationfinal";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "123456789";

   public void insertCommentaire(Forum_Commentaire commentaire) {
    try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
        String sql = "INSERT INTO forum_commentaire (content, created_at, updated_at) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, commentaire.getContent());
            statement.setTimestamp(2, java.sql.Timestamp.valueOf(commentaire.getCreatedAt()));
            statement.setTimestamp(3, java.sql.Timestamp.valueOf(commentaire.getUpdatedAt()));
            
            System.out.println("Executing SQL: " + sql); // Ajout d'un log de débogage
            
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Insertion successful."); // Ajout d'un log de débogage
            } else {
                throw new SQLException("Insertion into forum_commentaire table failed, no rows affected.");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Ajoutez ici la logique pour gérer l'erreur, par exemple afficher un message à l'utilisateur
    }
}

}
