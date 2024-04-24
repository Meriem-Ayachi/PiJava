package tn.esprit;

import org.w3c.dom.ls.LSOutput;
import tn.esprit.util.MaConnexion;
import tn.esprit.models.Vols;
import tn.esprit.services.VolService;
import java.util.ArrayList;
import java.util.List;


import java.sql.SQLOutput;


public class Main {
    public static void main(String[] args)
    {
        MaConnexion mac = MaConnexion.getInstance();
        MaConnexion mac1 = MaConnexion.getInstance();

        VolService vol = new VolService() {


        };

       // Vols vol1 = new Vols(10,10,"000","000","000", "first", "tokyo", "tokyo",1111.1);
        //vol.add(vol1);

        List<Vols> allVols = vol.getAll();
        for (Vols v : allVols) {
            System.out.println(v);
        }

    }


}
