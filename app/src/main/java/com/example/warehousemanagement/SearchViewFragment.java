package com.example.warehousemanagement;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.warehousemanagement.dao.ProductDao;
import com.example.warehousemanagement.dao.ReceiptDao;
import com.example.warehousemanagement.dao.WarehouseDao;

import java.util.ArrayList;
import java.util.List;

public class SearchViewFragment extends Fragment {
    View convertView;
    SearchView searchView;

    ReceiptActivity receiptActivity;
    ProductActivity productActivity;
    WarehouseActivity warehouseActivity;

    ReceiptDao receiptDao;
    WarehouseDao warehouseDao;
    ProductDao productDao;

    public interface ISendSearchResult {
        void sendSearchResult(List filteredList);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ReceiptActivity) {
            receiptActivity = (ReceiptActivity) context;
            receiptDao = new ReceiptDao(DatabaseHelper.getInstance(requireContext()));
        }
        if (context instanceof ProductActivity) {
            productActivity = (ProductActivity) context;
            productDao = new ProductDao(DatabaseHelper.getInstance(requireContext()));
        }
        if (context instanceof WarehouseActivity) {
            warehouseActivity = (WarehouseActivity) context;
            warehouseDao = new WarehouseDao(DatabaseHelper.getInstance(requireContext()));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        convertView = inflater.inflate(R.layout.search_view, container, false);
        searchView = convertView.findViewById(R.id.searchView);

        initSearchView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return false;
            }
        });
        return convertView;
    }

    private void search(String searchString) {
        List filteredList = new ArrayList<>();

        if (receiptActivity != null) {
            if (!searchString.trim().isEmpty()) {
                filteredList = receiptDao.search(searchString);
            } else {
                filteredList = receiptDao.getAll();
            }
            receiptActivity.sendSearchResult(filteredList);

        } else if (productActivity != null) {
            if (!searchString.trim().isEmpty()) {
                filteredList = productDao.search(searchString);
            } else {
                filteredList = productDao.getAll();
            }
            productActivity.sendSearchResult(filteredList);

        } else if (warehouseActivity != null) {
            if (!searchString.trim().isEmpty()) {
                filteredList = warehouseDao.search(searchString);
            } else {
                filteredList = warehouseDao.getAll();
            }
            warehouseActivity.sendSearchResult(filteredList);
        }
    }

    private void initSearchView() {
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = convertView.findViewById(searchPlateId);
        searchPlate.setBackgroundResource(0);

        ((EditText) ((android.widget.SearchView) convertView
                .findViewById(R.id.searchView))
                .findViewById(((android.widget.SearchView) convertView
                        .findViewById(R.id.searchView)).getContext().getResources().getIdentifier("android:id/search_src_text", null, null)))
                .setTextColor(requireActivity().getColor(R.color.cultured));


        int hintTextId = searchView.getContext()
                .getResources()
                .getIdentifier("android:id/search_src_text", null, null);
        TextView hintText = (TextView) convertView.findViewById(hintTextId);
        hintText.setHintTextColor(requireActivity().getColor(R.color.steel_blue));

        int closeBtnId = searchView.getContext()
                .getResources()
                .getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeIcon = (ImageView) searchView.findViewById(closeBtnId);
        closeIcon.setImageResource(R.drawable.ic_close_24);
    }
}
