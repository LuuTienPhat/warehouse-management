package com.example.warehousemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanagement.dialog.CustomDialog;
import com.example.warehousemanagement.model.Product;
import com.example.warehousemanagement.model.Receipt;
import com.example.warehousemanagement.model.ReceiptDetail;

public class EditReceiptDetailActivity extends AppCompatActivity implements CustomDialog.Listener {
    ImageButton btnDelete;
    EditText etQuantity, etUnit;
    Button btnSave, btnCancel;
    TextView tvProductId, tvProductName, tvProductOrigin, tvQuantityWarning, tvUnitWarning;

    Product product;
    Receipt receipt;
    int receiptDetailIndex;

    AddReceiptActivity addReceiptActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_detail_edit_activity);
        setControl();
        setEvent();

        product = (Product) this.getIntent().getSerializableExtra("product");
        receipt = (Receipt) this.getIntent().getSerializableExtra("receipt");

        tvProductId.setText(product.getId());
        tvProductName.setText(product.getName());
        tvProductOrigin.setText(product.getOrigin());

        receiptDetailIndex = getReceiptDetail(product.getId());
        if (receiptDetailIndex != -1) {
            ReceiptDetail receiptDetail = receipt.getReceiptDetails().get(receiptDetailIndex);
            etQuantity.setText(Integer.toString(receiptDetail.getQuantity()));
            etUnit.setText(receiptDetail.getUnit());
        }

    }

    private int getReceiptDetail(String productId) {
        for (int i = 0; i < receipt.getReceiptDetails().size(); i++) {
            if (receipt.getReceiptDetails().get(i).getProductId().equals(productId)) {
                return i;
            }
        }
        return -1;
    }

    private void setEvent() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnDeleteClick(view);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnSaveClick(view);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnCancelClick(view);
            }
        });
    }

    private void setControl() {
        btnDelete = findViewById(R.id.btnDelete);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        btnSave = findViewById(R.id.btnOK);
        btnCancel = findViewById(R.id.btnCancel);
        tvProductId = findViewById(R.id.tvProductId);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductOrigin = findViewById(R.id.tvProductOrigin);
        tvQuantityWarning = findViewById(R.id.tvQuantityWarning);
        tvUnitWarning = findViewById(R.id.tvUnitWarning);
    }

    private void handleBtnDeleteClick(View view) {
        CustomDialog customDialog = new CustomDialog(CustomDialog.Type.CONFIRM, "Xác nhận", "Bạn muốn xóa vật tư này chứ", "");
        customDialog.show(getSupportFragmentManager(), "");
    }

    private void handleBtnSaveClick(View view) {
        if (isInputValid()) {
            int quantity = Integer.parseInt(etQuantity.getText().toString().trim());
            String unit = etUnit.getText().toString();

            ReceiptDetail receiptDetail = new ReceiptDetail();
            receiptDetail.setReceiptId(receipt.getId());
            receiptDetail.setProductId(product.getId());
            receiptDetail.setQuantity(quantity);
            receiptDetail.setUnit(unit);

            Intent intent = new Intent();
            intent.putExtra("receiptDetail", receiptDetail);
            setResult(2, intent);
            finish();
        }
    }

    private boolean isInputValid() {
        int count = 0;

        String quantity = etQuantity.getText().toString().trim();
        if (quantity.isEmpty()) {
            count++;
            tvQuantityWarning.setText("Xin hãy nhập số lượng nhập");
        } else {
            try {
                int q = Integer.parseInt(quantity);

                if (q <= 0) {
                    count++;
                    tvQuantityWarning.setText("Số lượng nhập không hợp lệ");
                } else tvQuantityWarning.setText("");

            } catch (Exception ex) {
                count++;
                tvQuantityWarning.setText("Số lượng nhập không hợp lệ");
            }
        }

        if (etUnit.getText().toString().trim().isEmpty()) {
            count++;
            tvUnitWarning.setText("Xin hãy nhập đơn vị tính");
        } else tvUnitWarning.setText("");

        return count == 0;
    }

    private void handleBtnCancelClick(View view) {
        finish();
    }

    @Override
    public void sendDialogResult(CustomDialog.Result result, String request) {
        if (result == CustomDialog.Result.OK) {
            ReceiptDetail receiptDetail = new ReceiptDetail();
            receiptDetail.setProductId(product.getId());

            Intent intent = new Intent();
            intent.putExtra("receiptDetail", receiptDetail);
            setResult(3, intent);
            finish();
        }
    }
}
