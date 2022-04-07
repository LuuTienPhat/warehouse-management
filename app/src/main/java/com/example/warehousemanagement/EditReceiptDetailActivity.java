package com.example.warehousemanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EditReceiptDetailActivity extends AppCompatActivity {
    ImageButton btnDelete;
    EditText etQuantity, etUnit;
    Button btnSave, btnCancel;
    TextView tvProductId, tvProductName, tvProductOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_detail_edit_activity);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnDeleteClick(view);
            }
        });

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
        btnDelete = findViewById(R.id.btnDelete);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        tvProductId = findViewById(R.id.tvProductId);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductOrigin = findViewById(R.id.tvProductOrigin);
    }

    private void handleBtnDeleteClick(View view) {

    }

    private void handleBtnSaveClick(View view) {
        int quantity = Integer.parseInt(etQuantity.getText().toString().trim());
        String unit = etUnit.getText().toString();

        
    }

    private void handleBtnCancelClick(View view) {
        finish();
    }
}
