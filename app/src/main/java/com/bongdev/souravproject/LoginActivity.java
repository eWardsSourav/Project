package com.bongdev.souravproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button btn;
    DatabaseHelper databaseHelper;
    boolean isLoginSuccess = false;
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn = findViewById(R.id.btn);
        register = findViewById(R.id.registertv);
        databaseHelper = new DatabaseHelper(this);
        
        btn.setOnClickListener(v -> {
            if (email.getText().toString().length()==0 && password.getText().toString().length()==0){
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }
            isLoginSuccess =  databaseHelper.checkLogin(email.getText().toString(),password.getText().toString());
            if (isLoginSuccess){
                Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show();
                SharedPreferences userData = getSharedPreferences("souravproject",MODE_PRIVATE);
                SharedPreferences.Editor editor = userData.edit();
                editor.putBoolean("isLogin",true);
                editor.commit();

                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(intent);

            }
            else {
                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
            }
        });

        register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);

        });
        





    }
}