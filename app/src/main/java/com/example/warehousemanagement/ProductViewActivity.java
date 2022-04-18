package com.example.warehousemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanagement.adapter.ReceiptDetailProductAdapter;
import com.example.warehousemanagement.dao.ProductDao;
import com.example.warehousemanagement.model.Product;
import com.example.warehousemanagement.model.Receipt;
import com.example.warehousemanagement.model.ReceiptDetail;

import java.util.ArrayList;
import java.util.List;

public class ProductViewActivity extends AppCompatActivity {
    private ListView listView;

    ProductDao productDao = null;
    ReceiptDetailProductAdapter receiptDetailProductAdapter = null;
    List<Product> products = new ArrayList<>();
    List<ReceiptDetail> receiptDetails = new ArrayList<>();
    Receipt receipt = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_detail_add_product_activity);
        setControl();
        setEvent();

        receipt = (Receipt) this.getIntent().getSerializableExtra("receipt");
        productDao = new ProductDao(DatabaseHelper.getInstance(this));
        products = productDao.getAll();

        receiptDetailProductAdapter = new ReceiptDetailProductAdapter(this, R.layout.product_item_small,
                products, receipt.getReceiptDetails());
        listView.setAdapter(receiptDetailProductAdapter);
    }

    private void setControl() {
        listView = findViewById(R.id.listView);
    }

    private void setEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Product product = (Product) adapterView.getItemAtPosition(position);

                Intent intent = new Intent(ProductViewActivity.this, EditReceiptDetailActivity.class);
                intent.putExtra("product", product);
                intent.putExtra("receipt", receipt);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            ReceiptDetail receiptDetail = (ReceiptDetail) data.getSerializableExtra("receiptDetail");
            Intent intent = new Intent();
            intent.putExtra("receiptDetail", receiptDetail);
            setResult(2, intent);
            finish();
        }
    }

}
