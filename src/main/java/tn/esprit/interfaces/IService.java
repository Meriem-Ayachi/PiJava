package tn.esprit.interfaces;

import tn.esprit.models.Reservation;
import tn.esprit.models.hotel;

import java.time.LocalDate;
import java.util.List;

public interface IService <T>{
    //CRUD

    //1
    void add(T t );
    //2
    void update(T t);
    //3


    void generatePDF(List<Reservation> reservations, String filePath);


    List<hotel> rechercherParNom(String nom);

    void delete(T t);

    void delete(int id);



    //4 : All


    List<T> getAll();

    //5 : One
    T getOne(int id);

    List<Reservation> getReservationByDate(LocalDate dateSelectionnee);

    //6 : by criteria


}