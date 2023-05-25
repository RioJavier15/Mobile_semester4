package com.example.projectmobile_semester4.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectmobile_semester4.Adapter.ContactAdapter;
import com.example.projectmobile_semester4.Adapter.JenisPaketAdapter;
import com.example.projectmobile_semester4.Model.ContactModel;
import com.example.projectmobile_semester4.Model.CustomerRiwayat;
import com.example.projectmobile_semester4.Model.JenisPaket;
import com.example.projectmobile_semester4.R;
import com.example.projectmobile_semester4.apiConfig;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Beranda extends Fragment implements JenisPaketAdapter.OnItemClickListener {
    private RecyclerView recyclerView,recyclerViewContact;
    private JenisPaketAdapter adapter;
    private List<JenisPaket> jenisPaketList;
    private RequestQueue requestQueue;
    private String url = apiConfig.BERANDA;
    private ArrayList<ContactModel> contactList;


    public Beranda() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);

        // Mengambil data dari SharedPreferences
        String status = sharedPreferences.getString("status", "");
        String name = sharedPreferences.getString("name", "");
        String id = sharedPreferences.getString("id", "");
        String name_product = sharedPreferences.getString("name_product", "");

        // Mendapatkan referensi TextView yang ingin diubah teksnya
        TextView nameTextView = view.findViewById(R.id.namaUser);
        TextView statusTextView = view.findViewById(R.id.statusPaket);
        TextView productTextView = view.findViewById(R.id.namaPaketBerlanggan);

        // Mengatur teks pada TextView
        nameTextView.setText(name);
        statusTextView.setText(status);
        productTextView.setText(name_product);

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
                                String foto = jsonObject.getString("foto");

                                JenisPaket jenisPaket = new JenisPaket(id, nameProduct, speed, price, foto);
                                jenisPaketList.add(jenisPaket);
                            }
                            adapter = new JenisPaketAdapter(jenisPaketList, getActivity());
                            adapter.setOnItemClickListener(Beranda.this); // Menambahkan listener klik pada adapter
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


        recyclerViewContact = view.findViewById(R.id.kontak);
        recyclerViewContact.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        contactList = new ArrayList<>();
        ContactAdapter contactAdapter = new ContactAdapter(contactList, getActivity());
        recyclerViewContact.setAdapter(contactAdapter);
        contactList.add(new ContactModel(R.drawable.admin, "Admin"));
        contactList.add(new ContactModel(R.drawable.teknisi, "Teknisi"));

// Notifikasi adapter bahwa data telah berubah
        contactAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onItemClick(JenisPaket jenisPaket) {
        // Menampilkan BottomSheetLayout dan mengatur teks pada TextView harga
        View bottomSheetView = LayoutInflater.from(getActivity()).inflate(R.layout.bottomsheetlayout, null);
        TextView hargaTextView = bottomSheetView.findViewById(R.id.harga);
        hargaTextView.setText(jenisPaket.getHargaPaket());

        // Mengambil referensi ImageView dengan ID "barcode"
        ImageView barcodeImageView = bottomSheetView.findViewById(R.id.barcode);

        // Menggunakan library Picasso/Glide/Fresco/Universal Image Loader untuk memuat gambar dari URL
        Picasso.get().load(apiConfig.URL+"/storage/foto_produk/"+jenisPaket.getFoto()).into(barcodeImageView);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

        ImageView cancelButton = bottomSheetView.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
    }
}
