//package com.example.warehousemanagement;
//
//
//
//import static com.example.warehousemanagement.MainActivity.MY_REQUEST_CODE;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.bumptech.glide.Glide;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.UserProfileChangeRequest;
//
//public class MyProfileFragment extends Fragment {
//    private View mview;
//    private ImageView imageView;
//    private EditText edtFullName,edtEmail;
//    private Button btnUpdateProfile;
//    private  Uri mUri;
//    private MainActivity mMainActivity;
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        mview = inflater.inflate(R.layout.fragment_my_profile,container,false);
//        unitUi();
//        mMainActivity = (MainActivity) ChangePassword.this;
//        setUserInformation();
//        initListener();
//        return mview;
//    }
//
//    private void initListener() {
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onClickRequestPermission();
//            }
//        });
//        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onClickUpdateProfile();
//            }
//        });
//    }
//
//    public void setUri(Uri mUri) {
//        this.mUri = mUri;
//    }
//
//    private void onClickUpdateProfile() {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if(user == null){
//            return;
//        }
//        String strfullname= edtFullName.getText().toString().trim();
//
//        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                .setDisplayName(strfullname)
//                .setPhotoUri(mUri)
//                .build();
//
//        user.updateProfile(profileUpdates)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(ChangePassword.this,"Update Success",Toast.LENGTH_SHORT).show();
//                            mMainActivity.showUserInformation();
//                        }
//                    }
//                });
//    }
//
//    private void onClickRequestPermission() {
////        MainActivity mainActivity = (MainActivity) ChangePassword.this;
//        if(mMainActivity == null){
//            return;
//        }
//        if(Build.VERSION.SDK_INT <Build.VERSION_CODES.M){
//            mMainActivity.openGallery();
//            return;
//        }
//        if(ChangePassword.this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
//            mMainActivity.openGallery();
//        }else{
//            String [] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
//            ChangePassword.this.requestPermissions(permissions,MY_REQUEST_CODE);
//        }
//    }
//
//
//
//    private void setUserInformation() {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if(user == null){
//            return;
//        }
////        if(user.getDisplayName().equals("")){
////            edtFullName.setText("No name");
////        }
//        edtFullName.setText(user.getDisplayName());
//        //edtFullName.setText("123");
//        edtEmail.setText(user.getEmail());
//        Glide.with(ChangePassword.this).load(user.getPhotoUrl()).error(R.drawable.ic_avatar_default).into(imageView);
//
//    }
//    public void setBitmapImageView(Bitmap bitmapImageView){
//        imageView.setImageBitmap(bitmapImageView);
//    }
//    private  void unitUi(){
//
//        imageView = mview.findViewById(R.id.img_avatar);
//        edtFullName = mview.findViewById(R.id.edt_full_name);
//        edtEmail = mview.findViewById(R.id.edt_email);
//        btnUpdateProfile = mview.findViewById(R.id.btn_update_profile);
//    }
//}
