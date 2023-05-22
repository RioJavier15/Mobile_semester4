package com.example.projectmobile_semester4.Model;

public class JenisPaket {
    private int id;
    private String namaPaket;
    private String kecepatan;
    private String hargaPaket;
    private String foto;

    public JenisPaket(int id, String namaPaket, String kecepatan, String hargaPaket, String foto) {
        this.id = id;
        this.namaPaket = namaPaket;
        this.kecepatan = kecepatan;
        this.hargaPaket = hargaPaket;
        this.foto = foto;
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

    public String getFoto() {
        return foto;
    }
}
