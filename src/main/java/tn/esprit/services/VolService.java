package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Reservation;
import tn.esprit.models.Vols;
import tn.esprit.models.hotel;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public abstract class VolService implements IService<Vols> {
    Connection cnx = MaConnexion.getInstance().getCnx();
    @Override
    public void add (Vols vols )
    {

        String req = "INSERT INTO vols(nbrescale, nbrplace, duree, datedepart, datearrive, classe, destination, pointdepart, prix) VALUES (" +
                vols.getNbrescale() + "," +
                vols.getNbrplace() + ",'" +
                vols.getDuree() + "','" +
                vols.getDatedepart() + "','" +
                vols.getDatearrive() + "','" +
                vols.getClasse() + "','" +
                vols.getDestination() + "','" +
                vols.getPointdepart() + "'," +
                vols.getPrix() + ")";
        //System.out.println("Executing query: " + req); // Print out the query
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("vol ajouté avec succès");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void update(Vols vols) {
        String req = "UPDATE vols SET nbrescale = ?, nbrplace = ?, duree = ?, datedepart = ?, datearrive = ?, classe = ?, destination = ?, pointdepart = ?, prix = ? WHERE id = ?";
        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setInt(1, vols.getNbrescale());
            st.setInt(2, vols.getNbrplace());
            st.setString(3, vols.getDuree());
            st.setString(4, vols.getDatedepart());
            st.setString(5, vols.getDatearrive());
            st.setString(6, vols.getClasse());
            st.setString(7, vols.getDestination());
            st.setString(8, vols.getPointdepart());
            st.setDouble(9, vols.getPrix());
            st.setInt(10, vols.getId());
            st.executeUpdate();
            System.out.println("vol mis à jour avec succès");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void delete(Vols vols) {
        String query = "DELETE FROM vols WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, vols.getId());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("vol supprimé avec succès");
            } else {
                System.out.println("Aucun vol n'a été supprimée");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression du vol", e);
        }
    }





    @Override
    public List<Vols> getAll() {
        List<Vols> vols = new ArrayList<>();
        try {
            String req = "SELECT * FROM vols";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Vols v = new Vols();
                v.setId(rs.getInt("id"));
                v.setNbrescale(rs.getInt("nbrescale"));
                v.setNbrplace(rs.getInt("nbrplace")); // Correction du nom de la colonne
                v.setDuree(rs.getString("duree")); // Correction du nom de la colonne
                v.setDatedepart(rs.getString("datedepart"));
                v.setDatearrive(rs.getString("datearrive"));
                v.setClasse(rs.getString("classe"));
                v.setDestination(rs.getString("destination"));
                v.setPointdepart(rs.getString("pointdepart"));
                v.setPrix(rs.getDouble("prix"));
                vols.add(v);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Affiche l'erreur
            // Retourne une liste vide en cas d'erreur
            return vols;
        }
        return vols;

    }


    @Override

    public Vols getOne(int id) {
        Vols vols = null;
        String query = "SELECT * FROM vols WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Create a new Vols object
                    vols = new Vols();
                    // Set all relevant fields from the ResultSet
                    vols.setId(resultSet.getInt("id"));
                    vols.setNbrescale(resultSet.getInt("nbrescale"));
                    vols.setNbrplace(resultSet.getInt("nbrplace"));
                    vols.setDuree(resultSet.getString("duree"));
                    vols.setDatedepart(resultSet.getString("datedepart"));
                    vols.setDatearrive(resultSet.getString("datearrive"));
                    vols.setClasse(resultSet.getString("classe"));
                    vols.setDestination(resultSet.getString("destination"));
                    vols.setPointdepart(resultSet.getString("pointdepart"));
                    vols.setPrix(resultSet.getDouble("prix"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving flight with ID " + id, e);
        }
        return vols;
    }


    public List<Integer> getAllFlightIds() throws SQLException {
        List<Integer> flightIds = new ArrayList<>();
        String query = "SELECT id FROM vols";
        try (Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                flightIds.add(id);
            }
        }
        return flightIds;
    }




    public List<String> getAllDestinations() {
        List<String> destinations = new ArrayList<>();
        String query = "SELECT DISTINCT destination FROM vols";
        try (Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String destination = resultSet.getString("destination");
                destinations.add(destination);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving destinations", e);
        }
        return destinations;
    }



    @Override
    public void generatePDF(List<Reservation> reservations, String filePath) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generatePDF'");
    }

    @Override
    public List<hotel> rechercherParNom(String nom) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rechercherParNom'");
    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<Reservation> getReservationByDate(LocalDate dateSelectionnee) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getReservationByDate'");
    }
}