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
import com.example.warehousemanagement.model.ProductQuantityDetail;

import java.util.List;

public class ProductQuantityDetailAdapter extends ArrayAdapter<ProductQuantityDetail> {
    Context context;
    List<ProductQuantityDetail> productQuantityDetails;
    int resource;


    public ProductQuantityDetailAdapter(@NonNull Context context, int resource, @NonNull List<ProductQuantityDetail> productQuantityDetails) {
        super(context, resource, productQuantityDetails);
        this.context = context;
        this.resource = resource;
        this.productQuantityDetails = productQuantityDetails;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ProductQuantityDetail productQuantityDetail = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        TextView tvWarehouseId = convertView.findViewById(R.id.tvWarehouseId);
        TextView tvWarehouseName = convertView.findViewById(R.id.tvWarehouseName);
        TextView tvProductQuantity = convertView.findViewById(R.id.tvProductQuantity);
//        TextView tvProductUnit = convertView.findViewById(R.id.tvProductUnit);

        tvWarehouseId.setText(productQuantityDetail.getWareHouseId());
        tvWarehouseName.setText(productQuantityDetail.getWareHouseName());
        tvProductQuantity.setText(productQuantityDetail.getProductId()
                +" - Số lượng: "+productQuantityDetail.getQuantity()+", "+productQuantityDetail.getUnit());
//        tvProductUnit.setText("ĐVT");

        return convertView;
    }
    public List<ProductQuantityDetail> getData(){
        return productQuantityDetails;
    }
}
