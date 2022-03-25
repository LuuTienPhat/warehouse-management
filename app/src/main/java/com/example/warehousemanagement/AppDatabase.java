package com.example.warehousemanagement;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.warehousemanagement.dao.ProductDao;
import com.example.warehousemanagement.dao.WarehouseDao;
import com.example.warehousemanagement.entity.ProductEntity;
import com.example.warehousemanagement.entity.ReceiptEntity;
import com.example.warehousemanagement.entity.WarehouseEntity;

import java.util.Date;

@Database(entities = {ProductEntity.class, WarehouseEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "warehouse.db";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public String getDatabaseName() {
        return this.DATABASE_NAME;
    }

    public abstract ProductDao productDao();

    public abstract WarehouseDao warehouseDao();

    public boolean initializeDatabase() {
        boolean result = true;
        ProductDao productDao = this.productDao();
        WarehouseDao warehouseDao = this.warehouseDao();

        ProductEntity p1 = new ProductEntity("GO", "Gạch ống", "Đồng Nai");
        ProductEntity p2 = new ProductEntity("GT", "Gạch thẻ", "Long An");
        ProductEntity p3 = new ProductEntity("SA", "Sắt tròn", "Sắt tròn");
        ProductEntity p4 = new ProductEntity("SO", "Sơn dầu", "Tiền Giang");
        ProductEntity p5 = new ProductEntity("XM", "Xi măng", "Hà Tiên");

        WarehouseEntity w1 = new WarehouseEntity("K1", "Bình Chánh", "Bình Chánh");
        WarehouseEntity w2 = new WarehouseEntity("K2", "Tân Phú", "Tân Phú");
        WarehouseEntity w3 = new WarehouseEntity("K3", "Thủ Đức", "Thủ Đức");

        ReceiptEntity r1 = new ReceiptEntity("1", new Date());
        ReceiptEntity r2 = new ReceiptEntity("2", new Date());

        try {
            productDao.insertOne(p1);
            productDao.insertOne(p2);
            productDao.insertOne(p3);
            productDao.insertOne(p4);
            productDao.insertOne(p5);

            warehouseDao.insertOne(w1);
            warehouseDao.insertOne(w2);
            warehouseDao.insertOne(w3);
        } catch (Exception ex) {
            ex.printStackTrace();
            result = false;
        }

        return result;
    }
}
