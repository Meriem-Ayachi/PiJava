package tn.esprit.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Voiture;
import tn.esprit.util.MaConnexion;

public class VoitureService implements IService <Voiture> {

    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void add(Voiture v) {
        String req = "INSERT INTO `voiture` (`couleur`, `marque`, `model`, `energy`, `capacite`, `image_file_name`) VALUES(?,?,?,?,?,?);";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, v.getCouleur());
            ps.setString(2, v.getMarque());
            ps.setString(3, v.getModel());
            ps.setString(4, v.getEnergy());
            ps.setInt(5, v.getCapacite());
            ps.setString(6, v.getImage_file_name());

            ps.executeUpdate();

            System.out.println("Voiture ajoutée avec succés!");

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(Voiture v) {
        String req = "UPDATE `voiture` SET `couleur`=?, `marque`=?, `model`=?, `energy`=?, `capacite`=?, `image_file_name`=? WHERE `id`=?;";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, v.getCouleur());
            ps.setString(2, v.getMarque());
            ps.setString(3, v.getModel());
            ps.setString(4, v.getEnergy());
            ps.setInt(5, v.getCapacite());
            ps.setString(6, v.getImage_file_name());
            ps.setInt(7, v.getId());

            ps.executeUpdate();

            System.out.println("Voiture modifier avec succés!");

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(int id) {
        String req = "DELETE FROM voiture WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Voiture supprimé avec succès");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Voiture> getAll() {
        List<Voiture> voitures = new ArrayList<>();

        String req = "select * from voiture";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Voiture voiture = new Voiture();
                voiture.setId(res.getInt(1));
                voiture.setCouleur(res.getString(2));
                voiture.setMarque(res.getString(3));
                voiture.setModel(res.getString(4));
                voiture.setEnergy(res.getString(5));
                voiture.setCapacite(res.getInt(6));
                voiture.setImage_file_name(res.getString(7));

                voitures.add(voiture);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return voitures;
    }

    @Override
    public Voiture getOne(int id) {

        String req = "select * from voiture where id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,id);
            
            ResultSet res = ps.executeQuery();
            if (res.next()){
                Voiture voiture = new Voiture();
                voiture.setId(res.getInt(1));
                voiture.setCouleur(res.getString(2));
                voiture.setMarque(res.getString(3));
                voiture.setModel(res.getString(4));
                voiture.setEnergy(res.getString(5));
                voiture.setCapacite(res.getInt(6));
                voiture.setImage_file_name(res.getString(7));
                return voiture;
            }else{
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
}