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
        TextView tvAlamat = (TextView) convertView.findViewById(R.id.alamat);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.listTime);

        tvNama.setText(pelanggan.getName());
        tvAlamat.setText(pelanggan.getAddress());
        tvStatus.setText(pelanggan.getStatus());

        return convertView;
    }

}

