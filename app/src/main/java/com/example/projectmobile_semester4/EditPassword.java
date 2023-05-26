package com.example.projectmobile_semester4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class EditPassword extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    EditText editTextPassword, editTextConfirmPassword;
    Button btnSave;
    private Button btnKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        editTextPassword = findViewById(R.id.txtPassEdit);
        editTextConfirmPassword = findViewById(R.id.txtPassConfirmEdit);
        btnSave = findViewById(R.id.btnSavePass);
        btnKembali = findViewById(R.id.btnKembaliGantiPassword);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
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

    private void updatePassword() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        String id = sharedPreferences.getString("id", "");
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        // Validasi kolom tidak boleh kosong
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Harap lengkapi semua kolom", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        // Validasi konfirmasi password
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Konfirmasi password tidak cocok", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        StringRequest request = new StringRequest(Request.Method.POST, apiConfig.EDITPASS_ENDPOINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(EditPassword.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        // Intent ke halaman lain setelah berhasil mengedit Profil
                        Intent intent = new Intent(EditPassword.this, BottomNav.class);
                        intent.putExtra("selectedFragment", "profil"); // Menambahkan data tambahan
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditPassword.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
