package com.example.warehousemanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.warehousemanagement.R;
import com.example.warehousemanagement.entity.ProductEntity;
import com.example.warehousemanagement.entity.ReceiptEntity;
import com.example.warehousemanagement.model.Receipt;

import java.util.List;

public class ReceiptAdapter extends ArrayAdapter<Receipt> {
    Context context;
    List<Receipt> receipts;
    int resource;

    public ReceiptAdapter(@NonNull Context context, int resource, @NonNull List<Receipt> receipts) {
        super(context, resource, receipts);
        this.context = context;
        this.receipts = receipts;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Receipt receipt = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(this.resource, parent, false);
        }

        TextView tvReceiptId = convertView.findViewById(R.id.tvReceiptId);
        TextView tvReceiptDate = convertView.findViewById(R.id.tvReceiptDate);
        TextView tvWarehouseId = convertView.findViewById(R.id.tvWarehouseId);
        TextView tvNumberOfProducts = convertView.findViewById(R.id.tvNumberOfProducts);

        tvReceiptId.setText(receipt.getId());
        tvReceiptDate.setText(receipt.getDate().toString());
        tvWarehouseId.setText(receipt.getWarehouseId());
        tvNumberOfProducts.setText(receipt.countNumberOfProductTypes() + " loại vật tư");

        return convertView;
    }
}
