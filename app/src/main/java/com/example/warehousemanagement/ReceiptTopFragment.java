package com.example.warehousemanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.warehousemanagement.adapter.WarehouseAdapter;
import com.example.warehousemanagement.dao.ReceiptDao;
import com.example.warehousemanagement.dao.WarehouseDao;
import com.example.warehousemanagement.model.Warehouse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReceiptTopFragment extends Fragment {
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

    View convertView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater layoutInflater = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        convertView = inflater.inflate(R.layout.receipt_top_fragment, container, false);

        spinnerWarehouse = convertView.findViewById(R.id.spinnerWarehouse);
        tvProcess = convertView.findViewById(R.id.tvProcess);
        tvReceiptDateWarning = convertView.findViewById(R.id.tvReceiptDateWarning);
        tvReceiptIdWarning = convertView.findViewById(R.id.tvReceiptIdWarning);
        tvWarehouseWarning = convertView.findViewById(R.id.tvWarehouseWarning);
        etReceiptId = convertView.findViewById(R.id.etReceiptId);
        etReceiptDate = convertView.findViewById(R.id.etReceiptDate);

        warehouseDao = new WarehouseDao(DatabaseHelper.getInstance(convertView.getContext()));
        warehouses = warehouseDao.getAll();
        warehouses.add(0, new Warehouse("", "Chọn kho", ""));

        warehouseAdapter = new WarehouseAdapter(convertView.getContext(), R.layout.warehouse_spinner, warehouses);
        spinnerWarehouse.setAdapter(warehouseAdapter);

        initDatePicker();

        etReceiptDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        return convertView;
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                chosenDate = LocalDate.of(year, month, day);
                etReceiptDate.setText(chosenDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        };

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue() - 1;
        int day = now.getDayOfMonth();

        etReceiptDate.setText(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        datePickerDialog = new DatePickerDialog(requireActivity(), dateSetListener, year, month, day);
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

        ReceiptDao receiptDao = new ReceiptDao(DatabaseHelper.getInstance(convertView.getContext()));
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
}
