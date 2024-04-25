package tn.esprit.services;

import tn.esprit.models.Promo_Vols;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Promo_Vols> getAll() {
        List<Promo_Vols> promoVols = new ArrayList<>();
        try {
            String query = "SELECT * FROM promo_vols";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Promo_Vols promoVol = new Promo_Vols();
                promoVol.setId(rs.getInt("id"));
                promoVol.setPourcentage(rs.getDouble("pourcentage"));
                promoVol.setDate_debut_promo(rs.getDate("date_debut_promo"));
                promoVol.setDate_fin_promo(rs.getDate("date_fin_promo"));
                promoVols.add(promoVol);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return promoVols;
    }

}
