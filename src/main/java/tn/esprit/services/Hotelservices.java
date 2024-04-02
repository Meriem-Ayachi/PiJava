package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.hotel;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Hotelservices implements IService<hotel> {
    Connection cnx =MaConnexion.getInstance().getCnx();
    @Override
    public void add (hotel hotel )
    {
        String req = "INSERT INTO `hotel`(`nom`, `nbretoile`, `emplacement`, `avis`) VALUES ('"+hotel.getNom()+"',"+hotel.getNbretoile()+",'"+hotel.getEmplacement()+"','"+hotel.getAvis()+"')";
        try {
            Statement st =cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("user ajouter");
        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    @Override
    public void update (hotel hotel )
    {

    }
    @Override

    public void delete(int id) {
        String query = "DELETE FROM hotel WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Hôtel supprimé avec succès");
            } else {
                System.out.println("Aucun hôtel n'a été supprimé");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de l'hôtel", e);
        }
    }
    @Override
    public List<hotel> getAll() {
        List<hotel> hotels = new ArrayList<>();
        try {
            String req = "SELECT * FROM hotel";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                hotel p = new hotel();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setEmplacement(rs.getString("emplacement"));
                p.setAvis(rs.getString("avis"));
                hotels.add(p);
            }
        } catch (SQLException ex) {

        }
        return hotels;
    }

    @Override
    public hotel getOne(int id){
        return null;
    }

}
