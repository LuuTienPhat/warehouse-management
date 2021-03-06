package com.example.warehousemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanagement.adapter.ReceiptAdapter;
import com.example.warehousemanagement.adapter.WarehouseAdapter;
import com.example.warehousemanagement.dao.ReceiptDao;
import com.example.warehousemanagement.dao.WarehouseDao;
import com.example.warehousemanagement.dialog.SortOptionDialog;
import com.example.warehousemanagement.model.Receipt;
import com.example.warehousemanagement.model.Warehouse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReceiptActivity extends AppCompatActivity implements IViewActivity, SearchViewFragment.ISendSearchResult, SortOptionDialog.SortOptionDialogListener {
    ImageButton btnMinimize, btnAdd, btnSort, btnFilter, btnRefresh, btnStatistical;
    ListView listView;
    SearchView searchView;
    TextView tvTitle;
    Spinner spinner;

    ReceiptDao receiptDao = null;
    ReceiptAdapter receiptAdapter = null;
    List<Receipt> receipts = new ArrayList<>();

    WarehouseDao warehouseDao = null;
    WarehouseAdapter warehouseAdapter = null;
    List<Warehouse> warehouses = new ArrayList<>();

    public static String sortOption = "";
    public String sortOption2 = "";

    private boolean maximized = true;
    private int listViewItemLayout = R.layout.receipt_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        setControl();
        setEvent();

        tvTitle.setText("Phiếu nhập");

        receiptDao = new ReceiptDao(DatabaseHelper.getInstance(this));
        receipts = receiptDao.getAll();

        updateListView(receipts);
    }

    private void setEvent() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnAddClick(view);
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnRefreshClick(view);
            }
        });
        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnSortClick(view);
            }
        });
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnFilterClick(view);
            }
        });
        btnStatistical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReceiptActivity.this, ChartActivityReceipt.class));
            }
        });
        btnMinimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnMinimizeClick(view);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Receipt receipt = (Receipt) adapterView.getItemAtPosition(position);

                Intent intent = new Intent(ReceiptActivity.this, ReceiptViewActivity.class);
                intent.putExtra("receipt", receipt);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        btnStatistical = findViewById(R.id.btnStatistical);
        btnAdd = findViewById(R.id.btnAdd);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnFilter = findViewById(R.id.btnFilter);
        btnSort = findViewById(R.id.btnSort);
        btnMinimize = findViewById(R.id.btnMinimize);
        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.searchView);
        tvTitle = findViewById(R.id.tvTitle);
    }


    public void updateListView(List list) {
        receiptAdapter = new ReceiptAdapter(ReceiptActivity.this, listViewItemLayout, list);
        listView.setAdapter(receiptAdapter);
        receiptAdapter.notifyDataSetChanged();
    }

    @Override
    public void handleBtnAddClick(View view) {
        Intent intent = new Intent(this, AddReceiptActivity.class);
        startActivity(intent);
    }

    @Override
    public void handleBtnRefreshClick(View view) {
        refresh();
    }

    private void refresh() {
        receipts = receiptDao.getAll();
        updateListView(receipts);
    }

    public void sortData() {
        List<Receipt> receiptsToSort = new ArrayList<>();
        receiptsToSort = receiptAdapter.getData();
        System.out.println("begin sort;" + sortOption2);
        if (!sortOption2.isEmpty()) {
            System.out.println("begin sort;" + sortOption2);
            Collections.sort(receiptsToSort, new Comparator<Receipt>() {
                @Override
                public int compare(Receipt rc1, Receipt rc2) {
                    // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                    int lessThan = -99;
                    int greaterThan = -99;
                    String thuTuSapXep = sortOption2.split(";")[0];
                    String sapXepTheo = sortOption2.split(";")[1];
                    System.out.println(thuTuSapXep + ";" + sapXepTheo);

                    if (thuTuSapXep.equalsIgnoreCase("tang_dan")) {
                        lessThan = -1;
                        greaterThan = 1;
                    } else if (thuTuSapXep.equalsIgnoreCase("giam_dan")) {
                        lessThan = 1;
                        greaterThan = -1;
                    }

                    if (sapXepTheo.equalsIgnoreCase("theo_ma")) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return rc1.getId() < rc2.getId() ? lessThan : rc1.getId() > rc2.getId() ? greaterThan : 0;
                    } else if (sapXepTheo.equalsIgnoreCase("theo_ngay")) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return rc1.getDate().isBefore(rc2.getDate()) ? lessThan : rc1.getDate().isAfter(rc2.getDate()) ? greaterThan : 0;
                    } else if (sapXepTheo.equalsIgnoreCase("theo_so_luong")) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return rc1.countReceiptDetailsQuantity() < rc2.countReceiptDetailsQuantity() ? lessThan : rc1.countReceiptDetailsQuantity() > rc2.countReceiptDetailsQuantity() ? greaterThan : 0;
                    }
                    return 0;
                }
            });
        }
        receipts = receiptsToSort;
        updateListView(receiptsToSort);
    }

    @Override
    public void handleBtnSortClick(View view) {
        sortOption2 = "";
        openDialog();
    }

    public void openDialog() {
        SortOptionDialog sortOptionDialog = new SortOptionDialog("receipt");
        sortOptionDialog.show(getSupportFragmentManager(), "sortOptionDialog");
    }

    @Override
    public void handleBtnFilterClick(View view) {

    }

    @Override
    public void handleBtnMinimizeClick(View view) {
        maximized = !maximized;
        if (maximized) {
            listViewItemLayout = R.layout.receipt_item;
            btnMinimize.setBackgroundResource(R.drawable.ic_minimize_32);
        } else {
            listViewItemLayout = R.layout.receipt_item_small;
            btnMinimize.setBackgroundResource(R.drawable.ic_maximize_32);
        }
        updateListView(receipts);
    }

    @Override
    public void setSortOption(String sortOption) {
        sortOption2 = sortOption;
        System.out.println(sortOption2 + "///////////////////////////////////////////////////////");
        sortData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void sendSearchResult(List filteredList) {
        receipts = filteredList;
        updateListView(filteredList);
    }
}