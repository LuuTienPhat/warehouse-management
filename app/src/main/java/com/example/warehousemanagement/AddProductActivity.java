package com.example.warehousemanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanagement.dao.ProductDao;
import com.example.warehousemanagement.model.Product;

public class AddProductActivity extends AppCompatActivity {
    EditText etName, etId, etOrigin;
    ImageButton btnEdit, btnDelete;
    Button btnSave, btnCancel;
    Product product;
    LinearLayout lyOption, lyUtils;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_add_activity);
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
        btnEdit = findViewById(R.id.btnEdit);
        btnCancel = findViewById(R.id.btnCancel);

        etOrigin = findViewById(R.id.etOrigin);
        etName = findViewById(R.id.etName);
        etId = findViewById(R.id.etId);

        lyOption = findViewById(R.id.lyOption);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void handleBtnSaveClick(View view) {
        String id = etId.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String origin = etOrigin.getText().toString().trim();

        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setOrigin(origin);

        ProductDao productDao = new ProductDao(DatabaseHelper.getInstance(this));
        if (productDao.insertOne(product)) {
            Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
        }
    }

    private void handleBtnCancelClick(View view) {
        finish();
    }
}

