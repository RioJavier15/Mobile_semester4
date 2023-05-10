package com.example.projectmobile_semester4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PelangganAdapter extends ArrayAdapter<Pelanggan> {

    public PelangganAdapter(Context context, ArrayList<Pelanggan> pelanggan) {
        super(context, 0, pelanggan);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pelanggan pelanggan = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView tvNama = (TextView) convertView.findViewById(R.id.listName);
        TextView tvEmail = (TextView) convertView.findViewById(R.id.alamat);
        TextView tvAlamat = (TextView) convertView.findViewById(R.id.listTime);

        tvNama.setText(pelanggan.getNama_pelanggan());
        tvEmail.setText(pelanggan.getEmail_pelanggan());
        tvAlamat.setText(pelanggan.getStatus());

        return convertView;
    }
}

