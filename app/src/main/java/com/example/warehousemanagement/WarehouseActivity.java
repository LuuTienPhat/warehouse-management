package com.example.warehousemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanagement.adapter.WarehouseAdapter;
import com.example.warehousemanagement.dao.WarehouseDao;
import com.example.warehousemanagement.entity.WarehouseEntity;

import java.util.ArrayList;
import java.util.List;

public class WarehouseActivity extends AppCompatActivity {
    ImageButton btnAdd, btnMinimize;
    SearchView searchView;
    TextView tvTitle;
    ListView listview;
    WarehouseAdapter warehouseAdapter;
    List<WarehouseEntity> warehouses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        setControl();
        setEvent();

        tvTitle.setText("Kho");

        AppDatabase db = AppDatabase.getInstance(this);
        WarehouseDao warehouseDao = db.warehouseDao();
        warehouses = warehouseDao.getAll();

        WarehouseAdapter warehouseAdapter = new WarehouseAdapter(this, R.layout.warehouse_item, warehouses);
        listview.setAdapter(warehouseAdapter);
    }

    private void setControl() {
        btnAdd = findViewById(R.id.btnAdd);
        btnMinimize = findViewById(R.id.btnMinimize);
        listview = findViewById(R.id.listView);
        tvTitle = findViewById(R.id.tvTitle);
    }

    private void setEvent() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                WarehouseEntity warehouse = (WarehouseEntity) adapterView.getItemAtPosition(position);

                Intent intent = new Intent(WarehouseActivity.this, WarehouseDetailActivity.class);
                intent.putExtra("warehouseId", warehouse.getId());
                intent.putExtra("warehouseName", warehouse.getName());
                intent.putExtra("warehouseAddress", warehouse.getAddress());
                startActivity(intent);
            }
        });
    }

}