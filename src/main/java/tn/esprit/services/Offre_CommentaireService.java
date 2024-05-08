package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Offre_Commentaire;
import tn.esprit.models.Offres;
import tn.esprit.models.Reservation;
import tn.esprit.models.hotel;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Offre_CommentaireService implements IService<Offre_Commentaire> {
    Connection cnx = MaConnexion.getInstance().getCnx();
    public Statement ste;


    @Override

    public void add(Offre_Commentaire offreCommentaire) {
        String req = "INSERT INTO offre_commentaire (avis, created_at, offres_id,active) VALUES (?, ?, ?,?)";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setString(1, offreCommentaire.getAvis());
            pre.setDate(2, offreCommentaire.getCreated_at());
            pre.setInt(3, offreCommentaire.getOffres_id());
            pre.setBoolean(4, offreCommentaire.isActive());
            pre.executeUpdate();
            System.out.println("ajout avec succès");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Offre_Commentaire offreCommentaire) {

    }

    @Override
    public void delete(int id) {
        try {
            String req = "delete from offres where id =? ";
            PreparedStatement pre = cnx.prepareStatement(req);
            pre.setInt(1,id);
            pre.executeUpdate();
            System.out.println("suppression avec succes");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Offre_Commentaire> getAll() {


            List<Offre_Commentaire> comm = new ArrayList<>();
            String req = "SELECT * FROM offre_commentaire ";
            try {
                ste = cnx.createStatement();
                ResultSet res = ste.executeQuery(req);
                while (res.next()) {
                    Offre_Commentaire commentaire = new Offre_Commentaire(
                            res.getInt("id"),
                            res.getString("avis"),
                            res.getDate("created_at"),
                            res.getInt("offres_id"),
                            res.getBoolean("active"));

                    comm.add(commentaire);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return comm;
        }

    public List<Offre_Commentaire> getAll_byOffreId(int offre_Id) throws SQLException {


        List<Offre_Commentaire> comm = new ArrayList<>();
        String req = "SELECT * FROM offre_commentaire WHERE offres_id = ?";
        PreparedStatement pre = cnx.prepareStatement(req);
        pre.setInt(1, offre_Id);
        try (ResultSet res = pre.executeQuery()) {
            while (res.next()) {
                Offre_Commentaire commentaire = new Offre_Commentaire(
                        res.getInt("id"),
                        res.getString("avis"),
                        res.getDate("created_at"),
                        res.getInt("offres_id"),
                        res.getBoolean("active"));

                comm.add(commentaire);
            }
        }
        return comm;
    }



    @Override
    public Offre_Commentaire getOne(int id) {
        return null;
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
    public void delete(Offre_Commentaire t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }


    @Override
    public List<Reservation> getReservationByDate(LocalDate dateSelectionnee) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getReservationByDate'");
    }
}
