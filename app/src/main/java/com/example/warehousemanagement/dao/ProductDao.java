package com.example.warehousemanagement.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.warehousemanagement.DatabaseHelper;
import com.example.warehousemanagement.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDao implements Dao<Product> {
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;

    public ProductDao(Context context) {
        dbHelper = new DatabaseHelper(context);
        Product p1 = new Product("GO", "Gạch ống", "Đồng Nai");
        Product p2 = new Product("GT", "Gạch thẻ", "Long An");
        Product p3 = new Product("SA", "Sắt tròn", "Sắt tròn");
        Product p4 = new Product("SO", "Sơn dầu", "Tiền Giang");
        Product p5 = new Product("XM", "Xi măng", "Hà Tiên");

        insertOne(p1);
        insertOne(p2);
        insertOne(p3);
        insertOne(p4);
        insertOne(p5);

//        ProductDao productDao = new ProductDao(this.context);
//        productDao.insertOne(p1);
//        productDao.insertOne(p2);
//        productDao.insertOne(p3);
//        productDao.insertOne(p4);
//        productDao.insertOne(p5);
    }

    @Override
    public boolean insertOne(Product product) {
        boolean result = true;
        db = dbHelper.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseHelper.TABLE_PRODUCT_ID, product.getId());
            cv.put(DatabaseHelper.TABLE_PRODUCT_NAME, product.getName());
            cv.put(DatabaseHelper.TABLE_PRODUCT_ORIGIN, product.getOrigin());

            db.insert(DatabaseHelper.TABLE_PRODUCT, null, cv);
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            result = false;
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
        db.close();

        return result;
    }

    @Override
    public boolean updateOne(Product product) {
        boolean result = true;
        db = dbHelper.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseHelper.TABLE_PRODUCT_ID, product.getId());
            cv.put(DatabaseHelper.TABLE_PRODUCT_NAME, product.getName());
            cv.put(DatabaseHelper.TABLE_PRODUCT_ORIGIN, product.getOrigin());

            db.update(DatabaseHelper.TABLE_PRODUCT, cv, DatabaseHelper.TABLE_PRODUCT_ID + " = ?", new String[]{String.valueOf(product.getId())});
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            result = false;
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
        db.close();

        return result;
    }

    @Override
    public boolean deleteOne(Product product) {
        boolean result = true;
        db = dbHelper.getWritableDatabase();

        db.beginTransaction();
        try {
            db.delete(DatabaseHelper.TABLE_PRODUCT, DatabaseHelper.TABLE_PRODUCT_ID + " = ?", new String[]{String.valueOf(product.getId())});
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            result = false;
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
        db.close();

        return result;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        db = dbHelper.getReadableDatabase();

        String queryString = "SELECT * FROM " + DatabaseHelper.TABLE_PRODUCT;

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String origin = cursor.getString(2);

                Product product = new Product(id, name, origin);
                products.add(product);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return products;
    }

    @Override
    public Product getOne(String id) {
        Product product = null;
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_PRODUCT, null, DatabaseHelper.TABLE_PRODUCT_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.moveToFirst()) {
            String productId = cursor.getString(0);
            String name = cursor.getString(1);
            String origin = cursor.getString(2);

            product = new Product(productId, name, origin);
        }
        return product;
    }
}
