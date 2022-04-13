package com.example.warehousemanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AddReceiptActivity extends AppCompatActivity {
    private ImageButton btnCancel, btnPrevious, btnForward;
    ReceiptTopFragment receiptTopFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_add);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receiptTopFragment = new ReceiptTopFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentNavigation1, receiptTopFragment);
                fragmentTransaction.commit();
            }
        });
    }

    private void setControl() {
        btnCancel = findViewById(R.id.btnCancel);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnForward = findViewById(R.id.btnForward);
    }
}