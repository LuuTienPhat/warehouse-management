package com.example.warehousemanagement;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.warehousemanagement.model.Receipt;

public class ReceiptViewActivity extends AppCompatActivity {
    TextView tvTitle;
    Receipt receipt;
    ReceiptViewFragment receiptViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_view_activity);
        setControl();
        setEvent();

        tvTitle.setText("Chi tiết");

        receipt = (Receipt) this.getIntent().getSerializableExtra("receipt");
        ReceiptViewFragment receiptViewFragment = new ReceiptViewFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, receiptViewFragment);
        fragmentTransaction.commit();
    }

    private void setEvent() {
    }

    private void setControl() {
        tvTitle = findViewById(R.id.tvTitle);
    }

    public Receipt getReceipt() {
        return this.receipt;
    }
}
