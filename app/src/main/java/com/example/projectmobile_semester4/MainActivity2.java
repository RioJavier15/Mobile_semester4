package com.example.projectmobile_semester4;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectmobile_semester4.Adapter.PelangganAdapter;
import com.example.projectmobile_semester4.Model.Pelanggan;
import com.example.projectmobile_semester4.databinding.ActivityMain2Binding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity2 extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    ActivityMain2Binding binding;
    private static final String ENDPOINT_URL = apiConfig.CUSTOMERS_ENDPOINT;
    SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private ArrayList<Pelanggan> pelangganArrayList;
    private PelangganAdapter adapter,adapter1;
    TextView textView;
    private EditText etFilter;
    private ArrayList<Pelanggan> filteredPelangganArrayList;
    SharedPreferences sharedPreferences;

    private BroadcastReceiver dataUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Menghandle siaran yang diterima
            if (intent.getAction().equals("com.example.projectmobile_semester4.DATA_UPDATED")) {
                // Lakukan pembaruan tampilan sesuai kebutuhan
                fetchData();
            }
        }
    };

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
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String email = sharedPreferences.getString("emailTeknisi", "");
        String name = sharedPreferences.getString("nameTeknisi", "");
        String idTeknisi = sharedPreferences.getString("idTeknisi", "");
        String address = sharedPreferences.getString("addressTeknisi", "");

        TextView addressTextView = findViewById(R.id.txtLokasiTeknisi);
        TextView nameTextView = findViewById(R.id.txtTeknisiLogin);

        addressTextView.setText(address);
        nameTextView.setText(name);


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
        fetchData();

        //logout
        Button btnLogOut = findViewById(R.id.btnLogoutTeknisi);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });

        //refresh swipe atas
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        // Daftarkan BroadcastReceiver untuk mendengar siaran (broadcast)
        IntentFilter intentFilter = new IntentFilter("com.example.projectmobile_semester4.DATA_UPDATED");
        registerReceiver(dataUpdateReceiver, intentFilter);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Hapus pendaftaran BroadcastReceiver saat Activity dihancurkan
        unregisterReceiver(dataUpdateReceiver);
    }
    @Override
    public void onRefresh() {
        fetchData();
        // Set the refreshing state to false when the data has been refreshed
        swipeRefreshLayout.setRefreshing(false);
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

    public void fetchData() {
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
    public void onBackPressed() {
        moveTaskToBack(true);


    }
    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
        builder.setTitle("Logout");
        builder.setMessage("Apakah Anda yakin ingin logout?");
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Arahkan ke halaman login
                Intent intent = new Intent(MainActivity2.this, LoginTeknisi.class);
                startActivity(intent);
                finish(); // Tutup aktivitas BottomNav agar pengguna tidak dapat kembali ke halaman ini setelah logout

                Toast.makeText(MainActivity2.this, "Logged out", Toast.LENGTH_SHORT).show();
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

}