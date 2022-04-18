package com.example.warehousemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanagement.adapter.ProductAdapter;
import com.example.warehousemanagement.adapter.ReceiptAdapter;
import com.example.warehousemanagement.dao.ProductDao;
import com.example.warehousemanagement.dialog.SortOptionDialog;
import com.example.warehousemanagement.model.Product;
import com.example.warehousemanagement.model.Receipt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements BaseActivity, SortOptionDialog.SortOptionDialogListener {
    ImageButton btnMinimize, btnAdd, btnSort, btnFilter, btnRefresh, btnStatistical;
    ListView listView;
    SearchView searchView;
    TextView tvTitle;
    ProductDao productDao = null;
    ProductAdapter productAdapter = null;
    List<Product> products = new ArrayList<>();
    int dividerHeight;
    public String sortOption2 = "";


    public static int LAUNCH_ADD_PRODUCT_ACTIVITY = 1;
    public static int LAUNCH_EDIT_PRODUCT_ACTIVITY = 2;
    public static int LAUNCH_DEL_PRODUCT_ACTIVITY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        setControl();
        setEvent();



        tvTitle.setText("Vật tư");
        productDao = new ProductDao(DatabaseHelper.getInstance(this));
        products = productDao.getAll();

        productAdapter = new ProductAdapter(this, R.layout.constraint_product_item, products);
        listView.setAdapter(productAdapter);
        dividerHeight = listView.getDividerHeight();

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
        btnStatistical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductActivity.this, ChartActivity.class));
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
                Product product = (Product) adapterView.getItemAtPosition(position);
                //khi ấn vào mở form chỉnh sửa = startActivityForResult
                handleListViewItemClick(view, product);
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

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                    }
                }
            });

    public void openSomeActivityForResult() {
        Intent intent = new Intent(this, AddProductActivity.class);
        someActivityResultLauncher.launch(intent);
    }

    @Override
    public void handleBtnAddClick(View view) {
        Intent intent = new Intent(this, AddProductActivity.class);
        intent.putExtra("requestCode", LAUNCH_ADD_PRODUCT_ACTIVITY);
        startActivityForResult(intent, LAUNCH_ADD_PRODUCT_ACTIVITY);
    }

    //hàm trong set event khi click vào item treên list view
    public void handleListViewItemClick(View view, Product product) {
        Intent intent = new Intent(this, AddProductActivity.class);
        intent.putExtra("requestCode", LAUNCH_EDIT_PRODUCT_ACTIVITY);
        intent.putExtra("product", product);
        startActivityForResult(intent, LAUNCH_EDIT_PRODUCT_ACTIVITY);
    }

    @Override
    public void handleBtnRefreshClick(View view) {
        products = productDao.getAll();

        productAdapter = new ProductAdapter(this, R.layout.constraint_product_item, products);
        listView.setAdapter(productAdapter);
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
        productAdapter = new ProductAdapter(this, R.layout.constraint_product_item_small, products);
        listView.setAdapter(productAdapter);
        listView.setDividerHeight(10);
        productAdapter.notifyDataSetChanged();
    }

    public void sortData() {
        System.out.println("begin sort;" + sortOption2);
        if (!sortOption2.isEmpty()) {
            System.out.println("begin sort;" + sortOption2);
            Collections.sort(products, new Comparator<Product>() {
                @Override
                public int compare(Product pd1, Product pd2) {
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
                    } else if (sapXepTheo.equalsIgnoreCase("theo_xuat_xu")) {
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                        return (pd1.getOrigin().compareTo(pd2.getOrigin()) < 0) ? lessThan : (pd1.getOrigin().compareTo(pd2.getOrigin()) > 0) ? greaterThan : 0;
                    }
                    return 0;
                }
            });
        }
        productAdapter = new ProductAdapter(this, R.layout.constraint_product_item, products);
        listView.setAdapter(productAdapter);
        System.out.println("notify change");
        productAdapter.notifyDataSetChanged();
    }

    public void openDialog() {
        SortOptionDialog sortOptionDialog = new SortOptionDialog("product");
        sortOptionDialog.show(getSupportFragmentManager(), "sortOptionDialog");
    }

    @Override
    public void setSortOption(String sortOption) {
        sortOption2 = sortOption;
        sortData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_ADD_PRODUCT_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                System.out.println(result);

                Product product = (Product) data.getSerializableExtra("product");
                this.addProduct(product);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
        if (requestCode == LAUNCH_EDIT_PRODUCT_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                System.out.println(result);

                Product product = (Product) data.getSerializableExtra("product");
                this.editProduct(product);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
                Toast.makeText(this, "Đã hủy", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == LAUNCH_DEL_PRODUCT_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    } //onActivityResult

    public void addProduct(Product product) {
        if (productDao.insertOne(product)) {
            Toast.makeText(this, "Thêm vật tư thành công", Toast.LENGTH_LONG).show();
        }
        products = productDao.getAll();
        productAdapter = new ProductAdapter(this, R.layout.constraint_product_item, products);
        listView.setAdapter(productAdapter);
        listView.setDividerHeight(dividerHeight);
    }

    public void editProduct(Product product) {
        if (productDao.updateOne(product)) {
            Toast.makeText(this, "Sửa vật tư thành công", Toast.LENGTH_LONG).show();
        }
        products = productDao.getAll();
        productAdapter = new ProductAdapter(this, R.layout.constraint_product_item, products);
        listView.setAdapter(productAdapter);
        listView.setDividerHeight(dividerHeight);
    }
}

