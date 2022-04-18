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

import java.util.ArrayList;
import java.util.List;


public class WarehouseSpinnerAdapter extends ArrayAdapter<Warehouse> {
    private Context context;
    private int resource;
    private List<Warehouse> warehouseList = new ArrayList<>();

    public WarehouseSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Warehouse> warehouseList) {
        super(context, resource, warehouseList);
        this.context = context;
        this.resource = resource;
        this.warehouseList = warehouseList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, parent, false);

        Warehouse warehouse = getItem(position);

        TextView tvWarehouseName = convertView.findViewById(R.id.tvWarehouseName);

        if (warehouse != null) {
            if (position == 0)
                tvWarehouseName.setTextColor(context.getColor(R.color.steel_blue));
            else tvWarehouseName.setTextColor(context.getColor(R.color.space_cadet));

            tvWarehouseName.setText(warehouse.getName());

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
        View divider = convertView.findViewById(R.id.divider);

        if (position == warehouseList.size() - 1) {
            divider.setVisibility(View.GONE);
        }

        tvWarehouseId.setText(warehouse.getId());
        tvWarehouseName.setText(warehouse.getName());

        return convertView;
    }
}
