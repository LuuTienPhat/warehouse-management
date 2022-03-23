package com.example.warehousemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddWarehouseActivity extends AppCompatActivity {
    EditText txtName, txtId, txtAddress;
    Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();
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
        txtAddress = findViewById(R.id.txtAddress);
        txtName = findViewById(R.id.txtName);
        txtId = findViewById(R.id.txtId);
        btnSave = findViewById(R.id.btnSave);
    }

    private void handleBtnSaveClick(View view) {
        String id = txtId.getText().toString().trim();
        String name = txtName.getText().toString().trim();
        String address = txtAddress.getText().toString().trim();

        WarehouseModel warehouse = new WarehouseModel(id, name, address);

    }

    private void handleBtnCancelClick(View view) {

    }
}
