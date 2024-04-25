package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Offre_Commentaire;
import tn.esprit.models.Offres;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Offre_CommentaireService implements IService<Offre_Commentaire> {
    Connection cnx = MaConnexion.getInstance().getCnx();
    public Statement ste;


    @Override

    public void add(Offre_Commentaire offreCommentaire) throws SQLException {
        String req = "INSERT INTO Offre_Commentaire (avis, created_at, offres_id) VALUES (?, ?, ?)";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setString(1, offreCommentaire.getAvis());
            pre.setDate(2, offreCommentaire.getCreated_at());
            pre.setInt(3, offreCommentaire.getOffres_id());
            pre.executeUpdate();
            System.out.println("ajout avec succès");
        }
    }


    @Override
    public void update(Offre_Commentaire offreCommentaire) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {
        String req = "delete from offres where id =? ";
        PreparedStatement pre = cnx.prepareStatement(req);
        pre.setInt(1,id);
        pre.executeUpdate();
        System.out.println("suppression avec succes");

    }

    @Override
    public List<Offre_Commentaire> getAll() throws SQLException {


            List<Offre_Commentaire> comm = new ArrayList<>();
            String req = "SELECT * FROM Offre_Commentaire ";
            try (ResultSet res = ste.executeQuery(req)) {
                while (res.next()) {
                    Offre_Commentaire commentaire = new Offre_Commentaire(
                            res.getInt("id"),
                            res.getString("avis"),
                            res.getDate("created_at"),
                            res.getInt("offres_id"));

                    comm.add(commentaire);
                }
            }
            return comm;
        }




    @Override
    public Offre_Commentaire getOne(int id) {
        return null;
    }
}
