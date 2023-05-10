package com.example.projectmobile_semester4;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectmobile_semester4.databinding.ActivityMain2Binding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    ActivityMain2Binding binding;
    private static final String ENDPOINT_URL = "https://fans-vision.wstif3b.id/API/pelanggan.php";

    private ListView listView;
    private ArrayList<Pelanggan> pelangganArrayList;
    private PelangganAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listView = findViewById(R.id.listview);
        pelangganArrayList = new ArrayList<>();
        adapter = new PelangganAdapter(this, pelangganArrayList);
        listView.setAdapter(adapter);

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ENDPOINT_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                // Parse JSON data and create Pelanggan object
                                Pelanggan pelanggan = parsePelanggan(jsonObject);
                                pelangganArrayList.add(pelanggan);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(request);

        // Set item click listener for the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the clicked Pelanggan object
                Pelanggan clickedPelanggan = pelangganArrayList.get(position);
                // Start the detail activity and pass the clickedPelanggan as an extra
                Intent intent = new Intent(MainActivity2.this, DetailActivity.class);
                intent.putExtra("pelanggan", clickedPelanggan);
                startActivity(intent);
            }
        });
    }

    private Pelanggan parsePelanggan(JSONObject jsonObject) throws JSONException {
        // Parse the JSON object and create a new Pelanggan object
        String kodePelanggan = jsonObject.getString("kode_pelanggan");
        String namaPelanggan = jsonObject.getString("nama_pelanggan");
        String emailPelanggan = jsonObject.getString("email_pelanggan");
        String password = jsonObject.getString("password");
        String nomerHp = jsonObject.getString("nomer_hp");
        String status = jsonObject.getString("status");
        String tanggalBerlangganan = jsonObject.getString("tanggal_berlangganan");
        String kodeProduk = jsonObject.getString("kode_produk");
        String namaProduk = jsonObject.getString("nama_produk");
        String kecepatan = jsonObject.getString("kecepatan");
        String hargaProduk = jsonObject.getString("harga_produk");
        String bandwith = jsonObject.getString("bandwith");

        return new Pelanggan(kodePelanggan, namaPelanggan, emailPelanggan, password, nomerHp,
                status, tanggalBerlangganan, kodeProduk, namaProduk, kecepatan, hargaProduk, bandwith);
    }
}