package com.tubes.mgolem.SQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DBHelper {

    public UserDAO(Context context) {
        super(context);
    }

    public List<String> getUser(){
            List<String> user = new ArrayList<>();
            String sql = "SELECT * FROM pengguna WHERE id='1'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(sql, null);
            String username;
            String password;
            String status;
            if(cursor.moveToFirst()){
                username=cursor.getString(cursor.getColumnIndex("username"));
                password=cursor.getString(cursor.getColumnIndex("password"));
                status=cursor.getString(cursor.getColumnIndex("status"));
                user.add(username);
                user.add(password);
                user.add(status);
            }else{
                return null;
            }
            return user;
    }

    public void setUser(String username, String password, String status){
        String sql="INSERT INTO pengguna VALUES('1', '"+ username +"', '"+password+"', '"+status+"' );";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(sql);
    }

    public void ubahPassword(String password){
        String sql="UPDATE pengguna SET password='"+password+"' where id='1' ";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(sql);
    }

    public void deleteUser(){
        String sql="DELETE FROM pengguna WHERE id='1'";
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(sql);
    }



}
