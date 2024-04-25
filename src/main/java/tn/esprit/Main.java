package tn.esprit;


import tn.esprit.services.Offre_CommentaireService;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        Offre_CommentaireService of =new Offre_CommentaireService();



        System.out.println(of.getAll());

    }
}