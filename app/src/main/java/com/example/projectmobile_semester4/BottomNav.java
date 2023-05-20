package com.example.projectmobile_semester4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.projectmobile_semester4.Fragment.Beranda;
import com.example.projectmobile_semester4.Fragment.Profil;
import com.example.projectmobile_semester4.Fragment.Riwayat;
import com.example.projectmobile_semester4.databinding.ActivityBottomNavBinding;

public class BottomNav extends AppCompatActivity {
    ActivityBottomNavBinding binding;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBottomNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        replaceFragment(new Beranda());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.beranda:
                    replaceFragment(new Beranda());
                    break;

                case R.id.riwayat:
                    replaceFragment(new Riwayat());
                    break;

                case R.id.profil:
                    replaceFragment(new Profil());
                    break;

            }

            return true;

        });
        String id = getIntent().getStringExtra("idpelanggan");
        String name = getIntent().getStringExtra("namepelanggan");
        String status = getIntent().getStringExtra("status");
        String name_product = getIntent().getStringExtra("name_product");

        Bundle bundle = new Bundle();
        bundle.putString("idpelanggan", id);
        bundle.putString("namepelanggan", name);
        bundle.putString("status", status);
        bundle.putString("name_product", name_product);

        Fragment berandaFragment = new Beranda();
        berandaFragment.setArguments(bundle);

        replaceFragment(berandaFragment);

        // Set fragment Profil jika data tambahan "selectedFragment" bernilai "profil"
        String selectedFragment = getIntent().getStringExtra("selectedFragment");
        if (selectedFragment != null && selectedFragment.equals("profil")) {
            replaceFragment(new Profil());
            binding.bottomNavigationView.setSelectedItemId(R.id.profil);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(1);


    }



}
