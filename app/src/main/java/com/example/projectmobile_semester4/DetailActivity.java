package com.example.projectmobile_semester4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private TextView txtKodePelanggan, txtNamaPelanggan, txtEmailPelanggan, txtPassword, txtNomerHp, txtStatus,
            txtTanggalBerlangganan, txtKodeProduk, txtNamaProduk, txtKecepatan, txtHargaProduk, txtBandwidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get the Intent that started this activity
        Intent intent = getIntent();

        // Get the Pelanggan object from the Intent
        Pelanggan pelanggan = (Pelanggan) intent.getSerializableExtra("pelanggan");

        // Initialize TextViews
        txtKodePelanggan = findViewById(R.id.txt_kode_pelanggan);
        txtNamaPelanggan = findViewById(R.id.txt_nama_pelanggan);
        txtEmailPelanggan = findViewById(R.id.txt_email_pelanggan);
        txtPassword = findViewById(R.id.txt_password);
        txtNomerHp = findViewById(R.id.txt_nomer_hp);
        txtStatus = findViewById(R.id.txt_status);
        txtTanggalBerlangganan = findViewById(R.id.txt_tanggal_berlangganan);
        txtKodeProduk = findViewById(R.id.txt_kode_produk);
        txtNamaProduk = findViewById(R.id.txt_nama_produk);
        txtKecepatan = findViewById(R.id.txt_kecepatan);
        txtHargaProduk = findViewById(R.id.txt_harga_produk);
        txtBandwidth = findViewById(R.id.txt_bandwidth);

        // Set the data to TextViews
        txtKodePelanggan.setText(pelanggan.getKode_pelanggan());
        txtNamaPelanggan.setText(pelanggan.getNama_pelanggan());
        txtEmailPelanggan.setText(pelanggan.getEmail_pelanggan());
        txtPassword.setText(pelanggan.getPassword());
        txtNomerHp.setText(pelanggan.getNomer_hp());
        txtStatus.setText(pelanggan.getStatus());
        txtTanggalBerlangganan.setText(pelanggan.getTanggal_berlangganan());
        txtKodeProduk.setText(pelanggan.getKode_produk());
        txtNamaProduk.setText(pelanggan.getNama_produk());
        txtKecepatan.setText(pelanggan.getKecepatan());
        txtHargaProduk.setText(pelanggan.getHarga_produk());
        txtBandwidth.setText(pelanggan.getBandwith());
    }
}
