package com.example.warehousemanagement.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.warehousemanagement.entity.ProductEntity;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insertOne(ProductEntity product);

    @Insert
    void insertAll(List<ProductEntity> products);

    @Update
    void updateOne(ProductEntity product);

    @Delete
    void deleteOne(ProductEntity product);

    @Query("SELECT * FROM VATTU")
    List<ProductEntity> getAll();
}
