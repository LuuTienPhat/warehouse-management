package com.example.warehousemanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanagement.databinding.ActivityOtpSendBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;




public class OtpSendActivity extends AppCompatActivity {
    final  String DATABASE_NAME = "warehouse.db";
    DatabaseAccess DB;
    SQLiteDatabase database;
    public static final String EXTRA_DATA = "EXTRA_DATA";
    private ActivityOtpSendBinding binding;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String strNewPassword ;
    private String strEmail ;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpSendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.etPhone.setEnabled(false);
        DB = DatabaseAccess.getInstance(getApplicationContext());
        LayUser();
        mAuth = FirebaseAuth.getInstance();
//        Bundle extras = getIntent().getExtras();
//        if(extras == null) {
//            strNewPassword= null;
//            strEmail= null;
//        } else {
//            strNewPassword= getIntent().getStringExtra("strPassword");
//            strEmail= getIntent().getStringExtra("strEmail");
//        }
        Toast.makeText(OtpSendActivity.this,strEmail ,Toast.LENGTH_SHORT).show();
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.etPhone.getText().toString().trim().isEmpty()) {
                    Toast.makeText(OtpSendActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                }
//                else if (binding.etPhone.getText().toString().trim().length() != 10) {
//                    Toast.makeText(OtpSendActivity.this, "Type valid Phone Number", Toast.LENGTH_SHORT).show();
//                }
                else {
                    otpSend();
//                    Intent intent = new Intent(OtpSendActivity.this, ChangePassword.class);
//                    setResult(ChangePassword.RESULT_OK, intent);
//                    finish();
                    // Sử dụng kết quả result bằng cách hiện Toast

                }
            }
        });
    }
    public void LayUser()
    {
        database = Database.initDatabase(OtpSendActivity.this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM User WHERE ID_User = ?",new String[]{String.valueOf(DB.iduser)});
        cursor.moveToNext();
        String Iduser = cursor.getString(0);
        String HoTen = cursor.getString(1);
        int Point = cursor.getInt(2);
        String Email = cursor.getString(3);
        String SDT = cursor.getString(4);
        user = new User(Iduser,HoTen,Point,Email,SDT);
        binding.etPhone.setText(user.getSDT());

    }
    private void otpSend() {

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btnSend.setVisibility(View.INVISIBLE);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                binding.progressBar.setVisibility(View.GONE);
                binding.btnSend.setVisibility(View.VISIBLE);
                Toast.makeText(OtpSendActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                binding.progressBar.setVisibility(View.GONE);
                binding.btnSend.setVisibility(View.VISIBLE);
                Toast.makeText(OtpSendActivity.this, "OTP is successfully send.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OtpSendActivity.this, OtpVerifyActivity.class);
                intent.putExtra("phone", binding.etPhone.getText().toString().trim());
                intent.putExtra("verificationId", verificationId);
//                intent.putExtra("strEmail",strEmail);
//                intent.putExtra("strPassword",strNewPassword);
                //startActivity(intent);
                startActivityForResult(intent, 2);
            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84" + binding.etPhone.getText().toString().trim())
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Kiểm tra requestCode có trùng với REQUEST_CODE vừa dùng
        if(requestCode == 2) {

            // resultCode được set bởi DetailActivity
            // RESULT_OK chỉ ra rằng kết quả này đã thành công
            if(resultCode == ChangePassword.RESULT_OK) {
                // Nhận dữ liệu từ Intent trả về
                final String result = data.getStringExtra(OtpVerifyActivity.EXTRA_DATA);
                //onClickChangePassword();
                Intent intent = new Intent(OtpSendActivity.this, ChangePassword.class);
                setResult(ChangePassword.RESULT_OK, intent);
                finish();
                // Sử dụng kết quả result bằng cách hiện Toast
                Toast.makeText(this, "Result222: " + result, Toast.LENGTH_LONG).show();
            } else {
//                Intent intent = new Intent(OtpSendActivity.this, ChangePassword.class);
//                setResult(ChangePassword.RESULT_CANCELED, intent);
                finish();
            }
        }
    }
}

//package com.example.warehousemanagement;
//
//import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.warehousemanagement.databinding.ActivityOtpSendBinding;
//import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthOptions;
//import com.google.firebase.auth.PhoneAuthProvider;
//
//import java.util.concurrent.TimeUnit;
//
//
//
//
//public class OtpSendActivity extends AppCompatActivity {
//    final  String DATABASE_NAME = "warehouse.db";
//    DatabaseAccess DB;
//    SQLiteDatabase database;
//    public static final String EXTRA_DATA = "EXTRA_DATA";
//    private ActivityOtpSendBinding binding;
//    private FirebaseAuth mAuth;
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
//    private String strNewPassword ;
//    private String strEmail ;
//    User user;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityOtpSendBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        binding.etPhone.setEnabled(false);
//        DB = DatabaseAccess.getInstance(getApplicationContext());
//        LayUser();
//        mAuth = FirebaseAuth.getInstance();
////        Bundle extras = getIntent().getExtras();
////        if(extras == null) {
////            strNewPassword= null;
////            strEmail= null;
////        } else {
////            strNewPassword= getIntent().getStringExtra("strPassword");
////            strEmail= getIntent().getStringExtra("strEmail");
////        }
//        Toast.makeText(OtpSendActivity.this,strEmail ,Toast.LENGTH_SHORT).show();
//        binding.btnSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (binding.etPhone.getText().toString().trim().isEmpty()) {
//                    Toast.makeText(OtpSendActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
//                }
////                else if (binding.etPhone.getText().toString().trim().length() != 10) {
////                    Toast.makeText(OtpSendActivity.this, "Type valid Phone Number", Toast.LENGTH_SHORT).show();
////                }
//                else {
//                    otpSend();
////                    Intent intent = new Intent(OtpSendActivity.this, ChangePassword.class);
////                    intent.putExtra(EXTRA_DATA, "Some interesting data!");
////                    setResult(OtpVerifyActivity.RESULT_OK, intent);
////                    finish();
//                }
//            }
//        });
//    }
//    public void LayUser()
//    {
//        database = Database.initDatabase(OtpSendActivity.this, DATABASE_NAME);
//        Cursor cursor = database.rawQuery("SELECT * FROM User WHERE ID_User = ?",new String[]{String.valueOf(DB.iduser)});
//        cursor.moveToNext();
//        String Iduser = cursor.getString(0);
//        String HoTen = cursor.getString(1);
//        int Point = cursor.getInt(2);
//        String Email = cursor.getString(3);
//        String SDT = cursor.getString(4);
//        user = new User(Iduser,HoTen,Point,Email,SDT);
//        binding.etPhone.setText(user.getSDT());
//
//    }
//    private void otpSend() {
//
//        binding.progressBar.setVisibility(View.VISIBLE);
//        binding.btnSend.setVisibility(View.INVISIBLE);
//
//        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            @Override
//            public void onVerificationCompleted(PhoneAuthCredential credential) {
//
//            }
//
//            @Override
//            public void onVerificationFailed(FirebaseException e) {
//                binding.progressBar.setVisibility(View.GONE);
//                binding.btnSend.setVisibility(View.VISIBLE);
//                Toast.makeText(OtpSendActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCodeSent(@NonNull String verificationId,
//                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
//                binding.progressBar.setVisibility(View.GONE);
//                binding.btnSend.setVisibility(View.VISIBLE);
//                Toast.makeText(OtpSendActivity.this, "OTP is successfully send.", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(OtpSendActivity.this, OtpVerifyActivity.class);
//                intent.putExtra("phone", binding.etPhone.getText().toString().trim());
//                intent.putExtra("verificationId", verificationId);
////                intent.putExtra("strEmail",strEmail);
////                intent.putExtra("strPassword",strNewPassword);
//                //startActivity(intent);
//                startActivityForResult(intent, 2);
//            }
//        };
//
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(mAuth)
//                        .setPhoneNumber("+84" + binding.etPhone.getText().toString().trim())
//                        .setTimeout(60L, TimeUnit.SECONDS)
//                        .setActivity(this)
//                        .setCallbacks(mCallbacks)
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Kiểm tra requestCode có trùng với REQUEST_CODE vừa dùng
//        if(requestCode == 2) {
//
//            // resultCode được set bởi DetailActivity
//            // RESULT_OK chỉ ra rằng kết quả này đã thành công
//            if(resultCode == ChangePassword.RESULT_OK) {
//                // Nhận dữ liệu từ Intent trả về
//                final String result = data.getStringExtra(OtpVerifyActivity.EXTRA_DATA);
//                //onClickChangePassword();
//                Intent intent = new Intent(OtpSendActivity.this, ChangePassword.class);
//                setResult(OtpSendActivity.RESULT_OK, intent);
//                finish();
//                // Sử dụng kết quả result bằng cách hiện Toast
//                //Toast.makeText(this, "Result222: " + result, Toast.LENGTH_LONG).show();
//            } else {
//                Intent intent = new Intent(OtpSendActivity.this, ChangePassword.class);
//                setResult(OtpSendActivity.RESULT_CANCELED, intent);
//                finish();
//            }
//        }
//    }
//}

//package com.example.warehousemanagement;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.warehousemanagement.databinding.ActivityOtpSendBinding;
//import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthOptions;
//import com.google.firebase.auth.PhoneAuthProvider;
//
//import java.util.concurrent.TimeUnit;
//
//
//
//
//public class OtpSendActivity extends AppCompatActivity {
//
//    private ActivityOtpSendBinding binding;
//    private FirebaseAuth mAuth;
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
//    private String strNewPassword ;
//    private String strEmail ;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityOtpSendBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//
//        mAuth = FirebaseAuth.getInstance();
//        Bundle extras = getIntent().getExtras();
//        if(extras == null) {
//            strNewPassword= null;
//            strEmail= null;
//        } else {
//            strNewPassword= getIntent().getStringExtra("strPassword");
//            strEmail= getIntent().getStringExtra("strEmail");
//        }
//        Toast.makeText(OtpSendActivity.this,strEmail ,Toast.LENGTH_SHORT).show();
//        binding.btnSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (binding.etPhone.getText().toString().trim().isEmpty()) {
//                    Toast.makeText(OtpSendActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
//                }
////                else if (binding.etPhone.getText().toString().trim().length() != 10) {
////                    Toast.makeText(OtpSendActivity.this, "Type valid Phone Number", Toast.LENGTH_SHORT).show();
////                }
//                else {
//                    otpSend();
//                }
//            }
//        });
//    }
//
//    private void otpSend() {
//        binding.progressBar.setVisibility(View.VISIBLE);
//        binding.btnSend.setVisibility(View.INVISIBLE);
//
//        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            @Override
//            public void onVerificationCompleted(PhoneAuthCredential credential) {
//
//            }
//
//            @Override
//            public void onVerificationFailed(FirebaseException e) {
//                binding.progressBar.setVisibility(View.GONE);
//                binding.btnSend.setVisibility(View.VISIBLE);
//                Toast.makeText(OtpSendActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCodeSent(@NonNull String verificationId,
//                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
//                binding.progressBar.setVisibility(View.GONE);
//                binding.btnSend.setVisibility(View.VISIBLE);
//                Toast.makeText(OtpSendActivity.this, "OTP is successfully send.", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(OtpSendActivity.this, OtpVerifyActivity.class);
//                intent.putExtra("phone", binding.etPhone.getText().toString().trim());
//                intent.putExtra("verificationId", verificationId);
//                intent.putExtra("strEmail",strEmail);
//                intent.putExtra("strPassword",strNewPassword);
//                //startActivity(intent);
//                startActivityForResult(intent, 2);
//            }
//        };
//
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(mAuth)
//                        .setPhoneNumber("+84" + binding.etPhone.getText().toString().trim())
//                        .setTimeout(60L, TimeUnit.SECONDS)
//                        .setActivity(this)
//                        .setCallbacks(mCallbacks)
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Kiểm tra requestCode có trùng với REQUEST_CODE vừa dùng
//        if(requestCode == 2) {
//
//            // resultCode được set bởi DetailActivity
//            // RESULT_OK chỉ ra rằng kết quả này đã thành công
//            if(resultCode == ChangePassword.RESULT_OK) {
//                // Nhận dữ liệu từ Intent trả về
//                final String result = data.getStringExtra(OtpVerifyActivity.EXTRA_DATA);
//                //onClickChangePassword();
//                finish();
//                // Sử dụng kết quả result bằng cách hiện Toast
//                Toast.makeText(this, "Result: " + result, Toast.LENGTH_LONG).show();
//            } else {
//                // DetailActivity không thành công, không có data trả về.
//            }
//        }
//    }
//}


//package com.example.warehousemanagement;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.warehousemanagement.databinding.ActivityOtpSendBinding;
//import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthOptions;
//import com.google.firebase.auth.PhoneAuthProvider;
//
//import java.util.concurrent.TimeUnit;
//
//
//
//
//public class OtpSendActivity extends AppCompatActivity {
//
//    private ActivityOtpSendBinding binding;
//    private FirebaseAuth mAuth;
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
//    private String strNewPassword ;
//    private String strEmail ;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityOtpSendBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//
//        mAuth = FirebaseAuth.getInstance();
//        Bundle extras = getIntent().getExtras();
//        if(extras == null) {
//            strNewPassword= null;
//            strEmail= null;
//        } else {
//            strNewPassword= getIntent().getStringExtra("strPassword");
//            strEmail= getIntent().getStringExtra("strEmail");
//        }
//        Toast.makeText(OtpSendActivity.this,strEmail ,Toast.LENGTH_SHORT).show();
//        binding.btnSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (binding.etPhone.getText().toString().trim().isEmpty()) {
//                    Toast.makeText(OtpSendActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
//                }
////                else if (binding.etPhone.getText().toString().trim().length() != 10) {
////                    Toast.makeText(OtpSendActivity.this, "Type valid Phone Number", Toast.LENGTH_SHORT).show();
////                }
//                else {
//                    otpSend();
//                }
//            }
//        });
//    }
//
//    private void otpSend() {
//        binding.progressBar.setVisibility(View.VISIBLE);
//        binding.btnSend.setVisibility(View.INVISIBLE);
//
//        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            @Override
//            public void onVerificationCompleted(PhoneAuthCredential credential) {
//
//            }
//
//            @Override
//            public void onVerificationFailed(FirebaseException e) {
//                binding.progressBar.setVisibility(View.GONE);
//                binding.btnSend.setVisibility(View.VISIBLE);
//                Toast.makeText(OtpSendActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCodeSent(@NonNull String verificationId,
//                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
//                binding.progressBar.setVisibility(View.GONE);
//                binding.btnSend.setVisibility(View.VISIBLE);
//                Toast.makeText(OtpSendActivity.this, "OTP is successfully send.", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(OtpSendActivity.this, OtpVerifyActivity.class);
//                intent.putExtra("phone", binding.etPhone.getText().toString().trim());
//                intent.putExtra("verificationId", verificationId);
//                intent.putExtra("strEmail",strEmail);
//                intent.putExtra("strPassword",strNewPassword);
//                startActivity(intent);
//            }
//        };
//
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(mAuth)
//                        .setPhoneNumber("+84" + binding.etPhone.getText().toString().trim())
//                        .setTimeout(60L, TimeUnit.SECONDS)
//                        .setActivity(this)
//                        .setCallbacks(mCallbacks)
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }
//}