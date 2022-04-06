package com.example.warehousemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanagement.activities.receipt.AddReceiptActivity;
import com.example.warehousemanagement.adapter.ReceiptAdapter;
import com.example.warehousemanagement.adapter.WarehouseAdapter;
import com.example.warehousemanagement.dao.ReceiptDao;
import com.example.warehousemanagement.dao.WarehouseDao;
import com.example.warehousemanagement.model.Receipt;
import com.example.warehousemanagement.model.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class ReceiptActivity extends AppCompatActivity implements BaseActivity {
    ImageButton btnMinimize, btnAdd, btnSort, btnFilter, btnRefresh;
    ListView listView;
    SearchView searchView;
    TextView tvTitle;
    Spinner spinner;

    ReceiptDao receiptDao = null;
    ReceiptAdapter receiptAdapter = null;
    List<Receipt> receipts = new ArrayList<>();

    WarehouseDao warehouseDao = null;
    WarehouseAdapter warehouseAdapter = null;
    List<Warehouse> warehouses = new ArrayList<>();

    int dividerHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        setControl();
        setEvent();

        tvTitle.setText("Phiếu nhập");

        receiptDao = new ReceiptDao(DatabaseHelper.getInstance(this));
        receipts = receiptDao.getAll();

        receiptAdapter = new ReceiptAdapter(this, R.layout.constraint_receipt_item, receipts);
        listView.setAdapter(receiptAdapter);
        dividerHeight = listView.getDividerHeight();

//        spinner.setAdapter(receiptAdapter);
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
                Receipt receipt = (Receipt) adapterView.getItemAtPosition(position);

                Intent intent = new Intent(ReceiptActivity.this, ReceiptViewActivity.class);
                intent.putExtra("receipt", receipt);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        btnAdd = findViewById(R.id.btnAdd);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnFilter = findViewById(R.id.btnFilter);
        btnSort = findViewById(R.id.btnSort);
        btnMinimize = findViewById(R.id.btnMinimize);
        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.searchView);
        tvTitle = findViewById(R.id.tvTitle);
    }

    @Override
    public void handleBtnAddClick(View view) {
        Intent intent = new Intent(this, ReceiptActivity2.class);
        startActivity(intent);
    }

    @Override
    public void handleBtnRefreshClick(View view) {
        receipts = receiptDao.getAll();

        receiptAdapter = new ReceiptAdapter(this, R.layout.constraint_receipt_item, receipts);
        listView.setAdapter(receiptAdapter);
        receiptAdapter.notifyDataSetChanged();
    }

    @Override
    public void handleBtnSortClick(View view) {

    }

    @Override
    public void handleBtnFilterClick(View view) {

    }

    @Override
    public void handleBtnMinimizeClick(View view) {
        receipts = receiptDao.getAll();

        receiptAdapter = new ReceiptAdapter(this, R.layout.constraint_receipt_item_small, receipts);
        listView.setAdapter(receiptAdapter);
        receiptAdapter.notifyDataSetChanged();
    }
}