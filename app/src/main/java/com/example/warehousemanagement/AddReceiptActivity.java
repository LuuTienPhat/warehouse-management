package com.example.warehousemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.warehousemanagement.dao.ReceiptDao;
import com.example.warehousemanagement.dao.ReceiptDetailDao;
import com.example.warehousemanagement.dialog.CustomDialog;
import com.example.warehousemanagement.model.Receipt;
import com.example.warehousemanagement.model.ReceiptDetail;

import java.util.ArrayList;

public class AddReceiptActivity extends AppCompatActivity implements CustomDialog.Listener {
    private ImageButton btnCancel, btnPrevious, btnForward;
    private FrameLayout frameLayout;
    private TextView tvTitle, tvProcess;

    private ReceiptInformationFragment receiptInformationFragment;
    private ReceiptDetailViewFragment receiptDetailViewFragment;
    private ReceiptViewFragment receiptViewFragment;
    private Fragment displayingFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private Receipt newReceipt = new Receipt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_add_activity);
        setControl();
        setEvent();

        tvTitle.setText("Tạo phiếu nhập");
        receiptInformationFragment = new ReceiptInformationFragment();
        receiptDetailViewFragment = new ReceiptDetailViewFragment();
        receiptViewFragment = new ReceiptViewFragment();
        replaceFragment(receiptInformationFragment);
    }

    private void replaceFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
        displayingFragment = fragment;
    }

    private void setEvent() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dialogContent = "Bạn có muốn thoát không";
                CustomDialog customDialog = new CustomDialog(CustomDialog.Type.CONFIRM, "Thông báo", dialogContent, "exit");
                customDialog.show(getSupportFragmentManager(), "");
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (displayingFragment == receiptDetailViewFragment) {
                    switchFragment(1);
                } else if (displayingFragment == receiptViewFragment) {
                    switchFragment(2);
                }
            }
        });
        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (displayingFragment == receiptInformationFragment) {
                    switchFragment(2);
                } else if (displayingFragment == receiptDetailViewFragment) {
                    switchFragment(3);
                } else if (displayingFragment == receiptViewFragment) {
                    ReceiptDao receiptDao = new ReceiptDao(DatabaseHelper.getInstance(AddReceiptActivity.this));
                    receiptDao.insertOne(newReceipt);

                    ReceiptDetailDao receiptDetailDao = new ReceiptDetailDao(DatabaseHelper.getInstance(AddReceiptActivity.this));
                    for (ReceiptDetail receiptDetail : newReceipt.getReceiptDetails()) {
                        receiptDetailDao.insertOne(receiptDetail);
                    }

                    CustomDialog customDialog = new CustomDialog(CustomDialog.Type.NOTIFICATION, "Thành công", "Tạo phiếu thành công", "exit");
                    customDialog.show(getSupportFragmentManager(), "");
                }
            }
        });
    }

    private void setControl() {
        btnCancel = findViewById(R.id.btnCancel);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnForward = findViewById(R.id.btnForward);
        frameLayout = findViewById(R.id.frameLayout);
        tvTitle = findViewById(R.id.tvTitle);
        tvProcess = findViewById(R.id.tvProcess);
    }

    private void switchFragment(int no) {
        switch (no) {
            case 1:
                tvTitle.setText("Tạo phiếu nhập");
                tvProcess.setText("1/3");
                replaceFragment(receiptInformationFragment);
                break;
            case 2:
                if (receiptInformationFragment.senDataToActivity()) {
                    tvTitle.setText("Thêm vật tư");
                    tvProcess.setText("2/3");
                    replaceFragment(receiptDetailViewFragment);
                } else {
                    Toast.makeText(this, "Hãy kiểm tra lại thông tin phiếu", Toast.LENGTH_LONG).show();
                }
                break;
            case 3:
                if (!newReceipt.getReceiptDetails().isEmpty()) {
                    tvTitle.setText("Xem trước");
                    tvProcess.setText("2/3");
                    replaceFragment(receiptViewFragment);
                } else {
                    Toast.makeText(this, "Phiếu nhập phải có ít nhất 1 vật tư", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        String dialogContent = "Bạn có muốn thoát không";
        CustomDialog customDialog = new CustomDialog(CustomDialog.Type.CONFIRM, "Thông báo", dialogContent, "exit");
        customDialog.show(getSupportFragmentManager(), "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == 2) {
                //Receipt receipt = (Receipt) getIntent().getSerializableExtra("receipt");
                ReceiptDetail receivedReceiptDetail = (ReceiptDetail) data.getSerializableExtra("receiptDetail");
                addReceiptDetail(receivedReceiptDetail);
            } else if (resultCode == 3) {
                ReceiptDetail receivedReceiptDetail = (ReceiptDetail) data.getSerializableExtra("receiptDetail");
                deleteReceiptDetail(receivedReceiptDetail);
            }
        }
    }

    private int getReceiptDetail(String productId) {
        if (newReceipt.getReceiptDetails() == null)
            newReceipt.setReceiptDetails(new ArrayList<ReceiptDetail>());

        if (newReceipt.getReceiptDetails().size() == 0) return -1;

        for (int i = 0; i < newReceipt.getReceiptDetails().size(); i++) {
            if (newReceipt.getReceiptDetails().get(i).getProductId().equals(productId)) {
                return i;
            }
        }
        return -1;
    }

    private void addReceiptDetail(ReceiptDetail receiptDetail) {
        int receiptDetailIndex = getReceiptDetail(receiptDetail.getProductId());

        if (receiptDetailIndex == -1) {
            newReceipt.getReceiptDetails().add(receiptDetail);
        } else {
            newReceipt.getReceiptDetails().set(receiptDetailIndex, receiptDetail);
        }
        receiptDetailViewFragment.updateListView();
    }

    private void deleteReceiptDetail(ReceiptDetail receiptDetail) {
        int receiptDetailIndex = getReceiptDetail(receiptDetail.getProductId());
        if (receiptDetailIndex != -1) {
            newReceipt.getReceiptDetails().remove(receiptDetailIndex);
        }
        receiptDetailViewFragment.updateListView();
    }

    @Override
    public void sendDialogResult(CustomDialog.Result result, String request) {
        if (result == CustomDialog.Result.OK) {
            if (request.equals("exit")) {
                finish();
            } else if (request.equals("deleteAll")) {
                newReceipt.getReceiptDetails().clear();
                receiptDetailViewFragment.updateListView();
            } else if (request.equals("add-success")) {
                finish();
            }
        }
    }

    public Receipt getNewReceipt() {
        return newReceipt;
    }

    public void setNewReceipt(Receipt newReceipt) {
        this.newReceipt = newReceipt;
    }
}