package com.example.warehousemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        },2000);
    }

    private void nextActivity() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            //Chưa login
            Intent intent = new Intent(this,SignInActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        finish();
    }
}