package com.example.projectmobile_semester4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.projectmobile_semester4.Fragment.Beranda;
import com.example.projectmobile_semester4.Fragment.Profil;
import com.example.projectmobile_semester4.Fragment.Riwayat;
import com.example.projectmobile_semester4.databinding.ActivityBottomNavBinding;

public class BottomNav extends AppCompatActivity {
    ActivityBottomNavBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBottomNavBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
    }
