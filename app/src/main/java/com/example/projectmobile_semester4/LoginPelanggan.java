package com.example.projectmobile_semester4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class LoginPelanggan extends AppCompatActivity implements View.OnClickListener{

    private Button signIn;
    private TextView daftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pelanggan);

        signIn = (Button) findViewById(R.id.btnSignIn);
        signIn.setOnClickListener(this);
        daftar = (TextView) findViewById(R.id.txtDaftar);
        daftar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
//            case R.id.btnSignIn:
//                Intent user = new Intent(this, .class);
//                startActivity(user);
//                break;
            case R.id.txtDaftar:
                Intent daftar = new Intent(this,RegisUser.class);
                startActivity(daftar);
                break;
    }
}}