package tn.esprit.Service;

import tn.esprit.Model.Like;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LikeService {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Integrationfinal";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    public void insertLike(Like like) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "INSERT INTO likes (nom, likes) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, like.getNom());
                statement.setInt(2, like.getLikes());
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int id = generatedKeys.getInt(1);
                            like.setId(id);
                        } else {
                            throw new SQLException("Failed to get ID for new like.");
                        }
                    }
                    connection.commit(); // Commit de la transaction après l'insertion réussie
                } else {
                    throw new SQLException("Insertion into likes table failed, no rows affected.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Ajoutez ici la logique pour gérer l'erreur, par exemple afficher un message à l'utilisateur
        }
    }

    // Méthode pour incrémenter les likes dans la base de données
 public void incrementLikes() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "UPDATE likes SET likes = likes + 1 WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, 1); // Ici, 1 représente l'ID du like que vous voulez incrémenter
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Ajoutez ici la logique pour gérer l'erreur, par exemple afficher un message à l'utilisateur
        }
}
}
