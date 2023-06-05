package com.bongdev.souravproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE registration (id INTEGER PRIMARY KEY, firstname TEXT, lastname TEXT, dob TEXT, gender TEXT, skilles TEXT, email TEXT, mobile TEXT, password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean addRegistration(String firstname, String lastname,String dob, String gender,String skilles, String email,String mobile, String password ){
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname",firstname);
        contentValues.put("lastname",lastname);
        contentValues.put("dob",dob);
        contentValues.put("gender",gender);
        contentValues.put("skilles",skilles);
        contentValues.put("email",email);
        contentValues.put("mobile",mobile);
        contentValues.put("password",password);

        SQLiteDatabase db = this.getWritableDatabase();

        long rowId = db.insert("registration",null,contentValues);

        return rowId != -1;

    }

    public boolean checkLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {"email"};
        String selection = "email = ? AND password = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query("registration", projection, selection, selectionArgs, null, null, null);

        boolean isLoginValid = cursor.moveToFirst();
        cursor.close();

        return isLoginValid;
    }

    public UserDataModel getuserData(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {"id","firstname", "lastname","dob","gender","skilles","email","mobile","password"};
        String selection = "email = ? AND password = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query("registration", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            UserDataModel registrationModel = new UserDataModel();
            registrationModel.id = cursor.getInt(0);
            registrationModel.firstname = cursor.getString(1);
            registrationModel.lastname = cursor.getString(2);
            registrationModel.dob = cursor.getString(3);
            registrationModel.gender = cursor.getString(4);
            registrationModel.skilles = cursor.getString(5);
            registrationModel.email = cursor.getString(6);
            registrationModel.mobile = cursor.getString(7);
            registrationModel.password = cursor.getString(8);

            cursor.close();
            return registrationModel;
        } else {
            cursor.close();
            return null;
        }
    }






}
