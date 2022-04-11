package com.example.warehousemanagement.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.warehousemanagement.R;
import com.example.warehousemanagement.ReceiptActivity;

public class SortOptionDialog extends AppCompatDialogFragment {
    private RadioButton rBTangDan;
    private RadioButton rBGiamDan;

    private RadioButton rBTheoMa;
    private RadioButton rBTheoNgay;
    private RadioButton rBTheoSoLuongVatTu;

    private SortOptionDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sort_by_option, null);
        builder.setView(view).setTitle("Lựa chọn").setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setPositiveButton("Sắp xếp", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
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
                        listener.setSortOption(thuTuSapXep + ";" + sapXepTheo+";123444");
                        System.out.println(ReceiptActivity.sortOption + "-----------------------------------------------");
                    }
                });
        rBTangDan = (RadioButton) view.findViewById(R.id.radioBtnTangDan);
        rBGiamDan = (RadioButton) view.findViewById(R.id.radioBtnGiamDan);

        rBTheoMa = (RadioButton) view.findViewById(R.id.radioBtnTheoMa);
        rBTheoNgay = (RadioButton) view.findViewById(R.id.radioBtnTheoNgay);
        rBTheoSoLuongVatTu = (RadioButton) view.findViewById(R.id.radioBtnTheoSoLuong);
        return builder.create();
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
