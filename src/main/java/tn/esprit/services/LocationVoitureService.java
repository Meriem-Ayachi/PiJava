package tn.esprit.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Location_Voiture;
import tn.esprit.util.MaConnexion;

public class LocationVoitureService implements IService <Location_Voiture> {

    Connection cnx = MaConnexion.getInstance().getCnx();
   
    @Override
    public void add(Location_Voiture lv) {
        String req = "INSERT INTO `location_voiture` (`prix`,`date_debut`,`datefin`,`type`,`status`,`voiture_id`) VALUES(?,?,?,?,?,?);";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setDouble(1, lv.getPrix());
            ps.setTimestamp(2, lv.getDate_debut());
            ps.setTimestamp(3, lv.getDatefin());
            ps.setString(4, lv.getType());
            ps.setString(5, lv.getStatus());
            ps.setInt(6, lv.getVoiture_id());

            ps.executeUpdate();

            System.out.println("Location Voiture ajoutée avec succés!");

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(Location_Voiture lv) {

    }

    @Override
    public void delete(int id) {
        String req = "DELETE FROM location_voiture WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Location Voiture supprimé avec succès");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Location_Voiture> getAll() {
        return null;
    }

    @Override
    public Location_Voiture getOne(int id) {
        return null;
    }
    
}
