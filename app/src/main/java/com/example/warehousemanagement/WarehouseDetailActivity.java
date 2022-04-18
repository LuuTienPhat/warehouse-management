package com.example.warehousemanagement;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AlertDialog;
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
        //changeState();

        warehouseDao = new WarehouseDao(DatabaseHelper.getInstance(this));

        String warehouseId = this.getIntent().getStringExtra("warehouseId");
        String warehouseName = this.getIntent().getStringExtra("warehouseName");
        String warehouseAddress = this.getIntent().getStringExtra("warehouseAddress");

        this.warehouse = new Warehouse(warehouseId, warehouseName, warehouseAddress);
        etId.setText(warehouse.getId());
        etName.setText(warehouse.getName());
        etAddress.setText(warehouse.getAddress());
    }

    @Override
    @MainThread
    public void onBackPressed() {
        setResult(Activity.RESULT_OK);
        finish();
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
        btnSave = findViewById(R.id.btnOK);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void handleBtnSaveClick(View view) {
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        // validate inputs
        int errors = 0;
        if (name.isEmpty()) {
            etName.setError("Vui lòng nhập tên kho!");
            errors++;
        }
        if (address.isEmpty()) {
            etAddress.setError("Vui lòng nhập địa chỉ!");
            errors++;
        }
        if (errors != 0) {
            return;
        }

        // update data
        warehouse.setName(name);
        warehouse.setAddress(address);
        if (warehouseDao.updateOne(warehouse)) {
            Toast.makeText(this, "Lưu thay đổi thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Đã có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleBtnCancelClick(View view) {
        setResult(Activity.RESULT_OK);
        finish();
    }

    private void handleBtnDeleteClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa kho");
        builder.setMessage("Bạn có chắc muốn xóa kho này không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (warehouseDao.deleteOne(warehouse)) {
                    Toast.makeText(WarehouseDetailActivity.this.getApplicationContext(), "Xóa kho thành công", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(WarehouseDetailActivity.this.getApplicationContext(), "Đã có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
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
