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
import com.example.warehousemanagement.model.Product;

import java.util.List;

public class ProductAdapterSmall extends ArrayAdapter<ProductEntity> {
    Context context;
    List<ProductEntity> products;
    int resource;


    public ProductAdapterSmall(@NonNull Context context, int resource, @NonNull List<ProductEntity> products) {
        super(context, resource, products);
        this.context = context;
        this.resource = resource;
        this.products = products;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ProductEntity product = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_item_small, parent, false);
        }

        TextView tvProductId = convertView.findViewById(R.id.tvProductId);
        TextView tvProductName = convertView.findViewById(R.id.tvProductName);
        TextView tvProductOrigin = convertView.findViewById(R.id.tvProductOrigin);

        tvProductId.setText(product.getId());
        tvProductName.setText(product.getName());
        tvProductOrigin.setText(product.getOrigin());

        return convertView;
    }
}
