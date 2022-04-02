package com.example.warehousemanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.warehousemanagement.dao.ProductDao;
import com.example.warehousemanagement.model.Product;
import com.example.warehousemanagement.model.Warehouse;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static DatabaseHelper Instance = null;

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (Instance == null) {
            Instance = new DatabaseHelper(context.getApplicationContext());
        }
        return Instance;
    }

    public ProductDao getProductDao() {
        ProductDao productDao = new ProductDao(Instance);
        return productDao;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PRODUCT_QUERY);
        db.execSQL(CREATE_TABLE_WAREHOUSE_QUERY);
        db.execSQL(CREATE_TABLE_RECEIPT_QUERY);
        db.execSQL(CREATE_TABLE_RECEIPT_DETAIL_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
        onCreate(db);
    }

    public static final String DB_NAME = "warehouse.db";

    public static final String TABLE_WAREHOUSE = "warehouse";
    public static final String TABLE_PRODUCT = "product";
    public static final String TABLE_RECEIPT = "receipt";
    public static final String TABLE_RECEIPT_DETAIL = "receipt_detail";

    public static final String TABLE_WAREHOUSE_ID = "id";
    public static final String TABLE_WAREHOUSE_NAME = "name";
    public static final String TABLE_WAREHOUSE_ADDRESS = "address";

    public static final String TABLE_PRODUCT_ID = "id";
    public static final String TABLE_PRODUCT_NAME = "name";
    public static final String TABLE_PRODUCT_ORIGIN = "origin";

    public static final String TABLE_RECEIPT_ID = "id";
    public static final String TABLE_RECEIPT_DATE = "date";
    public static final String TABLE_RECEIPT_WAREHOUSE_ID = "warehouse_id";

    public static final String TABLE_RECEIPT_DETAIL_RECEIPT_ID = "receipt_id";
    public static final String TABLE_RECEIPT_DETAIL_PRODUCT_ID = "product_id";
    public static final String TABLE_RECEIPT_DETAIL_UNIT = "unit";
    public static final String TABLE_RECEIPT_DETAIL_QUANTITY = "quantity";

    private static final String CREATE_TABLE_PRODUCT_QUERY = "CREATE TABLE " + TABLE_PRODUCT + " (" +
            TABLE_PRODUCT_ID + " varchar (10) PRIMARY KEY, " +
            TABLE_PRODUCT_NAME + " varchar(50), " +
            TABLE_PRODUCT_ORIGIN + " varchar(50));";

    private static final String CREATE_TABLE_WAREHOUSE_QUERY = "CREATE TABLE " + TABLE_WAREHOUSE + " (" +
            TABLE_WAREHOUSE_ID + " VARCHAR (10) PRIMARY KEY, " +
            TABLE_WAREHOUSE_NAME + " VARCHAR (50), " +
            TABLE_WAREHOUSE_ADDRESS + " VARCHAR(255))";

    private static final String CREATE_TABLE_RECEIPT_QUERY = "CREATE TABLE " + TABLE_RECEIPT + " ( " +
            TABLE_RECEIPT_ID + " INT PRIMARY KEY, " +
            TABLE_RECEIPT_DATE + " DATE, " +
            TABLE_RECEIPT_WAREHOUSE_ID + " VARCHAR (10) REFERENCES " + TABLE_WAREHOUSE + " (" + TABLE_WAREHOUSE_ID + ") ON UPDATE CASCADE)";

    private static final String CREATE_TABLE_RECEIPT_DETAIL_QUERY = "CREATE TABLE " + TABLE_RECEIPT_DETAIL + " (" +
            TABLE_RECEIPT_DETAIL_RECEIPT_ID + " INT REFERENCES " + TABLE_RECEIPT + " (" + TABLE_RECEIPT_ID + "), " +
            TABLE_RECEIPT_DETAIL_PRODUCT_ID + " VARCHAR (10) REFERENCES " + TABLE_PRODUCT + " (" + TABLE_PRODUCT_ID + "), " +
            TABLE_RECEIPT_DETAIL_QUANTITY + " VARCHAR (50), " +
            TABLE_RECEIPT_DETAIL_UNIT + " INT, " +
            "PRIMARY KEY (" +
            TABLE_RECEIPT_DETAIL_RECEIPT_ID + ", " +
            TABLE_RECEIPT_DETAIL_PRODUCT_ID +
            "))";
}
