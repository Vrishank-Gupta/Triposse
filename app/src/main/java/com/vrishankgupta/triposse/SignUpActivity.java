package com.vrishankgupta.triposse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

public class SignUpActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar mToolbar;
    private Button btnSignUp;
    EditText userName,password,dob,country,number,email,address,passport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mToolbar = findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnSignUp = findViewById(R.id.btnSignup);
        userName = findViewById(R.id.Login_Name);
        password = findViewById(R.id.password);
        dob = findViewById(R.id.Login_DOB);
        country = findViewById(R.id.Login_Country);
        number = findViewById(R.id.Login_Contactnumber);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this , MainActivity.class);
                startActivity(i);
            }
        });
    }
}
