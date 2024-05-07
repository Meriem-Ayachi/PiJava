package tn.esprit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnexion {

    //DB

    final String URL = "jdbc:mysql://localhost:3306/piIntegration";
    final String USR = "root";
    final String PWD = "123456789";

    //att
    private Connection cnx;
    private static MaConnexion instance;

    //constructor
    //singleton step 1
    private MaConnexion(){
        try {
            cnx = DriverManager.getConnection(URL,USR,PWD);
            System.out.println("Connexion établie avec succés");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static MaConnexion getInstance() {
        if(instance == null )
            instance = new MaConnexion();

        return instance;
    }

    public Connection getCnx()
    {
        return cnx;
    }
}