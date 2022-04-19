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

import com.example.warehousemanagement.dao.WarehouseDao;
import com.example.warehousemanagement.model.Warehouse;

public class AddWarehouseActivity extends AppCompatActivity {
    EditText etName, etId, etAddress;
    ImageButton btnEdit, btnDelete;
    Button btnSave, btnCancel;
    Warehouse warehouse = null;
    LinearLayout lyOption, lyUtils;
    TextView tvWarehouseIdWarning, tvWarehouseNameWarning, tvWareHouseAddressWarning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warehouse_add_activity);
        setControl();
        setEvent();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
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
    }

    private void setControl() {
        btnEdit = findViewById(R.id.btnEdit);
        btnCancel = findViewById(R.id.btnCancel);

        etAddress = findViewById(R.id.etAddress);
        etName = findViewById(R.id.etName);
        etId = findViewById(R.id.etId);

        lyOption = findViewById(R.id.lyOption);
        btnSave = findViewById(R.id.btnOK);
        btnCancel = findViewById(R.id.btnCancel);

        tvWarehouseIdWarning = findViewById(R.id.tvWarehouseIdWarning);
        tvWarehouseNameWarning = findViewById(R.id.tvWarehouseNameWarning);
        tvWareHouseAddressWarning = findViewById(R.id.tvWarehouseAddressWarning);
    }

    private void handleBtnSaveClick(View view) {
        String id = etId.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        // validate inputs
        int errors = 0;
        if (id.isEmpty()) {
            tvWarehouseIdWarning.setText("Vui lòng nhập mã kho!");
            tvWarehouseIdWarning.setVisibility(View.VISIBLE);
            errors++;
        } else {
            tvWarehouseIdWarning.setVisibility(View.GONE);
        }
        if (name.isEmpty()) {
            tvWarehouseNameWarning.setVisibility(View.VISIBLE);
            errors++;
        } else {
            tvWarehouseNameWarning.setVisibility(View.GONE);
        }
        if (address.isEmpty()) {
            tvWareHouseAddressWarning.setVisibility(View.VISIBLE);
            errors++;
        } else {
            tvWareHouseAddressWarning.setVisibility(View.GONE);
        }
        if (errors != 0) {
            return;
        }

        // Check if the id exists or not. If yes, insert the new one. If not, show a warning.
        WarehouseDao warehouseDao = new WarehouseDao(DatabaseHelper.getInstance(this));
        if (warehouseDao.getOne(id) == null) {
            tvWarehouseIdWarning.setVisibility(View.GONE);
            if (warehouseDao.insertOne(new Warehouse(id, name, address))) {
                Toast.makeText(this, "Thêm kho thành công", Toast.LENGTH_SHORT).show();
                etId.setText("");
                etName.setText("");
                etAddress.setText("");
            } else {
                Toast.makeText(this, "Đã có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            }
        } else {
            etId.requestFocus();
            tvWarehouseIdWarning.setText("Mã kho này đã tồn tại!");
            tvWarehouseIdWarning.setVisibility(View.VISIBLE);
        }
    }

    private void handleBtnCancelClick(View view) {
        setResult(RESULT_OK);
        finish();
    }
}
