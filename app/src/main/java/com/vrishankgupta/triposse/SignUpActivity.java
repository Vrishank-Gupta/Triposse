package com.vrishankgupta.triposse;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.vrishankgupta.triposse.util.Dob;
import com.vrishankgupta.triposse.util.User;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class SignUpActivity extends AppCompatActivity {

    private int REQUEST_CAMERA = 0 , SELECT_FILE =1;
    private String userChoosenTask;

    private String imageURL;

    private android.support.v7.widget.Toolbar mToolbar;
    private Button btnSignUp;
    private ImageView ivProfImage;
    private TextView tvChangeProfImage;
    EditText userName,password,dob,country,number,email,address,passport;
    String jsonUser,token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        ivProfImage = findViewById(R.id.ivProfileImage);
        tvChangeProfImage = findViewById(R.id.tvAddProfImage);

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

                int perm = ContextCompat.checkSelfPermission(
                        SignUpActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE);

                if (perm == PackageManager.PERMISSION_GRANTED) {
                    checkValidity();
                    }

                    else {
                    ActivityCompat.requestPermissions(
                            SignUpActivity.this,
                            new String[] {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE},
                            44
                    );
                }



//                checkValidity();
            }
        });

        tvChangeProfImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
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

    @SuppressLint("MissingPermission")
    private void registerUser()
    {
        String date = dob.getText().toString().substring(0,1);
        String month = dob.getText().toString().substring(2,3);
        String year = dob.getText().toString().substring(4);

        Dob dob = new Dob(date,month,year);
        TelephonyManager tel = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        String imei;
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            imei=tel.getImei();
        }
        else
        {
            imei=tel.getDeviceId();
        }

        User user;
        user = new User(
                country.getText().toString(),
                password.getText().toString(),
                address.getText().toString(),
                dob,
                passport.getText().toString(),
                email.getText().toString(),
                number.getText().toString(),
                userName.getText().toString(),
                imei,
                "Hhhhhhhhhhh"
        );

        jsonUser = new Gson().toJson(user);

        Log.d("Results ", "registerUser: " + jsonUser);
//        Log.d("imei", "registerUser: " + imei);
//        Log.d("imgurl", "registerUser: " + imageURL);

        new RegisterTask().execute();
    }


    class RegisterTask extends AsyncTask<String,Void,String>
    {
        protected void onPreExecute(){
            Log.e("Pehle", "onPreExecute: ");
        }

        protected String doInBackground(String... arg0) {

            try {

                URL url = new URL("http://192.168.1.5:3000/users/register");
                JSONObject postDataParams = new JSONObject(jsonUser);
                Log.d("Hola", "registerUser:"+postDataParams.toString());
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
            // TODO: 18/07/18 Debug postexecute 
//            Toast.makeText(getApplicationContext(), result,
//                    Toast.LENGTH_LONG).show();
//            Log.d("Results", "Register "+result);

            try {

                JSONObject ans = new JSONObject(result);
                if(ans.getString("success").equals("true"))
                {
                    token = ans.getString("token");
                    Log.d("hogya", "onPostExecute: ");
                    saveandcontinue();
                }

                else {
                    Toast.makeText(SignUpActivity.this, "SignUp Failed", Toast.LENGTH_SHORT).show();
                    Log.d("nhihua", "onPostExecute: ");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void saveandcontinue()
    {
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preference), MODE_PRIVATE).edit();
        editor.putString("token",token);
        editor.apply();

        SharedPreferences preferences = getSharedPreferences(getString(R.string.preference),MODE_PRIVATE);
        String restoredText = preferences.getString("token", null);
        Log.d("pref", "saveandcontinue: "+ restoredText);

        startActivity(new Intent(SignUpActivity.this,MainActivity.class));

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

    //-------------------------SHIVAM'S NEW CODE ---------------------------------------------------  iske neeche for image to string

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
        builder.setTitle("Choose Photo!");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(SignUpActivity.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask="Choose from Library";
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public static class Utility {
        public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public static boolean checkPermission(final Context context)
        {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
            {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission necessary");
                        alertBuilder.setMessage("External storage permission is necessary");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    } else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == 44) { //write request
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkValidity();
            }
        }
        else if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
            Toast.makeText(SignUpActivity.this, "Go to Settings and Grant the permission to use this feature.", Toast.LENGTH_SHORT).show();
        }
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null)
        {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ivProfImage.setImageBitmap(bm);

        imageURL = BitMapToString(bm);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ivProfImage.setImageBitmap(thumbnail);
        imageURL = BitMapToString(thumbnail);
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}
