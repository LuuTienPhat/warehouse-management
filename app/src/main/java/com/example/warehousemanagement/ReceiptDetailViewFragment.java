package com.example.warehousemanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.warehousemanagement.adapter.ReceiptDetailAdapter;
import com.example.warehousemanagement.dao.ProductDao;
import com.example.warehousemanagement.dialog.CustomDialog;
import com.example.warehousemanagement.model.Product;
import com.example.warehousemanagement.model.Receipt;
import com.example.warehousemanagement.model.ReceiptDetail;

public class ReceiptDetailViewFragment extends Fragment {
    ImageButton btnAdd, btnDeleteAll;
    ListView listView;
    TextView tvNumberOfProducts;
    View convertView;
    AddReceiptActivity addReceiptActivity;

    Receipt receipt;
    ReceiptDetailAdapter receiptDetailAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddReceiptActivity) {
            addReceiptActivity = (AddReceiptActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        convertView = inflater.inflate(R.layout.receipt_detail_add_fragment, null);
        setControl();
        setEvent();

        receipt = addReceiptActivity.getNewReceipt();
        updateListView();

        return convertView;
    }

    private void handleBtnAddClick(View view) {
        Intent intent = new Intent(requireActivity(), ProductViewActivity.class);
        intent.putExtra("receipt", addReceiptActivity.getNewReceipt());
        requireActivity().startActivityForResult(intent, 1);
    }

    private void handleBtnDeleteAllClick(View view) {
        CustomDialog customDialog = new CustomDialog(CustomDialog.Type.CONFIRM, "Cảnh báo", "Tất cả dữ liệu vừa thêm sẽ bị xóa. Bạn có chắc không?", "deleteAll");
        customDialog.show(requireActivity().getSupportFragmentManager(), "");
    }

    public void updateListView() {
        tvNumberOfProducts.setText(Integer.toString(receipt.getReceiptDetails().size()));
        receiptDetailAdapter = new ReceiptDetailAdapter(requireContext(), R.layout.receipt_detail_item, receipt.getReceiptDetails());
        listView.setAdapter(receiptDetailAdapter);
        receiptDetailAdapter.notifyDataSetChanged();
    }

    private void setEvent() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnAddClick(view);
            }
        });
        btnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnDeleteAllClick(view);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ReceiptDetail receiptDetail = (ReceiptDetail) adapterView.getItemAtPosition(position);

                Intent intent = new Intent(requireActivity(), EditReceiptDetailActivity.class);
                intent.putExtra("receiptDetail", receiptDetail);
                intent.putExtra("receipt", addReceiptActivity.getNewReceipt());

                ProductDao productDao = new ProductDao(DatabaseHelper.getInstance(requireContext()));
                Product product = productDao.getOne(receiptDetail.getProductId());
                intent.putExtra("product", product);
                requireActivity().startActivityForResult(intent, 1);
            }
        });
    }

    private void setControl() {
        btnAdd = convertView.findViewById(R.id.btnAdd);
        btnDeleteAll = convertView.findViewById(R.id.btnDeleteAll);
        tvNumberOfProducts = convertView.findViewById(R.id.tvNumberOfProducts);
        listView = convertView.findViewById(R.id.listView);
    }

}
