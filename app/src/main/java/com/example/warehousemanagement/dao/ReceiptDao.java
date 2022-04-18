package com.example.warehousemanagement.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.warehousemanagement.DatabaseHelper;
import com.example.warehousemanagement.model.Receipt;
import com.example.warehousemanagement.model.ReceiptDetail;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReceiptDao implements Dao<Receipt> {
    private SQLiteDatabase db;
    private final SQLiteOpenHelper dbHelper;

    public ReceiptDao(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public void fillData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Receipt receipt1 = new Receipt(1, LocalDate.parse("2013-06-20", formatter), "K1", new ArrayList<>());
        Receipt receipt2 = new Receipt(2, LocalDate.parse("2013-07-07", formatter), "K2", new ArrayList<>());
        Receipt receipt3 = new Receipt(3, LocalDate.parse("2014-01-02", formatter), "K1", new ArrayList<>());
        Receipt receipt4 = new Receipt(4, LocalDate.parse("2014-03-05", formatter), "K3", new ArrayList<>());
        Receipt receipt5 = new Receipt(5, LocalDate.parse("2014-05-25", formatter), "K1", new ArrayList<>());

        boolean i = insertOne(receipt1);
        boolean i1 = insertOne(receipt2);
        boolean i2 = insertOne(receipt3);
        boolean i3 = insertOne(receipt4);
        boolean i4 = insertOne(receipt5);

//        try {
//            db = dbHelper.getWritableDatabase();
//            db.execSQL("CREATE VIRTUAL TABLE fts_table_receipt USING fts3 ( col_1, col_2, col_3 )");
//            db.close();
//        } catch (Exception ex) {
//
//        }
//
//
//        insertVirtualTable(receipt1);
//        insertVirtualTable(receipt2);
//        insertVirtualTable(receipt3);
//        insertVirtualTable(receipt4);
//        insertVirtualTable(receipt5);
    }

    private void insertVirtualTable(Receipt receipt) {
        boolean result = true;
        db = dbHelper.getWritableDatabase();

        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO fts_table_receipt VALUES ('" + receipt.getId() + "', '" + receipt.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "', '" + receipt.getWarehouseId() + "')");

            db.setTransactionSuccessful();
        } catch (Exception ex) {
            result = false;
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
        db.close();
    }

    @Override
    public boolean insertOne(Receipt receipt) {
        boolean result = true;
        db = dbHelper.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseHelper.TABLE_RECEIPT_ID, receipt.getId());
            cv.put(DatabaseHelper.TABLE_RECEIPT_DATE, receipt.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            cv.put(DatabaseHelper.TABLE_RECEIPT_WAREHOUSE_ID, receipt.getWarehouseId());

            db.insert(DatabaseHelper.TABLE_RECEIPT, null, cv);

//            ReceiptDetailDao receiptDetailDao = new ReceiptDetailDao((DatabaseHelper) this.dbHelper);
//
//            if (receipt.getReceiptDetails() != null) {
//                for (ReceiptDetail r : receipt.getReceiptDetails()
//                ) {
//                    receiptDetailDao.insertOne(r);
//                }
//            }

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
    public boolean updateOne(Receipt receipt) {

        return false;
    }

    @Override
    public boolean deleteOne(Receipt receipt) {

        return false;
    }

    @Override
    public List<Receipt> getAll() {
        List<Receipt> receipts = new ArrayList<>();
        db = dbHelper.getReadableDatabase();

        String queryString = "SELECT * FROM " + DatabaseHelper.TABLE_RECEIPT;
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int receiptId = cursor.getInt(0);
                String date = cursor.getString(1);
                String warehouseId = cursor.getString(2);

                LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                ReceiptDetailDao receiptDetailDao = new ReceiptDetailDao((DatabaseHelper) dbHelper);
                List<ReceiptDetail> receiptDetails = receiptDetailDao.getAll(receiptId);

                Receipt receipt = new Receipt(receiptId, localDate, warehouseId, receiptDetails);
                receipts.add(receipt);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return receipts;
    }

    @Override
    public Receipt getOne(String id) {
        Receipt receipt = null;
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_RECEIPT, null, DatabaseHelper.TABLE_RECEIPT_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor.moveToFirst()) {
            int receiptId = cursor.getInt(0);
            String date = cursor.getString(1);
            String warehouseId = cursor.getString(2);

            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);

            ReceiptDetailDao receiptDetailDao = new ReceiptDetailDao((DatabaseHelper) dbHelper);
            List<ReceiptDetail> receiptDetails = receiptDetailDao.getAll(receiptId);

            receipt = new Receipt(receiptId, localDate, warehouseId, receiptDetails);
        }
        return receipt;
    }

    @Override
    public List<Receipt> search(String keyword) {
        List<Receipt> receipts = new ArrayList<>();
        db = dbHelper.getReadableDatabase();

        keyword = "%" + keyword + "%";
//        String[] selectionArgs = {keyword};
//        Cursor cursor = db.rawQuery("SELECT * FROM fts_table_receipt WHERE fts_table_receipt MATCH ?", selectionArgs);

        String query = "SELECT * FROM " + DatabaseHelper.TABLE_RECEIPT + " WHERE "
                + DatabaseHelper.TABLE_RECEIPT_ID + " LIKE '" + keyword + "' OR "
                + DatabaseHelper.TABLE_RECEIPT_DATE + " LIKE '" + keyword + "' OR "
                + DatabaseHelper.TABLE_RECEIPT_WAREHOUSE_ID + " LIKE '" + keyword + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int receiptId = cursor.getInt(0);
                String date = cursor.getString(1);
                String warehouseId = cursor.getString(2);

                LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                ReceiptDetailDao receiptDetailDao = new ReceiptDetailDao((DatabaseHelper) dbHelper);
                List<ReceiptDetail> receiptDetails = receiptDetailDao.getAll(receiptId);

                Receipt receipt = new Receipt(receiptId, localDate, warehouseId, receiptDetails);
                receipts.add(receipt);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return receipts;
    }
}
