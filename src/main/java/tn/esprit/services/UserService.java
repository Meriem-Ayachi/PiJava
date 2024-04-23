package tn.esprit.services;

import com.google.gson.Gson;

import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.interfaces.IService;
import tn.esprit.models.User;
import tn.esprit.util.MaConnexion;
import org.json.*;

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
            ps.setInt(4, o.getIs_verified());
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
        String req = "update User set email=?, roles=?, is_verified=?, nom=? , prenom=? ,num_tel=? where id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            String rolesJson = new Gson().toJson(o.getRoles());
            ps.setString(1, o.getEmail());
            ps.setString(2, rolesJson);
            ps.setInt(3, o.getIs_verified());
            ps.setString(4, o.getNom());
            ps.setString(5, o.getPrenom());
            ps.setInt(6, o.getNum_tel());
            ps.setInt(7, o.getId());


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
                String[] role = {rs.getString(3)};
                user.setRoles(role);
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

        String req = "select id, email, roles, is_verified, prenom, nom, num_tel from user where id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,id);
            
            ResultSet res = ps.executeQuery();
            if (res.next()){
                User user = new User();
                user.setId(res.getInt("id"));
                user.setEmail(res.getString("email"));

                String rolesObj = res.getString("roles");
                String role = extractUserRole(rolesObj);
                String[] roles = {role};
                user.setRoles(roles);

                user.setIs_verified(res.getInt("is_verified"));
                user.setPrenom(res.getString("prenom"));
                user.setNom(res.getString("nom"));
                user.setNum_tel(res.getInt("num_tel"));
                return user;
            }else{
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    
    public boolean emailExists(String email) { 
        String req = "SELECT * FROM user WHERE email=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // true if email exists, false otherwise
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean verifierUtilisateur(String email, String password){
        String req = "SELECT * FROM user WHERE email=?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Récupérer le mot de passe haché de la base de données
                    String hashedPasswordFromDB = resultSet.getString("password");

                    // Vérifier si le mot de passe fourni correspond au mot de passe haché
                    return BCrypt.checkpw(password, hashedPasswordFromDB);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getUtilisateurid(String email){
        String req = "SELECT id FROM user WHERE email=?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getUtilisateurRole(String email){
        String req = "SELECT roles FROM user WHERE email=?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String rolesObj = resultSet.getString("roles");
                    return extractUserRole(rolesObj);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private String extractUserRole(String rolesJson){
        try {
            JSONArray roleArray = new JSONArray(rolesJson);
            String role = roleArray.get(0).toString();
            return role;
        } catch (Exception e) {
            System.err.println("Couldnt convert String to JSONArray");
            return null;
        }
    }
}
