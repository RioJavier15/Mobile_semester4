package com.example.projectmobile_semester4.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmobile_semester4.EditPassword;
import com.example.projectmobile_semester4.EditProfil;
import com.example.projectmobile_semester4.LoginPelanggan;
import com.example.projectmobile_semester4.LoginPertama;
import com.example.projectmobile_semester4.MainActivity2;
import com.example.projectmobile_semester4.R;

public class Profil extends Fragment {
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        sharedPreferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String firstInitial = String.valueOf(name.charAt(0));
        TextView nameTextView = view.findViewById(R.id.namaUserLogin);
        TextView nameAwal = view.findViewById(R.id.hurufAwal);
        nameTextView.setText(name);
        nameAwal.setText(firstInitial);
        // Inflate the layout for this fragment
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnLogOut = view.findViewById(R.id.btnLogOut);
        Button btnEdit = view.findViewById(R.id.btnEditProfil);
        Button btnEditPass = view.findViewById(R.id.btnGantiPassword);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutDialog();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfil.class);
                startActivity(intent);
            }
        });

        btnEditPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditPassword.class);
                startActivity(intent);
            }
        });
    }
    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout");
        builder.setMessage("Apakah Anda yakin ingin logout?");
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Arahkan ke halaman login
                Intent intent = new Intent(getActivity(), LoginPelanggan.class);
                startActivity(intent);
                getActivity().finish(); // Tutup aktivitas BottomNav agar pengguna tidak dapat kembali ke halaman ini setelah logout

                Toast.makeText(getActivity(), "Logged out", Toast.LENGTH_SHORT).show();
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
