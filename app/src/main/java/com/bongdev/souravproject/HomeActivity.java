package com.bongdev.souravproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView firstname,lastname,dob,currentage,gender,skills,email,mob,password;
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
        email = findViewById(R.id.skills);
        mob = findViewById(R.id.mobile);
        password = findViewById(R.id.password);

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


    }
}