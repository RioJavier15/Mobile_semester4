package com.example.projectmobile_semester4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    private TextView txtKodePelanggan, txtNamaPelanggan, txtEmailPelanggan, txtPassword, txtNomerHp, txtStatus,
            txtTanggalBerlangganan, txtKodeProduk, txtNamaProduk, txtKecepatan, txtHargaProduk, txtBandwidth;
    Button btn_Transaksi;
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
        txtKodePelanggan.setText(String.valueOf(pelanggan.getId()));
        txtNamaPelanggan.setText(pelanggan.getName());
        txtEmailPelanggan.setText(pelanggan.getEmail());
        txtPassword.setText(pelanggan.getPassword());
        txtNomerHp.setText(pelanggan.getPhoneNumber());
        txtStatus.setText(pelanggan.getStatus());
        txtTanggalBerlangganan.setText(pelanggan.getSubscribeDate());
        txtKodeProduk.setText(String.valueOf(pelanggan.getProductId()));
        txtNamaProduk.setText(pelanggan.getProductName());
        txtKecepatan.setText(pelanggan.getSpeed());
        txtHargaProduk.setText(pelanggan.getPrice());
        txtBandwidth.setText(pelanggan.getBandwidth());

        btn_Transaksi = findViewById(R.id.btnTransaksi);

        btn_Transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                insertData();
                btn_updateData();
            }
        });

    }
    private void insertData() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "https://6067-2001-448a-5122-3243-25e6-6b96-9b47-4dc.ngrok-free.app/api/transaction",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.equalsIgnoreCase("Data Inserted")){
                                Toast.makeText(DetailActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                            else{
                                Toast.makeText(DetailActivity.this, response, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<String,String>();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date date = new Date();
                    String currentDate = dateFormat.format(date);

                    String idTeknisi = getIntent().getStringExtra("idTeknisi");

                    params.put("date_transaction",currentDate);
                    params.put("total",txtHargaProduk.getText().toString().trim());
                    params.put("users",idTeknisi);
                    params.put("id_costumer",txtKodePelanggan.getText().toString().trim());



                    return params;
                }
            };


            RequestQueue requestQueue = Volley.newRequestQueue(DetailActivity.this);
            requestQueue.add(request);
    }

    public void btn_updateData() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "https://6067-2001-448a-5122-3243-25e6-6b96-9b47-4dc.ngrok-free.app/api/transactionUp",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(DetailActivity.this, response, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(DetailActivity.this, MainActivity2.class);
                        String idTeknisi = getIntent().getStringExtra("idTeknisi");
                        intent.putExtra("idTeknisi", idTeknisi);
//                        startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                        finish();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(DetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String,String>();

                params.put("id",txtKodePelanggan.getText().toString().trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DetailActivity.this);
        requestQueue.add(request);

    }
}
