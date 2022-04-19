package com.example.warehousemanagement;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanagement.dao.WarehouseDao;
import com.example.warehousemanagement.dialog.CustomDialog;
import com.example.warehousemanagement.model.Warehouse;

public class WarehouseDetailActivity extends AppCompatActivity implements CustomDialog.Listener {
    EditText etName, etId, etAddress;
    ImageButton btnEdit, btnDelete;
    Button btnSave, btnCancel;
    Warehouse warehouse;
    LinearLayout lyOption, lyUtils;
    TextView tvTitle, tvWarehouseNameWarning, tvWareHouseAddressWarning;;
    WarehouseDao warehouseDao;
    private boolean inEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warehouse_detail_activity);
        setControl();
        setEvent();

        changeState();

        warehouseDao = new WarehouseDao(DatabaseHelper.getInstance(this));

        String warehouseId = this.getIntent().getStringExtra("warehouseId");
        String warehouseName = this.getIntent().getStringExtra("warehouseName");
        String warehouseAddress = this.getIntent().getStringExtra("warehouseAddress");

        warehouse = new Warehouse(warehouseId, warehouseName, warehouseAddress);
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

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnEditClick(view);
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

        tvWarehouseNameWarning = findViewById(R.id.tvWarehouseNameWarning);
        tvWareHouseAddressWarning = findViewById(R.id.tvWarehouseAddressWarning);
    }

    private void handleBtnSaveClick(View view) {
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        // validate inputs
        int errors = 0;
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

        // update data
        warehouse.setName(name);
        warehouse.setAddress(address);
        if (warehouseDao.updateOne(warehouse)) {
            inEditMode = false;
            changeState();
            Toast.makeText(this, "Lưu thay đổi thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Đã có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleBtnCancelClick(View view) {
        if (inEditMode) {
            etName.setText(warehouse.getName());
            etAddress.setText(warehouse.getAddress());
            tvWarehouseNameWarning.setVisibility(View.GONE);
            tvWareHouseAddressWarning.setVisibility(View.GONE);

            inEditMode = false;
            changeState();
        } else {
            setResult(Activity.RESULT_OK);
            finish();
        }
    }

    private void handleBtnDeleteClick(View view) {
        CustomDialog customDialog = new CustomDialog(CustomDialog.Type.CONFIRM, "Xóa kho",
                "Bạn có chắc muốn xóa kho này không?", "delete");
        customDialog.show(getSupportFragmentManager(), "DeleteWarehouse");

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Xóa kho");
//        builder.setMessage("Bạn có chắc muốn xóa kho này không?");
//        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                if (warehouseDao.deleteOne(warehouse)) {
//                    Toast.makeText(WarehouseDetailActivity.this.getApplicationContext(), "Xóa kho thành công", Toast.LENGTH_SHORT).show();
//                    setResult(Activity.RESULT_OK);
//                    finish();
//                } else {
//                    Toast.makeText(WarehouseDetailActivity.this.getApplicationContext(), "Đã có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//            }
//        });
//        builder.create().show();
    }

    private void handleBtnEditClick(View view) {
        if (!inEditMode) {
            inEditMode = true;
            changeState();
        }
    }

    private void changeState() {
        if (!inEditMode) {
            tvTitle.setText("Chi tiết");

            btnEdit.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.space_cadet, null)));

            etName.setFocusable(false);
            etName.setFocusableInTouchMode(false);
            etName.setAlpha(0.5f);

            etAddress.setFocusable(false);
            etAddress.setFocusableInTouchMode(false);
            etAddress.setAlpha(0.5f);

            btnSave.setVisibility(View.GONE);
            btnCancel.setText("Thoát");
        } else {
            tvTitle.setText("Chỉnh sửa");

            btnEdit.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.alert_red, null)));

            etName.setFocusable(true);
            etName.setFocusableInTouchMode(true);
            etName.setAlpha(1f);

            etAddress.setFocusable(true);
            etAddress.setFocusableInTouchMode(true);
            etAddress.setAlpha(1f);

            btnSave.setVisibility(View.VISIBLE);
            btnSave.setText("Lưu");
            btnCancel.setText("Hủy");
        }
    }

    @Override
    public void sendDialogResult(CustomDialog.Result result, String request) {
        if (result == CustomDialog.Result.OK && request.equals("delete")) {
            if (warehouseDao.canDelete(warehouse.getId())) {
                if (warehouseDao.deleteOne(warehouse)) {
                    Toast.makeText(WarehouseDetailActivity.this.getApplicationContext(), "Xóa kho thành công", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(WarehouseDetailActivity.this.getApplicationContext(), "Đã có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Không thể xóa kho có phiếu nhập!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
