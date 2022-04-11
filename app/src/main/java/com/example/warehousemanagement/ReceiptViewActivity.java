package com.example.warehousemanagement;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.warehousemanagement.adapter.ReceiptDetailAdapter;
import com.example.warehousemanagement.dao.WarehouseDao;
import com.example.warehousemanagement.model.Receipt;
import com.example.warehousemanagement.model.Warehouse;
import com.example.warehousemanagement.utilities.DateHandler;

public class ReceiptViewActivity extends AppCompatActivity {
    TextView tvTitle, tvNumberOfProducts;
    EditText etWarehouseId, etWarehouseName, etReceiptId, etReceiptDate;
    ListView listView;
    ConstraintLayout receipt_detail_view;

    ReceiptDetailAdapter receiptDetailAdapter;
    Receipt receipt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_view_activity);
        setControl();
        setEvent();

        tvTitle.setText("Chi tiết");

        receipt = (Receipt) this.getIntent().getSerializableExtra("receipt");
        WarehouseDao warehouseDao = new WarehouseDao(DatabaseHelper.getInstance(this));
        Warehouse warehouse = warehouseDao.getOne(receipt.getWarehouseId());

        etWarehouseId.setText(receipt.getWarehouseId());
        etWarehouseName.setText(warehouse.getName());

        etReceiptId.setText(Integer.toString(receipt.getId()));
        etReceiptDate.setText(DateHandler.convertLocalDateToString(receipt.getDate()));

        tvNumberOfProducts.setText(Integer.toString(receipt.getReceiptDetails().size()));
        receiptDetailAdapter = new ReceiptDetailAdapter(this, R.layout.receipt_detail_item, receipt.getReceiptDetails());
        listView.setAdapter(receiptDetailAdapter);
        int height = 80 * 3;
        receipt_detail_view.setMinHeight(height);
    }

    private void setEvent() {
    }

    private void setControl() {
        tvTitle = findViewById(R.id.tvTitle);
        tvNumberOfProducts = findViewById(R.id.tvNumberOfProducts);
        etWarehouseId = findViewById(R.id.etWarehouseId);
        etWarehouseName = findViewById(R.id.etWarehouseName);
        etReceiptId = findViewById(R.id.etReceiptId);
        etReceiptDate = findViewById(R.id.etReceiptDate);
        listView = findViewById(R.id.listView);
        receipt_detail_view = findViewById(R.id.receipt_detail_view);
    }
}
