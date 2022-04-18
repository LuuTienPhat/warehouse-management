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

import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanagement.dao.ProductDao;
import com.example.warehousemanagement.dialog.CustomDialog;
import com.example.warehousemanagement.model.Product;

public class HandleProductActivity extends AppCompatActivity implements CustomDialog.Listener {
    EditText etName, etId, etOrigin;
    ImageButton btnEdit, btnDelete;
    Button btnSave, btnCancel;
    Product product;
    LinearLayout lyOption, lyUtils;
    TextView tvTitle, tvProductIdAlert, tvProductNameAlert, tvProductOriginAlert;

    //xét xem là thêm hay sửa
    public int editMode = -99;

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

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnEditClick(view);
            }
        });
        //default k báo lỗi
        tvProductIdAlert.setText("");
        tvProductNameAlert.setText("");
        tvProductOriginAlert.setText("");

        editMode = this.getIntent().getIntExtra("requestCode", -99);

        if (editMode == ProductActivity.LAUNCH_ADD_PRODUCT_ACTIVITY) {
            tvTitle.setText("Thêm vật tư");
//            btnEdit.setEnabled(false);
//            btnEdit.getBackground().setAlpha(76);
//            btnEdit.setVisibility(View.INVISIBLE);
//            btnDelete.setEnabled(false);
//            btnDelete.getBackground().setAlpha(76);
//            btnDelete.setVisibility(View.INVISIBLE);
        }

        if (editMode == ProductActivity.LAUNCH_EDIT_DELETE_PRODUCT_ACTIVITY) {
            tvTitle.setText("Chi tiết");
//            btnEdit.setEnabled(false);
//            btnEdit.getBackground().setAlpha(76);
//            btnEdit.setVisibility(View.INVISIBLE);
//            btnDelete.setEnabled(true);
            Product productFromParent = (Product) this.getIntent().getSerializableExtra("product");

            etId.setText(productFromParent.getId());
            etId.setEnabled(false);
            etId.getBackground().setAlpha(76);
            etId.setTextColor(R.color.text_gray);

            etName.setText(productFromParent.getName());
            etName.setEnabled(false);
            etName.getBackground().setAlpha(76);
            etName.setTextColor(R.color.text_gray);

            etOrigin.setText(productFromParent.getOrigin());
            etOrigin.setEnabled(false);
            etOrigin.getBackground().setAlpha(76);
            etOrigin.setTextColor(R.color.text_gray);

            btnSave.setEnabled(false);
            btnSave.getBackground().setAlpha(76);
        }
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnDeleteClick(view);
            }
        });
    }

    private void setControl() {
        btnEdit = findViewById(R.id.btnEdit);
        btnCancel = findViewById(R.id.btnCancel);
        btnDelete = findViewById(R.id.btnDelete);

        etOrigin = findViewById(R.id.editTextProductOrigin);
        etName = findViewById(R.id.editTextProductName);
        etId = findViewById(R.id.editTextProductId);

        lyOption = findViewById(R.id.lyOption);
        btnSave = findViewById(R.id.btnOK);
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
        System.out.println(new ProductDao(DatabaseHelper.getInstance(this)).checkIdExists(id) + " --------------------");

        if (id.isEmpty()) {
            tvProductIdAlert.setText("Vui lòng nhập mã vật tư");
            error = error + 1;
        } else if ( // nếu mode là thêm thì mới kiểm tra khóa
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

    private void handleBtnDeleteClick(View view) {
        openDialog("delete");
    }

    private void handleBtnSaveClick(View view) {
        tvProductIdAlert.setText("");
        tvProductNameAlert.setText("");
        tvProductOriginAlert.setText("");

        if (validate() == false) {
            return;
        }

        // nếu validate chuẩn r mới hiện dialog xác nhận
        if(editMode == ProductActivity.LAUNCH_ADD_PRODUCT_ACTIVITY){
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
        //nếu là chế độ edit delete mới mở confirm dialog // đây là confirm sửa
        if(editMode==ProductActivity.LAUNCH_EDIT_DELETE_PRODUCT_ACTIVITY){
            openDialog("edit");
        }
    }

    @SuppressLint("ResourceAsColor")
    private void handleBtnCancelClick(View view) {
//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("result", "canceled");
//        setResult(Activity.RESULT_CANCELED, returnIntent);
//        finish();
        etName.setEnabled(false);
        etName.getBackground().setAlpha(76);
        etName.setTextColor(R.color.text_gray);

        etOrigin.setEnabled(false);
        etOrigin.getBackground().setAlpha(76);
        etOrigin.setTextColor(R.color.text_gray);
    }

    @SuppressLint("ResourceAsColor")
    private void handleBtnEditClick(View view) {
        etName.setEnabled(true);
        etName.getBackground().setAlpha(255);
        etName.setTextColor(R.color.space_cadet);

        etOrigin.setEnabled(true);
        etOrigin.getBackground().setAlpha(255);
        etOrigin.setTextColor(R.color.space_cadet);

        btnSave.setEnabled(true);
        btnSave.getBackground().setAlpha(255);
    }

    public void openDialog(String confirmFor) {
        String content="";
        if(confirmFor.equalsIgnoreCase("edit")){
            content = "Bạn có chắc muốn SỬA thông tin cho vật tư này?";
        }
        if(confirmFor.equalsIgnoreCase("delete")){
            content = "Bạn có chắc muôn XÓA vật tư này?";
        }
        CustomDialog edit_deleteConfirmCustomDialog = new CustomDialog(CustomDialog.Type.CONFIRM, "Xác nhận", content, confirmFor);
        edit_deleteConfirmCustomDialog.show(getSupportFragmentManager(), "edit_deleteConfirmCustomDialog");
    }

    @Override
    public void sendDialogResult(CustomDialog.Result result, String request) {
        if (request.equalsIgnoreCase("delete") && result==CustomDialog.Result.OK) {
            String id = etId.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String origin = etOrigin.getText().toString().trim();

            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setOrigin(origin);

            Intent returnIntent = new Intent();
            returnIntent.putExtra("product", product);
            returnIntent.putExtra("result", "OKE");
            returnIntent.putExtra("deleteMode", true);

            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
        // nếu xác nhận sửa trả về true
        if (request.equalsIgnoreCase("edit") && result==CustomDialog.Result.OK) {
            String id = etId.getText().toString().trim();
            String name = etName.getText().toString().trim();
            String origin = etOrigin.getText().toString().trim();

            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setOrigin(origin);

            Intent returnIntent = new Intent();
            returnIntent.putExtra("product", product);
            returnIntent.putExtra("result", "OKE");

            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }
//nếu dùng edit_deleteConfirmDialog thì uncomment đống này và implements Edit_DeleteConfirmDialog.Edit_DeleteConfirmDialogListener
//    public void openDialog(String confirmFor) {
//        Edit_DeleteConfirmDialog edit_deleteConfirmDialog = new Edit_DeleteConfirmDialog(confirmFor);
//        edit_deleteConfirmDialog.show(getSupportFragmentManager(), "edit_deleteConfirmDialog");
//    }
//    @Override
//    public void setConfirmResult(boolean result, String mode) {
//        //nếu xác nhận xóa trả về true
//        if (mode.equalsIgnoreCase("delete") && result) {
//            String id = etId.getText().toString().trim();
//            String name = etName.getText().toString().trim();
//            String origin = etOrigin.getText().toString().trim();
//
//            Product product = new Product();
//            product.setId(id);
//            product.setName(name);
//            product.setOrigin(origin);
//
//            Intent returnIntent = new Intent();
//            returnIntent.putExtra("product", product);
//            returnIntent.putExtra("result", "OKE");
//            returnIntent.putExtra("deleteMode", true);
//
//            setResult(Activity.RESULT_OK, returnIntent);
//            finish();
//        }
//        // nếu xác nhận sửa trả về true
//        if (mode.equalsIgnoreCase("edit") && result) {
//            String id = etId.getText().toString().trim();
//            String name = etName.getText().toString().trim();
//            String origin = etOrigin.getText().toString().trim();
//
//            Product product = new Product();
//            product.setId(id);
//            product.setName(name);
//            product.setOrigin(origin);
//
////            ProductDao productDao = new ProductDao(DatabaseHelper.getInstance(this));
////            if (productDao.insertOne(product)) {
////                Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
////            }
//
//            Intent returnIntent = new Intent();
//            returnIntent.putExtra("product", product);
//            returnIntent.putExtra("result", "OKE");
//
//            setResult(Activity.RESULT_OK, returnIntent);
//            finish();
//        }
//    }

}

