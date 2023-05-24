package com.example.projectmobile_semester4.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.projectmobile_semester4.R;
import com.example.projectmobile_semester4.apiConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Riwayat extends Fragment {

    private RecyclerView recyclerView;
    private List<Customer> customerList;
    private CustomerAdapter customerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riwayat, container, false);;
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        customerList = new ArrayList<>();
        customerAdapter = new CustomerAdapter(customerList);
        recyclerView.setAdapter(customerAdapter);


        fetchDataFromAPI();
        return view;
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
                                String subscribeDate = jsonObject.getString("subcribe_date");
                                String speed = jsonObject.getString("speed");
                                String tanggal = jsonObject.getString("date_transaction");
                                String idCostumer = jsonObject.getString("id_costumer");
                                //filter riwayat berdasarkan id
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
                                String id = sharedPreferences.getString("id", "");
                                if (idCostumer.equals(id)) {
                                    Customer customer = new Customer(nameProduct, subscribeDate, speed, tanggal);
                                    customerList.add(customer);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        customerAdapter.notifyDataSetChanged();
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


    private static class Customer {
        private String nameProduct;
        private String subscribeDate;
        private String speed;

        private String tanggal;

        public Customer(String nameProduct, String subscribeDate, String speed, String tanggal) {
            this.nameProduct = nameProduct;
            this.subscribeDate = subscribeDate;
            this.speed = speed;
            this.tanggal = tanggal;

        }

        public String getNameProduct() {
            return nameProduct;
        }

        public String getSubscribeDate() {
            return subscribeDate;
        }

        public String getSpeed() {
            return speed;
        }

        public String getTanggal() {
            return tanggal;
        }
    }

    private static class CustomerViewHolder extends RecyclerView.ViewHolder {
        private TextView textNameProduct;
        private TextView textSubscribeDate;
        private TextView textSpeed;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            textNameProduct = itemView.findViewById(R.id.textNameProduct);
            textSubscribeDate = itemView.findViewById(R.id.textSubscribeDate);
            textSpeed = itemView.findViewById(R.id.textSpeed);
        }
    }

    private class CustomerAdapter extends RecyclerView.Adapter<CustomerViewHolder> {
        private List<Customer> customerList;

        public CustomerAdapter(List<Customer> customerList) {
            this.customerList = customerList;
        }

        @NonNull
        @Override
        public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.riwayat_item_layout, parent, false);
            return new CustomerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
            Customer customer = customerList.get(position);
            holder.textNameProduct.setText(customer.getNameProduct());
            holder.textSubscribeDate.setText(customer.getSubscribeDate());
            holder.textSpeed.setText(customer.getSpeed());
        }

        @Override
        public int getItemCount() {
            return customerList.size();
        }
    }
}