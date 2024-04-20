package tn.esprit.interfaces;

import tn.esprit.models.Reservation;

import java.util.List;

public interface IService <T>{
    //CRUD

    //1
    void add(T t );
    //2
    void update(T t);
    //3


    void generatePDF(List<Reservation> reservations, String filePath);


    void delete(T t);

    void delete(int id);

    //4 : All
    List<T> getAll();

    //5 : One
    T getOne(int id);

    //6 : by criteria


}
