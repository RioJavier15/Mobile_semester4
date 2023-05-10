package com.example.projectmobile_semester4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisUser extends AppCompatActivity implements View.OnClickListener{

    private Button btnSignUp;
    private TextView txtLoginDisini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis_user);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
        txtLoginDisini = findViewById(R.id.txtLogindisini);
        txtLoginDisini.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
//            case R.id.btnSignUp:
//                Intent daftar = new Intent(this, .class);
//                startActivity(user);
//                break;
            case R.id.txtLogindisini:
                Intent Login = new Intent(this,LoginPelanggan.class);
                startActivity(Login);
                break;
    }
}}