package com.example.warehousemanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanagement.databinding.ActivityOtpVerifyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;



public class OtpVerifyActivity extends AppCompatActivity {
    public static final String EXTRA_DATA = "EXTRA_DATA";
    private ActivityOtpVerifyBinding binding;
    private String verificationId;
    private ProgressDialog progressDialog;
    private String strEmail ;
    String strNewPassword ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(OtpVerifyActivity.this);
        editTextInput();

        binding.tvMobile.setText(String.format(
                "%s", getIntent().getStringExtra("phone")
        ));

        verificationId = getIntent().getStringExtra("verificationId");
//        Intent intent = new Intent(OtpVerifyActivity.this, OtpSendActivity.class);
//        intent.putExtra(EXTRA_DATA, "Some interesting data!");
//        setResult(ChangePassword.RESULT_OK, intent);
//        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        finish();
        binding.tvResendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OtpVerifyActivity.this, "OTP Send Successfully.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progressBarVerify.setVisibility(View.VISIBLE);
                binding.btnVerify.setVisibility(View.INVISIBLE);
                if (binding.etC1.getText().toString().trim().isEmpty() ||
                        binding.etC2.getText().toString().trim().isEmpty() ||
                        binding.etC3.getText().toString().trim().isEmpty() ||
                        binding.etC4.getText().toString().trim().isEmpty() ||
                        binding.etC5.getText().toString().trim().isEmpty() ||
                        binding.etC6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(OtpVerifyActivity.this, "OTP is not Valid!", Toast.LENGTH_SHORT).show();
                } else {
                    if (verificationId != null) {
                        String code = binding.etC1.getText().toString().trim() +
                                binding.etC2.getText().toString().trim() +
                                binding.etC3.getText().toString().trim() +
                                binding.etC4.getText().toString().trim() +
                                binding.etC5.getText().toString().trim() +
                                binding.etC6.getText().toString().trim();

                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
                        FirebaseAuth
                                .getInstance()
                                .signInWithCredential(credential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            //Intent myIntent = new Intent(view.getContext(), Calculated.class);

//                                            Bundle extras = getIntent().getExtras();
//                                            if(extras == null) {
//                                                strNewPassword= null;
//                                                strEmail= null;
//                                            } else {
//                                                strNewPassword= getIntent().getStringExtra("strPassword");
//                                                strEmail= getIntent().getStringExtra("strEmail");
//                                            }
                                            //onClickChangePassword(strEmail,strNewPassword);
                                            binding.progressBarVerify.setVisibility(View.VISIBLE);
                                            binding.btnVerify.setVisibility(View.INVISIBLE);
                                            //Toast.makeText(OtpVerifyActivity.this, "Welcome...", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(OtpVerifyActivity.this, OtpSendActivity.class);
                                            intent.putExtra(EXTRA_DATA, "Some interesting data!");
                                            setResult(ChangePassword.RESULT_OK, intent);
                                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            finish();

                                            //startActivity(intent);
                                        } else {
                                            binding.progressBarVerify.setVisibility(View.GONE);
                                            binding.btnVerify.setVisibility(View.VISIBLE);
                                            Toast.makeText(OtpVerifyActivity.this, "OTP is not Valid!", Toast.LENGTH_SHORT).show();
//                                            Intent intent = new Intent(OtpVerifyActivity.this, OtpSendActivity.class);
//                                            intent.putExtra(EXTRA_DATA, "Some interesting data!");
//                                            setResult(ChangePassword.RESULT_CANCELED, intent);
                                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            //finish();
                                        }
                                    }
                                });
                    }
                }
            }
        });

    }
    public void onClickChangePassword(String strEmail,String strNewPassword) {

//        String strEmail="";
        progressDialog.show();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            // String name = user.getDisplayName();
            //strEmail = user.getEmail();
        }

        String finalStrEmail = strEmail;
        //reAuthentiate(finalStrEmail,strOldPassword);
        user.updatePassword(strNewPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
//                            Toast.makeText(OtpVerifyActivity.this,"Change password successfully",Toast.LENGTH_SHORT).show();
                            Toast.makeText(OtpVerifyActivity.this,strEmail+"==========",Toast.LENGTH_SHORT).show();
                            //                           progressDialog.dismiss();
                        }else{
                            Toast.makeText(OtpVerifyActivity.this,"Fix đi",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public void onBackPressed() {

        // đặt resultCode là Activity.RESULT_CANCELED thể hiện
        // đã thất bại khi người dùng click vào nút Back.
        // Khi này sẽ không trả về data.
        setResult(OtpVerifyActivity.RESULT_CANCELED);
        super.onBackPressed();
    }
    private void editTextInput() {
        binding.etC1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.etC2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etC2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.etC3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etC3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.etC4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etC4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.etC5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.etC5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.etC6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}

//package com.example.warehousemanagement;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.warehousemanagement.databinding.ActivityOtpVerifyBinding;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthProvider;
//
//import org.jetbrains.annotations.NotNull;
//
//
//
//public class OtpVerifyActivity extends AppCompatActivity {
//    public static final String EXTRA_DATA = "EXTRA_DATA";
//    private ActivityOtpVerifyBinding binding;
//    private String verificationId;
//    private ProgressDialog progressDialog;
//    private String strEmail ;
//    String strNewPassword ;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityOtpVerifyBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        progressDialog = new ProgressDialog(OtpVerifyActivity.this);
//        editTextInput();
//
//        binding.tvMobile.setText(String.format(
//                "+84-%s", getIntent().getStringExtra("phone")
//        ));
////        Intent intent = new Intent(OtpVerifyActivity.this, OtpSendActivity.class);
////        Intent intent = new Intent(OtpVerifyActivity.this, ChangePassword.class);
////        intent.putExtra(EXTRA_DATA, "Some interesting data!");
////        setResult(OtpVerifyActivity.RESULT_OK, intent);
////        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
////
////                finish();
//        verificationId = getIntent().getStringExtra("verificationId");
//
//        binding.tvResendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(OtpVerifyActivity.this, "OTP Send Successfully.", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                binding.progressBarVerify.setVisibility(View.VISIBLE);
//                binding.btnVerify.setVisibility(View.INVISIBLE);
//                if (binding.etC1.getText().toString().trim().isEmpty() ||
//                        binding.etC2.getText().toString().trim().isEmpty() ||
//                        binding.etC3.getText().toString().trim().isEmpty() ||
//                        binding.etC4.getText().toString().trim().isEmpty() ||
//                        binding.etC5.getText().toString().trim().isEmpty() ||
//                        binding.etC6.getText().toString().trim().isEmpty()) {
//                    Toast.makeText(OtpVerifyActivity.this, "OTP is not Valid!", Toast.LENGTH_SHORT).show();
//                } else {
//                    if (verificationId != null) {
//                        String code = binding.etC1.getText().toString().trim() +
//                                binding.etC2.getText().toString().trim() +
//                                binding.etC3.getText().toString().trim() +
//                                binding.etC4.getText().toString().trim() +
//                                binding.etC5.getText().toString().trim() +
//                                binding.etC6.getText().toString().trim();
//
//                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
//                        FirebaseAuth
//                                .getInstance()
//                                .signInWithCredential(credential)
//                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
//                                        if (task.isSuccessful()) {
//                                            //Intent myIntent = new Intent(view.getContext(), Calculated.class);
//
//                                            Bundle extras = getIntent().getExtras();
//                                            if(extras == null) {
//                                                strNewPassword= null;
//                                                strEmail= null;
//                                            } else {
//                                                strNewPassword= getIntent().getStringExtra("strPassword");
//                                                strEmail= getIntent().getStringExtra("strEmail");
//                                            }
//                                            //onClickChangePassword(strEmail,strNewPassword);
//                                            binding.progressBarVerify.setVisibility(View.VISIBLE);
//                                            binding.btnVerify.setVisibility(View.INVISIBLE);
//                                            //Toast.makeText(OtpVerifyActivity.this, "Welcome...", Toast.LENGTH_SHORT).show();
//                                            Intent intent = new Intent(OtpVerifyActivity.this, OtpSendActivity.class);
//                                            intent.putExtra(EXTRA_DATA, "Some interesting data!");
//                                            setResult(OtpVerifyActivity.RESULT_OK, intent);
//                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                            finish();
//
//                                            //startActivity(intent);
//                                        } else {
//                                            binding.progressBarVerify.setVisibility(View.GONE);
//                                            binding.btnVerify.setVisibility(View.VISIBLE);
//                                            Toast.makeText(OtpVerifyActivity.this, "OTP is not Valid!", Toast.LENGTH_SHORT).show();
//                                            Intent intent = new Intent(OtpVerifyActivity.this, OtpSendActivity.class);
//                                            intent.putExtra(EXTRA_DATA, "Some interesting data!");
//                                            setResult(OtpVerifyActivity.RESULT_CANCELED, intent);
//                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                            finish();
//                                        }
//                                    }
//                                });
//                    }
//                }
//            }
//        });
//
//    }
//    public void onClickChangePassword(String strEmail,String strNewPassword) {
//
////        String strEmail="";
//        progressDialog.show();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            // Name, email address, and profile photo Url
//            // String name = user.getDisplayName();
//            //strEmail = user.getEmail();
//        }
//
//        String finalStrEmail = strEmail;
//        //reAuthentiate(finalStrEmail,strOldPassword);
//        user.updatePassword(strNewPassword)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
////                            Toast.makeText(OtpVerifyActivity.this,"Change password successfully",Toast.LENGTH_SHORT).show();
//                            Toast.makeText(OtpVerifyActivity.this,strEmail+"==========",Toast.LENGTH_SHORT).show();
//                            //                           progressDialog.dismiss();
//                        }else{
//                            Toast.makeText(OtpVerifyActivity.this,"Fix đi",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//    @Override
//    public void onBackPressed() {
//
//        // đặt resultCode là Activity.RESULT_CANCELED thể hiện
//        // đã thất bại khi người dùng click vào nút Back.
//        // Khi này sẽ không trả về data.
//        setResult(OtpVerifyActivity.RESULT_CANCELED);
//        super.onBackPressed();
//    }
//    private void editTextInput() {
//        binding.etC1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                binding.etC2.requestFocus();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        binding.etC2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                binding.etC3.requestFocus();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        binding.etC3.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                binding.etC4.requestFocus();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        binding.etC4.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                binding.etC5.requestFocus();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        binding.etC5.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                binding.etC6.requestFocus();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//    }
//}

//package com.example.warehousemanagement;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.warehousemanagement.databinding.ActivityOtpVerifyBinding;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthProvider;
//
//import org.jetbrains.annotations.NotNull;
//
//
//
//public class OtpVerifyActivity extends AppCompatActivity {
//    public static final String EXTRA_DATA = "EXTRA_DATA";
//    private ActivityOtpVerifyBinding binding;
//    private String verificationId;
//    private ProgressDialog progressDialog;
//    private String strEmail ;
//    String strNewPassword ;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityOtpVerifyBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        progressDialog = new ProgressDialog(OtpVerifyActivity.this);
//        editTextInput();
//
//        binding.tvMobile.setText(String.format(
//                "+84-%s", getIntent().getStringExtra("phone")
//        ));
//
//        verificationId = getIntent().getStringExtra("verificationId");
//
//        binding.tvResendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(OtpVerifyActivity.this, "OTP Send Successfully.", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                binding.progressBarVerify.setVisibility(View.VISIBLE);
//                binding.btnVerify.setVisibility(View.INVISIBLE);
//                if (binding.etC1.getText().toString().trim().isEmpty() ||
//                        binding.etC2.getText().toString().trim().isEmpty() ||
//                        binding.etC3.getText().toString().trim().isEmpty() ||
//                        binding.etC4.getText().toString().trim().isEmpty() ||
//                        binding.etC5.getText().toString().trim().isEmpty() ||
//                        binding.etC6.getText().toString().trim().isEmpty()) {
//                    Toast.makeText(OtpVerifyActivity.this, "OTP is not Valid!", Toast.LENGTH_SHORT).show();
//                } else {
//                    if (verificationId != null) {
//                        String code = binding.etC1.getText().toString().trim() +
//                                binding.etC2.getText().toString().trim() +
//                                binding.etC3.getText().toString().trim() +
//                                binding.etC4.getText().toString().trim() +
//                                binding.etC5.getText().toString().trim() +
//                                binding.etC6.getText().toString().trim();
//
//                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
//                        FirebaseAuth
//                                .getInstance()
//                                .signInWithCredential(credential)
//                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
//                                        if (task.isSuccessful()) {
//                                            //Intent myIntent = new Intent(view.getContext(), Calculated.class);
//
//                                            Bundle extras = getIntent().getExtras();
//                                            if(extras == null) {
//                                                strNewPassword= null;
//                                                strEmail= null;
//                                            } else {
//                                                strNewPassword= getIntent().getStringExtra("strPassword");
//                                                strEmail= getIntent().getStringExtra("strEmail");
//                                            }
//                                            //onClickChangePassword(strEmail,strNewPassword);
//                                            binding.progressBarVerify.setVisibility(View.VISIBLE);
//                                            binding.btnVerify.setVisibility(View.INVISIBLE);
//                                            //Toast.makeText(OtpVerifyActivity.this, "Welcome...", Toast.LENGTH_SHORT).show();
//                                            Intent intent = new Intent(OtpVerifyActivity.this, ChangePassword.class);
//                                            intent.putExtra(EXTRA_DATA, "Some interesting data!");
//                                            setResult(OtpVerifyActivity.RESULT_OK, intent);
//                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                            finish();
//
//                                            //startActivity(intent);
//                                        } else {
//                                            binding.progressBarVerify.setVisibility(View.GONE);
//                                            binding.btnVerify.setVisibility(View.VISIBLE);
//                                            Toast.makeText(OtpVerifyActivity.this, "OTP is not Valid!", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                });
//                    }
//                }
//            }
//        });
//    }
//    public void onClickChangePassword(String strEmail,String strNewPassword) {
//
////        String strEmail="";
//        progressDialog.show();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            // Name, email address, and profile photo Url
//            // String name = user.getDisplayName();
//            //strEmail = user.getEmail();
//        }
//
//        String finalStrEmail = strEmail;
//        //reAuthentiate(finalStrEmail,strOldPassword);
//        user.updatePassword(strNewPassword)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
////                            Toast.makeText(OtpVerifyActivity.this,"Change password successfully",Toast.LENGTH_SHORT).show();
//                            Toast.makeText(OtpVerifyActivity.this,strEmail+"==========",Toast.LENGTH_SHORT).show();
//                            //                           progressDialog.dismiss();
//                        }else{
//                            Toast.makeText(OtpVerifyActivity.this,"Fix đi",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//    @Override
//    public void onBackPressed() {
//
//        // đặt resultCode là Activity.RESULT_CANCELED thể hiện
//        // đã thất bại khi người dùng click vào nút Back.
//        // Khi này sẽ không trả về data.
//        setResult(OtpVerifyActivity.RESULT_CANCELED);
//        super.onBackPressed();
//    }
//    private void editTextInput() {
//        binding.etC1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                binding.etC2.requestFocus();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        binding.etC2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                binding.etC3.requestFocus();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        binding.etC3.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                binding.etC4.requestFocus();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        binding.etC4.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                binding.etC5.requestFocus();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        binding.etC5.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                binding.etC6.requestFocus();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//    }
//}

//package com.example.warehousemanagement;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.warehousemanagement.databinding.ActivityOtpVerifyBinding;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthProvider;
//
//import org.jetbrains.annotations.NotNull;
//
//
//
//public class OtpVerifyActivity extends AppCompatActivity {
//
//    private ActivityOtpVerifyBinding binding;
//    private String verificationId;
//    private ProgressDialog progressDialog;
//    private String strEmail ;
//    String strNewPassword ;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityOtpVerifyBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        progressDialog = new ProgressDialog(OtpVerifyActivity.this);
//        editTextInput();
//
//        binding.tvMobile.setText(String.format(
//                "+84-%s", getIntent().getStringExtra("phone")
//        ));
//
//        verificationId = getIntent().getStringExtra("verificationId");
//
//        binding.tvResendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(OtpVerifyActivity.this, "OTP Send Successfully.", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        binding.btnVerify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                binding.progressBarVerify.setVisibility(View.VISIBLE);
//                binding.btnVerify.setVisibility(View.INVISIBLE);
//                if (binding.etC1.getText().toString().trim().isEmpty() ||
//                        binding.etC2.getText().toString().trim().isEmpty() ||
//                        binding.etC3.getText().toString().trim().isEmpty() ||
//                        binding.etC4.getText().toString().trim().isEmpty() ||
//                        binding.etC5.getText().toString().trim().isEmpty() ||
//                        binding.etC6.getText().toString().trim().isEmpty()) {
//                    Toast.makeText(OtpVerifyActivity.this, "OTP is not Valid!", Toast.LENGTH_SHORT).show();
//                } else {
//                    if (verificationId != null) {
//                        String code = binding.etC1.getText().toString().trim() +
//                                binding.etC2.getText().toString().trim() +
//                                binding.etC3.getText().toString().trim() +
//                                binding.etC4.getText().toString().trim() +
//                                binding.etC5.getText().toString().trim() +
//                                binding.etC6.getText().toString().trim();
//
//                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
//                        FirebaseAuth
//                                .getInstance()
//                                .signInWithCredential(credential)
//                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    //Intent myIntent = new Intent(view.getContext(), Calculated.class);
//
//                                    Bundle extras = getIntent().getExtras();
//                                    if(extras == null) {
//                                        strNewPassword= null;
//                                        strEmail= null;
//                                    } else {
//                                        strNewPassword= getIntent().getStringExtra("strPassword");
//                                        strEmail= getIntent().getStringExtra("strEmail");
//                                    }
//                                    onClickChangePassword(strEmail,strNewPassword);
//                                    binding.progressBarVerify.setVisibility(View.VISIBLE);
//                                    binding.btnVerify.setVisibility(View.INVISIBLE);
//                                    //Toast.makeText(OtpVerifyActivity.this, "Welcome...", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(OtpVerifyActivity.this, ChangePassword.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    startActivity(intent);
//                                } else {
//                                    binding.progressBarVerify.setVisibility(View.GONE);
//                                    binding.btnVerify.setVisibility(View.VISIBLE);
//                                    Toast.makeText(OtpVerifyActivity.this, "OTP is not Valid!", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                    }
//                }
//            }
//        });
//    }
//    public void onClickChangePassword(String strEmail,String strNewPassword) {
//
////        String strEmail="";
//        progressDialog.show();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            // Name, email address, and profile photo Url
//            // String name = user.getDisplayName();
//            //strEmail = user.getEmail();
//        }
//
//        String finalStrEmail = strEmail;
//        //reAuthentiate(finalStrEmail,strOldPassword);
//        user.updatePassword(strNewPassword)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
////                            Toast.makeText(OtpVerifyActivity.this,"Change password successfully",Toast.LENGTH_SHORT).show();
//                            Toast.makeText(OtpVerifyActivity.this,strEmail+"==========",Toast.LENGTH_SHORT).show();
// //                           progressDialog.dismiss();
//                        }else{
//                            Toast.makeText(OtpVerifyActivity.this,"Fix đi",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//    private void editTextInput() {
//        binding.etC1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                binding.etC2.requestFocus();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        binding.etC2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                binding.etC3.requestFocus();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        binding.etC3.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                binding.etC4.requestFocus();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        binding.etC4.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                binding.etC5.requestFocus();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        binding.etC5.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                binding.etC6.requestFocus();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//    }
//}