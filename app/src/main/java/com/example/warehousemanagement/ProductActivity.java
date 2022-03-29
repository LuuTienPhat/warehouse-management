package com.example.warehousemanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanagement.adapter.ProductAdapter;
import com.example.warehousemanagement.dao.ProductDao;
import com.example.warehousemanagement.model.Product;

import java.util.List;

public class ProductActivity extends AppCompatActivity {
    ImageButton btnMinimize, btnAdd;
    ListView listView;
    SearchView searchView;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        setControl();
        setEvent();

        title.setText("Vật tư");

        ProductDao productDao = new ProductDao(this);
        List<Product> products = productDao.getAll();

        ProductAdapter productAdapter = new ProductAdapter(this, R.layout.product_item, products);
        listView.setAdapter(productAdapter);
    }

    private void setEvent() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnAddClick(view);
            }
        });
    }

    private void setControl() {
        btnAdd = findViewById(R.id.btnAdd);
        btnMinimize = findViewById(R.id.btnMinimize);
        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.searchView);
        title = findViewById(R.id.tvTitle);
    }

    private void handleBtnAddClick(View view) {

    }
}
