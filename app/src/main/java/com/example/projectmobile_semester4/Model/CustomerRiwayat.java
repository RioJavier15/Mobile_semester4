package com.example.projectmobile_semester4.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmobile_semester4.R;

import java.util.List;

public class CustomerRiwayat {
    private String nameProduct;
    private String subscribeDate;
    private String speed;
    private String price;

    public CustomerRiwayat(String nameProduct, String subscribeDate, String speed, String price) {
        this.nameProduct = nameProduct;
        this.subscribeDate = subscribeDate;
        this.speed = speed;
        this.price = price;

    }

    public String getNameProduct() {
        return nameProduct;
    }

    public String getSubscribeDate() {
        return subscribeDate;
    }

    public String getSpeed() {
        return speed;
    }

    public String getPrice() {
        return price;
    }


}
