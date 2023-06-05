package com.bongdev.souravproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button btn;
    DatabaseHelper databaseHelper;
    boolean isLoginSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn = findViewById(R.id.btn);
        databaseHelper = new DatabaseHelper(this);
        
        btn.setOnClickListener(v -> {
            if (email.getText().toString().length()==0 && password.getText().toString().length()==0){
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }
            isLoginSuccess =  databaseHelper.checkLogin(email.getText().toString(),password.getText().toString());
            if (isLoginSuccess){
                Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
        





    }
}