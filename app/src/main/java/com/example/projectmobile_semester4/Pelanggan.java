package com.example.projectmobile_semester4;

import java.io.Serializable;

public class Pelanggan implements Serializable {
    private String kode_pelanggan;
    private String nama_pelanggan;
    private String email_pelanggan;
    private String password;
    private String nomer_hp;
    private String status;
    private String tanggal_berlangganan;
    private String kode_produk;
    private String nama_produk;
    private String kecepatan;
    private String harga_produk;
    private String bandwith;

    public Pelanggan(String kode_pelanggan, String nama_pelanggan, String email_pelanggan, String password, String nomer_hp, String status, String tanggal_berlangganan, String kode_produk, String nama_produk, String kecepatan, String harga_produk, String bandwith) {
        this.kode_pelanggan = kode_pelanggan;
        this.nama_pelanggan = nama_pelanggan;
        this.email_pelanggan = email_pelanggan;
        this.password = password;
        this.nomer_hp = nomer_hp;
        this.status = status;
        this.tanggal_berlangganan = tanggal_berlangganan;
        this.kode_produk = kode_produk;
        this.nama_produk = nama_produk;
        this.kecepatan = kecepatan;
        this.harga_produk = harga_produk;
        this.bandwith = bandwith;
    }

    public String getKode_pelanggan() {
        return kode_pelanggan;
    }

    public String getNama_pelanggan() {
        return nama_pelanggan;
    }

    public String getEmail_pelanggan() {
        return email_pelanggan;
    }

    public String getPassword() {
        return password;
    }

    public String getNomer_hp() {
        return nomer_hp;
    }

    public String getStatus() {
        return status;
    }

    public String getTanggal_berlangganan() {
        return tanggal_berlangganan;
    }

    public String getKode_produk() {
        return kode_produk;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public String getKecepatan() {
        return kecepatan;
    }

    public String getHarga_produk() {
        return harga_produk;
    }

    public String getBandwith() {
        return bandwith;
    }
}
