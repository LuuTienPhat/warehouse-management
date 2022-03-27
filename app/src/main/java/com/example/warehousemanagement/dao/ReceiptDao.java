package com.example.warehousemanagement.dao;

import com.example.warehousemanagement.model.Receipt;

import java.util.List;

public class ReceiptDao implements Dao<Receipt> {
    @Override
    public boolean insertOne(Receipt receipt) {

        return false;
    }

    @Override
    public boolean updateOne(Receipt receipt) {

        return false;
    }

    @Override
    public boolean deleteOne(Receipt receipt) {

        return false;
    }

    @Override
    public List<Receipt> getAll() {
        return null;
    }

    @Override
    public Receipt getOne(String id) {
        return null;
    }
}
