package com.example.warehousemanagement.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.warehousemanagement.R;
import com.example.warehousemanagement.ReceiptActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class Edit_DeleteConfirmDialog extends BottomSheetDialogFragment {
    private Edit_DeleteConfirmDialogListener listener;
    private String confirmFor = "";

    public Edit_DeleteConfirmDialog() {
    }

    public Edit_DeleteConfirmDialog(String confirmFor) {
        this.confirmFor = confirmFor;
    }

    public void closeDialog() {
        this.dismiss();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1 = null;
        view1 = inflater.inflate(R.layout.dialog_confirm, container, false);
        Button btnXacNhan = (Button) view1.findViewById(R.id.btnOK);
        Button btnHuy = (Button) view1.findViewById(R.id.btnCancel);
        TextView tvContent = (TextView) view1.findViewById(R.id.tvContent);

        if (confirmFor.equalsIgnoreCase("edit")) {
            tvContent.setText("Xác nhận sửa thông tin cho vật tư?");
            btnXacNhan.setBackgroundResource(R.drawable.bg_warning_radius_15);
        }else if (confirmFor.equalsIgnoreCase("delete")) {
            tvContent.setText("Xác nhận xóa vật tư này?");
            btnXacNhan.setBackgroundResource(R.drawable.bg_alert_radius_15);
        }
            btnXacNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //kiểm tra xem là xác nhân cho xóa hay sửa
                    if (confirmFor.equalsIgnoreCase("edit")) {
                        listener.setConfirmResult(true, "edit");
                    }else if (confirmFor.equalsIgnoreCase("delete")) {
                        listener.setConfirmResult(true, "delete");
                    }
                    closeDialog();
                }
            });
            btnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    closeDialog();
                }
            });

        return view1;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (Edit_DeleteConfirmDialogListener) context;

        } catch (ClassCastException ex) {
            throw new ClassCastException(context.toString() + "must implement Edit_DeleteConfirmDialogListener");
        }
    }

    public interface Edit_DeleteConfirmDialogListener {
        void setConfirmResult(boolean result, String mode);
    }
}
