package com.vrishankgupta.triposse;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vrishankgupta.triposse.util.Dob;
import com.vrishankgupta.triposse.util.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class SignUpActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar mToolbar;
    private Button btnSignUp;
    EditText userName,password,dob,country,number,email,address,passport;
    String jsonUser,token;
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
        email = findViewById(R.id.Login_Email);
        address = findViewById(R.id.Login_Address);
        passport = findViewById(R.id.Login_Passport);



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidity();
            }
        });
    }


    private void checkValidity()
    {
        if(TextUtils.isEmpty(userName.getText()))
        {
            userName.setError("Required User name");
        }

        if(TextUtils.isEmpty(password.getText()))
        {
            password.setError("Required Password");
        }

        if(TextUtils.isEmpty(dob.getText()))
        {
            dob.setError("Required Date of Birth");
        }

        if(TextUtils.isEmpty(country.getText()))
        {
            country.setError("Required Nationality");
        }

        if(TextUtils.isEmpty(number.getText()))
        {
            number.setError("Required Contact Number");
        }

        if(TextUtils.isEmpty(email.getText()))
        {
            email.setError("Required Email-ID");
        }

        if(TextUtils.isEmpty(address.getText()))
        {
            address.setError("Required Address");
        }

        if(TextUtils.isEmpty(passport.getText()))
        {
            passport.setError("Required Passport Number");
        }


        else
        {
            registerUser();
        }
    }

    private void registerUser()
    {
        String date = dob.getText().toString().substring(0,1);
        String month = dob.getText().toString().substring(2,3);
        String year = dob.getText().toString().substring(4);

        Dob dob = new Dob(date,month,year);

        User user;
        user = new User(
                country.getText().toString(),
                password.getText().toString(),
                address.getText().toString(),
                dob,
                passport.getText().toString(),
                email.getText().toString(),
                number.getText().toString(),
                userName.getText().toString()
        );

        jsonUser = new Gson().toJson(user);

        new RegisterTask().execute();
    }


    class RegisterTask extends AsyncTask<String,Void,String>
    {
        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://192.168.1.5:3000/users/register");
                JSONObject postDataParams = new JSONObject(jsonUser);
                Log.d("Hola", "registerUser:"+postDataParams.toString());
                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setReadTimeout(150000);
//                conn.setConnectTimeout(150000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    in.close();
                    return sb.toString();
                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();
            Log.d("APICALL", result);



        }
    }

    public String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }

}
