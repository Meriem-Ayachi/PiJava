package tn.esprit.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IService <T>{
    //CRUD

    //1
    void add(T t ) throws SQLException;
    //2
    void update(T t) throws SQLException;
    //3
    void delete(int id) throws SQLException;
    //4 : All
    List<T> getAll() throws SQLException;

    //5 : One
    T getOne(int id);

    //6 : by criteria


}
