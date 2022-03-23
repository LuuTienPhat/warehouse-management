package com.example.warehousemanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.warehousemanagement.R;
import com.example.warehousemanagement.model.Warehouse;

import java.util.List;

public class WarehouseAdapter extends ArrayAdapter<Warehouse> {

    public WarehouseAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public WarehouseAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public WarehouseAdapter(@NonNull Context context, int resource, @NonNull Warehouse[] objects) {
        super(context, resource, objects);
    }

    public WarehouseAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Warehouse[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public WarehouseAdapter(@NonNull Context context, int resource, @NonNull List<Warehouse> objects) {
        super(context, resource, objects);
    }

    public WarehouseAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Warehouse> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Warehouse warehouse = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.warehouse_item, parent, false);

        }

        TextView tvWarehouseId = convertView.findViewById(R.id.warehouse_id);
        TextView tvWarehouseName = convertView.findViewById(R.id.warehouse_name);
        TextView tvWarehouseAddress = convertView.findViewById(R.id.warehouse_address);

        tvWarehouseId.setText(warehouse.getId());
        tvWarehouseName.setText(warehouse.getName());
        tvWarehouseAddress.setText(warehouse.getAddress());

        return super.getView(position, convertView, parent);
    }
}
