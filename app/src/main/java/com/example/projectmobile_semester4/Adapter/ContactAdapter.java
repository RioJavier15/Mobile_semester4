package com.example.projectmobile_semester4.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmobile_semester4.Model.ContactModel;
import com.example.projectmobile_semester4.R;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<ContactModel> contactList;
    private Context context;

    public ContactAdapter(List<ContactModel> contactList, Context context) {
        this.contactList = contactList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactModel contact = contactList.get(position);

        holder.imgContact.setImageResource(contact.getImageResId());
        holder.txtContact.setText(contact.getContactName());

        holder.btnHubungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aksi yang ingin dilakukan saat tombol Hubungi diklik

                String phoneNumber = contact.getPhoneNumber(); // Ganti dengan nomor telepon yang sesuai dengan kontak
                String message = contact.getMessage();
                // Membuka halaman WhatsApp dengan nomor telepon yang dituju
                try {
                    Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber+"&text="+ message);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // WhatsApp tidak terinstall di perangkat
                    Toast.makeText(context, "WhatsApp tidak terinstall", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgContact;
        TextView txtContact;
        Button btnHubungi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgContact = itemView.findViewById(R.id.imgContact);
            txtContact = itemView.findViewById(R.id.txtContact);
            btnHubungi = itemView.findViewById(R.id.btnHubungi);
        }
    }
}
