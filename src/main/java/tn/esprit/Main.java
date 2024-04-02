package tn.esprit;

import tn.esprit.models.Reservation;
import tn.esprit.services.Reservationservices;
import tn.esprit.models.hotel;
import tn.esprit.services.Hotelservices;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Main {
    public static void main(String[] args)
    {
        LocalDate currentDate = LocalDate.now();

        Reservation res = new Reservation();

;
        Hotelservices ho = new Hotelservices() {
            @Override
            public void delete(hotel hotel) {

            }
        };
        Reservationservices reserve = new Reservationservices() {
            @Override
            public void delete(Reservation reservation) {

            }

            @Override
            public void delete(int id) {

            }
        };

        hotel hotel = new hotel ("bayrem",10,"Tunis","mauvaise");
        ho.add(hotel);
        int hotelIdToDelete = 10;
        ho.delete(hotelIdToDelete);
        System.out.println(ho.getAll());
        Reservation reservation = new Reservation(17,"2023/04/01", "2024/04/10", "Classe BB", "New York", "Paris", 2);
        if (validerDate("01/04/2022", currentDate)) { // Valider la date

            res.setDatedepart("01/04/2021");


            reserve.add(res);
        }
        int id=1;
        reserve.add(reservation);
        System.out.println(reservation);
        reservation.setDestinationretour("allemagne ");
        reserve.update(reservation);
         reserve.delete(reservation);
         reserve.getOne(id);


        reserve.delete(id);
        List<Reservation> reserveAll = reserve.getAll();


    }

    private static boolean validerDate(String dateSaisie, LocalDate currentDate) {
        try {
            LocalDate rdvDate = LocalDate.parse(dateSaisie, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (rdvDate.isBefore(currentDate)) {
                System.out.println("Erreur : Vous ne pouvez pas saisir une date antérieure à la date actuelle !");
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Erreur : Format de date invalide !");
            return false;
        }}


}