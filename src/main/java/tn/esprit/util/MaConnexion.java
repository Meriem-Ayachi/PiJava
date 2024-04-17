package tn.esprit.util;

import java.sql.*;

public class MaConnexion {

    //DB

    final String URL = "jdbc:mysql://localhost:3306/pi";
    final String USR = "root";
    final String PWD = "";

    //att
    private static Connection cnx;
    private static Statement ste;
    private static MaConnexion instance;

    //constructor
    //singleton step 1
    private MaConnexion(){
        try {
            cnx = DriverManager.getConnection(URL,USR,PWD);
            System.out.println("Connexion etablie avec succes");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static MaConnexion getInstance() {
        if(instance == null )
            instance = new MaConnexion();

        return instance;
    }


    public Connection getCnx() {
        return cnx;
    }




}
