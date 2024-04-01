package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Reservation;
import tn.esprit.models.Hotel;
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
                "WHERE idres = " + reservation.getIdres();
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
        String query = "DELETE FROM reservation WHERE idres = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1,reservation.getIdres());
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
                p.setDateretour(rs.getString("Dateretour"));
                p.setClasse(rs.getString("classe"));
                p.setDestinationdepart(rs.getString("destinationdepart"));
                p.setDestinationretour(rs.getString("destinationretour"));
                reservations.add(p);
            }
        } catch (SQLException ex) {

        }
        return reservations;
    }

    @Override

    public Reservation getOne(int idres) {
        Reservation reservation = null;
        String query = "SELECT * FROM reservation WHERE idres = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, idres);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    reservation = new Reservation();
                    reservation.setIdres(resultSet.getInt("idres"));
                    // Assurez-vous de récupérer les autres propriétés de la réservation à partir du résultat de la requête
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving reservation with ID " + idres, e);
        }
        return reservation;
    }
}





