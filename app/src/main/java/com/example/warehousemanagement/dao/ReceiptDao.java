package com.example.warehousemanagement.dao;

import androidx.room.Insert;
import androidx.room.Query;

import com.example.warehousemanagement.entity.ReceiptEntity;

import java.util.List;

public interface ReceiptDao {
    @Insert
    void insertOne(ReceiptEntity receiptEntity);

    @Query("SELECT * FROM receipt")
    List<ReceiptDao> getAll();
}
