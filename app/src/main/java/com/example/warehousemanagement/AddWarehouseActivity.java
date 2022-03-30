package com.example.warehousemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanagement.dao.WarehouseDao;
import com.example.warehousemanagement.entity.WarehouseEntity;

public class AddWarehouseActivity extends AppCompatActivity {
    EditText tvName, tvId, tvAddress;
    ImageButton btnEdit, btnDelete;
    Button btnSave, btnCancel;
    WarehouseEntity warehouse = null;
    LinearLayout lyOption, lyUtils;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_warehouse);
        setControl();
        setEvent();

        String warehouseId = this.getIntent().getStringExtra("warehouseId");
        String warehouseName = this.getIntent().getStringExtra("warehouseName");
        String warehouseAddress = this.getIntent().getStringExtra("warehouseAddress");

        this.warehouse = new WarehouseEntity(warehouseId, warehouseName, warehouseAddress);
        tvId.setText(warehouse.getId());
        tvName.setText(warehouse.getName());
        tvAddress.setText(warehouse.getAddress());
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
    }

    private void setControl() {


        btnEdit = findViewById(R.id.btnEdit);
        btnCancel = findViewById(R.id.btnCancel);

        tvAddress = findViewById(R.id.etAddress);
        tvName = findViewById(R.id.etName);
        tvId = findViewById(R.id.etId);

        lyOption = findViewById(R.id.lyOption);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void handleBtnSaveClick(View view) {
        String id = tvId.getText().toString().trim();
        String name = tvName.getText().toString().trim();
        String address = tvAddress.getText().toString().trim();

//        WarehouseEntity warehouse = new WarehouseEntity(id, name, address);
//        AppDatabase db = AppDatabase.getInstance(this);
//        WarehouseDao warehouseDao = db.warehouseDao();
//        warehouseDao.insertOne(warehouse);
    }

    private void handleBtnCancelClick(View view) {
        Intent intent = new Intent(this, WarehouseActivity.class);
        startActivity(intent);
    }


}
