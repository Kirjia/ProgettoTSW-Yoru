package com.yoru.DBServices;


import java.sql.SQLException;
import java.util.Collection;


public interface GenericDBOp<T>{

    Collection<T> getAll() throws SQLException;
    T getById(int id)throws SQLException;
    boolean insert(T entity)throws SQLException;
    boolean update(T entity)throws SQLException;
    boolean remove(T entity)throws SQLException;

}
