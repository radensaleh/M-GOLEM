package com.tubes.mgolem.entitas;

public class Barang {
    private String id_barang;
    private String kategori;
    private String tipe;
    private String merk;
    private int kuantitas;

    public String getId_barang() {
        return id_barang;
    }

    public void setId_barang(String id_barang) {
        this.id_barang = id_barang;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getMerk() {
        return merk;
    }

    public int getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(int kuantitas) {
        this.kuantitas = kuantitas;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }
}
