package com.example.projectmobile_semester4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;

public class RegisUser extends AppCompatActivity {

    private Button btnSignUp;
    private TextView txtLoginDisini;

    EditText ed_username,ed_email,ed_password,ed_phone_number,ed_address;
    String str_name,str_email,str_password,str_phone_number,str_address;
    String url = apiConfig.REGISTER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis_user);

        ed_email = findViewById(R.id.txtEmailRegis);
        ed_username = findViewById(R.id.txtUsernameRegis);
        ed_password = findViewById(R.id.txtPassRegis);
        ed_phone_number = findViewById(R.id.txtNohp);
        ed_address = findViewById(R.id.txtPassRegis);

//        txtLoginDisini = findViewById(R.id.txtLogindisini);
//        txtLoginDisini.setOnClickListener(this);
    }

//    @Override
//    public void onClick(View view) {
//        int id = view.getId();
//        switch (id){
//            case R.id.txtLogindisini:
//                Intent Login = new Intent(this,LoginPelanggan.class);
//                startActivity(Login);
//                break;
//    }
//}
    public void Register(View view) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");


        if(ed_username.getText().toString().equals("")){
            Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
        }
        else if(ed_email.getText().toString().equals("")){
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
        }
        else if(ed_password.getText().toString().equals("")){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        }
        else if(ed_phone_number.getText().toString().equals("")){
            Toast.makeText(this, "Enter No HP", Toast.LENGTH_SHORT).show();
        }
        else if(ed_address.getText().toString().equals("")){
            Toast.makeText(this, "Enter Alamat", Toast.LENGTH_SHORT).show();
        }
        else{

            progressDialog.show();
            str_name = ed_username.getText().toString().trim();
            str_email = ed_email.getText().toString().trim();
            str_password = ed_password.getText().toString().trim();
            str_phone_number = ed_phone_number.getText().toString().trim();
            str_address = ed_address.getText().toString().trim();


            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    ed_username.setText("");
                    ed_email.setText("");
                    ed_password.setText("");
                    ed_phone_number.setText("");
                    ed_address.setText("");
                    Toast.makeText(RegisUser.this, response, Toast.LENGTH_SHORT).show();
                }
            },new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisUser.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();

                    params.put("username",str_name);
                    params.put("email",str_email);
                    params.put("password",str_password);
                    params.put("phone_number",str_phone_number);
                    params.put("address",str_address);
                    return params;

                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(RegisUser.this);
            requestQueue.add(request);


        }

    }
}