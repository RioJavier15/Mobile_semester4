package com.example.projectmobile_semester4;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectmobile_semester4.Model.Pelanggan;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    private TextView txtKodePelanggan, txtNamaPelanggan, txtNomerHp, txtStatus,
            txtTanggalBerlangganan, txtNamaProduk, txtKecepatan, txtHargaProduk, txtAddress,txtTot;
    Button btn_Transaksi;
    SharedPreferences sharedPreferences;
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
        txtKodePelanggan.setVisibility(View.GONE);
        txtNamaPelanggan = findViewById(R.id.txt_nama_pelanggan);
        txtNomerHp = findViewById(R.id.txt_nomer_hp);
        txtStatus = findViewById(R.id.txt_status);
        txtTanggalBerlangganan = findViewById(R.id.txt_tanggal_berlangganan);
        txtNamaProduk = findViewById(R.id.txt_nama_produk);
        txtKecepatan = findViewById(R.id.txt_kecepatan);
        txtHargaProduk = findViewById(R.id.txt_harga_produk);
        txtAddress = findViewById(R.id.txt_addressDetail);
        txtTot = findViewById(R.id.txt_total);
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String idTeknisi = sharedPreferences.getString("idTeknisi", "");
        // Set the data to TextViews
        txtKodePelanggan.setText(String.valueOf(pelanggan.getId()));
        txtNamaPelanggan.setText(pelanggan.getName());
        txtNomerHp.setText(pelanggan.getPhoneNumber());
        txtStatus.setText(pelanggan.getStatus());
        txtTanggalBerlangganan.setText(pelanggan.getSubscribeDate());
        txtNamaProduk.setText(pelanggan.getProductName());
        txtKecepatan.setText(pelanggan.getSpeed());
        txtHargaProduk.setText(pelanggan.getPrice());
        txtAddress.setText(pelanggan.getAddress());
        txtTot.setText(pelanggan.getPrice());




        btn_Transaksi = findViewById(R.id.btnTransaksi);

        btn_Transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setTitle("Bayar");
                builder.setMessage("Apakah Anda yakin ingin bayar?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        insertData();
                        btn_updateData();
                        expired(pelanggan.getId());

                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        // Tombol kembali (back button)
        Button btnBack = findViewById(R.id.btnKembaliTeknisi);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void insertData() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, apiConfig.TRANSACTION,
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

                    sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                    String idTeknisi = sharedPreferences.getString("idTeknisi", "");

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

        StringRequest request = new StringRequest(Request.Method.POST, apiConfig.TRANSACTIONUPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(DetailActivity.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent("com.example.projectmobile_semester4.DATA_UPDATED");
                        sendBroadcast(intent);
                        finish();

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
    private void expired(int pelangganId) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, apiConfig.EXPIRED,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");
                            if (message.equals("Berhasil")) {

                            } else {
                                // Proses gagal
                                Toast.makeText(DetailActivity.this, "Failed to update data", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DetailActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(DetailActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(pelangganId));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(DetailActivity.this);
        requestQueue.add(request);
    }

}
