package com.example.warehousemanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.warehousemanagement.DatabaseHelper;
import com.example.warehousemanagement.R;
import com.example.warehousemanagement.dao.ProductDao;
import com.example.warehousemanagement.model.Product;
import com.example.warehousemanagement.model.ReceiptDetail;

import java.util.List;

public class ReceiptDetailAdapter extends ArrayAdapter<ReceiptDetail> {
    Context context;
    List<ReceiptDetail> receiptDetails;
    int resource;

    public ReceiptDetailAdapter(@NonNull Context context, int resource, @NonNull List<ReceiptDetail> receiptDetails) {
        super(context, resource, receiptDetails);
        this.context = context;
        this.receiptDetails = receiptDetails;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ReceiptDetail receiptDetail = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(this.resource, parent, false);
        }

        ProductDao productDao = new ProductDao(DatabaseHelper.getInstance(context));
        Product product = productDao.getOne(receiptDetail.getProductId());

        TextView tvNo = convertView.findViewById(R.id.tvNo);
        TextView tvProductId = convertView.findViewById(R.id.tvProductId);
        TextView tvProductName = convertView.findViewById(R.id.tvProductName);
//        TextView tvProductOrigin = convertView.findViewById(R.id.tvProductOrigin);
        TextView tvProductQuantity = convertView.findViewById(R.id.tvProductQuantity);
        TextView tvProductUnit = convertView.findViewById(R.id.tvProductUnit);

        tvNo.setText(Integer.toString(position + 1));
        tvProductId.setText(product.getId());
        tvProductName.setText(product.getName());
//        tvProductOrigin.setText(product.getOrigin());
        tvProductQuantity.setText(Integer.toString(receiptDetail.getQuantity()));
        tvProductUnit.setText("(" + receiptDetail.getUnit() + ")");

        return convertView;
    }
}
