package com.example.projectmobile_semester4.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.projectmobile_semester4.LoginPelanggan;
import com.example.projectmobile_semester4.LoginPertama;
import com.example.projectmobile_semester4.Model.ContactModel;
import com.example.projectmobile_semester4.Model.CustomerRiwayat;
import com.example.projectmobile_semester4.Model.JenisPaket;
import com.example.projectmobile_semester4.R;
import com.example.projectmobile_semester4.TransactionActivity;
import com.example.projectmobile_semester4.apiConfig;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Beranda extends Fragment implements JenisPaketAdapter.OnItemClickListener {
    private RecyclerView recyclerView,recyclerViewContact;
    private JenisPaketAdapter adapter;
    private List<JenisPaket> jenisPaketList;
    private RequestQueue requestQueue;
    private String url = apiConfig.BERANDA;
    private ArrayList<ContactModel> contactList;
    String status,name,id,name_product,subcribe_date,speed;
    Button BayarButton;
    TextView deskripsiTextView;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

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
        id = sharedPreferences.getString("id", "");
        requestQueue = Volley.newRequestQueue(getActivity());



        recyclerView = view.findViewById(R.id.listPaket);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        jenisPaketList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getActivity());




        recyclerViewContact = view.findViewById(R.id.kontak);
        recyclerViewContact.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        contactList = new ArrayList<>();
        ContactAdapter contactAdapter = new ContactAdapter(contactList, getActivity());
        recyclerViewContact.setAdapter(contactAdapter);
        contactList.add(new ContactModel(R.drawable.admin, "Admin", "+6281332499299", "Hallo min saya ingin berlangganan"));
        contactList.add(new ContactModel(R.drawable.teknisi, "Teknisi", "+6282231678985","Hallo pak saya ada kendala jaringan"));

// Notifikasi adapter bahwa data telah berubah
        contactAdapter.notifyDataSetChanged();
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
        fetchDataFromAPI();

        return view;
    }
    private void fetchDataFromAPI() {
        String url = apiConfig.URL+"/api/costumer?costumer_id="+id;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(0);

                            status = jsonObject.getString("status");
                            name = jsonObject.getString("name");
                            name_product = jsonObject.getString("name_product");
                            subcribe_date = jsonObject.getString("subcribe_date");
                            speed = jsonObject.getString("speed");

                            // Mengupdate teks pada TextView setelah mendapatkan data
                            updateTextViews();
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
    }
    private void updateTextViews() {
        View view = getView();
        if (view != null) {
            TextView nameTextView = view.findViewById(R.id.namaUser);
            TextView statusTextView = view.findViewById(R.id.statusPaket);
            TextView productTextView = view.findViewById(R.id.namaPaketBerlanggan);
            deskripsiTextView = view.findViewById(R.id.isiDeskripsi);

            // Mengatur teks pada TextView
            nameTextView.setText(name);
            statusTextView.setText(status);
            productTextView.setText(name_product);
        }
        if (status.equals("active")) {
            try {
                // Konversi tanggal subscribe_date menjadi objek Date
                Date subscribeDate = DATE_FORMAT.parse(subcribe_date);

                // Buat objek Calendar dan atur tanggalnya sebagai subscribe_date
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(subscribeDate);

                // Tambahkan 30 hari
                calendar.add(Calendar.DAY_OF_MONTH, 30);

                // Dapatkan tanggal setelah penambahan 30 hari
                Date newDate = calendar.getTime();

                // Format ulang tanggal menjadi string
                String newDateStr = DATE_FORMAT.format(newDate);
                TextView TenggattanggalTextView = getView().findViewById(R.id.batasTenggat);
                TenggattanggalTextView.setText("Sebelum tanggal " + newDateStr);

                // Mendapatkan tanggal sekarang
                Date tanggalSekarang = Calendar.getInstance().getTime();
                // Mengurangi tanggal newDate dari subscribe_date
                long differenceInMillis = newDate.getTime() - tanggalSekarang.getTime();

                // Mengubah selisih waktu menjadi jumlah hari
                int differenceInDays = (int) (differenceInMillis / (24 * 60 * 60 * 1000));

                TextView WaktuTenggatTextView = getView().findViewById(R.id.waktuTenggat);
                WaktuTenggatTextView.setText(differenceInDays + " hari lagi");

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (getView() != null) {
                deskripsiTextView.setText("Internet sangat kencang dengan kecepatan " + speed +" Mbps");
                CardView cardView = view.findViewById(R.id.cardStatus);
                cardView.setCardBackgroundColor(getResources().getColor(R.color.green));
            }

        }else if(status.equalsIgnoreCase("non active")){
            if (getView() != null) {
                deskripsiTextView.setText("Upload bukti transaksi untuk melakukan aktivasi paket internet");
                CardView cardView = getView().findViewById(R.id.cardStatus);
                cardView.setCardBackgroundColor(ContextCompat.getColor(getActivity(), R.color.red));

            }
        } else {
            if (getView() != null) {
                deskripsiTextView.setText("Mohon tunggu sedang diproses");
                CardView cardView = getView().findViewById(R.id.cardStatus);
                cardView.setCardBackgroundColor(ContextCompat.getColor(getActivity(), R.color.grey));

            }
        }



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
        BayarButton = bottomSheetView.findViewById(R.id.btnBayar);
        if (status.equals("active")) {
            BayarButton.setVisibility(View.GONE);
        } else {
            BayarButton.setVisibility(View.VISIBLE);
        }
        BayarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TransactionActivity.class);
                startActivity(intent);
//                getActivity().finish();
                bottomSheetDialog.dismiss();
            }
        });
    }
}
