package com.example.projectmobile_semester4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginPelanggan extends AppCompatActivity implements View.OnClickListener {
    EditText ed_email,ed_password;

    String str_email,str_password;
    String url = apiConfig.LOGINPELANGGAN_ENDPOINT;
    private Button signIn;
    private TextView daftar;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pelanggan);

//        signIn = (Button) findViewById(R.id.btnSignIn);
//        signIn.setOnClickListener(this);
        daftar = (TextView) findViewById(R.id.txtDaftar);
        daftar.setOnClickListener(this);
        ed_email = findViewById(R.id.txtUser);
        ed_password = findViewById(R.id.txtPassword);

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
//        checkLoginStatus();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){

            case R.id.txtDaftar:
                Intent daftar = new Intent(this,RegisUser.class);
                startActivity(daftar);
                break;
        }

    }



    public void Login2(View view) {

        if(ed_email.getText().toString().equals("")){
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
        }
        else if(ed_password.getText().toString().equals("")){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        }
        else{


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait..");

            progressDialog.show();

            str_email = ed_email.getText().toString().trim();
            str_password = ed_password.getText().toString().trim();


            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");

                        if(success){
                            String status = jsonObject.getString("status");
                            String name = jsonObject.getString("name");
                            String id = jsonObject.getString("id");
                            String name_product = jsonObject.getString("name_product");
                            String email = jsonObject.getString("email");
                            String address = jsonObject.getString("address");
                            String phone_number = jsonObject.getString("phone_number");
                            String subcribe_date = jsonObject.getString("subcribe_date");

                            sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                            // Simpan data ke SharedPreferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("status", status);
                            editor.putString("name", name);
                            editor.putString("id", id);
                            editor.putString("name_product", name_product);
                            editor.putString("email", email);
                            editor.putString("address", address);
                            editor.putString("phone_number", phone_number);
                            editor.putString("subcribe_date", subcribe_date);
                            editor.putBoolean("isLoggedIn", true); // Setelah login berhasil, simpan status login
                            editor.apply();


                            ed_email.setText("");
                            ed_password.setText("");
                            Intent intent = new Intent(LoginPelanggan.this, BottomNav.class);
                            startActivity(intent);
                            Toast.makeText(LoginPelanggan.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(LoginPelanggan.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginPelanggan.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", str_email);
                    params.put("password", str_password);
                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(LoginPelanggan.this);
            requestQueue.add(request);




        }
    }


    private void checkLoginStatus() {
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            // Pengguna sudah login sebelumnya, arahkan ke halaman beranda
            Intent intent = new Intent(LoginPelanggan.this, BottomNav.class);

            startActivity(intent);
            finish();
        }
    }
    public void onBackPressed() {
        Intent intent = new Intent(LoginPelanggan.this, LoginPertama.class);
        startActivity(intent);
    }



}