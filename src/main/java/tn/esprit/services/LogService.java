package tn.esprit.services;

import tn.esprit.models.Log;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogService {

    //attr
    Connection cnx = MaConnexion.getInstance().getCnx();
    public void add(Log log) {

        String req = "INSERT INTO historiqueuser(timestamp, type, description, userId) VALUES (?,?,?,?);";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, log.getTimestamp().toString());
            ps.setString(2, log.getType());
            ps.setString(3, log.getDescription());
            ps.setInt(4, log.getUserId());

            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    public void delete(int id) {
        String req = "DELETE FROM historiqueuser WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Log> getAll() {
        List<Log> listLog = new ArrayList<>();

        String req = "select * from historiqueuser";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Log log = new Log();
                log.setLogId(rs.getInt("id"));
                log.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                log.setType(rs.getString("type"));
                log.setDescription(rs.getString("description"));
                log.setUserId(rs.getInt("userId"));
                listLog.add(log);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listLog;
    }


    public List<Log> recherche (String nom , String prenom, String typeLog)
    {
        List<Log> listLog = new ArrayList<>();
        String req = "SELECT l.* FROM historiqueuser l";
        req += " INNER JOIN user u ON l.userId = u.id";

        boolean firstValue = true;

        // Add conditions based on parameters
        if (nom != null && !nom.isEmpty()) {
            if (firstValue)
            {req += " WHERE nom LIKE ?";
                firstValue = false;
            }
            else
                req += " AND nom LIKE ?";
            nom = "%" + nom + "%";
        }
        if (prenom != null && !prenom.isEmpty()) {
            if (firstValue)
            {req += " WHERE prenom LIKE ?";
                firstValue = false;
            }
            else
                req += " AND prenom LIKE ?";
            prenom = "%" + prenom + "%";
        }
        if (typeLog != null && !typeLog.isEmpty()) {
            if (firstValue)
            {req += " WHERE type = ?";
                firstValue = false;
            }
            else
                req += " AND type = ?";
        }

        // Prepare and execute the query
        try {
            PreparedStatement stmt = cnx.prepareStatement(req);
            int paramIndex = 1;
            if (nom != null && !nom.isEmpty()) {
                stmt.setString(paramIndex, nom);
                paramIndex++;
            }
            if (prenom != null && !prenom.isEmpty()) {
                stmt.setString(paramIndex, prenom);
                paramIndex++;
            }
            if (typeLog != null && !typeLog.isEmpty()) {
                stmt.setString(paramIndex, typeLog);
                paramIndex++;
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Log log = new Log();
                log.setLogId(rs.getInt("id"));
                log.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                log.setType(rs.getString("type"));
                log.setDescription(rs.getString("description"));
                log.setUserId(rs.getInt("userId"));
                listLog.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listLog;

    }
}