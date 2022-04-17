package com.example.warehousemanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanagement.dao.ProductDao;
import com.example.warehousemanagement.dao.ReceiptDao;
import com.example.warehousemanagement.dao.ReceiptDetailDao;
import com.example.warehousemanagement.dao.WarehouseDao;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    LinearLayout btnWarehouse, btnProduct, btnReceipt;
    DatabaseHelper databaseHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();

        databaseHelper = DatabaseHelper.getInstance(this);
        ProductDao productDao = new ProductDao(databaseHelper);
        WarehouseDao warehouseDao = new WarehouseDao(databaseHelper);
        ReceiptDao receiptDao = new ReceiptDao(databaseHelper);
        ReceiptDetailDao receiptDetailDao = new ReceiptDetailDao(databaseHelper);

        // only call fillData() when the database file doesn't exist and is just created
        if (!databaseExists(this, DatabaseHelper.DB_NAME)) {
            productDao.fillData();
            warehouseDao.fillData();
            receiptDao.fillData();
            receiptDetailDao.fillData();
        }
    }

    private void setEvent() {
        btnProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnProductClick(view);
            }
        });

        btnReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnReceiptClick(view);
            }
        });

        btnWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnWarehouseClick(view);
            }
        });
    }

    private void setControl() {
        btnProduct = findViewById(R.id.btnProduct);
        btnWarehouse = findViewById(R.id.btnWarehouse);
        btnReceipt = findViewById(R.id.btnReceipt);
    }

    public void handleBtnWarehouseClick(View view) {
        Intent intent = new Intent(this, WarehouseActivity.class);
        startActivity(intent);
    }

    public void handleBtnProductClick(View view) {
        Intent intent = new Intent(this, ProductActivity.class);
        startActivity(intent);
    }

    public void handleBtnReceiptClick(View view) {
        Intent intent = new Intent(this, ReceiptActivity.class);
        startActivity(intent);
    }

    private boolean databaseExists(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
}