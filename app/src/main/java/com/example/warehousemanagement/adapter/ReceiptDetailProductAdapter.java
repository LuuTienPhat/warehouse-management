package com.example.warehousemanagement.adapter;

import android.annotation.SuppressLint;
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
import com.example.warehousemanagement.model.ReceiptDetail;

import java.util.List;

public class ReceiptDetailProductAdapter extends ArrayAdapter<Product> {
    Context context;
    List<Product> productList;
    List<ReceiptDetail> receiptDetailList;
    int resource;

    public ReceiptDetailProductAdapter(@NonNull Context context, int resource, @NonNull List<Product> productList, @NonNull List<ReceiptDetail> receiptDetailList) {
        super(context, resource, productList);
        this.context = context;
        this.resource = resource;
        this.productList = productList;
        this.receiptDetailList = receiptDetailList;
    }

    private boolean isProductInReceiptDetailList(String productId) {
        for (ReceiptDetail receiptDetail : this.receiptDetailList) {
            if (productId.equals(receiptDetail.getProductId())) {
                return true;
            }
        }
        return false;
    }

    private ReceiptDetail getReceiptDetailByProductId(String productId) {
        for (ReceiptDetail receiptDetail : this.receiptDetailList) {
            if (productId.equals(receiptDetail.getProductId())) {
                return receiptDetail;
            }
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        Product product = getItem(position);
        if (isProductInReceiptDetailList(product.getId()))
            return 1;
        else return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int listViewItemType = getItemViewType(position);
        Product product = getItem(position);

        if (listViewItemType == 0) {
            convertView = LayoutInflater.from(context).inflate(R.layout.constraint_product_item_small, parent, false);

            TextView tvProductId = convertView.findViewById(R.id.tvProductId);
            TextView tvProductName = convertView.findViewById(R.id.tvProductName);
            TextView tvProductOrigin = convertView.findViewById(R.id.tvProductOrigin);
//        TextView tvProductUnit = convertView.findViewById(R.id.tvProductUnit);

            tvProductId.setText(product.getId());
            tvProductName.setText(product.getName());
            tvProductOrigin.setText(product.getOrigin());
//        tvProductUnit.setText("ĐVT");

        } else if (listViewItemType == 1) {
            convertView = LayoutInflater.from(context).inflate(R.layout.receipt_detail_item, parent, false);

            TextView tvNo = convertView.findViewById(R.id.tvNo);
            TextView tvProductId = convertView.findViewById(R.id.tvProductId);
            TextView tvProductName = convertView.findViewById(R.id.tvProductName);
//        TextView tvProductOrigin = convertView.findViewById(R.id.tvProductOrigin);
            TextView tvProductQuantity = convertView.findViewById(R.id.tvProductQuantity);
            TextView tvProductUnit = convertView.findViewById(R.id.tvProductUnit);

            ReceiptDetail receiptDetail = getReceiptDetailByProductId(product.getId());

            tvNo.setText(Integer.toString(position + 1));
            tvProductId.setText(product.getId());
            tvProductName.setText(product.getName());
//        tvProductOrigin.setText(product.getOrigin());
            tvProductQuantity.setText(Integer.toString(receiptDetail.getQuantity()));
            tvProductUnit.setText("(" + receiptDetail.getUnit() + ")");
        }

        return convertView;
    }
}
