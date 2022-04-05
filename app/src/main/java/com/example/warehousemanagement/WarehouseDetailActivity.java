package com.example.warehousemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanagement.dao.WarehouseDao;
import com.example.warehousemanagement.model.Warehouse;

public class WarehouseDetailActivity extends AppCompatActivity {
    EditText etName, etId, etAddress;
    ImageButton btnEdit, btnDelete;
    Button btnSave, btnCancel;
    Warehouse warehouse = null;
    LinearLayout lyOption, lyUtils;
    TextView tvTitle;

    WarehouseDao warehouseDao;
    int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warehouse_detail_activity);
        setControl();
        setEvent();

        this.state = 0;
        changeState();

//        db = AppDatabase.getInstance(this);
//        warehouseDao = db.warehouseDao();
//
//        String warehouseId = this.getIntent().getStringExtra("warehouseId");
//        String warehouseName = this.getIntent().getStringExtra("warehouseName");
//        String warehouseAddress = this.getIntent().getStringExtra("warehouseAddress");
//
//        this.warehouse = new Warehouse(warehouseId, warehouseName, warehouseAddress);
//        etId.setText(warehouse.getId());
//        etName.setText(warehouse.getName());
//        etAddress.setText(warehouse.getAddress());
    }

    @Override
    @MainThread
    public void onBackPressed() {
        if (this.state == 0) {
            Intent intent = new Intent(this, WarehouseActivity.class);
            startActivity(intent);
        } else if (this.state == 1) {
            this.state = 0;
            changeState();
        } else if (this.state == 2) {
            this.state = 0;
            changeState();
        }
    }

    private void setEvent() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnSaveClick(view);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnCancelClick(view);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnEditClick(view);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnDeleteClick(view);
            }
        });
    }

    private void setControl() {
        tvTitle = findViewById(R.id.tvTitle);
        lyUtils = findViewById(R.id.lyUtils);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        etAddress = findViewById(R.id.etAddress);
        etName = findViewById(R.id.etName);
        etId = findViewById(R.id.etId);

        lyOption = findViewById(R.id.lyOption);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void handleBtnSaveClick(View view) {
        String id = etId.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        //validation

//        if (this.state == 1) {
//            Warehouse newWarehouse = new Warehouse(id, name, address);
//            AppDatabase db = AppDatabase.getInstance(this);
//            WarehouseDao warehouseDao = db.warehouseDao();
//            warehouseDao.updateOne(newWarehouse);
//        } else if (this.state == 2) {
//            Warehouse newWarehouse = new Warehouse(id, name, address);
//            AppDatabase db = AppDatabase.getInstance(this);
//            WarehouseDao warehouseDao = db.warehouseDao();
//            warehouseDao.insertOne(newWarehouse);
//        }
    }

    private void handleBtnCancelClick(View view) {
        if (this.state == 1) {
            this.state = 0;
            changeState();
        } else if (this.state == 2) {
            Intent intent = new Intent(this, WarehouseActivity.class);
            startActivity(intent);
        }
    }

    private void handleBtnDeleteClick(View view) {
//        warehouseDao.deleteOne(warehouse);
    }

    private void handleBtnEditClick(View view) {
        this.state = 1;
        changeState();
    }

    //0: Detail, 1: Edit, 2: Add
    private void changeState() {
        if (this.state == 0) { //Detail
            tvTitle.setText("Chi tiết");
            lyUtils.setVisibility(View.VISIBLE);

            etId.setFocusable(false);
            etId.setFocusableInTouchMode(false);
            etName.setFocusable(false);
            etName.setFocusableInTouchMode(false);
            etAddress.setFocusable(false);
            etAddress.setFocusableInTouchMode(false);

            lyOption.setVisibility(View.GONE);
        } else if (this.state == 1) {
            tvTitle.setText("Chỉnh sửa");
            lyUtils.setVisibility(View.GONE);

            etId.setFocusable(true);
            etId.setFocusableInTouchMode(true);
            etName.setFocusable(true);
            etName.setFocusableInTouchMode(true);
            etAddress.setFocusable(true);
            etAddress.setFocusableInTouchMode(true);

            lyOption.setVisibility(View.VISIBLE);
        } else if (this.state == 2) {
            tvTitle.setText("Thêm");
            lyUtils.setVisibility(View.GONE);

            etId.setFocusable(true);
            etId.setFocusableInTouchMode(true);
            etName.setFocusable(true);
            etName.setFocusableInTouchMode(true);
            etAddress.setFocusable(true);
            etAddress.setFocusableInTouchMode(true);

            lyOption.setVisibility(View.VISIBLE);
        }
    }
}
