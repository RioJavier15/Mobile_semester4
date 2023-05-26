package com.example.projectmobile_semester4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EditProfil extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Button btnKembali;
    EditText editTextEmail,editTextName,editTextAddress,editTextPhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        String name = sharedPreferences.getString("name", "");
        String address = sharedPreferences.getString("address", "");
        String phone_number = sharedPreferences.getString("phone_number", "");

        editTextEmail = findViewById(R.id.txtEmailEdit);
        editTextName = findViewById(R.id.txtUsernameEdit);
        editTextAddress = findViewById(R.id.txtAlamatEdit);
        editTextPhoneNumber = findViewById(R.id.txtNohpEdit);
        btnKembali = findViewById(R.id.btnKembali);

        editTextEmail.setText(email);
        editTextName.setText(name);
        editTextAddress.setText(address);
        editTextPhoneNumber.setText(phone_number);
        Button btnSave = findViewById(R.id.btnsave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfil();
            }
        });
        // Menambahkan onClickListener untuk tombol kembali
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Mengakhiri activity dan kembali ke activity sebelumnya
            }
        });
    }

    private void updateProfil() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        String id = sharedPreferences.getString("id", "");
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();

        // Validasi kolom tidak boleh kosong
        if (name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Harap lengkapi semua kolom", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        // Validasi email
        if (!isValidEmail(email)) {
            Toast.makeText(this, "Email tidak valid", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        // Validasi panjang nama minimal 8 karakter
        if (name.length() < 8) {
            Toast.makeText(this, "Nama harus memiliki minimal 8 karakter", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        // Validasi nomor telepon tidak lebih dari 13 digit
        if (phoneNumber.length() > 13) {
            Toast.makeText(this, "Nomor telepon tidak valid", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        StringRequest request = new StringRequest(Request.Method.POST, apiConfig.EditPROFIL_ENDPOINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(EditProfil.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        // Memperbarui nilai Shared Preferences setelah sukses memperbarui profil
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", name);
                        editor.putString("email", email);
                        editor.putString("phone_number", phoneNumber);
                        editor.putString("address", address);
                        editor.apply();
                        // Intent ke halaman lain setelah berhasil mengedit Profil
                        Intent intent = new Intent(EditProfil.this, BottomNav.class);
                        intent.putExtra("selectedFragment", "profil"); // Menambahkan data tambahan
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditProfil.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("name", name);
                params.put("email", email);
                params.put("phone_number", phoneNumber);
                params.put("address", address);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private boolean isValidEmail(String email) {
        // Implementasi validasi email sesuai kebutuhan Anda
        // Misalnya, menggunakan regex atau library validasi email
        // Contoh sederhana menggunakan regex:
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

}