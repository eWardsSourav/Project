package com.bongdev.souravproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.niwattep.materialslidedatepicker.SlideDatePickerDialog;
import com.niwattep.materialslidedatepicker.SlideDatePickerDialogCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SlideDatePickerDialogCallback {

    EditText first_name,last_name,dob,email,mobile,password,confirm_pass;
    Spinner spinner;
    TextView textView;
    Button button;
    CheckBox checkBoxFlutter,checkBoxKotlin,checkBoxJava,checkBoxAndroidSDK,checkBoxSwift,checkBoxGit,checkBoxNodeJs;
    String[] genderList = { "Male", "Female", "Transgender"};
    DatabaseHelper databaseHelper;
    UserDataModel userDataModel = new UserDataModel();
    String GENDER = "";
    String skills = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        first_name = findViewById(R.id.firstnametv);
        last_name = findViewById(R.id.lastnametv);
        textView = findViewById(R.id.textView2);
        dob = findViewById(R.id.dob);
        email = findViewById(R.id.emailet);
        mobile = findViewById(R.id.mobet);
        password = findViewById(R.id.password);
        confirm_pass = findViewById(R.id.editTextConfirmPassword);
        spinner = findViewById(R.id.gender);
        button = findViewById(R.id.btn);
        checkBoxFlutter = findViewById(R.id.checkBoxFlutter);
        checkBoxKotlin = findViewById(R.id.checkBoxKotlin);
        checkBoxJava = findViewById(R.id.checkBoxJava);
        checkBoxAndroidSDK = findViewById(R.id.checkBoxAndroidSDK);
        checkBoxSwift = findViewById(R.id.checkBoxSwift);
        checkBoxGit = findViewById(R.id.checkBoxGit);
        checkBoxNodeJs = findViewById(R.id.checkBoxNodeJs);

        databaseHelper = new DatabaseHelper(this);


        spinner.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,genderList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        Date td = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(td);


        if (getIntent().getBooleanExtra("isFromHome",false)){
            setValue();
        }


        dob.setOnClickListener(view -> {
            showDOBDatePicker("1919-01-01",formattedDate);
        });

        button.setOnClickListener(v -> {


            if (first_name.getText().toString().length()==0){
                Toast.makeText(this, "Enter first name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (last_name.getText().toString().length()==0){
                Toast.makeText(this, "Enter last name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dob.getText().toString().length()==0){
                Toast.makeText(this, "Select DOB", Toast.LENGTH_SHORT).show();
                return;
            }

            if (checkBoxFlutter.isChecked() || checkBoxKotlin.isChecked() || checkBoxJava.isChecked() || checkBoxAndroidSDK.isChecked() || checkBoxSwift.isChecked() || checkBoxGit.isChecked() || checkBoxNodeJs.isChecked() ){
               if (checkBoxFlutter.isChecked()){
                   skills  += checkBoxFlutter.getText() + ", ";
               }if (checkBoxKotlin.isChecked()){
                   skills  += checkBoxKotlin.getText() + ", ";
               }if (checkBoxJava.isChecked()){
                   skills  += checkBoxJava.getText() + ", ";
               }if (checkBoxAndroidSDK.isChecked()){
                   skills  += checkBoxAndroidSDK.getText() + ", ";
               }if (checkBoxSwift.isChecked()){
                   skills  += checkBoxSwift.getText() + ", ";
               }if (checkBoxGit.isChecked()){
                   skills  += checkBoxGit.getText() + ", ";
               }if (checkBoxNodeJs.isChecked()){
                   skills  += checkBoxNodeJs.getText() + ", ";
               }

                if (!skills.isEmpty()) {
                    skills = skills.substring(0, skills.length() - 2);
                }
            }
            else {
                Toast.makeText(this, "Select Skills", Toast.LENGTH_SHORT).show();
                return;
            }

            if (email.getText().toString().length()==0){
                Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (mobile.getText().toString().length()==0){
                Toast.makeText(this, "Enter mobile number", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.getText().toString().length()==0){
                Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
                return;
            }
            if (email.getText().toString().length()==0){
                Toast.makeText(this, "Enter confirm password", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.getText().toString().equals(confirm_pass.getText().toString())){
                Toast.makeText(this, "paassword and confirm password not match", Toast.LENGTH_SHORT).show();
                return;
            }

            if (getIntent().getBooleanExtra("isFromHome",false)){
                boolean isUpdate = databaseHelper.addUpdate(skills,email.getText().toString(),password.getText().toString(),dob.getText().toString());

                if (isUpdate) {
                    Toast.makeText(MainActivity.this, "successfully Update", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                }



            }

            else {

                boolean isDataSaved =   databaseHelper.addRegistration(first_name.getText().toString(),last_name.getText().toString(),dob.getText().toString(),GENDER,skills,email.getText().toString(),mobile.getText().toString(),password.getText().toString());

                if (isDataSaved) {
                    Toast.makeText(MainActivity.this, "successfully registration", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                }

            }














        });
    }

    private void setValue() {


        SharedPreferences userData = getSharedPreferences("souravproject",MODE_PRIVATE);
        userDataModel =   databaseHelper.getuserData(userData.getString("email",""),userData.getString("password",""));
        button.setText("Update");
        first_name.setText(userDataModel.firstname);
        first_name.setEnabled(false);
        last_name.setText(userDataModel.lastname);
        last_name.setEnabled(false);
        dob.setText(userDataModel.dob);

        textView.setVisibility(View.INVISIBLE);

        if (userDataModel.skilles.contains("Flutter")){
            checkBoxFlutter.setChecked(true);
        }if (userDataModel.skilles.contains("Kotlin")){
            checkBoxKotlin.setChecked(true);
        }if (userDataModel.skilles.contains("Java")){
            checkBoxJava.setChecked(true);
        }if (userDataModel.skilles.contains("Android SDK")){
            checkBoxAndroidSDK.setChecked(true);
        }if (userDataModel.skilles.contains("Swift")){
            checkBoxSwift.setChecked(true);
        }if (userDataModel.skilles.contains("GIT")){
            checkBoxGit.setChecked(true);
        }if (userDataModel.skilles.contains("NodeJs")){
            checkBoxNodeJs.setChecked(true);
        }
        email.setText(userDataModel.email);
        email.setEnabled(false);
        mobile.setText(userDataModel.mobile);
        mobile.setEnabled(false);
        password.setText(userDataModel.password);
        confirm_pass.setText(userDataModel.password);




    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        GENDER = genderList[i];



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void showDOBDatePicker(String start, String initial) {
        Calendar endDate = Calendar.getInstance();
//        endDate.set(Calendar.YEAR, 2200);
        SlideDatePickerDialog.Builder builder = new SlideDatePickerDialog.Builder();
        builder.setThemeColor(Color.BLACK);
        builder.setEndDate(endDate);
        SlideDatePickerDialog dialog = builder.build();
        dialog.show(getSupportFragmentManager(), "Dialog");
    }


    @Override
    public void onPositiveClick(int i, int i1, int i2, @NonNull Calendar calendar) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(calendar.getTime());
        dob.setText(formattedDate);
    }
}