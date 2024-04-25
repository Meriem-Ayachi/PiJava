package tn.esprit.services;
import javafx.scene.control.Alert;
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
        catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour : " + e.getMessage());
            // Afficher un message d'erreur dans l'interface
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Une erreur est survenue lors de la mise à jour de l'offre.");
            errorAlert.showAndWait();
        }
    }

    @Override
    public void update(Offres offres) throws SQLException {
        String req = "update offres set id=?, title=?, description=?, published=?, prix=?, lieu=?, image=?, created_at=? where id=? ";
        PreparedStatement pre = cnx.prepareStatement(req);
        pre.setInt(1, offres.getId()); // Utilisation de l'index 1 pour id
        pre.setString(2, offres.getTitle()); // Utilisation de l'index 2 pour title
        pre.setString(3, offres.getDescription()); // Utilisation de l'index 3 pour description
        pre.setBoolean(4, offres.isPublished()); // Utilisation de l'index 4 pour published
        pre.setDouble(5, offres.getPrix()); // Utilisation de l'index 5 pour prix
        pre.setString(6, offres.getLieu()); // Utilisation de l'index 6 pour lieu
        pre.setString(7, offres.getImage()); // Utilisation de l'index 7 pour image
        pre.setDate(8, offres.getCreated_at()); // Utilisation de l'index 8 pour created_at
        pre.setInt(9, offres.getId()); // Utilisation de l'index 9 pour l'ID de mise à jour
        pre.executeUpdate();
        System.out.println("Mise à jour avec succès");
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
