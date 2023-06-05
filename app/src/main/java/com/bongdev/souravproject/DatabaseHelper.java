package com.bongdev.souravproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
}
