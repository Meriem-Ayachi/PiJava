package tn.esprit.services;

import com.google.gson.Gson;
import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.interfaces.IService;
import tn.esprit.models.User;
import tn.esprit.util.MaConnexion;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService <User> {

    //attr
    Connection cnx = MaConnexion.getInstance().getCnx();
    @Override
    public void add(User o) {

        String password = o.getPassword();
        String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt());


        String req = "INSERT INTO user(email, roles, password, is_verified, nom, prenom, num_tel) VALUES (?,?,?,?,?,?,?);";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, o.getEmail());
            // Convertir les rôles en JSON
            String rolesJson = new Gson().toJson(o.getRoles());
            ps.setString(2, rolesJson);
            ps.setString(3, encryptedPassword);
            ps.setByte(4, o.getIs_verified());
            ps.setString(5, o.getNom());
            ps.setString(6, o.getPrenom());
            ps.setInt(7, o.getNum_tel());

            ps.executeUpdate();

            System.out.println("User ajoutée avec succès!");

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(User o) {
        String req = "update User set email=?, roles=?, password=?, is_verified=?, nom=? , prenom=? ,num_tel=? where id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            String rolesJson = new Gson().toJson(o.getRoles());
            ps.setString(1, o.getEmail());
            ps.setString(2, rolesJson);
            ps.setString(3, o.getPassword());
            ps.setByte(4, o.getIs_verified());
            ps.setString(5, o.getNom());
            ps.setString(6, o.getPrenom());
            ps.setInt(7, o.getNum_tel());
            ps.setInt(8, o.getId());


            ps.executeUpdate();
            System.out.println("Utilisateur modifiée avec succès");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    @Override
    public void delete(int id) {
        String req = "DELETE FROM user WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Utilisateur supprimée avec succès");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<User> getAll() {
        List<User> listuser = new ArrayList<>();

        String req = "select * from User";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                User user = new User();
                user.setId(rs.getInt(1));
                user.setEmail(rs.getString(2));
                user.setRoles(rs.getString(3));
                user.setPassword(rs.getString(4));
                user.setIs_verified(rs.getByte(5));
                user.setNom(rs.getString(6));
                user.setPrenom(rs.getString(7));
                user.setNum_tel(rs.getInt(8
                ));



                listuser.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listuser;
    }

    @Override
    public User getOne(int id) {
        return null;
    }
}
