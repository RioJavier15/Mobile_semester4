package com.example.projectmobile_semester4.Model;

public class JenisPaket {
    private int id;
    private String namaPaket;
    private String kecepatan;
    private String hargaPaket;
    private String bandwidth;

    public JenisPaket(int id, String namaPaket, String kecepatan, String hargaPaket, String bandwidth) {
        this.id = id;
        this.namaPaket = namaPaket;
        this.kecepatan = kecepatan;
        this.hargaPaket = hargaPaket;
        this.bandwidth = bandwidth;
    }

    public int getId() {
        return id;
    }

    public String getNamaPaket() {
        return namaPaket;
    }

    public String getKecepatan() {
        return kecepatan;
    }

    public String getHargaPaket() {
        return hargaPaket;
    }

    public String getBandwidth() {
        return bandwidth;
    }
}
