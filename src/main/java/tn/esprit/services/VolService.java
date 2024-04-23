package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Vols;
import tn.esprit.util.MaConnexion;

import java.sql.*;
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
    public void update (Vols vols )
    {
        String req = "UPDATE vols SET " +
                "nbrescale = '" + vols.getNbrescale() + "', " +
                "nbrplace = '" + vols.getNbrplace() + "', " +
                "duree = '" + vols.getDuree() + "', " +
                "datedepart = '" + vols.getDatedepart() + "', " +
                "datearrive = '" + vols.getDatearrive() + "', " +
                "classe = " + vols.getClasse() + " " +
                "destination = " + vols.getDestination() + " " +
                "pointdepart = " + vols.getPointdepart() + " " +
                "prix = " + vols.getPrix() + " " +
                "WHERE id = " + vols.getId();
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("vol mis à jour avec succès");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void delete(Vols vols) {
        String query = "DELETE FROM vols WHERE id = ";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1,vols.getId());
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
                    vols = new Vols();
                    vols.setId(resultSet.getInt("id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving reservation with ID " + id, e);
        }
        return vols;

    }

    public abstract void delete(int id);
}





