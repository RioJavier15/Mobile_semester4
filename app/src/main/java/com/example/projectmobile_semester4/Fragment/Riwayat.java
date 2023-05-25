package com.example.projectmobile_semester4.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectmobile_semester4.Adapter.CustomerRiwayatAdapter;
import com.example.projectmobile_semester4.DetailRiwayatActivity;
import com.example.projectmobile_semester4.Model.CustomerRiwayat;
import com.example.projectmobile_semester4.R;
import com.example.projectmobile_semester4.apiConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Riwayat extends Fragment implements CustomerRiwayatAdapter.OnItemClickListener{

    private RecyclerView recyclerView;
    private List<CustomerRiwayat> customerRiwayatList;
    private CustomerRiwayatAdapter customerRiwayatAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riwayat, container, false);;
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        customerRiwayatList = new ArrayList<>();
        customerRiwayatAdapter = new CustomerRiwayatAdapter(customerRiwayatList);
        recyclerView.setAdapter(customerRiwayatAdapter);
        customerRiwayatAdapter.setOnItemClickListener(this);

        fetchDataFromAPI();
        return view;
    }
    @Override
    public void onItemClick(CustomerRiwayat customerRiwayat) {
        // Buka halaman detail dengan menggunakan data customerRiwayat
        // Anda dapat mengganti 'DetailActivity' dengan nama activity detail yang sesuai di aplikasi Anda
        Intent intent = new Intent(getActivity(), DetailRiwayatActivity.class);
        intent.putExtra("nameProduct", customerRiwayat.getNameProduct()); // Mengirim data customerRiwayat ke halaman detail
        intent.putExtra("subscribeDate", customerRiwayat.getSubscribeDate());
        intent.putExtra("speed", customerRiwayat.getSpeed());
        intent.putExtra("price", customerRiwayat.getPrice());
        startActivity(intent);
    }
    private void fetchDataFromAPI() {
        String url = apiConfig.RIWAYAT_ENDPOINT;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String nameProduct = jsonObject.getString("name_product");
                                String subscribeDate = jsonObject.getString("date_transaction");
                                String speed = jsonObject.getString("speed");
                                String idCostumer = jsonObject.getString("id_costumer");
                                String price = jsonObject.getString("total");
                                //filter riwayat berdasarkan id
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
                                String id = sharedPreferences.getString("id", "");
                                if (idCostumer.equals(id)) {
                                    CustomerRiwayat customerRiwayat = new CustomerRiwayat(nameProduct, subscribeDate, speed, price);
                                    customerRiwayatList.add(customerRiwayat);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        customerRiwayatAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }





}