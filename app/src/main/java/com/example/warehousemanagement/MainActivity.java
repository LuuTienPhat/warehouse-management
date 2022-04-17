package com.example.warehousemanagement;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.warehousemanagement.dao.ProductDao;
import com.example.warehousemanagement.dao.ReceiptDao;
import com.example.warehousemanagement.dao.ReceiptDetailDao;
import com.example.warehousemanagement.dao.WarehouseDao;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    LinearLayout btnWarehouse, btnProduct, btnReceipt;
    DatabaseHelper databaseHelper = null;
    public static final int MY_REQUEST_CODE =10;
    private static final int FRAGMENT_HOME =0;
    private static final int FRAGMENT_FRAVORITE =1;
    private static final int FRAGMENT_HISTORY =2;
    private static final int FRAGMENT_MY_PROFILE =3;
    private DrawerLayout mDrawerLayout;
    private ImageView imageView;
    private NavigationView mNavigationView;
    private int mCurrentFragment = FRAGMENT_HOME;
    private TextView tv_name,tv_email;
    final private ThongTinTaikhoanActivity mMyProfileFragment = new ThongTinTaikhoanActivity();
//    final private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if(result.getResultCode()==RESULT_OK){
//                        Intent intent = result.getData();
//                        if(intent == null){
//                            return;
//                        }
//                        Uri uri = intent.getData();
//                        mMyProfileFragment.setUri(uri);
//                        try {
//                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
//                            mMyProfileFragment.setBitmapImageView(bitmap);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }
//            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        unitUi();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //NavigationView navigationView = findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());
        mNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        showUserInformation();
        databaseHelper = DatabaseHelper.getInstance(this);
        ProductDao productDao = new ProductDao(databaseHelper);
        productDao.fillData();

        WarehouseDao warehouseDao = new WarehouseDao(databaseHelper);
        warehouseDao.fillData();

        ReceiptDao receiptDao = new ReceiptDao(databaseHelper);
        receiptDao.fillData();

        ReceiptDetailDao receiptDetailDao = new ReceiptDetailDao(databaseHelper);
        receiptDetailDao.fillData();
        showUserInformation();
    }
    public void showUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            if(name == null){
                tv_name.setVisibility(View.GONE);

            }else{
                tv_name.setVisibility(View.VISIBLE);
                tv_name.setText(name);
            }
            //imageView.
            tv_email.setText(email);
            Glide.with(this).load(photoUrl).error(R.drawable.ic_avatar_default).into(imageView);
        }

    }
    private void unitUi(){
        mNavigationView = findViewById(R.id.navigation_view);
        imageView= mNavigationView.getHeaderView(0).findViewById(R.id.img_avatar);
        tv_email= mNavigationView.getHeaderView(0).findViewById(R.id.tv_email);
        tv_name= mNavigationView.getHeaderView(0).findViewById(R.id.tv_name);
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();
    }

    private void setEvent() {
        btnProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnProductClick(view);
            }
        });

        btnReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnReceiptClick(view);
            }
        });

        btnWarehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBtnWarehouseClick(view);
            }
        });
    }

    private void setControl() {
        btnProduct = findViewById(R.id.btnProduct);
        btnWarehouse = findViewById(R.id.btnWarehouse);
        btnReceipt = findViewById(R.id.btnReceipt);
    }

    public void handleBtnWarehouseClick(View view) {
        Intent intent = new Intent(this, WarehouseActivity.class);
        startActivity(intent);
    }

    public void handleBtnProductClick(View view) {
        Intent intent = new Intent(this, ProductActivity.class);
        startActivity(intent);
    }

    public void handleBtnReceiptClick(View view) {
        Intent intent = new Intent(this, ReceiptActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.nav_home){
            if(mCurrentFragment != FRAGMENT_HOME){
                replaceFragment(new HomeFragment());
                mCurrentFragment = FRAGMENT_HOME;
            }
        }
//        else if(id==R.id.nav_sign_out) {
//            FirebaseAuth.getInstance().signOut();
//            Intent intent = new Intent(this,SignInActivity.class);
//            startActivity(intent);
//            finish();
//        }
        else if(id==R.id.ThongTinTaiKhoan){
            Intent intent = new Intent(this,ThongTinTaikhoanActivity.class);
            startActivityForResult(intent, 2);
            showUserInformation();
//            if(mCurrentFragment != FRAGMENT_MY_PROFILE){
//                replaceFragment(mMyProfileFragment);
//                mCurrentFragment = FRAGMENT_MY_PROFILE;
//            }
        }else if(id==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this,SignInActivity.class);
            startActivity(intent);
            finish();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode == MY_REQUEST_CODE){
//            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                openGallery();
//            }
//        }
//    }
//    public void openGallery() {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        mActivityResultLauncher.launch(Intent.createChooser(intent,"select picture"));
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            showUserInformation();
        }
    }


}