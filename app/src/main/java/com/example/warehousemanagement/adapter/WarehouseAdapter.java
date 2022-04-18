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
import com.example.warehousemanagement.model.Warehouse;

import java.util.List;

public class WarehouseAdapter extends ArrayAdapter<Warehouse> {

    Context context;
    List<Warehouse> warehouses;
    int resource;

    public WarehouseAdapter(@NonNull Context context, int resource, @NonNull List<Warehouse> warehouses) {
        super(context, resource, warehouses);
        this.context = context;
        this.resource = resource;
        this.warehouses = warehouses;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        Warehouse warehouse = getItem(position);

        TextView tvWarehouseId = convertView.findViewById(R.id.tvWarehouseId);
        TextView tvWarehouseName = convertView.findViewById(R.id.tvWarehouseName);
        TextView tvWarehouseAddress = convertView.findViewById(R.id.tvWarehouseAddress);

        tvWarehouseId.setText(warehouse.getId());
        tvWarehouseName.setText(warehouse.getName());
        tvWarehouseAddress.setText(warehouse.getAddress());


        return convertView;
    }
}
