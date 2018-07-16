package com.vrishankgupta.triposse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
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

public class LoginActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button btnLogin;
    private TextView tvSignUp;
    private String token;
    private EditText etEmail,etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvSignUp = findViewById(R.id.tvSignUp);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        mToolbar = findViewById(R.id.main_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  = new Intent(LoginActivity.this , MainActivity.class);
                startActivity(i);
                finish();


                //validateData();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j  = new Intent(LoginActivity.this , SignUpActivity.class);
                startActivity(j);
            }
        });
    }

    private void validateData()
    {
        if(TextUtils.isEmpty(etEmail.getText()))
        {
            etEmail.setError("Required UserName");
        }

        if(TextUtils.isEmpty(etPassword.getText()))
        {
            etPassword.setError("Required Password");
        }

        else
        {
            loginUser();
        }
    }

    private void loginUser()
    {
        new AuthenticatePostTask().execute();
    }


    class AuthenticatePostTask extends AsyncTask<String,Void,String>
    {
        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://192.168.1.5:3000/users/authenticate");
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("username", etEmail.getText().toString());
                postDataParams.put("password", etPassword.getText().toString());
                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
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
            try {
                JSONObject ans = new JSONObject(result);
                if(ans.getString("success").equals("true"))
                {
                    token = ans.getString("token");
                    saveandcontinue();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveandcontinue()
    {
        SharedPreferences rem= getSharedPreferences(getString(R.string.preference), MODE_PRIVATE);
        rem.edit().remove("token").apply();


        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preference), MODE_PRIVATE).edit();
        editor.putString("token",token);
        editor.apply();

        SharedPreferences preferences = getSharedPreferences(getString(R.string.preference),MODE_PRIVATE);
        String restoredText = preferences.getString("token", null);
        Log.d("pref", "saveandcontinue: "+ restoredText);

        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
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
