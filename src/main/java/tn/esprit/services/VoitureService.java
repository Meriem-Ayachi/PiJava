package tn.esprit.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        return null;
    }

    @Override
    public Voiture getOne(int id) {
        return null;
    }
    
}
