package com.tubes.mgolem.entitas;

public class Barang {
    private String idBarang;
    private String kategori;
    private String tipe;
    private String merk;

    public Barang(String idBarang, String kategori) {
        this.idBarang = idBarang;
        this.kategori = kategori;
    }

    public String getIdBarang() {
        return idBarang;
    }

    public String getKategori() {
        return kategori;
    }
}
