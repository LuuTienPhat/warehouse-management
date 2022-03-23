package com.example.warehousemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

public class WarehouseActivity extends AppCompatActivity {
    ImageButton btnAdd, btnMinimize;
    SearchView searchView;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse);
        setControl();
        setEvent();
    }

    private void setControl() {
        btnAdd = findViewById(R.id.add_btn);
        btnMinimize = findViewById(R.id.minimize_btn);

    }

    private void setEvent() {

    }
}