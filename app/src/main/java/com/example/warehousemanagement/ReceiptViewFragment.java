package com.example.warehousemanagement;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.warehousemanagement.adapter.ReceiptDetailAdapter;
import com.example.warehousemanagement.dao.WarehouseDao;
import com.example.warehousemanagement.model.Receipt;
import com.example.warehousemanagement.model.Warehouse;
import com.example.warehousemanagement.utilities.DateHandler;

public class ReceiptViewFragment extends Fragment {
    View convertView;
    TextView tvNumberOfProducts;
    EditText etWarehouseId, etWarehouseName, etReceiptId, etReceiptDate;
    ListView listView;

    ReceiptDetailAdapter receiptDetailAdapter;
    Receipt receipt;
    AddReceiptActivity addReceiptActivity;
    ReceiptViewActivity receiptViewActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddReceiptActivity)
            addReceiptActivity = (AddReceiptActivity) context;
        else if (context instanceof ReceiptViewActivity)
            receiptViewActivity = (ReceiptViewActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        convertView = inflater.inflate(R.layout.receipt_view_fragment, null);
        setControl();
        setEvent();
        return convertView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateData();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }

    public void updateData() {
        if (addReceiptActivity != null) receipt = addReceiptActivity.getNewReceipt();
        else if (receiptViewActivity != null) receipt = receiptViewActivity.getReceipt();

        WarehouseDao warehouseDao = new WarehouseDao(DatabaseHelper.getInstance(requireContext()));
        Warehouse warehouse = warehouseDao.getOne(receipt.getWarehouseId());
        etWarehouseId.setText(warehouse.getId());
        etWarehouseName.setText(warehouse.getName());
        etReceiptId.setText(Integer.toString(receipt.getId()));
        etReceiptDate.setText(DateHandler.convertLocalDateToString(receipt.getDate()));
        tvNumberOfProducts.setText(Integer.toString(receipt.getReceiptDetails().size()));

        ReceiptDetailAdapter receiptDetailAdapter = new ReceiptDetailAdapter(requireActivity(), R.layout.receipt_detail_item, receipt.getReceiptDetails());
        listView.setAdapter(receiptDetailAdapter);
    }

    private void setControl() {
        tvNumberOfProducts = convertView.findViewById(R.id.tvNumberOfProducts);
        etWarehouseId = convertView.findViewById(R.id.etWarehouseId);
        etWarehouseName = convertView.findViewById(R.id.etWarehouseName);
        etReceiptId = convertView.findViewById(R.id.etReceiptId);
        etReceiptDate = convertView.findViewById(R.id.etReceiptDate);
        listView = convertView.findViewById(R.id.listView);
    }

    private void setEvent() {
    }
}
