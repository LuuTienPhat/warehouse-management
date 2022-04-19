package com.example.warehousemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
public class SignInActivity extends AppCompatActivity {

    Button btnDangnhap;
    TextView tvDangky, tvforgotPassword;
    EditText edttaikhoan, edtmatkhau;
    DatabaseAccess DB;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_login);
        AnhXa();
        DB = DatabaseAccess.getInstance(getApplicationContext());
        mAuth = FirebaseAuth.getInstance();

        //Đăng nhập thành công chuyển sang MainActivity
        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edttaikhoan.getText().toString().trim();
                String matkhau = edtmatkhau.getText().toString().trim();
//                String email = "1@gmail.com";
//                String matkhau = "123456";
//                String email = "1236@gmail.com";
//                String matkhau = "123456";

                // validations for input email and password // check th trong
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(),
                            "Hãy nhập Email của bạn!!",
                            Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                if (TextUtils.isEmpty(matkhau)) {
                    Toast.makeText(getApplicationContext(),
                            "Hãy nhập mật khẩu của bạn!!",
                            Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                // signin existing user

                mAuth.signInWithEmailAndPassword(email, matkhau)
                        .addOnCompleteListener(
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(
                                            @NonNull Task<AuthResult> task)
                                    {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(),
                                                    "Đăng nhập thành công!!",
                                                    Toast.LENGTH_LONG)
                                                    .show();

                                            DB.iduser = mAuth.getCurrentUser().getUid();
                                            DB.CapNhatUser(mAuth.getCurrentUser().getUid());
                                            // hide the progress bar
                                            // if sign-in is successful
                                            // intent to home activity
                                            Intent intent
                                                    = new Intent(SignInActivity.this,
                                                    MainActivity.class);
                                            startActivity(intent);
                                        }
                                        else {
                                            // sign-in failed
                                            Toast.makeText(getApplicationContext(),
                                                    "Sai Email hoặc mật khẩu!!",
                                                    Toast.LENGTH_LONG)
                                                    .show();
                                        }
                                    }
                                });



//                if(taikhoan.equals("")||matkhau.equals(""))
//                {
//                    Toast.makeText(LoginActivity.this, "Điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    DB.open();
//                    //Kiểm tra username password
//                    Boolean kiemtra = DB.checktaikhoanmatkhau(taikhoan,matkhau);
//                    DB.close();
//
//                    if(kiemtra == true)
//                    {
////                        Intent tentaikhoan = new Intent(LoginActivity.this, ThongTinTaikhoanActivity.class);
////                        tentaikhoan.putExtra("username", taikhoan);
//                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(intent);
//                    }
//                    else{
//                        Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
//                    }
//                }


            }
        });
        tvDangky.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
//
        tvforgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
    private void AnhXa()
    {
        btnDangnhap=(Button) findViewById(R.id.buttonDangnhap);
        tvDangky = (TextView) findViewById(R.id.textView_register);
        tvforgotPassword = (TextView) findViewById(R.id.textView_forgotPassword);
        edttaikhoan = (EditText) findViewById(R.id.editTextUser);
        edtmatkhau = (EditText) findViewById(R.id.editTextPass);

    }
}
//public class SignInActivity extends AppCompatActivity {
//    private LinearLayout layoutSignUp;
//    Button btnDangnhap;
//    TextView tvDangky, tvforgotPassword;
//    EditText edttaikhoan, edtmatkhau;
//    DatabaseAccess DB;
//    private FirebaseAuth mAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
//        initUI();
//        DB = DatabaseAccess.getInstance(getApplicationContext());
//        mAuth = FirebaseAuth.getInstance();
//        initListener();
//        //Đăng nhập thành công chuyển sang MainActivity
//
//
////        tvDangky.setOnClickListener(new View.OnClickListener() {
////
//////            @Override
//////            public void onClick(View v) {
//////                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
//////                startActivity(intent);
//////            }
////        });
////
////        tvforgotPassword.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
////                startActivity(intent);
////            }
////        });
//    }
//    private void initUI()
//    {
//        layoutSignUp =findViewById(R.id.linearlayout_inputs);
//        //linearLayout1 = findViewById(R.id.linearlayout_inputs);
//        btnDangnhap= findViewById(R.id.buttonDangnhap);
//        tvDangky =  findViewById(R.id.textView_register);
//        tvforgotPassword =  findViewById(R.id.textView_forgotPassword);
//        edttaikhoan =  findViewById(R.id.editTextUser);
//        edtmatkhau = findViewById(R.id.editTextPass);
//
//    }
//    private void initListener() {
//        btnDangnhap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = edttaikhoan.getText().toString().trim();
//                String matkhau = edtmatkhau.getText().toString().trim();
//
//
//                // validations for input email and password // check th trong
//                if (TextUtils.isEmpty(email)) {
//                    Toast.makeText(getApplicationContext(),
//                            "Hãy nhập Email của bạn!!",
//                            Toast.LENGTH_LONG)
//                            .show();
//                    return;
//                }
//
//                if (TextUtils.isEmpty(matkhau)) {
//                    Toast.makeText(getApplicationContext(),
//                            "Hãy nhập mật khẩu của bạn!!",
//                            Toast.LENGTH_LONG)
//                            .show();
//                    return;
//                }
//
//                // signin existing user
//
//                mAuth.signInWithEmailAndPassword(email, matkhau)
//                        .addOnCompleteListener(
//                                new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(
//                                            @NonNull Task<AuthResult> task)
//                                    {
//                                        if (task.isSuccessful()) {
//                                            Toast.makeText(getApplicationContext(),
//                                                    "Đăng nhập thành công!!",
//                                                    Toast.LENGTH_LONG)
//                                                    .show();
//
//                                            DB.iduser = mAuth.getCurrentUser().getUid();
//                                            DB.CapNhatUser(mAuth.getCurrentUser().getUid());
//                                            // hide the progress bar
//                                            // if sign-in is successful
//                                            // intent to home activity
//                                            Intent intent
//                                                    = new Intent(SignInActivity.this,
//                                                    MainActivity.class);
//                                            startActivity(intent);
//                                        }
//                                        else {
//                                            // sign-in failed
//                                            Toast.makeText(getApplicationContext(),
//                                                    "Sai Email hoặc mật khẩu!!",
//                                                    Toast.LENGTH_LONG)
//                                                    .show();
//                                        }
//                                    }
//                                });
//
//
//
//
////                if(taikhoan.equals("")||matkhau.equals(""))
////                {
////                    Toast.makeText(LoginActivity.this, "Điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
////                }
////                else{
////                    DB.open();
////                    //Kiểm tra username password
////                    Boolean kiemtra = DB.checktaikhoanmatkhau(taikhoan,matkhau);
////                    DB.close();
////
////                    if(kiemtra == true)
////                    {
//////                        Intent tentaikhoan = new Intent(LoginActivity.this, ThongTinTaikhoanActivity.class);
//////                        tentaikhoan.putExtra("username", taikhoan);
////                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
////                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
////                        startActivity(intent);
////                    }
////                    else{
////                        Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
////                    }
////                }
//
//
//            }
//        });
//    }
//}
////public class SignInActivity extends AppCompatActivity {
//////    private LinearLayout layoutSignUp;
////    private TextView layoutSignUp;
////    private Button btnSignIn;
////    private EditText edtEmail,edtPassword;
////    private ProgressDialog progressDialog;
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_sign_in);
////        initUI();
////
////
////        initListener();
////
////    }
////
////    private void initListener() {
////        layoutSignUp.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
////                startActivity(intent);
////            }
////        });
////        btnSignIn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                onClickSignIn();
////
////            }
////        });
////    }
////
////    private void onClickSignIn() {
////        FirebaseAuth mAuth = FirebaseAuth.getInstance();
////        progressDialog.show();
////        String email = edtEmail.getText().toString().trim();
////        String password = edtPassword.getText().toString().trim();
////        mAuth.signInWithEmailAndPassword(email, password)
////                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
////                    @Override
////                    public void onComplete(@NonNull Task<AuthResult> task) {
////                        progressDialog.dismiss();
////                        if (task.isSuccessful()) {
////                            // Sign in success, update UI with the signed-in user's information
////                            Intent intent = new Intent(SignInActivity.this,MainActivity.class);
////                            startActivity(intent);
////                            finishAffinity();
////                        } else {
////                            // If sign in fails, display a message to the user.
////                            Toast.makeText(SignInActivity.this, "Authentication failed.",
////                                    Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                });
////    }
////
////    private void initUI(){
////        progressDialog = new ProgressDialog(this);
////        layoutSignUp =findViewById(R.id.layout_sign_up);
////        edtEmail = findViewById(R.id.edt_email);
////        edtPassword = findViewById(R.id.edt_password);
////        btnSignIn = findViewById(R.id.btn_sign_in);
////
////    }
////}