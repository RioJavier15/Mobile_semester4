package com.example.projectmobile_semester4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

    private EditText ed_email, ed_password;
    private String str_email, str_password;
    private String url = apiConfig.LOGIN_ENDPOINT;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_teknisi);

        ed_email = findViewById(R.id.txtUserTeknisi);
        ed_password = findViewById(R.id.txtPasswordTeknisi);

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
//        checkLoginStatus();
    }

    public void Login(View view) {
        if (ed_email.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
        } else if (ed_password.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait..");
            progressDialog.show();

            str_email = ed_email.getText().toString().trim();
            str_password = ed_password.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");

                                if (success) {
                                    String email = jsonObject.getString("email");
                                    String name = jsonObject.getString("name");
                                    String id = jsonObject.getString("id");
                                    String address = jsonObject.getString("address");

                                    ed_email.setText("");
                                    ed_password.setText("");

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("isLoggedInTeknisi", true);
                                    editor.putString("emailTeknisi", email);
                                    editor.putString("nameTeknisi", name);
                                    editor.putString("idTeknisi", id);
                                    editor.putString("addressTeknisi", address);
                                    editor.apply();

                                    Intent intent = new Intent(LoginTeknisi.this, MainActivity2.class);
                                    intent.putExtra("email", email);
                                    intent.putExtra("name", name);
                                    intent.putExtra("idTeknisi", id);
                                    intent.putExtra("address", address);
                                    startActivity(intent);
                                    Toast.makeText(LoginTeknisi.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                } else {
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
                    Toast.makeText(LoginTeknisi.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", str_email);
                    params.put("password", str_password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(LoginTeknisi.this);
            requestQueue.add(request);
        }
    }

    private void checkLoginStatus() {
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedInTeknisi", false);
        if (isLoggedIn) {
            String email = sharedPreferences.getString("emailTeknisi", "");
            String name = sharedPreferences.getString("nameTeknisi", "");
            String id = sharedPreferences.getString("idTeknisi", "");
            String address = sharedPreferences.getString("addressTeknisi", "");

            Intent intent = new Intent(LoginTeknisi.this, MainActivity2.class);
            intent.putExtra("email", email);
            intent.putExtra("name", name);
            intent.putExtra("idTeknisi", id);
            intent.putExtra("address", address);
            startActivity(intent);
            finish();
        }

    }
    public void onBackPressed() {
        Intent intent = new Intent(LoginTeknisi.this, LoginPertama.class);
        startActivity(intent);
    }
}
