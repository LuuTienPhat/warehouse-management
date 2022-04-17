package com.example.warehousemanagement.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.warehousemanagement.DatabaseHelper;
import com.example.warehousemanagement.model.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class WarehouseDao implements Dao<Warehouse> {
    private SQLiteDatabase db;
    private final SQLiteOpenHelper dbHelper;

    public WarehouseDao(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public void fillData() {
        Warehouse w1 = new Warehouse("K1", "Bình Chánh", "Bình Chánh");
        Warehouse w2 = new Warehouse("K2", "Tân Phú", "Tân Phú");
        Warehouse w3 = new Warehouse("K3", "Thủ Đức", "Thủ Đức");

        insertOne(w1);
        insertOne(w2);
        insertOne(w3);
    }

    @Override
    public boolean insertOne(Warehouse warehouse) {
        db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseHelper.TABLE_WAREHOUSE_ID, warehouse.getId());
            cv.put(DatabaseHelper.TABLE_WAREHOUSE_NAME, warehouse.getName());
            cv.put(DatabaseHelper.TABLE_WAREHOUSE_ADDRESS, warehouse.getAddress());

            db.insert(DatabaseHelper.TABLE_WAREHOUSE, null, cv);
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
            db.close();
        }

        return true;
    }

    @Override
    public boolean updateOne(Warehouse warehouse) {
        db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
//            cv.put(DatabaseHelper.TABLE_WAREHOUSE_ID, warehouse.getId());
            cv.put(DatabaseHelper.TABLE_WAREHOUSE_NAME, warehouse.getName());
            cv.put(DatabaseHelper.TABLE_WAREHOUSE_ADDRESS, warehouse.getAddress());

            db.update(DatabaseHelper.TABLE_WAREHOUSE, cv, DatabaseHelper.TABLE_WAREHOUSE_ID + " = ?", new String[]{String.valueOf(warehouse.getId())});
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
            db.close();
        }

        return true;
    }

    @Override
    public boolean deleteOne(Warehouse warehouse) {
        db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(DatabaseHelper.TABLE_WAREHOUSE, DatabaseHelper.TABLE_WAREHOUSE_ID + " = ?", new String[]{String.valueOf(warehouse.getId())});
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
            db.close();
        }

        return true;
    }

    @Override
    public List<Warehouse> getAll() {
        List<Warehouse> warehouses = new ArrayList<>();
        db = dbHelper.getReadableDatabase();

        String queryString = "SELECT * FROM " + DatabaseHelper.TABLE_WAREHOUSE;

        Cursor cursor = db.rawQuery(queryString, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String address = cursor.getString(2);

            Warehouse warehouse = new Warehouse(id, name, address);
            warehouses.add(warehouse);
        }

        cursor.close();
        db.close();
        return warehouses;
    }

    @Override
    public Warehouse getOne(String id) {
        Warehouse warehouse = null;
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_WAREHOUSE, null, DatabaseHelper.TABLE_WAREHOUSE_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.moveToFirst()) {
            String warehouseId = cursor.getString(0);
            String name = cursor.getString(1);
            String address = cursor.getString(2);

            warehouse = new Warehouse(warehouseId, name, address);
        }

        cursor.close();
        db.close();
        return warehouse;
    }

    @Override
    public List<Warehouse> search(String keyword) {
        List<Warehouse> warehouses = new ArrayList<>();
        db = dbHelper.getReadableDatabase();

        keyword = "%" + keyword + "%";

        String query = "SELECT * FROM " + DatabaseHelper.TABLE_WAREHOUSE + " WHERE "
                + DatabaseHelper.TABLE_WAREHOUSE_ID + " LIKE '" + keyword + "' OR "
                + DatabaseHelper.TABLE_WAREHOUSE_NAME + " LIKE '" + keyword + "' OR "
                + DatabaseHelper.TABLE_WAREHOUSE_ADDRESS + " LIKE '" + keyword + "'";
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String address = cursor.getString(2);

            Warehouse warehouse = new Warehouse(id, name, address);
            warehouses.add(warehouse);
        }

        cursor.close();
        db.close();
        return warehouses;
    }
}
