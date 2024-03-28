package tn.esprit;

import tn.esprit.models.Reclamation;
import tn.esprit.models.Reclamation_Commentaire;
import tn.esprit.services.ReclamationService;
import tn.esprit.services.Reclamation_CommentaireService;
import tn.esprit.util.MaConnexion;
import java.sql.Timestamp;


public class Main {
    public static void main(String[] args)
    {
       // MaConnexion mac = MaConnexion.getInstance();

        //Reclamation

        //ReclamationService rs = new ReclamationService();
        //Timestamp dateReclamation = Timestamp.valueOf("2024-03-05 10:55:07");

        //Reclamation reclamation = new Reclamation("test","test", dateReclamation, (byte) 0, 1);
        //rs.add(reclamation);

        //System.out.println(rs.getAll());
        //rs.update(new Reclamation(17,"test2","test2",dateReclamation,(byte) 0, 2));
        //rs.delete(17);

        //----------------------------
        //Commentaire

        Reclamation_CommentaireService rcs = new Reclamation_CommentaireService();
        Timestamp dateCreation = Timestamp.valueOf("2024-03-05 10:55:07");

        //Reclamation_Commentaire commentaire = new Reclamation_Commentaire("test",dateCreation,4,1);
        //rcs.add(commentaire);

        System.out.println(rcs.getAll());


        //rcs.update(new Reclamation_Commentaire(10,"test2",dateCreation,6, 3));
        //rcs.delete(10);


    }
}