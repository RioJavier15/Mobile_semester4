package com.example.projectmobile_semester4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailRiwayatActivity extends AppCompatActivity {
    private TextView txtNameProduct;
    private TextView txtSubscribeDate;
    private TextView txtSpeed;
    private TextView txtPrice;
    private TextView txtAddress;
    private SharedPreferences sharedPreferences;
    private Button btnKembaliRiwayat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_riwayat);
        // Inisialisasi TextView
        txtNameProduct = findViewById(R.id.txt_nama_produk);
        txtSubscribeDate = findViewById(R.id.txt_tanggal_berlangganan);
        txtSpeed = findViewById(R.id.txt_kecepatan);
        txtPrice = findViewById(R.id.txt_harga_produk);
        txtAddress = findViewById(R.id.txt_addressDetail);
        btnKembaliRiwayat = findViewById(R.id.btnKembaliRiwayat);


        // Mendapatkan data yang diterima melalui Intent
        Intent intent = getIntent();
        String nameProduct = intent.getStringExtra("nameProduct");
        String subscribeDate = intent.getStringExtra("subscribeDate");
        String speed = intent.getStringExtra("speed");
        String price = intent.getStringExtra("price");

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String address = sharedPreferences.getString("address", "");


        // Mengatur nilai TextView dengan data yang diterima
        txtNameProduct.setText(nameProduct);
        txtSubscribeDate.setText(subscribeDate);
        txtSpeed.setText(speed);
        txtPrice.setText(price);
        txtAddress.setText(address);
        // Menambahkan onClickListener untuk tombol kembali
        btnKembaliRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Mengakhiri activity dan kembali ke activity sebelumnya
            }
        });

    }

}