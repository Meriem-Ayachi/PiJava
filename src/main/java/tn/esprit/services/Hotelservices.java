package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Reservation;
import tn.esprit.models.hotel;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public  class Hotelservices implements IService<hotel> {
    Connection cnx = MaConnexion.getInstance().getCnx();

    public static List<hotel> getAllHotels() {
        return null;
    }

    @Override
    public void add(hotel hotel) {
        String req = "INSERT INTO `hotel`(`nom`, `nbretoile`, `emplacement`, `avis`) VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setString(1, hotel.getNom());
            preparedStatement.setString(2, hotel.getNbretoile());
            preparedStatement.setString(3, hotel.getEmplacement());
            preparedStatement.setString(4, hotel.getAvis());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de l'hôtel", e);
        }
    }

    @Override
    public void update(hotel hotel) {
        String req = "UPDATE hotel SET nom=?, nbretoile=?, emplacement=?, avis=? WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setString(1, hotel.getNom());
            preparedStatement.setString(2, hotel.getNbretoile());
            preparedStatement.setString(3, hotel.getEmplacement());
            preparedStatement.setString(4, hotel.getAvis());
            preparedStatement.setInt(5, hotel.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'hôtel", e);
        }
    }
    @Override
    public List<hotel> rechercherParNom(String nom) {
        List<hotel> hotels = new ArrayList<>();
        try {
            String req = "SELECT * FROM hotel WHERE nom LIKE ?";
            PreparedStatement preparedStatement = cnx.prepareStatement(req);
            preparedStatement.setString(1, "%" + nom + "%");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                hotel p = new hotel();
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setNbretoile(rs.getString("nbretoile"));
                p.setEmplacement(rs.getString("emplacement"));
                p.setAvis(rs.getString("avis"));
                hotels.add(p);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erreur lors de la recherche des hôtels par nom", ex);
        }
        return hotels;
    }


    @Override
    public void delete(hotel hotel) {
        String req = "DELETE FROM hotel WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, hotel.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de l'hôtel", e);
        }
    }


    @Override
    public void delete(int id) {
        // Implémenter la suppression de l'hôtel par ID
    }

    @Override
    public void generatePDF(List<Reservation> reservations, String filePath) {
        // Implémenter la génération du PDF
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
                p.setNbretoile(rs.getString("nbretoile"));
                p.setEmplacement(rs.getString("emplacement"));
                p.setAvis(rs.getString("avis"));
                hotels.add(p);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erreur lors de la récupération des hôtels", ex);
        }
        return hotels;
    }

    @Override
    public hotel getOne(int id) {
        // Implémenter la récupération d'un hôtel par son ID
        return null;
    }
}
