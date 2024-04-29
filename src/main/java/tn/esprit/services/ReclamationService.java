package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Reclamation;
import tn.esprit.util.MaConnexion;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReclamationService  implements IService<Reclamation> {

    //attr
    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void add(Reclamation reclamation) {
        String req = "INSERT INTO `reclamation`(`sujet`, `description`, `datesoumission`, `est_traite` , `user_id`) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, reclamation.getSujet());
            ps.setString(2, reclamation.getDescription());
            ps.setTimestamp(3, reclamation.getDatesoummission());
            ps.setByte(4, reclamation.getEst_traite());
            ps.setInt(5, reclamation.getUser_id());

            ps.executeUpdate();

            System.out.println("Réclamation ajoutée avec succés!");

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    @Override
    public void update(Reclamation reclamation) {

        String req = "update reclamation set sujet=? , description=? , datesoumission=? , est_traite=? ,user_id=? where id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, reclamation.getSujet());
            ps.setString(2, reclamation.getDescription());
            ps.setTimestamp(3, reclamation.getDatesoummission());
            ps.setByte(4, reclamation.getEst_traite());
            ps.setInt(5, reclamation.getUser_id());
            ps.setInt(6, reclamation.getId());


            ps.executeUpdate();
            System.out.println("Réclamation modifiée avec succès");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(int id) {
        String req = "DELETE FROM reclamation WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Réclamation supprimée avec succès");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public List<Reclamation> getAll() {
        List<Reclamation> reclamations = new ArrayList<>();

        String req = "select * from reclamation";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt(1));
                reclamation.setSujet(rs.getString(2));
                reclamation.setDescription(rs.getString(3));
                reclamation.setDatesoummission(rs.getTimestamp(4));
                reclamation.setEst_traite(rs.getByte(5));
                reclamation.setUser_id(rs.getInt(6));


                reclamations.add(reclamation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reclamations;
    }


    @Override
    public Reclamation getOne(int id) {
        String req = "SELECT * FROM reclamation WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt(1));
                reclamation.setSujet(rs.getString(2));
                reclamation.setDescription(rs.getString(3));
                reclamation.setDatesoummission(rs.getTimestamp(4));
                reclamation.setEst_traite(rs.getByte(5));
                reclamation.setUser_id(rs.getInt(6));
                return reclamation;
            } else {
                System.out.println("Réclamation non trouvée");
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Reclamation> getAllByUserId(int userId) {
        List<Reclamation> reclamations = new ArrayList<>();

        String req = "SELECT * FROM reclamation WHERE user_id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt("id"));
                reclamation.setSujet(rs.getString("sujet"));
                reclamation.setDescription(rs.getString("description"));
                reclamation.setDatesoummission(rs.getTimestamp("datesoumission"));
                reclamation.setEst_traite(rs.getByte("est_traite"));
                reclamation.setUser_id(rs.getInt("user_id"));

                reclamations.add(reclamation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reclamations;
    }

    public List<Reclamation> recherche (String nom , String prenom , Byte estTraite)
    {
        List<Reclamation> reclamations = new ArrayList<>();
        String req = "SELECT rec.* FROM reclamation rec";
        req += " INNER JOIN user u ON rec.user_id = u.id";

        boolean firstValue = true;

        // Add conditions based on parameters
        if (nom != null && !nom.isEmpty()) {
            if (firstValue)
                {req += " WHERE nom LIKE ?";
                    firstValue = false;
                }
            else
                req += " AND nom LIKE ?";
            nom = "%" + nom + "%";
        }
        if (prenom != null && !prenom.isEmpty()) {
            if (firstValue)
            {req += " WHERE prenom LIKE ?";
                firstValue = false;
            }
            else
                req += " AND prenom LIKE ?";
            prenom = "%" + prenom + "%";
        }
        if (estTraite != null) {
            if (firstValue)
            {req += " WHERE est_traite = ?";
                firstValue = false;
            }
            else
                req += " AND est_traite = ?";
        }

        // Prepare and execute the query
        try {
            PreparedStatement stmt = cnx.prepareStatement(req);
            int paramIndex = 1;
            if (nom != null && !nom.isEmpty()) {
                stmt.setString(paramIndex, nom);
                paramIndex++;
            }
            if (prenom != null && !prenom.isEmpty()) {
                stmt.setString(paramIndex, prenom);
                paramIndex++;
            }
            if (estTraite != null) {
                stmt.setByte(paramIndex, estTraite);
                paramIndex++;
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt(1));
                reclamation.setSujet(rs.getString(2));
                reclamation.setDescription(rs.getString(3));
                reclamation.setDatesoummission(rs.getTimestamp(4));
                reclamation.setEst_traite(rs.getByte(5));
                reclamation.setUser_id(rs.getInt(6));


                reclamations.add(reclamation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reclamations;

    }



}


