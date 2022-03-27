package com.example.warehousemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnWarehouse, btnProduct, btnReceipt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();

//        AppDatabase db = AppDatabase.getInstance(this);
//        this.deleteDatabase(db.getDatabaseName());
//        db.initializeDatabase();


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
        btnProduct = findViewById(R.id.product_btn);
        btnWarehouse = findViewById(R.id.warehouse_btn);
        btnReceipt = findViewById(R.id.receipt_btn);
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
        Intent intent = new Intent(this, WarehouseActivity.class);
        startActivity(intent);
    }
}