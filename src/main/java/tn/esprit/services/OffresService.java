package tn.esprit.services;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Offres;

import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffresService implements IService <Offres>{
    public Statement ste;

    Connection cnx = MaConnexion.getInstance().getCnx();


    @Override
    public void add(Offres offre) throws SQLException {
        System.out.println(offre);
        String req = "INSERT INTO offres (`title`, `description`, `published`, `prix`, `lieu`, `image`, `created_at`) VALUES (?, ?, ?,?,?,?,?)";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setString(1, offre.getTitle());
            pre.setString(2,offre.getDescription());
            pre.setBoolean(3,offre.isPublished());
            pre.setDouble(4,offre.getPrix());
            pre.setString(5,offre.getLieu());
            pre.setString(6,offre.getImage());
            pre.setDate(7,offre.getCreated_at());
            pre.executeUpdate();
            System.out.println("ajout avec succes");
        }
    }

    @Override
    public void update(Offres offres) throws SQLException {

        String req = "update offres set title=?,description=?,published=?,prix=?,lieu=?,image=?,created_at=? where id=? ";
        PreparedStatement pre = cnx.prepareStatement(req);
        pre.setString(2,offres.getTitle());
        pre.setString(3,offres.getDescription());
        pre.setBoolean(4,offres.isPublished());
        pre.setDouble(5,offres.getPrix());
        pre.setString(6,offres.getLieu());
        pre.setString(7,offres.getImage());
        pre.setDate(8,offres.getCreated_at());
        pre.setInt(1,offres.getId());
        pre.executeUpdate();
        System.out.println("mise a jour avec succes");


    }

    @Override
    public void delete(int id ) throws SQLException {
        String req = "delete from offres where id =? ";
        PreparedStatement pre = cnx.prepareStatement(req);
        pre.setInt(1,id);
        pre.executeUpdate();
        System.out.println("suppression avec succes");

    }

    @Override
    public List<Offres> getAll() throws SQLException {
        List<Offres> off = new ArrayList<>();
        String req = "select * from offres";
        ste = cnx.createStatement();
        ResultSet res =ste.executeQuery(req);
        while (res.next()){
            Offres p = new Offres(res.getInt(1),res.getString("title"),res.getString("description") ,res.getBoolean("published"),res.getDouble(5),res.getString("lieu"),res.getString("image"),res.getDate("created_at"));

            off.add(p);
        }
        return off;
    }


    @Override
    public Offres getOne(int id) {
        String req = "SELECT * FROM Offres WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Offres off = new Offres();
                off.setId(rs.getInt(1));
                off.setTitle(rs.getString(2));
                off.setDescription(rs.getString(3));
                off.setPublished(rs.getBoolean(4));
                off.setPrix(rs.getDouble(5));
                off.setLieu(rs.getString(6));
                off.setImage(rs.getString(7));
                off.setCreated_at(rs.getDate(8));
                return off;
            } else {
                System.out.println("erreur");
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
