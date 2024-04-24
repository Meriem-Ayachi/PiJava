package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Reservation;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;


public abstract class Reservationservices implements IService<Reservation> {
    public IService<Reservation> reserve;
    Connection cnx = MaConnexion.getInstance().getCnx();
    @Override
    public void add(Reservation reservation) {
        String req = "INSERT INTO reservation(datedepart, dateretour, classe, destinationdepart, destinationretour, nbrdepersonne) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setString(1, reservation.getDatedepart());
            st.setString(2, reservation.getDateretour());
            st.setString(3, reservation.getClasse());
            st.setString(4, reservation.getDestinationdepart());
            st.setString(5, reservation.getDestinationretour());
            st.setInt(6, reservation.getNbrdepersonne());

            int rowsInserted = st.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Réservation ajoutée avec succès");
            } else {
                System.out.println("Échec de l'ajout de la réservation");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de la réservation", e);
        }
    }




    @Override
    public void update (Reservation reservation )
    {
        String req = "UPDATE reservation SET " +
                "datedepart = '" + reservation.getDatedepart() + "', " +
                "dateretour = '" + reservation.getDateretour() + "', " +
                "classe = '" + reservation.getClasse() + "', " +
                "destinationdepart = '" + reservation.getDestinationdepart() + "', " +
                "destinationretour = '" + reservation.getDestinationretour() + "', " +
                "nbrdepersonne = " + reservation.getNbrdepersonne() + " " +
                "WHERE id = " + reservation.getId();
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Réservation mise à jour avec succès");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void generatePDF(List<Reservation> reservations, String filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Liste des réservations");
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA, 10);
                int y = 700;
                for (Reservation reservation : reservations) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, y);
                    contentStream.showText("ID: " + reservation.getId());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Date de départ: " + reservation.getDatedepart());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Date de retour: " + reservation.getDateretour());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Classe: " + reservation.getClasse());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Destination de départ: " + reservation.getDestinationdepart());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Destination de retour: " + reservation.getDestinationretour());
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("Nombre de personnes: " + reservation.getNbrdepersonne());
                    contentStream.endText();
                    y -= 100;
                }
            }

            document.save(filePath);
            System.out.println("Document PDF généré avec succès à l'emplacement : " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void delete(int id) {
        String query = "DELETE FROM reservation WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Réservation supprimée avec succès");
            } else {
                System.out.println("Aucune réservation n'a été supprimée");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la suppression de la réservation", e);
        }
    }













    @Override
    public List<Reservation> getAll() {
        List<Reservation> reservations = new ArrayList<>();
        try {
            String req = "SELECT * FROM reservation";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Reservation p = new Reservation();
                p.setDatedepart(rs.getString("datedepart"));
                p.setDateretour(rs.getString("dateretour")); // Correction du nom de la colonne
                p.setClasse(rs.getString("classe")); // Correction du nom de la colonne
                p.setDestinationdepart(rs.getString("destinationdepart"));
                p.setDestinationretour(rs.getString("destinationretour"));
                reservations.add(p);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Affiche l'erreur
            // Retourne une liste vide en cas d'erreur
            return reservations;
        }
        return reservations;
    }


    @Override

    public Reservation getOne(int id) {
        Reservation reservation = null;
        String query = "SELECT * FROM reservation WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    reservation = new Reservation();
                    reservation.setId(resultSet.getInt("id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving reservation with ID " + id, e);
        }
        return reservation;
    }


}




