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
import com.example.warehousemanagement.entity.WarehouseEntity;

import java.util.List;

public class WarehouseAdapter extends ArrayAdapter<WarehouseEntity> {

    Context context;
    List<WarehouseEntity> warehouses;
    int resource;

    public WarehouseAdapter(@NonNull Context context, int resource, @NonNull List<WarehouseEntity> warehouses) {
        super(context, resource, warehouses);
        this.context = context;
        this.resource = resource;
        this.warehouses = warehouses;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        WarehouseEntity warehouse = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.warehouse_item, parent, false);
        }

        TextView tvWarehouseId = convertView.findViewById(R.id.warehouseId);
        TextView tvWarehouseName = convertView.findViewById(R.id.warehouseName);
        TextView tvWarehouseAddress = convertView.findViewById(R.id.warehouseAddress);

        tvWarehouseId.setText(warehouse.getId());
        tvWarehouseName.setText(warehouse.getName());
        tvWarehouseAddress.setText(warehouse.getAddress());

        return convertView;
    }
}
