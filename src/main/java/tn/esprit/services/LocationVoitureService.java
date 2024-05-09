package tn.esprit.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Location_Voiture;
import tn.esprit.models.Reservation;
import tn.esprit.models.hotel;
import tn.esprit.util.MaConnexion;

public class LocationVoitureService implements IService <Location_Voiture> {

    Connection cnx = MaConnexion.getInstance().getCnx();
   
    @Override
    public void add(Location_Voiture lv) {
        String req = "INSERT INTO `location_voiture` (`prix`,`date_debut`,`datefin`,`type`,`status`,`voiture_id`) VALUES(?,?,?,?,?,?);";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setDouble(1, lv.getPrix());
            ps.setDate(2, lv.getDate_debut());
            ps.setDate(3, lv.getDatefin());
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
        String req = "UPDATE `location_voiture` SET `prix`=?, `date_debut`=?, `datefin`=?, `type`=?, `status`=?, `voiture_id`=?, `user_id`=? WHERE `id`=?;";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setDouble(1, lv.getPrix());
            ps.setDate(2, lv.getDate_debut());
            ps.setDate(3, lv.getDatefin());
            ps.setString(4, lv.getType());
            ps.setString(5, lv.getStatus());
            ps.setInt(6, lv.getVoiture_id());
            if (lv.getUser_id() == 0){
                ps.setNull(7, java.sql.Types.INTEGER);
            }else{
                ps.setInt(7, lv.getUser_id());
            }
            ps.setInt(8, lv.getId());

            ps.executeUpdate();

            System.out.println("Voiture modifier avec succés!");

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

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
        List<Location_Voiture> location_Voitures = new ArrayList<>();

        String req = "select * from location_voiture";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                Location_Voiture location_Voiture = new Location_Voiture();
                location_Voiture.setId(res.getInt(1));
                location_Voiture.setPrix(res.getInt(2));
                location_Voiture.setDate_debut(res.getDate(3));
                location_Voiture.setDatefin(res.getDate(4));
                location_Voiture.setType(res.getString(5));
                location_Voiture.setStatus(res.getString(6));
                location_Voiture.setVoiture_id(res.getInt(7));
                location_Voiture.setUser_id(res.getInt(8));

                location_Voitures.add(location_Voiture);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return location_Voitures;
    }

    @Override
    public Location_Voiture getOne(int id) {

        String req = "select * from location_voiture where id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,id);
            
            ResultSet res = ps.executeQuery();
            if (res.next()){
                Location_Voiture location_Voiture = new Location_Voiture();
                location_Voiture.setId(res.getInt(1));
                location_Voiture.setPrix(res.getInt(2));
                location_Voiture.setDate_debut(res.getDate(3));
                location_Voiture.setDatefin(res.getDate(4));
                location_Voiture.setType(res.getString(5));
                location_Voiture.setStatus(res.getString(6));
                location_Voiture.setVoiture_id(res.getInt(7));
                location_Voiture.setUser_id(res.getInt(8));
                return location_Voiture;
            }else{
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Location_Voiture> getAll_UserIdReserved(int userId) {
        List<Location_Voiture> location_Voitures = new ArrayList<>();

        String req = "select * from location_voiture where user_id=? AND status='réservé'";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,userId);
            ResultSet res = ps.executeQuery();


            while (res.next()) {
                Location_Voiture location_Voiture = new Location_Voiture();
                location_Voiture.setId(res.getInt(1));
                location_Voiture.setPrix(res.getInt(2));
                location_Voiture.setDate_debut(res.getDate(3));
                location_Voiture.setDatefin(res.getDate(4));
                location_Voiture.setType(res.getString(5));
                location_Voiture.setStatus(res.getString(6));
                location_Voiture.setVoiture_id(res.getInt(7));
                location_Voiture.setUser_id(res.getInt(8));

                location_Voitures.add(location_Voiture);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return location_Voitures;
    }

    public List<Location_Voiture> getAll_unreserved(){
        List<Location_Voiture> location_Voitures = new ArrayList<>();

        String req = "select * from location_voiture where status='disponible'";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet res = ps.executeQuery();


            while (res.next()) {
                Location_Voiture location_Voiture = new Location_Voiture();
                location_Voiture.setId(res.getInt(1));
                location_Voiture.setPrix(res.getInt(2));
                location_Voiture.setDate_debut(res.getDate(3));
                location_Voiture.setDatefin(res.getDate(4));
                location_Voiture.setType(res.getString(5));
                location_Voiture.setStatus(res.getString(6));
                location_Voiture.setVoiture_id(res.getInt(7));
                location_Voiture.setUser_id(res.getInt(8));

                location_Voitures.add(location_Voiture);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return location_Voitures;

    }

    public List<Location_Voiture> rechercher(String model, String marque, String type, int maxPrix, int minPrix){
        List<Location_Voiture> location_Voitures = new ArrayList<>();
        String req = "SELECT lv.* FROM location_voiture lv";
        req += " INNER JOIN voiture v ON lv.voiture_id = v.id";
        req += " WHERE lv.status = 'disponible'";
        
        // Add conditions based on parameters
        if (model != null && !model.isEmpty()) {
            req += " AND model LIKE ?";
            model = "%" + model + "%";
        }
        if (marque != null && !marque.isEmpty()) {
            req += " AND marque LIKE ?";
            marque = "%" + marque + "%";
        }
        if (type != null && !type.isEmpty()) {
            req += " AND type LIKE ?";
            type = "%" + type + "%";
        }
        if (maxPrix > 0) {
            req += " AND prix <= ?";
        }
        if (minPrix > 0) {
            req += " AND prix >= ?";
        }
    
        // Prepare and execute the query
        try {
            PreparedStatement stmt = cnx.prepareStatement(req);
            int paramIndex = 1;
            if (model != null && !model.isEmpty()) {
                stmt.setString(paramIndex, model);
                paramIndex++;
            }
            if (marque != null && !marque.isEmpty()) {
                stmt.setString(paramIndex, marque);
                paramIndex++;
            }
            if (type != null && !type.isEmpty()) {
                stmt.setString(paramIndex, type);
                paramIndex++;
            }
            if (maxPrix > 0) {
                stmt.setInt(paramIndex, maxPrix);
                paramIndex++;
            }
            if (minPrix > 0) {
                stmt.setInt(paramIndex, minPrix);
            }
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                Location_Voiture location_Voiture = new Location_Voiture();
                location_Voiture.setId(res.getInt(1));
                location_Voiture.setPrix(res.getInt(2));
                location_Voiture.setDate_debut(res.getDate(3));
                location_Voiture.setDatefin(res.getDate(4));
                location_Voiture.setType(res.getString(5));
                location_Voiture.setStatus(res.getString(6));
                location_Voiture.setVoiture_id(res.getInt(7));
                location_Voiture.setUser_id(res.getInt(8));

                location_Voitures.add(location_Voiture);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return location_Voitures;
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
    public void delete(Location_Voiture t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<Reservation> getReservationByDate(LocalDate dateSelectionnee) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getReservationByDate'");
    }
    

}
