package com.example.warehousemanagement.dao;

import com.example.warehousemanagement.model.Warehouse;

import java.util.List;

public class WarehouseDao implements Dao<Warehouse> {

    @Override
    public boolean insertOne(Warehouse warehouse) {

        return false;
    }

    @Override
    public boolean updateOne(Warehouse warehouse) {

        return false;
    }

    @Override
    public boolean deleteOne(Warehouse warehouse) {

        return false;
    }

    @Override
    public List<Warehouse> getAll() {
        return null;
    }

    @Override
    public Warehouse getOne(String id) {
        return null;
    }
}
