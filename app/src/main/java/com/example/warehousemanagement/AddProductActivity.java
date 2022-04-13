package com.example.warehousemanagement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanagement.dao.ProductDao;
import com.example.warehousemanagement.model.Product;

public class AddProductActivity extends AppCompatActivity {
    EditText etName, etId, etOrigin;
    ImageButton btnEdit, btnDelete;
    Button btnSave, btnCancel;
    Product product;
    LinearLayout lyOption, lyUtils;
    TextView tvTitle, tvProductIdAlert, tvProductNameAlert, tvProductOriginAlert;

    //xét xem là thêm hay sửa
    public int editMode=-99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_add_activity_linear);
        setControl();
        setEvent();
    }
    //SuppressLint để set textcolor(@color) trong code
    @SuppressLint("ResourceAsColor")
    private void setEvent() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnSaveClick(view);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnCancelClick(view);
            }
        });
        //default k báo lỗi
        tvProductIdAlert.setText("");
        tvProductNameAlert.setText("");
        tvProductOriginAlert.setText("");

        editMode = this.getIntent().getIntExtra("requestCode", -99);

        if(editMode == ProductActivity.LAUNCH_ADD_PRODUCT_ACTIVITY){
            tvTitle.setText("Thêm vật tư");
            btnEdit.setEnabled(false);
            btnEdit.getBackground().setAlpha(76);
            btnDelete.setEnabled(false);
            btnDelete.getBackground().setAlpha(76);
        }

        if(editMode == ProductActivity.LAUNCH_EDIT_PRODUCT_ACTIVITY){
            tvTitle.setText("Sửa vật tư");
            btnEdit.setEnabled(false);
            btnEdit.getBackground().setAlpha(76);
            btnDelete.setEnabled(true);
            Product productFromParent = (Product) this.getIntent().getSerializableExtra("product");

            etId.setText(productFromParent.getId());
            etId.setEnabled(false);
            etId.getBackground().setAlpha(76);
//            etId.setTextColor(R.color.text_gray);

            etName.setText(productFromParent.getName());
            etOrigin.setText(productFromParent.getOrigin());
        }
    }

    private void setControl() {
        btnEdit = findViewById(R.id.btnEdit);
        btnCancel = findViewById(R.id.btnCancel);
        btnDelete = findViewById(R.id.btnDelete);

        etOrigin = findViewById(R.id.editTextProductOrigin);
        etName = findViewById(R.id.editTextProductName);
        etId = findViewById(R.id.editTextProductId);

        lyOption = findViewById(R.id.lyOption);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        tvTitle = findViewById(R.id.tvTitle);
        tvProductIdAlert = findViewById(R.id.tvProductIdAlert);
        tvProductNameAlert = findViewById(R.id.tvProductNameAlert);
        tvProductOriginAlert = findViewById(R.id.tvProductOriginAlert);
    }

    private boolean validate() {
        int error = 0;

        String id = etId.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String origin = etOrigin.getText().toString().trim();
        System.out.println(new ProductDao(DatabaseHelper.getInstance(this)).checkIdExists(id) +" --------------------");

        if (id.isEmpty()) {
            tvProductIdAlert.setText("Vui lòng nhập mã vật tư");
            error = error + 1;
        }else if ( // nếu mode là thêm thì mới kiểm tra khóa
                editMode == ProductActivity.LAUNCH_ADD_PRODUCT_ACTIVITY
                        && new ProductDao(DatabaseHelper.getInstance(this)).checkIdExists(id)) { // kiểm tra xem mã tồn tại chưa
            tvProductIdAlert.setText("Mã vật tư đã tồn tại");
            error = error + 1;
        }
        if (name.isEmpty()) {
            tvProductNameAlert.setText("Vui lòng nhập tên vật tư");
            error = error + 1;
        }
        if (origin.isEmpty()) {
            tvProductOriginAlert.setText("Vui lòng nhập xuất xứ của vật tư");
            error = error + 1;
        }
        return error == 0;
    }

    private void handleBtnSaveClick(View view) {
        tvProductIdAlert.setText("");
        tvProductNameAlert.setText("");
        tvProductOriginAlert.setText("");

        if(validate()==false){
            return;
        }
            String id = etId.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String origin = etOrigin.getText().toString().trim();

            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setOrigin(origin);

//            ProductDao productDao = new ProductDao(DatabaseHelper.getInstance(this));
//            if (productDao.insertOne(product)) {
//                Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
//            }

        Intent returnIntent = new Intent();
        returnIntent.putExtra("product", product);
        returnIntent.putExtra("result", "OKE");

        setResult(Activity.RESULT_OK, returnIntent);
        finish();

    }

    private void handleBtnCancelClick(View view) {
        finish();
    }
}

