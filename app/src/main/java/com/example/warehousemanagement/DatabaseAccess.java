package com.example.warehousemanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    FirebaseDatabase rootNode; //f_instanse
    DatabaseReference userref; //f_db
    private static DatabaseAccess instance;
    Cursor c = null;
    public String iduser;
    Map<String, String> user; // Map lưu dữ liệu dạng String : String --> Hoten: Thien
    Map<String, Long> diem; //Firebase sử dụng kiểu Long thay vì Int

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseHelper(context);

    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.db = openHelper.getWritableDatabase();
    }

    public void close() {
        if (db != null) {
            this.db.close();
        }
    }

    public Boolean insertData(String iduser, String hoten, String email, String sdt, int diem) {
        db = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_User", iduser);
        contentValues.put("HoTen", hoten);
        contentValues.put("Point", diem);
        contentValues.put("Email", email);
        contentValues.put("SDT", sdt);
        long result = db.insert("User", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public Boolean checktaikhoan(String email) {
        db = openHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from User where Email = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public void CapNhatUser(String iduser) {

        //Kiểm tra xem dữ liệu đã có trong SQLite chưa
        db = openHelper.getWritableDatabase();
        //Lấy dữ liệu từ Firebase xuống
        rootNode = FirebaseDatabase.getInstance();
        userref = rootNode.getReference("User").child(iduser);
        Cursor cursor = db.rawQuery("Select * from User where ID_User = ?", new String[]{iduser});
        if (cursor.getCount() > 0) {
            //Cập Nhật User từ FireBase
            //TH1: Đã có dữ liệu ở SQLite
            userref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    user = (Map<String, String>) dataSnapshot.getValue();
                    diem = (Map<String, Long>) dataSnapshot.getValue();

                    db = openHelper.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("HoTen", user.get("hoTen"));

                    int Point = diem.get("point").intValue();
                    contentValues.put("Point", Point);
                    contentValues.put("SDT", user.get("sdt"));
                    //db.rawQuery("Select * from User where ID_User = ?", new String[]{iduser});
                    db.update("User", contentValues, "ID_User = ?", new String[]{iduser});
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        } else {
            //Cập Nhật User từ FireBase
            //TH2: Chưa có dữ liệu ở SQLite
            rootNode = FirebaseDatabase.getInstance();
            userref = rootNode.getReference("User").child(iduser);

            userref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    user = (Map<String, String>) dataSnapshot.getValue();
                    diem = (Map<String, Long>) dataSnapshot.getValue();

                    db = openHelper.getWritableDatabase();

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("ID_User", iduser);
                    contentValues.put("HoTen", user.get("hoTen"));
                    int Point = diem.get("point").intValue();
                    contentValues.put("Point", Point);
                    contentValues.put("Email", user.get("email"));
                    contentValues.put("SDT", user.get("sdt"));
                    db.insert("User", null, contentValues);
                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }
    }

    public Boolean capnhatthongtin(String iduser, String hoten, String sdt) {
        rootNode = FirebaseDatabase.getInstance();
        userref = rootNode.getReference("User").child(iduser);

        userref.child("hoTen").setValue(hoten);
        userref.child("sdt").setValue(sdt);


        db = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("HoTen", hoten);
        contentValues.put("SDT", sdt);
        Cursor cursor = db.rawQuery("Select * from User where ID_User = ?", new String[]{iduser});
        if (cursor.getCount() > 0) {
            long result = db.update("User", contentValues, "ID_User = ?", new String[]{iduser});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean capnhatdiem(String iduser, int Point, int PointPlus) {

        //Cập Nhật User lên FireBase
        rootNode = FirebaseDatabase.getInstance();
        userref = rootNode.getReference("User").child(iduser);
        userref.child("point").setValue(Point + PointPlus);

        //Cập nhật dữ liệu lên SQLite
        db = openHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Point", Point + PointPlus);
        Cursor cursor = db.rawQuery("Select * from User where ID_User = ?", new String[]{iduser});
        if (cursor.getCount() > 0) {
            long result = db.update("User", contentValues, "ID_User = ?", new String[]{iduser});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


}
