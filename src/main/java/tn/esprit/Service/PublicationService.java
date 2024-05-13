package tn.esprit.Service;

import tn.esprit.Model.Publication;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PublicationService {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Integrationfinalghada";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "";

    public List<Publication> getAllPublications() {
        List<Publication> publications = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM publication";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Publication publication = mapToPublication(resultSet);
                        publications.add(publication);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publications;
    }

    public Publication getPublicationById(int id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM publication WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return mapToPublication(resultSet);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addPublication(Publication publication) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            connection.setAutoCommit(false); // Disable autocommit

            String sql = "INSERT INTO publication (title, content, image, created_at, updated_at,short_description) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, publication.getTitle());

                statement.setString(2, publication.getContent());
                statement.setString(3, publication.getImage());
                statement.setTimestamp(4, java.sql.Timestamp.valueOf(LocalDateTime.now()));
                statement.setTimestamp(5, java.sql.Timestamp.valueOf(LocalDateTime.now()));
                statement.setString(6, publication.getShortDescription());
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int id = generatedKeys.getInt(1);
                            publication.setId(id);
                        } else {
                            throw new SQLException("Failed to get ID for new publication.");
                        }
                    }
                    connection.commit(); // Commit the transaction after successful insertion
                } else {
                    throw new SQLException("Insertion into publication table failed, no rows affected.");
                }
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback the transaction if an error occurs
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            // Add error handling logic here, e.g., display a message to the user
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true); // Re-enable autocommit
                    connection.close(); // Close the connection
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public void updatePublication(Publication publication) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "UPDATE publication SET title = ?, content = ?, image = ?, updated_at = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, publication.getTitle());
                statement.setString(2, publication.getContent());
                statement.setString(3, publication.getImage());
                statement.setTimestamp(4, java.sql.Timestamp.valueOf(LocalDateTime.now()));
                statement.setInt(5, publication.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePublication(int id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "DELETE FROM publication WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Publication mapToPublication(ResultSet resultSet) throws SQLException {
        Publication publication = new Publication();
        publication.setId(resultSet.getInt("id"));
        publication.setTitle(resultSet.getString("title"));
        publication.setContent(resultSet.getString("content"));
        publication.setImage(resultSet.getString("image"));
        publication.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
        publication.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
        return publication;
    }
    
    
    public void insertPublication(Publication publication) {
    try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
        String sql = "INSERT INTO publication (title, content, image, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, publication.getTitle());
            statement.setString(2, publication.getContent());
            statement.setString(3, publication.getImage());
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            statement.setTimestamp(5, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        publication.setId(id);
                    } else {
                        throw new SQLException("Failed to get ID for new publication.");
                    }
                }
                connection.commit(); // Commit de la transaction après l'insertion réussie
            } else {
                throw new SQLException("Insertion into publication table failed, no rows affected.");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Ajoutez ici la logique pour gérer l'erreur, par exemple afficher un message à l'utilisateur
    }
}

}
