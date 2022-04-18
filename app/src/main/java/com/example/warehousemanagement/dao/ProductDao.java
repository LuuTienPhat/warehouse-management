package com.example.warehousemanagement.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.warehousemanagement.DatabaseHelper;
import com.example.warehousemanagement.model.Product;
import com.example.warehousemanagement.model.ProductQuantityDetail;

import java.util.ArrayList;
import java.util.List;

public class ProductDao implements Dao<Product> {
    private SQLiteDatabase db;
    private final SQLiteOpenHelper dbHelper;

    public ProductDao(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void fillData() {
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

    @Override
    public List<Product> search(String keyword) {
        List<Product> products = new ArrayList<>();
        db = dbHelper.getReadableDatabase();

        keyword = "%" + keyword + "%";
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_PRODUCT + " WHERE "
                + DatabaseHelper.TABLE_PRODUCT_ID + " LIKE '" + keyword + "' OR "
                + DatabaseHelper.TABLE_PRODUCT_ORIGIN + " LIKE '" + keyword + "' OR "
                + DatabaseHelper.TABLE_PRODUCT_NAME + " LIKE '" + keyword + "'";
        Cursor cursor = db.rawQuery(query, null);

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

    // mới thêm, chưa chuyển vào dao vì RecepitDAO phải override
    public boolean checkIdExists(String id) {
        Product product = null;
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_PRODUCT, null, DatabaseHelper.TABLE_PRODUCT_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.moveToFirst()) {
            String productId = cursor.getString(0);
            String name = cursor.getString(1);
            String origin = cursor.getString(2);

            product = new Product(productId, name, origin);
        }
        // nếu product khác null chứng tỏ đã tồn tại
        return product != null;
    }

    public List<ProductQuantityDetail> getProductQuantityInWarehouses(String productId) {
        List<ProductQuantityDetail> productQuantityDetails = new ArrayList<>();
        db = dbHelper.getReadableDatabase();

        String queryString = "SELECT "+DatabaseHelper.TABLE_WAREHOUSE+"."+DatabaseHelper.TABLE_WAREHOUSE_ID+", "
                +DatabaseHelper.TABLE_WAREHOUSE_NAME + ", " + DatabaseHelper.TABLE_RECEIPT_DETAIL_UNIT
                + ", SUM("+DatabaseHelper.TABLE_RECEIPT_DETAIL_QUANTITY+") AS QUANTITY "
                + " FROM " + DatabaseHelper.TABLE_RECEIPT_DETAIL +" INNER JOIN " +DatabaseHelper.TABLE_RECEIPT
                +" ON "+DatabaseHelper.TABLE_RECEIPT_DETAIL+"."+DatabaseHelper.TABLE_RECEIPT_DETAIL_RECEIPT_ID +"="+DatabaseHelper.TABLE_RECEIPT+"."+DatabaseHelper.TABLE_RECEIPT_ID
                +" INNER JOIN " +DatabaseHelper.TABLE_WAREHOUSE
                +" ON "+DatabaseHelper.TABLE_RECEIPT+"."+DatabaseHelper.TABLE_RECEIPT_WAREHOUSE_ID +"="+DatabaseHelper.TABLE_WAREHOUSE+"."+DatabaseHelper.TABLE_WAREHOUSE_ID
                +" WHERE "+DatabaseHelper.TABLE_RECEIPT_DETAIL+"."+DatabaseHelper.TABLE_RECEIPT_DETAIL_PRODUCT_ID+"= '"+ productId+"' "
                +" GROUP BY "+DatabaseHelper.TABLE_WAREHOUSE+"."+DatabaseHelper.TABLE_WAREHOUSE_ID+", "+DatabaseHelper.TABLE_RECEIPT_DETAIL+"."+DatabaseHelper.TABLE_RECEIPT_DETAIL_PRODUCT_ID;

        System.out.println(queryString);

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                String warehouseId = cursor.getString(0);
                String warehouseName = cursor.getString(1);
                String unit = cursor.getString(2);
                int quantity = cursor.getInt(3);

                ProductQuantityDetail productQuantityDetail = new ProductQuantityDetail(warehouseId, warehouseName, quantity, unit, productId);

                productQuantityDetails.add(productQuantityDetail);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        for(ProductQuantityDetail p : productQuantityDetails){
            System.out.println("-------------"+p.getWareHouseId()+", "+p.getProductId()+", "+p.getQuantity());
        }

        return productQuantityDetails;
    }
}
