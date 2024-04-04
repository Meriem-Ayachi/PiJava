package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Reservation;
import tn.esprit.models.hotel;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public abstract class Reservationservices implements IService<Reservation> {
    Connection cnx = MaConnexion.getInstance().getCnx();
    @Override
    public void add (Reservation reservation )
    {

        String req = "INSERT INTO reservation(datedepart, dateretour, classe, destinationdepart, destinationretour, nbrdepersonne) VALUES ('" +
                reservation.getDatedepart() + "','" +
                reservation.getDateretour() + "','" +
                reservation.getClasse() + "','" +
                reservation.getDestinationdepart() + "','" +
                reservation.getDestinationretour() + "'," +
                reservation.getNbrdepersonne() + ")";
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Réservation ajoutée avec succès");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void update (Reservation reservation )
    {
        String req = "UPDATE reservation SET " +
                "datedepart = '" + reservation.getDatedepart() + "', " +
                "dateretour = '" + reservation.getDateretour() + "', " +
                "classe = '" + reservation.getClasse() + "', " +
                "destinationdepart = '" + reservation.getDestinationdepart() + "', " +
                "destinationretour = '" + reservation.getDestinationretour() + "', " +
                "nbrdepersonne = " + reservation.getNbrdepersonne() + " " +
                "WHERE id = " + reservation.getId();
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Réservation mise à jour avec succès");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void delete(Reservation reservation) {
        String query = "DELETE FROM reservation WHERE id = ";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1,reservation.getId());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Réservation supprimée avec succès");
            } else {
                System.out.println("Aucune réservation n'a été supprimée");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de la réservation", e);
        }
    }





    @Override
    public List<Reservation> getAll() {
        List<Reservation> reservations = new ArrayList<>();
        try {
            String req = "SELECT * FROM reservation";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Reservation p = new Reservation();
                p.setDatedepart(rs.getString("datedepart"));
                p.setDateretour(rs.getString("dateretour")); // Correction du nom de la colonne
                p.setClasse(rs.getString("classe")); // Correction du nom de la colonne
                p.setDestinationdepart(rs.getString("destinationdepart"));
                p.setDestinationretour(rs.getString("destinationretour"));
                reservations.add(p);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Affiche l'erreur
            // Retourne une liste vide en cas d'erreur
            return reservations;
        }
        return reservations;
    }


    @Override

    public Reservation getOne(int id) {
        Reservation reservation = null;
        String query = "SELECT * FROM reservation WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    reservation = new Reservation();
                    reservation.setId(resultSet.getInt("id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving reservation with ID " + id, e);
        }
        return reservation;
    }

    public abstract void delete(int id);
}





