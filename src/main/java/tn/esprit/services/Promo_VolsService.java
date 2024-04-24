package tn.esprit.services;

import tn.esprit.models.Promo_Vols;
import tn.esprit.util.MaConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Promo_VolsService {

    private Connection cnx;

    public Promo_VolsService() {
        cnx = MaConnexion.getInstance().getCnx();
    }

    public void add(Promo_Vols promo) {
        String query = "INSERT INTO promo_vols(id,pourcentage, date_debut_promo, date_fin_promo) VALUES (?,?, ?, ?)";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, promo.getId());
            statement.setDouble(2, promo.getPourcentage());
            statement.setDate(3, promo.getDate_debut_promo());
            statement.setDate(4, promo.getDate_fin_promo());
            statement.executeUpdate();
            System.out.println("Promotion added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding promotion: " + e.getMessage());
        }
    }
}
