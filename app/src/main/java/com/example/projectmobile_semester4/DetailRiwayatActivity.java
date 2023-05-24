package com.example.projectmobile_semester4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailRiwayatActivity extends AppCompatActivity {
    private TextView txtNameProduct;
    private TextView txtSubscribeDate;
    private TextView txtSpeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat);
        // Inisialisasi TextView
        txtNameProduct = findViewById(R.id.txt_nama_produk);
        txtSubscribeDate = findViewById(R.id.txt_tanggal_berlangganan);
        txtSpeed = findViewById(R.id.txt_kecepatan);


        // Mendapatkan data yang diterima melalui Intent
        Intent intent = getIntent();
        String nameProduct = intent.getStringExtra("nameProduct");
        String subscribeDate = intent.getStringExtra("subscribeDate");
        String speed = intent.getStringExtra("speed");


        // Mengatur nilai TextView dengan data yang diterima
        txtNameProduct.setText(nameProduct);
        txtSubscribeDate.setText(subscribeDate);
        txtSpeed.setText(speed);

    }
}