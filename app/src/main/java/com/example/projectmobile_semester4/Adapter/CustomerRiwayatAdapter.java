package com.example.projectmobile_semester4.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmobile_semester4.Model.CustomerRiwayat;
import com.example.projectmobile_semester4.R;

import java.util.List;

public class CustomerRiwayatAdapter extends RecyclerView.Adapter<CustomerRiwayatAdapter.CustomerViewHolder> {
    private static List<CustomerRiwayat> customerRiwayatList;
    private static OnItemClickListener onItemClickListener;

    public CustomerRiwayatAdapter(List<CustomerRiwayat> customerRiwayatList) {
        this.customerRiwayatList = customerRiwayatList;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.riwayat_item_layout, parent, false);
        return new CustomerViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(CustomerRiwayat customerRiwayat);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        CustomerRiwayat customerRiwayat = customerRiwayatList.get(position);
        holder.textNameProduct.setText(customerRiwayat.getNameProduct());
        holder.textSubscribeDate.setText(customerRiwayat.getSubscribeDate());
        holder.textSpeed.setText(customerRiwayat.getSpeed());
    }

    @Override
    public int getItemCount() {
        return customerRiwayatList.size();
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {
        private TextView textNameProduct;
        private TextView textSubscribeDate;
        private TextView textSpeed;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            textNameProduct = itemView.findViewById(R.id.textNameProduct);
            textSubscribeDate = itemView.findViewById(R.id.textSubscribeDate);
            textSpeed = itemView.findViewById(R.id.textSpeed);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                        CustomerRiwayat customerRiwayat = customerRiwayatList.get(position);
                        onItemClickListener.onItemClick(customerRiwayat);
                    }
                }
            });
        }
    }
}
