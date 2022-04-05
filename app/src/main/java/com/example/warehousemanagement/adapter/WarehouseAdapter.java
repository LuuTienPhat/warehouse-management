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

        if (resource == R.layout.warehouse_spinner) {
            TextView tvWarehouseName = convertView.findViewById(R.id.tvWarehouseName);

            if (warehouse != null) {
                if (position == 0)
                    tvWarehouseName.setTextColor(context.getColor(R.color.steel_blue));
                else tvWarehouseName.setTextColor(context.getColor(R.color.space_cadet));

                tvWarehouseName.setText(warehouse.getName());

            }

        } else {
            TextView tvWarehouseId = convertView.findViewById(R.id.tvWarehouseId);
            TextView tvWarehouseName = convertView.findViewById(R.id.tvWarehouseName);
            TextView tvWarehouseAddress = convertView.findViewById(R.id.tvWarehouseAddress);

            tvWarehouseId.setText(warehouse.getId());
            tvWarehouseName.setText(warehouse.getName());
            tvWarehouseAddress.setText(warehouse.getAddress());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Warehouse warehouse = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.warehouse_item_spinner, parent, false);
        }

        TextView tvWarehouseId = convertView.findViewById(R.id.tvWarehouseId);
        TextView tvWarehouseName = convertView.findViewById(R.id.tvWarehouseName);

        tvWarehouseId.setText(warehouse.getId());
        tvWarehouseName.setText(warehouse.getName());

        return convertView;
    }
}
