package com.example.projectmobile_semester4;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity2 extends AppCompatActivity {
    ActivityMain2Binding binding;
    private static final String ENDPOINT_URL = "https://6067-2001-448a-5122-3243-25e6-6b96-9b47-4dc.ngrok-free.app/api/customers-with-product";

    private ListView listView;
    private ArrayList<Pelanggan> pelangganArrayList;
    private PelangganAdapter adapter,adapter1;
    TextView textView;
    private EditText etFilter;
    private ArrayList<Pelanggan> filteredPelangganArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        filteredPelangganArrayList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listView = findViewById(R.id.listview);
        pelangganArrayList = new ArrayList<>();
        adapter = new PelangganAdapter(this, pelangganArrayList);
        listView.setAdapter(adapter);

        String email = getIntent().getStringExtra("email");
        String name = getIntent().getStringExtra("name");
        String idTeknisi = getIntent().getStringExtra("idTeknisi");
        String address = getIntent().getStringExtra("address");



        // Set item click listener for the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Retrieve the clicked Pelanggan object from filteredPelangganArrayList
                Pelanggan clickedPelanggan = filteredPelangganArrayList.get(position);
                // Start the detail activity and pass the clickedPelanggan as an extra
                Intent intent = new Intent(MainActivity2.this, DetailActivity.class);
                intent.putExtra("pelanggan", clickedPelanggan);
                intent.putExtra("idTeknisi", idTeknisi);
                startActivity(intent);
            }
        });
        startFetchingData();

    }

    private void filterData(String filterAddress) {
        filteredPelangganArrayList.clear();

        for (Pelanggan pelanggan : pelangganArrayList) {
            if (pelanggan.getAddress().toLowerCase().contains(filterAddress.toLowerCase())) {
                filteredPelangganArrayList.add(pelanggan);
            }
        }

        adapter = new PelangganAdapter(this, filteredPelangganArrayList);
        listView.setAdapter(adapter);
    }

    private void fetchData() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ENDPOINT_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pelangganArrayList.clear(); // Kosongkan arraylist sebelum mengisi data baru

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
                        String address = getIntent().getStringExtra("address");
                        filterData(address);
                        adapter.notifyDataSetChanged(); // Beritahu adapter bahwa data telah berubah
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(request);
    }
    private void startFetchingData() {
        // Buat objek Timer untuk menjadwalkan pengambilan data setiap 5 detik
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                fetchData(); // Ambil data dari server
            }
        }, 0, 2000); // Ambil data setiap 5 detik (5000 ms)
    }
    private Pelanggan parsePelanggan(JSONObject jsonObject) throws JSONException {
        // Parse the JSON object and create a new Pelanggan object
        int id = jsonObject.getInt("id");
        String name = jsonObject.getString("name");
        String username = jsonObject.getString("username");
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        String phoneNumber = jsonObject.getString("phone_number");
        String status = jsonObject.getString("status");
        String address = jsonObject.getString("address");
        String subscribeDate = jsonObject.getString("subcribe_date");
        int productId = jsonObject.getInt("id_product");
        String productName = jsonObject.getString("name_product");
        String speed = jsonObject.getString("speed");
        String price = jsonObject.getString("price");
        String bandwidth = jsonObject.getString("bandwith");

        return new Pelanggan(id, name, username, email, password, phoneNumber, status, address, subscribeDate,
                productId, productName, speed, price, bandwidth);
    }

}