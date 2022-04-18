package com.example.warehousemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.warehousemanagement.dao.ProductDao;
import com.example.warehousemanagement.dao.ReceiptDao;
import com.example.warehousemanagement.dao.ReceiptDetailDao;
import com.example.warehousemanagement.dao.WarehouseDao;
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
        productDao.fillData();

        WarehouseDao warehouseDao = new WarehouseDao(databaseHelper);
        warehouseDao.fillData();

        ReceiptDao receiptDao = new ReceiptDao(databaseHelper);
        receiptDao.fillData();

        ReceiptDetailDao receiptDetailDao = new ReceiptDetailDao(databaseHelper);
        receiptDetailDao.fillData();
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
}