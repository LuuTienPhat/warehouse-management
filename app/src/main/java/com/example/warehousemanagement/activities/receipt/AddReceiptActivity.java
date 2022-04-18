package com.example.warehousemanagement.activities.receipt;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanagement.DatabaseHelper;
import com.example.warehousemanagement.R;
import com.example.warehousemanagement.adapter.WarehouseAdapter;
import com.example.warehousemanagement.dao.ReceiptDao;
import com.example.warehousemanagement.dao.WarehouseDao;
import com.example.warehousemanagement.model.Receipt;
import com.example.warehousemanagement.model.Warehouse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AddReceiptActivity extends AppCompatActivity {
    ImageButton btnCancel, btnPrevious, btnForward;
    TextView tvProcess, tvWarehouseWarning, tvReceiptIdWarning, tvReceiptDateWarning;
    EditText etReceiptId;
    Button etReceiptDate;
    Spinner spinnerWarehouse;
    WarehouseAdapter warehouseAdapter;
    WarehouseDao warehouseDao = null;
    List<Warehouse> warehouses = new ArrayList<>();
    Warehouse selectedWarehouse = null;
    DatePickerDialog datePickerDialog;
    LocalDate chosenDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_add_activity);
        setControl();
        setEvent();

        warehouseDao = new WarehouseDao(DatabaseHelper.getInstance(this));
        warehouses = warehouseDao.getAll();
        warehouses.add(0, new Warehouse("", "Chọn kho", ""));

        warehouseAdapter = new WarehouseAdapter(this, R.layout.warehouse_spinner, warehouses);
        spinnerWarehouse.setAdapter(warehouseAdapter);

        initDatePicker();
    }

    private void setEvent() {
        etReceiptDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnCancelClick(view);
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnPreviousClick(view);
            }
        });

        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnNextClick(view);
            }
        });

        spinnerWarehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    selectedWarehouse = (Warehouse) adapterView.getSelectedItem();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = year + "-" + month + "-" + day;
                chosenDate = LocalDate.of(year, month, day);
                etReceiptDate.setText(chosenDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        };

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
    }

    private void setControl() {
        spinnerWarehouse = findViewById(R.id.spinnerWarehouse);
        btnCancel = findViewById(R.id.btnCancel);
        btnForward = findViewById(R.id.btnForward);
        btnPrevious = findViewById(R.id.btnPrevious);
        tvProcess = findViewById(R.id.tvProcess);
        tvReceiptDateWarning = findViewById(R.id.tvReceiptDateWarning);
        tvReceiptIdWarning = findViewById(R.id.tvReceiptIdWarning);
        tvWarehouseWarning = findViewById(R.id.tvWarehouseWarning);
        etReceiptId = findViewById(R.id.etReceiptId);
        etReceiptDate = findViewById(R.id.etReceiptDate);
    }

    private void handleBtnCancelClick(View view) {

    }

    private void handleBtnPreviousClick(View view) {

    }

    private boolean isInputValid() {
        int count = 0;
        if (chosenDate == null) {
            count++;
            tvReceiptDateWarning.setText("Ngày tạo không hợp lệ!");
        } else {
            count--;
            tvReceiptDateWarning.setText("");
        }

        if (selectedWarehouse == null) {
            count++;
            tvWarehouseWarning.setText("Kho không hợp lệ");

        } else {
            count--;
            tvReceiptIdWarning.setText("");
        }

        ReceiptDao receiptDao = new ReceiptDao(DatabaseHelper.getInstance(this));
        String receiptId = etReceiptId.getText().toString();

        if (receiptId.isEmpty()) {
            count++;
            tvReceiptIdWarning.setText("Số phiếu không hợp lệ");

        } else if (receiptDao.getOne(receiptId).getId() == Integer.parseInt(receiptId)) {
            count++;
            tvReceiptIdWarning.setText("Số phiếu đã bị trùng");
        } else {
            count--;
            tvReceiptIdWarning.setText("");
        }
        return count == 0;
    }

    private void handleBtnNextClick(View view) {
        int receiptId = Integer.parseInt(etReceiptId.getText().toString().trim());

        Receipt receipt = new Receipt();
        receipt.setId(receiptId);
        receipt.setWarehouseId(selectedWarehouse.getId());
        receipt.setDate(chosenDate);

        Intent intent = new Intent();
    }
}
