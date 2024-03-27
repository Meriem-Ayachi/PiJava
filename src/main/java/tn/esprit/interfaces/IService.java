package tn.esprit.interfaces;

import java.util.List;

public interface IService <T>{
    //CRUD

    //1
    void add(T t );
    //2
    void update(T t);
    //3
    void delete(T t);
    //4 : All
    List<T> getAll();

    //5 : One
    T getOne(int id);

    //6 : by criteria


}
