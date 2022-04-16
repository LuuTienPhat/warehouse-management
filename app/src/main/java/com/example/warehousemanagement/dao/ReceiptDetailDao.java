package com.example.warehousemanagement.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.warehousemanagement.DatabaseHelper;
import com.example.warehousemanagement.model.ReceiptDetail;

import java.util.ArrayList;
import java.util.List;

public class ReceiptDetailDao implements Dao<ReceiptDetail> {
    private SQLiteDatabase db;
    private final SQLiteOpenHelper dbHelper;

    public ReceiptDetailDao(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public void fillData() {
        ReceiptDetail receiptDetail1 = new ReceiptDetail(1, "GO", "Viên", 5000);
        ReceiptDetail receiptDetail2 = new ReceiptDetail(1, "GT", "Viên", 2000);
        ReceiptDetail receiptDetail3 = new ReceiptDetail(1, "XM", "Bao", 1500);

        ReceiptDetail receiptDetail4 = new ReceiptDetail(2, "SO", "Thùng", 75);

        ReceiptDetail receiptDetail5 = new ReceiptDetail(3, "SA", "Tấn", 25);
        ReceiptDetail receiptDetail6 = new ReceiptDetail(3, "XM", "Bao", 100);

        ReceiptDetail receiptDetail7 = new ReceiptDetail(4, "GO", "Viên", 10000);
        ReceiptDetail receiptDetail8 = new ReceiptDetail(4, "SA", "Tấn", 50);

        ReceiptDetail receiptDetail9 = new ReceiptDetail(5, "SO", "Thùng", 240);
        ReceiptDetail receiptDetail10 = new ReceiptDetail(5, "XM", "Bao", 430);

        insertOne(receiptDetail1);
        insertOne(receiptDetail2);
        insertOne(receiptDetail3);
        insertOne(receiptDetail4);
        insertOne(receiptDetail5);
        insertOne(receiptDetail6);
        insertOne(receiptDetail7);
        insertOne(receiptDetail8);
        insertOne(receiptDetail9);
        insertOne(receiptDetail10);

    }

    @Override
    public boolean insertOne(ReceiptDetail receiptDetail) {
        boolean result = true;
        db = dbHelper.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseHelper.TABLE_RECEIPT_DETAIL_RECEIPT_ID, receiptDetail.getReceiptId());
            cv.put(DatabaseHelper.TABLE_RECEIPT_DETAIL_PRODUCT_ID, receiptDetail.getProductId());
            cv.put(DatabaseHelper.TABLE_RECEIPT_DETAIL_UNIT, receiptDetail.getUnit());
            cv.put(DatabaseHelper.TABLE_RECEIPT_DETAIL_QUANTITY, receiptDetail.getQuantity());

            db.insert(DatabaseHelper.TABLE_RECEIPT_DETAIL, null, cv);
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
    public boolean updateOne(ReceiptDetail receiptDetail) {
        return false;
    }

    @Override
    public boolean deleteOne(ReceiptDetail receiptDetail) {
        return false;
    }

    @Override
    @Deprecated
    public List<ReceiptDetail> getAll() {
        return null;
    }

    public List<ReceiptDetail> getAll(int receiptId) {
        List<ReceiptDetail> receiptDetails = new ArrayList<>();
        db = dbHelper.getReadableDatabase();

        String queryString = "SELECT * FROM " + DatabaseHelper.TABLE_RECEIPT_DETAIL + " WHERE " + DatabaseHelper.TABLE_RECEIPT_DETAIL_RECEIPT_ID + " = " + receiptId;
        Cursor cursor = db.rawQuery(queryString, null);

        //b.query(DatabaseHelper.TABLE_RECEIPT_DETAIL, null, DatabaseHelper.TABLE_WAREHOUSE_ID + " = ?", new String[]{String.valueOf(Integer.toString(receiptId))}, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String productName = cursor.getString(1);
                String unit = cursor.getString(2);
                int quantity = cursor.getInt(3);

                ReceiptDetail receiptDetail = new ReceiptDetail(receiptId, productName, unit, quantity);
                receiptDetails.add(receiptDetail);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return receiptDetails;
    }

    @Override
    @Deprecated
    public ReceiptDetail getOne(String warehouseId) {
        return null;
    }

    @Override
    public List<ReceiptDetail> search(String keyword) {
        return null;
    }

}
