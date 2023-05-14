package com.example.projectmobile_semester4;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginTeknisi extends AppCompatActivity {

    EditText ed_email,ed_password;

    String str_email,str_password;
    String url = "https://6067-2001-448a-5122-3243-25e6-6b96-9b47-4dc.ngrok-free.app/api/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_teknisi);

        ed_email = findViewById(R.id.txtUserTeknisi);
        ed_password = findViewById(R.id.txtPasswordTeknisi);
    }

    public void Login(View view) {

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
                            String email = jsonObject.getString("email");
                            String name = jsonObject.getString("name");
                            String id = jsonObject.getString("id");
                            String address = jsonObject.getString("address");

                            ed_email.setText("");
                            ed_password.setText("");
                            Intent intent = new Intent(LoginTeknisi.this, MainActivity2.class);
                            intent.putExtra("email", email);
                            intent.putExtra("name", name);
                            intent.putExtra("idTeknisi", id);
                            intent.putExtra("address", address);
                            startActivity(intent);
                            Toast.makeText(LoginTeknisi.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(LoginTeknisi.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginTeknisi.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
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

            RequestQueue requestQueue = Volley.newRequestQueue(LoginTeknisi.this);
            requestQueue.add(request);




        }
    }

}