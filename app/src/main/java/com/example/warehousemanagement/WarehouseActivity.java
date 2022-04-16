package com.example.warehousemanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanagement.adapter.WarehouseAdapter;
import com.example.warehousemanagement.dao.WarehouseDao;
import com.example.warehousemanagement.dialog.SortOptionDialog;
import com.example.warehousemanagement.model.Warehouse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WarehouseActivity extends AppCompatActivity implements IViewActivity, SortOptionDialog.SortOptionDialogListener {
    ImageButton btnAdd, btnMinimize, btnSort, btnFilter, btnRefresh;
    SearchView searchView;
    TextView tvTitle;
    ListView listView;
    WarehouseDao warehouseDao = null;
    WarehouseAdapter warehouseAdapter;
    List<Warehouse> warehouses = new ArrayList<>();
    int dividerHeight;
    public String sortOption2 = "";
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private boolean maximized = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        setControl();
        setEvent();

        tvTitle.setText("Kho");
        warehouseDao = new WarehouseDao(DatabaseHelper.getInstance(this));
        warehouses = warehouseDao.getAll();

        warehouseAdapter = new WarehouseAdapter(this, R.layout.warehouse_item, warehouses);
        listView.setAdapter(warehouseAdapter);
        dividerHeight = listView.getDividerHeight();

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            btnRefresh.performClick();
                        }
                    }
                });
    }

    private void setControl() {
        btnAdd = findViewById(R.id.btnAdd);
        btnRefresh = findViewById(R.id.btnRefresh);
        btnFilter = findViewById(R.id.btnFilter);
        btnSort = findViewById(R.id.btnSort);
        btnMinimize = findViewById(R.id.btnMinimize);
        listView = findViewById(R.id.listView);
        tvTitle = findViewById(R.id.tvTitle);
        searchView = findViewById(R.id.searchView);
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
        btnMinimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnMinimizeClick(view);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Warehouse warehouse = (Warehouse) adapterView.getItemAtPosition(position);

                Intent intent = new Intent(WarehouseActivity.this, WarehouseDetailActivity.class);
                intent.putExtra("warehouseId", warehouse.getId());
                intent.putExtra("warehouseName", warehouse.getName());
                intent.putExtra("warehouseAddress", warehouse.getAddress());
                activityResultLauncher.launch(intent);
            }
        });
    }

    @Override
    public void handleBtnAddClick(View view) {
        Intent intent = new Intent(this, AddWarehouseActivity.class);
        activityResultLauncher.launch(intent);
    }

    @Override
    public void handleBtnRefreshClick(View view) {
        warehouses = warehouseDao.getAll();
        warehouseAdapter = new WarehouseAdapter(this, R.layout.warehouse_item, warehouses);
        listView.setAdapter(warehouseAdapter);
        listView.setDividerHeight(dividerHeight);
    }

    @Override
    public void handleBtnSortClick(View view) {
        sortOption2 = "";
        openDialog();
    }

    @Override
    public void handleBtnFilterClick(View view) {

    }

    @Override
    public void handleBtnMinimizeClick(View view) {
        if (maximized) {
            warehouseAdapter = new WarehouseAdapter(this, R.layout.warehouse_item, warehouses);
            btnMinimize.setBackgroundResource(R.drawable.ic_eye_32);
        } else {
            warehouseAdapter = new WarehouseAdapter(this, R.layout.warehouse_item_small, warehouses);
            btnMinimize.setBackgroundResource(R.drawable.ic_baseline_visibility_off_24);
        }
        listView.setAdapter(warehouseAdapter);
        maximized = !maximized;
    }

    @Override
    public void setSortOption(String sortOption) {
        sortOption2 = sortOption;
        sortData();
    }
    public void sortData(){
        System.out.println("begin sort;"+sortOption2);
        if (!sortOption2.isEmpty()) {
            System.out.println("begin sort;"+sortOption2);
            Collections.sort(warehouses, new Comparator<Warehouse>() {
                @Override
                public int compare(Warehouse pd1, Warehouse pd2) {
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
                        // so sanh string dungf compare to, < tra ve <0, lon hon tra ve > 0
                        return (pd1.getId().compareTo(pd2.getId()) < 0) ? lessThan : (pd1.getId().compareTo(pd2.getId()) > 0) ? greaterThan : 0;
                    } else if (sapXepTheo.equalsIgnoreCase("theo_ten")) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return (pd1.getName().compareTo(pd2.getName()) < 0) ? lessThan : (pd1.getName().compareTo(pd2.getName()) > 0) ? greaterThan : 0;
                    } else if (sapXepTheo.equalsIgnoreCase("theo_dia_chi")) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return (pd1.getAddress().compareTo(pd2.getAddress()) < 0) ? lessThan : (pd1.getAddress().compareTo(pd2.getAddress()) > 0) ? greaterThan : 0;
                    }
                    return 0;
                }
            });
        }
        warehouseAdapter.notifyDataSetChanged();
    }

    public void openDialog() {
        SortOptionDialog sortOptionDialog = new SortOptionDialog("warehouse");
        sortOptionDialog.show(getSupportFragmentManager(), "sortOptionDialog");
    }
}