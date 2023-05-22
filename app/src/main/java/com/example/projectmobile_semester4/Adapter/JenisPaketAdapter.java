package com.example.projectmobile_semester4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmobile_semester4.Model.JenisPaket;
import com.example.projectmobile_semester4.R;

import java.util.List;

public class JenisPaketAdapter extends RecyclerView.Adapter<JenisPaketAdapter.JenisPaketHolder> {
    private List<JenisPaket> jenisPaketList;
    private Context context;
    private OnItemClickListener listener;

    public JenisPaketAdapter(List<JenisPaket> jenisPaketList, Context context) {
        this.jenisPaketList = jenisPaketList;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(JenisPaket jenisPaket);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public JenisPaketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_paket, parent, false);
        return new JenisPaketHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JenisPaketHolder holder, int position) {
        JenisPaket jenisPaket = jenisPaketList.get(position);
        holder.txtNamaPaket.setText(jenisPaket.getNamaPaket());
        holder.txtKecepatan.setText(jenisPaket.getKecepatan());
        holder.txtHargaPaket.setText(jenisPaket.getHargaPaket());

        // Set click listener on item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(jenisPaket);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return jenisPaketList.size();
    }

    public static class JenisPaketHolder extends RecyclerView.ViewHolder {
        TextView txtNamaPaket, txtKecepatan, txtHargaPaket;

        public JenisPaketHolder(@NonNull View itemView) {
            super(itemView);
            txtNamaPaket = itemView.findViewById(R.id.namaPaket);
            txtKecepatan = itemView.findViewById(R.id.kecepatan);
            txtHargaPaket = itemView.findViewById(R.id.hargaPaket);
        }
    }
}
