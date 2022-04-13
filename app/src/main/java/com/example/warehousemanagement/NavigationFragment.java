package com.example.warehousemanagement;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class NavigationFragment extends Fragment {
    private MainActivity mainActivity;
    private ImageButton btnCancel, btnPrevious, btnForward;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater layoutInflater = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflateView = inflater.inflate(R.layout.pending_delete_constraint_cancel_previous_next, container, false);
        btnCancel = inflateView.findViewById(R.id.btnCancel);
        btnPrevious = inflateView.findViewById(R.id.btnPrevious);
        btnForward = inflateView.findViewById(R.id.btnForward);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return inflateView;
    }

    // Called when a fragment is first attached to its context.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MainActivity) {
            this.mainActivity = (MainActivity) context;
        }
    }
}
