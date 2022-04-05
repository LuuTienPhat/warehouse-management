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
import com.example.warehousemanagement.model.Product;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {
    Context context;
    List<Product> products;
    int resource;


    public ProductAdapter(@NonNull Context context, int resource, @NonNull List<Product> products) {
        super(context, resource, products);
        this.context = context;
        this.resource = resource;
        this.products = products;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product product = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        TextView tvProductId = convertView.findViewById(R.id.tvProductId);
        TextView tvProductName = convertView.findViewById(R.id.tvProductName);
        TextView tvProductOrigin = convertView.findViewById(R.id.tvProductOrigin);
//        TextView tvProductUnit = convertView.findViewById(R.id.tvProductUnit);

        tvProductId.setText(product.getId());
        tvProductName.setText(product.getName());
        tvProductOrigin.setText(product.getOrigin());
//        tvProductUnit.setText("ĐVT");

        return convertView;
    }
}
