package com.example.warehousemanagement.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.warehousemanagement.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CustomDialog extends BottomSheetDialogFragment {
    public enum Type {
        NOTIFICATION, CONFIRM
    }

    public enum Result {
        OK, CANCEL
    }

    public interface Listener {
        void sendDialogResult(Result result);
    }

    private Listener listener;
    private Type type;
    private String title, content;

    public CustomDialog(Type type, String title, String content) {
        this.title = title;
        this.content = content;
        this.type = type;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (Listener) getActivity();
        } catch (ClassCastException e) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View convertView = null;
        if (type == Type.NOTIFICATION) {
            convertView = inflater.inflate(R.layout.dialog_notification, null);

            TextView tvContent = convertView.findViewById(R.id.tvContent);
            Button btnOK = convertView.findViewById(R.id.btnOK);

            tvContent.setText(this.content);
            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.sendDialogResult(Result.OK);
                    dismiss();
                }
            });
        } else if (type == Type.CONFIRM) {
            convertView = inflater.inflate(R.layout.dialog_confirm, null);

            TextView tvTitle = convertView.findViewById(R.id.tvTitle);
            TextView tvContent = convertView.findViewById(R.id.tvContent);
            Button btnOK = convertView.findViewById(R.id.btnOK);
            Button btnCancel = convertView.findViewById(R.id.btnCancel);

            tvTitle.setText(this.title);
            tvContent.setText(this.content);
            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.sendDialogResult(Result.OK);
                    dismiss();
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.sendDialogResult(Result.CANCEL);
                    dismiss();
                }
            });
        }

        return convertView;
    }
}
