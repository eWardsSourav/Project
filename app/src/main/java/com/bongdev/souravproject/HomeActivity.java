package com.bongdev.souravproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView firstname,lastname,dob,currentage,gender,skills,email,mob,password;
    ImageView imageView;
    DatabaseHelper databaseHelper;
    UserDataModel userDataModel = new UserDataModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firstname = findViewById(R.id.fname);
        lastname = findViewById(R.id.lname);
        dob = findViewById(R.id.dob);
        currentage = findViewById(R.id.cage);
        gender = findViewById(R.id.gender);
        skills = findViewById(R.id.skills);
        email = findViewById(R.id.email);
        mob = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        imageView = findViewById(R.id.imageView);

        databaseHelper = new DatabaseHelper(this);

        SharedPreferences userData = getSharedPreferences("souravproject",MODE_PRIVATE);

        userDataModel =   databaseHelper.getuserData(userData.getString("email",""),userData.getString("password",""));


        String dateOfBirthStr = userDataModel.dob;


        LocalDate dateOfBirth = null;
        LocalDate currentDate = null;
        Period period = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateOfBirth = LocalDate.parse(dateOfBirthStr, DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
            currentDate = LocalDate.now();
            period = Period.between(dateOfBirth, currentDate);
            int years = period.getYears();
            int months = period.getMonths();
            int days = period.getDays();

            currentage.setText(years+"Y "+months +"M "+days+"D");
        }





        firstname.setText(userDataModel.firstname);
        lastname.setText(userDataModel.lastname);
        dob.setText(userDataModel.dob);
        gender.setText(userDataModel.gender);
        skills.setText(userDataModel.skilles);
        email.setText(userDataModel.email);
        mob.setText(userDataModel.mobile);
        password.setText(userDataModel.password);

        imageView.setOnClickListener(v -> {
            PopUpLogOut();
        });


    }

    private void PopUpLogOut() {
        final Dialog dialog2 = new Dialog(this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.popup_logout);
        dialog2.setCancelable(false);
        TextView dismiss = (TextView) dialog2.findViewById(R.id.popup_minimize);
        TextView go = (TextView) dialog2.findViewById(R.id.popup_go_button);

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
                logOut();


            }
        });
        dialog2.show();
    }

    private void logOut() {

        SharedPreferences userData = getSharedPreferences("souravproject", MODE_PRIVATE);
        SharedPreferences.Editor editor = userData.edit();
        editor.clear();
        editor.commit();
        finish();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}