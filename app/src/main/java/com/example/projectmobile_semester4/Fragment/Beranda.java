package com.example.projectmobile_semester4.Fragment;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectmobile_semester4.Adapter.JenisPaketAdapter;
import com.example.projectmobile_semester4.Model.JenisPaket;
import com.example.projectmobile_semester4.R;
import com.example.projectmobile_semester4.apiConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Beranda extends Fragment {
    private RecyclerView recyclerView;
    private JenisPaketAdapter adapter;
    private List<JenisPaket> jenisPaketList;
    private RequestQueue requestQueue;
    private String url = apiConfig.BERANDA;

    public Beranda() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String id = bundle.getString("idpelanggan");
            String name = bundle.getString("namepelanggan");
            String status = bundle.getString("status");
            String name_product = bundle.getString("name_product");

            // Mendapatkan referensi TextView yang ingin diubah teksnya
            TextView nameTextView = view.findViewById(R.id.namaUser);
            TextView statusTextView = view.findViewById(R.id.statusPaket);
            TextView productTextView = view.findViewById(R.id.namaPaketBerlanggan);

            // Mengatur teks pada TextView
            nameTextView.setText(name);
            statusTextView.setText(status);
            productTextView.setText(name_product);
        }



        recyclerView = view.findViewById(R.id.listPaket);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        jenisPaketList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());

        // Mengambil data dari API menggunakan Volley
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String nameProduct = jsonObject.getString("name_product");
                                String speed = jsonObject.getString("speed");
                                String price = jsonObject.getString("price");
                                String bandwidth = jsonObject.getString("bandwith");

                                JenisPaket jenisPaket = new JenisPaket(id, nameProduct, speed, price, bandwidth);
                                jenisPaketList.add(jenisPaket);
                            }
                            adapter = new JenisPaketAdapter(jenisPaketList, getActivity());
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);

        return view;
    }
}
