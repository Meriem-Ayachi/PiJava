package tn.esprit;

import tn.esprit.models.Offres;
import tn.esprit.services.OffresService;
import tn.esprit.util.MaConnexion;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        OffresService os = new OffresService();
        Offres j= new Offres("3","b",true,5.2,"safax","vvv",Date.valueOf("2024-03-02"));
        Offres j2 = new Offres("hjj","kjhhh",true,10.2,"beja","llll",Date.valueOf("2024-03-02"));

        os.update(new Offres("test","test",true,1.6,"test","test",Date.valueOf("2023-03-02")));

        System.out.println(os.getOne(10));


    }
}