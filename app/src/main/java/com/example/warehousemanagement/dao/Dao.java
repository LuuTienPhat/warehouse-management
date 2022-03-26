package com.example.warehousemanagement.dao;

import java.util.List;

public interface Dao<T> {

    boolean insertOne(T t);

    boolean updateOne(T t);

    boolean deleteOne(T t);

    List<T> getAll();

    T getOne(String id);
}
