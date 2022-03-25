package com.example.warehousemanagement.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.warehousemanagement.entity.WarehouseEntity;

import java.util.List;

@Dao
public interface WarehouseDao {
    @Insert
    void insertOne(WarehouseEntity warehouseEntity);

    @Update
    void updateOne(WarehouseEntity warehouse);

    @Delete
    void deleteOne(WarehouseEntity warehouse);

    @Query("SELECT * FROM warehouse")
    List<WarehouseEntity> getAll();
}
