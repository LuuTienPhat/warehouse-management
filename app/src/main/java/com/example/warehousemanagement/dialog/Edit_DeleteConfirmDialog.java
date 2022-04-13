package com.example.warehousemanagement.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.warehousemanagement.R;
import com.example.warehousemanagement.ReceiptActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SortOptionDialog extends BottomSheetDialogFragment {
    private SortOptionDialogListener listener;
    private String sortOptionFor = "";

    public SortOptionDialog() {
    }

    public SortOptionDialog(String sortOptionFor) {
        this.sortOptionFor = sortOptionFor;
    }

    public void closeDialog() {
        this.dismiss();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = null;
        if (sortOptionFor.equalsIgnoreCase("receipt")) {
            view1 = inflater.inflate(R.layout.dialog_sort_by_option_for_receipt, container, false);

            Button btnXacNhan = (Button) view1.findViewById(R.id.btnXacNhan);
            Button btnHuy = (Button) view1.findViewById(R.id.btnHuy);
            RadioButton rBTangDan = (RadioButton) view1.findViewById(R.id.radioBtnTangDan);
            RadioButton rBGiamDan = (RadioButton) view1.findViewById(R.id.radioBtnGiamDan);

            RadioButton rBTheoMa = (RadioButton) view1.findViewById(R.id.radioBtnTheoMa);
            RadioButton rBTheoNgay = (RadioButton) view1.findViewById(R.id.radioBtnTheoNgay);
            RadioButton rBTheoSoLuongVatTu = (RadioButton) view1.findViewById(R.id.radioBtnTheoSoLuong);
            btnXacNhan.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    String thuTuSapXep = "";
                    String sapXepTheo = "";

                    if (rBTheoMa.isChecked()) {
                        sapXepTheo = "theo_ma";
                    }
                    if (rBTheoNgay.isChecked()) {
                        sapXepTheo = "theo_ngay";
                    }
                    if (rBTheoSoLuongVatTu.isChecked()) {
                        sapXepTheo = "theo_so_luong";
                    }

                    if (rBTangDan.isChecked()) {
                        thuTuSapXep = "tang_dan";
                    }
                    if (rBGiamDan.isChecked()) {
                        thuTuSapXep = "giam_dan";
                    }
                    //test = static variable, xóa sau
                    ReceiptActivity.sortOption = thuTuSapXep + ";" + sapXepTheo;
                    listener.setSortOption(thuTuSapXep + ";" + sapXepTheo + ";123444");
                    System.out.println(ReceiptActivity.sortOption + "-----------------------------------------------");
                    closeDialog();
                }
            });
            btnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    closeDialog();
                }
            });
        }

        if (sortOptionFor.equalsIgnoreCase("product")) {
            view1 = inflater.inflate(R.layout.dialog_sort_by_option_for_product, container, false);

            Button btnXacNhan = (Button) view1.findViewById(R.id.btnXacNhan);
            Button btnHuy = (Button) view1.findViewById(R.id.btnHuy);
            RadioButton rBTangDan = (RadioButton) view1.findViewById(R.id.radioBtnTangDan);
            RadioButton rBGiamDan = (RadioButton) view1.findViewById(R.id.radioBtnGiamDan);

            RadioButton rBTheoMa = (RadioButton) view1.findViewById(R.id.radioBtnTheoMa);
            RadioButton rBTheoTen = (RadioButton) view1.findViewById(R.id.radioBtnTheoTen);
            RadioButton rBTheoXuatXu = (RadioButton) view1.findViewById(R.id.radioBtnTheoXuatXu);
            btnXacNhan.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    String thuTuSapXep = "";
                    String sapXepTheo = "";

                    if (rBTheoMa.isChecked()) {
                        sapXepTheo = "theo_ma";
                    }
                    if (rBTheoTen.isChecked()) {
                        sapXepTheo = "theo_ten";
                    }
                    if (rBTheoXuatXu.isChecked()) {
                        sapXepTheo = "theo_xuat_xu";
                    }

                    if (rBTangDan.isChecked()) {
                        thuTuSapXep = "tang_dan";
                    }
                    if (rBGiamDan.isChecked()) {
                        thuTuSapXep = "giam_dan";
                    }
                    //test = static variable, xóa sau
                    ReceiptActivity.sortOption = thuTuSapXep + ";" + sapXepTheo;
                    listener.setSortOption(thuTuSapXep + ";" + sapXepTheo + ";----");
                    System.out.println(ReceiptActivity.sortOption + "-----------------------------------------------");
                    closeDialog();
                }
            });
            btnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    closeDialog();
                }
            });
        }

        if (sortOptionFor.equalsIgnoreCase("warehouse")) {
            view1 = inflater.inflate(R.layout.dialog_sort_by_option_for_warehouse, container, false);

            Button btnXacNhan = (Button) view1.findViewById(R.id.btnXacNhan);
            Button btnHuy = (Button) view1.findViewById(R.id.btnHuy);
            RadioButton rBTangDan = (RadioButton) view1.findViewById(R.id.radioBtnTangDan);
            RadioButton rBGiamDan = (RadioButton) view1.findViewById(R.id.radioBtnGiamDan);

            RadioButton rBTheoMa = (RadioButton) view1.findViewById(R.id.radioBtnTheoMa);
            RadioButton rBTheoTen = (RadioButton) view1.findViewById(R.id.radioBtnTheoTen);
            RadioButton rBTheoDiaChi = (RadioButton) view1.findViewById(R.id.radioBtnTheoDiaChi);
            btnXacNhan.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    String thuTuSapXep = "";
                    String sapXepTheo = "";

                    if (rBTheoMa.isChecked()) {
                        sapXepTheo = "theo_ma";
                    }
                    if (rBTheoTen.isChecked()) {
                        sapXepTheo = "theo_ten";
                    }
                    if (rBTheoDiaChi.isChecked()) {
                        sapXepTheo = "theo_dia_chi";
                    }

                    if (rBTangDan.isChecked()) {
                        thuTuSapXep = "tang_dan";
                    }
                    if (rBGiamDan.isChecked()) {
                        thuTuSapXep = "giam_dan";
                    }
                    //test = static variable, xóa sau
                    ReceiptActivity.sortOption = thuTuSapXep + ";" + sapXepTheo;
                    listener.setSortOption(thuTuSapXep + ";" + sapXepTheo + ";----");
                    System.out.println(ReceiptActivity.sortOption + "-----------------------------------------------");
                    closeDialog();
                }
            });
            btnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    closeDialog();
                }
            });
        }

        return view1;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (SortOptionDialogListener) context;

        } catch (ClassCastException ex) {
            throw new ClassCastException(context.toString() + "must implement SortOptionDialogListener");
        }
    }

    public interface SortOptionDialogListener {
        void setSortOption(String sortOption);
    }
}
