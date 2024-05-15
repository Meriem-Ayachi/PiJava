package tn.esprit.services;
import javafx.scene.control.Alert;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Offres;
import tn.esprit.models.Reservation;
import tn.esprit.models.hotel;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OffresService implements IService <Offres> {
    public Statement ste;

    Connection cnx = MaConnexion.getInstance().getCnx();


    @Override
    public void add(Offres offre) {
        System.out.println(offre);
        String req = "INSERT INTO offres (`title`, `description`, `published`, `prix`, `lieu`, `image`, `created_at`, `vol_id`) VALUES (?, ?, ?, ?,?,?,?,?)";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setString(1, offre.getTitle());
            pre.setString(2, offre.getDescription());
            pre.setBoolean(3, offre.isPublished());
            pre.setDouble(4, offre.getPrix());
            pre.setString(5, offre.getLieu());
            pre.setString(6, offre.getImage());
            pre.setDate(7, offre.getCreated_at());
            pre.setInt(8, offre.getVolId());
            pre.executeUpdate();
            System.out.println("ajout avec succes");
        } catch (SQLException e) {
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
    public void update(Offres offres) {
        try {
            String req = "update offres set id=?, title=?, description=?, published=?, prix=?, lieu=?, image=?, created_at=? where id=? ";
            PreparedStatement pre = cnx.prepareStatement(req);
            pre.setInt(1, offres.getId()); // Utilisation de l'index 1 pour id
            pre.setString(2, offres.getTitle());
            pre.setString(3, offres.getDescription()); // Utilisation de l'index 3 pour description
            pre.setBoolean(4, offres.isPublished()); // Utilisation de l'index 4 pour published
            pre.setDouble(5, offres.getPrix()); // Utilisation de l'index 5 pour prix
            pre.setString(6, offres.getLieu()); // Utilisation de l'index 6 pour lieu
            pre.setString(7, offres.getImage()); // Utilisation de l'index 7 pour image
            pre.setDate(8, offres.getCreated_at()); // Utilisation de l'index 8 pour created_at
            pre.setInt(9, offres.getId()); // Utilisation de l'index 9 pour l'ID de mise à jour
            pre.executeUpdate();
            System.out.println("Mise à jour avec succès");
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    @Override
    public void delete(int id) {
        try {
            String req = "delete from offres where id =? ";
            PreparedStatement pre = cnx.prepareStatement(req);
            pre.setInt(1, id);
            pre.executeUpdate();
            System.out.println("suppression avec succes");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Offres> getAll() {
        try {
            List<Offres> off = new ArrayList<>();
            String req = "select * from offres";
            ste = cnx.createStatement();
            ResultSet res = ste.executeQuery(req);
            while (res.next()) {
                Offres p = new Offres(res.getInt(1), res.getString("title"), res.getString("description"), res.getBoolean("published"), res.getDouble(5), res.getString("lieu"), res.getString("image"), res.getDate("created_at"));
                p.setVolId(res.getInt("vol_id"));
                off.add(p);
            }
            return off;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Offres getOne(int id) {
        String req = "SELECT * FROM offres WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Offres off = new Offres();
                off.setId(rs.getInt("id"));
                off.setTitle(rs.getString("title"));
                off.setDescription(rs.getString("description"));
                off.setPublished(rs.getBoolean("published"));
                off.setPrix(rs.getDouble("prix"));
                off.setLieu(rs.getString("lieu"));
                off.setImage(rs.getString("image"));
                off.setCreated_at(rs.getDate("created_at"));
                off.setVolId(rs.getInt("vol_id"));
                return off;
            } else {
                System.out.println("erreur");
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Offres> triParCritere(String critere) throws SQLException {
        if (critere == null) {
            // Si le critère est null, retourner la liste non triée
            return getAll();
        }

        switch (critere) {
            case "title":
                return getAll().stream().sorted(Comparator.comparing(Offres::getTitle)).collect(Collectors.toList());
            case "prix":
                return getAll().stream().sorted(Comparator.comparing(Offres::getPrix)).collect(Collectors.toList());

            case "created_at":
                return getAll().stream().sorted(Comparator.comparing(Offres::getCreated_at)).collect(Collectors.toList());
            default:
                return getAll();
        }
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
    public void delete(Offres t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<Reservation> getReservationByDate(LocalDate dateSelectionnee) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getReservationByDate'");
    }


}
