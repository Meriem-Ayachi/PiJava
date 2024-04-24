package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Reclamation;
import tn.esprit.models.Reclamation_Commentaire;
import tn.esprit.util.MaConnexion;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Reclamation_CommentaireService implements IService<Reclamation_Commentaire> {

    //attr
    Connection cnx = MaConnexion.getInstance().getCnx();
    @Override
    public void add(Reclamation_Commentaire commentaire) {
        String req = "INSERT INTO `reclamation_commentaire`(`contenu`, `date_creation`, `reclamation_id`,`user_id`) VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, commentaire.getContenu());
            ps.setTimestamp(2, commentaire.getDate_creation());
            ps.setInt(3, commentaire.getReclamation_id());
            ps.setInt(4, commentaire.getUser_id());

            ps.executeUpdate();

            System.out.println("Commentaire ajouté avec succés!");

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(Reclamation_Commentaire reclamation) {

        String req = "update reclamation_commentaire set contenu=? , date_creation=? , reclamation_id=? , user_id=? where id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, reclamation.getContenu());
            ps.setTimestamp(2, reclamation.getDate_creation());
            ps.setInt(3, reclamation.getReclamation_id());
            ps.setInt(4, reclamation.getUser_id());
            ps.setInt(5, reclamation.getId());


            ps.executeUpdate();
            System.out.println("Commentaire modifié avec succès");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(int id) {

        String req = "DELETE FROM reclamation_commentaire WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Commentaire supprimé avec succès");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Reclamation_Commentaire> getAll() {
        List<Reclamation_Commentaire> commentaires = new ArrayList<>();

        String req = "select * from reclamation_commentaire";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                Reclamation_Commentaire commentaire = new Reclamation_Commentaire();
                commentaire.setId(rs.getInt(1));
                commentaire.setContenu(rs.getString(2));
                commentaire.setDate_creation(rs.getTimestamp(3));
                commentaire.setReclamation_id(rs.getInt(4));
                commentaire.setUser_id(rs.getInt(5));


                commentaires.add(commentaire);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return commentaires;    }

    @Override
    public Reclamation_Commentaire getOne(int id) {
        String req = "SELECT * FROM reclamation_commentaire WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Reclamation_Commentaire commentaire = new Reclamation_Commentaire();
                commentaire.setId(rs.getInt(1));
                commentaire.setContenu(rs.getString(2));
                commentaire.setDate_creation(rs.getTimestamp(3));
                commentaire.setReclamation_id(rs.getInt(4));
                commentaire.setUser_id(rs.getInt(5));
                return commentaire;
            } else {
                System.out.println("Commentaire non trouvé");
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Reclamation_Commentaire> getAllByReclamationId(int reclamationId) {
        List<Reclamation_Commentaire> commentaires = new ArrayList<>();

        String req = "SELECT * FROM reclamation_commentaire WHERE reclamation_id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, reclamationId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reclamation_Commentaire commentaire = new Reclamation_Commentaire();
                commentaire.setId(rs.getInt(1));
                commentaire.setContenu(rs.getString(2));
                commentaire.setDate_creation(rs.getTimestamp(3));
                commentaire.setReclamation_id(rs.getInt(4));
                commentaire.setUser_id(rs.getInt(5));
                commentaires.add(commentaire);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return commentaires;
    }


}
