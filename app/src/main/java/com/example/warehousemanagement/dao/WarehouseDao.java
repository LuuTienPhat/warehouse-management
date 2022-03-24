package com.example.warehousemanagement.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.warehousemanagement.entity.WarehouseEntity;

import java.util.List;

@Dao
public interface WarehouseDao {
    @Insert
    void insertOne(WarehouseEntity warehouseEntity);

    @Query("SELECT * FROM warehouse")
    List<WarehouseEntity> getAll();
}
