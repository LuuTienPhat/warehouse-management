package com.example.warehousemanagement;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.warehousemanagement.adapter.WarehouseSpinnerAdapter;
import com.example.warehousemanagement.dao.ReceiptDao;
import com.example.warehousemanagement.dao.WarehouseDao;
import com.example.warehousemanagement.model.Receipt;
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
    WarehouseSpinnerAdapter warehouseSpinnerAdapter;
    WarehouseDao warehouseDao = null;
    List<Warehouse> warehouses = new ArrayList<>();
    Warehouse selectedWarehouse = null;
    DatePickerDialog datePickerDialog;
    LocalDate chosenDate = null;

    View convertView;

    AddReceiptActivity addReceiptActivity = null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddReceiptActivity) {
            addReceiptActivity = (AddReceiptActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        convertView = inflater.inflate(R.layout.receipt_top_fragment, container, false);

        setControl();

        warehouseDao = new WarehouseDao(DatabaseHelper.getInstance(convertView.getContext()));
        warehouses = warehouseDao.getAll();
        warehouses.add(0, new Warehouse("", "Chọn kho", ""));

        warehouseSpinnerAdapter = new WarehouseSpinnerAdapter(convertView.getContext(), R.layout.warehouse_spinner, warehouses);
        spinnerWarehouse.setAdapter(warehouseSpinnerAdapter);

        initDatePicker();

        setEvent();

        return convertView;
    }

    private void setEvent() {
        etReceiptDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        spinnerWarehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (position == 0) {
                    selectedWarehouse = null;
                    return;
                } else {
                    selectedWarehouse = (Warehouse) adapterView.getItemAtPosition(position);
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
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    public boolean senDataToActivity() {
        if (isInputValid()) {
            int receiptId = Integer.parseInt(etReceiptId.getText().toString().trim());
            String warehouseId = selectedWarehouse.getId();

            Receipt receipt = addReceiptActivity.getNewReceipt();
            receipt.setId(receiptId);
            receipt.setWarehouseId(selectedWarehouse.getId());
            LocalDate receiptDate = LocalDate.of(datePickerDialog.getDatePicker().getYear(),
                    datePickerDialog.getDatePicker().getMonth() + 1,
                    datePickerDialog.getDatePicker().getDayOfMonth());
            receipt.setDate(receiptDate);
            addReceiptActivity.setNewReceipt(receipt);

            return true;
        } else return false;
    }

    private boolean isInputValid() {
        int count = 0;
//        if (chosenDate == null) {
//            count++;
//            tvReceiptDateWarning.setText("Ngày tạo không hợp lệ!");
//        } else {
//            count--;
//            tvReceiptDateWarning.setText("");
//        }

        if (selectedWarehouse == null) {
            count++;
            tvWarehouseWarning.setText("Kho không hợp lệ");

        } else tvReceiptIdWarning.setText("");

        String receiptId = etReceiptId.getText().toString().trim();
        if (receiptId.isEmpty()) {
            count++;
            tvReceiptIdWarning.setText("Số phiếu không hợp lệ");
        } else {
            try {
                Integer.parseInt(receiptId);

                ReceiptDao receiptDao = new ReceiptDao(DatabaseHelper.getInstance(convertView.getContext()));
                if (receiptDao.getOne(receiptId) != null) {
                    count++;
                    tvReceiptIdWarning.setText("Số phiếu đã bị trùng");
                } else tvReceiptIdWarning.setText("");

            } catch (Exception ex) {
                count++;
                tvReceiptIdWarning.setText("Số phiếu phải là số");
            }
        }


        return count == 0;
    }

    private void setControl() {
        spinnerWarehouse = convertView.findViewById(R.id.spinnerWarehouse);
        tvProcess = convertView.findViewById(R.id.tvProcess);
        tvReceiptDateWarning = convertView.findViewById(R.id.tvReceiptDateWarning);
        tvReceiptIdWarning = convertView.findViewById(R.id.tvReceiptIdWarning);
        tvWarehouseWarning = convertView.findViewById(R.id.tvWarehouseWarning);
        etReceiptId = convertView.findViewById(R.id.etReceiptId);
        etReceiptDate = convertView.findViewById(R.id.etReceiptDate);
    }
}
