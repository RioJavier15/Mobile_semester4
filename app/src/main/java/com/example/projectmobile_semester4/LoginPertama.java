package com.example.projectmobile_semester4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginPertama extends AppCompatActivity implements View.OnClickListener {

    private Button loginPelanggan, loginTeknisi;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pertama);

        loginPelanggan = findViewById(R.id.btnLoginUser);
        loginPelanggan.setOnClickListener(this);
        loginTeknisi = findViewById(R.id.btnLoginTeknisi);
        loginTeknisi.setOnClickListener(this);
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        checkLoginStatus();
        checkLoginStatusTeknisi();
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
    private void checkLoginStatus() {
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            // Pengguna sudah login sebelumnya, arahkan ke halaman beranda
            Intent intent = new Intent(LoginPertama.this, BottomNav.class);
            startActivity(intent);
            finish();
        }
    }
    private void checkLoginStatusTeknisi() {
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedInTeknisi", false);
        if (isLoggedIn) {
            String email = sharedPreferences.getString("emailTeknisi", "");
            String name = sharedPreferences.getString("nameTeknisi", "");
            String id = sharedPreferences.getString("idTeknisi", "");
            String address = sharedPreferences.getString("addressTeknisi", "");

            Intent intent = new Intent(LoginPertama.this, MainActivity2.class);
            intent.putExtra("email", email);
            intent.putExtra("name", name);
            intent.putExtra("idTeknisi", id);
            intent.putExtra("address", address);
            startActivity(intent);
            finish();
        }
    }
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}