package com.example.projectmobile_semester4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginPertama extends AppCompatActivity implements View.OnClickListener {

    private Button loginPelanggan, loginTeknisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pertama);

        loginPelanggan = findViewById(R.id.btnLoginUser);
        loginPelanggan.setOnClickListener(this);
        loginTeknisi = findViewById(R.id.btnLoginTeknisi);
        loginTeknisi.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnLoginUser:
                Intent user = new Intent(this, LoginPelanggan.class);
                startActivity(user);
                break;
            case R.id.btnLoginTeknisi:
                Intent teknisi = new Intent(this,LoginTeknisi.class);
                startActivity(teknisi);
                break;
        }
    }
}