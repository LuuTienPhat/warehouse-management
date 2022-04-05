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
import com.example.warehousemanagement.model.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class WarehouseActivity extends AppCompatActivity implements BaseActivity {
    ImageButton btnAdd, btnMinimize, btnSort, btnFilter, btnRefresh;
    SearchView searchView;
    TextView tvTitle;
    ListView listView;
    WarehouseDao warehouseDao = null;
    WarehouseAdapter warehouseAdapter;
    List<Warehouse> warehouses = new ArrayList<>();
    int dividerHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        setControl();
        setEvent();

        tvTitle.setText("Kho");
        warehouseDao = new WarehouseDao(DatabaseHelper.getInstance(this));
        warehouses = warehouseDao.getAll();

        warehouseAdapter = new WarehouseAdapter(this, R.layout.warehouse_item, warehouses);
        listView.setAdapter(warehouseAdapter);
        dividerHeight = listView.getDividerHeight();
    }

    private void setControl() {
        btnAdd = findViewById(R.id.btnAdd);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnFilter = findViewById(R.id.btnFilter);
        btnSort = findViewById(R.id.btnSort);
        btnMinimize = findViewById(R.id.btnMinimize);
        listView = findViewById(R.id.listView);
        tvTitle = findViewById(R.id.tvTitle);
        searchView = findViewById(R.id.searchView);
    }

    private void setEvent() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnAddClick(view);
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnRefreshClick(view);
            }
        });
        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnSortClick(view);
            }
        });
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnFilterClick(view);
            }
        });
        btnMinimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnMinimizeClick(view);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Warehouse warehouse = (Warehouse) adapterView.getItemAtPosition(position);

                Intent intent = new Intent(WarehouseActivity.this, WarehouseDetailActivity.class);
                intent.putExtra("warehouseId", warehouse.getId());
                intent.putExtra("warehouseName", warehouse.getName());
                intent.putExtra("warehouseAddress", warehouse.getAddress());
                startActivity(intent);
            }
        });
    }

    @Override
    public void handleBtnAddClick(View view) {

    }

    @Override
    public void handleBtnRefreshClick(View view) {
        warehouses = warehouseDao.getAll();

        WarehouseAdapter warehouseAdapter = new WarehouseAdapter(this, R.layout.warehouse_item, warehouses);
        listView.setAdapter(warehouseAdapter);
        listView.setDividerHeight(dividerHeight);
    }

    @Override
    public void handleBtnSortClick(View view) {

    }

    @Override
    public void handleBtnFilterClick(View view) {

    }

    @Override
    public void handleBtnMinimizeClick(View view) {
        warehouses = warehouseDao.getAll();

        warehouseAdapter = new WarehouseAdapter(this, R.layout.warehouse_item_small, warehouses);
        listView.setAdapter(warehouseAdapter);
        listView.setDividerHeight(10);
        warehouseAdapter.notifyDataSetChanged();
    }
}