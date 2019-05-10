package com.tubes.mgolem.entitas;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class Peminjaman {
    private String id_pinjam;
    //@SerializedName("nim")
    private String nim;
    private String username_verifpinjam;
    private String username_verifkembali;
    //@SerializedName("nama_kegiatan")
    private String nama_kegiatan;
    //@SerializedName("tgl_pinjam")
    private Date tgl_pinjam;
    private Date tgl_kembali;
    //@SerializedName("tgl_kembali")
    private String status;
    //@SerializedName("barangList")
    private ArrayList<Barang> barangList = new ArrayList<>();


    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getId_pinjam() {
        return id_pinjam;
    }

    public void setId_pinjam(String id_pinjam) {
        this.id_pinjam = id_pinjam;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public ArrayList<Barang> getBarangList() {
        return barangList;
    }

    public void setBarangList(ArrayList<Barang> barangList) {
        this.barangList = barangList;
    }

    public String getNama_kegiatan() {
        return nama_kegiatan;
    }

    public void setNama_kegiatan(String nama_kegiatan) {
        this.nama_kegiatan = nama_kegiatan;
    }

    public Date getTgl_pinjam() {
        return tgl_pinjam;
    }

    public void setTgl_pinjam(Date tgl_pinjam) {
        this.tgl_pinjam = tgl_pinjam;
    }

    public Date getTgl_kembali() {
        return tgl_kembali;
    }

    public void setTgl_kembali(Date tgl_kembali) {
        this.tgl_kembali = tgl_kembali;
    }

    public String getUsername_verifpinjam() {
        return username_verifpinjam;
    }

    public void setUsername_verifpinjam(String username_verifpinjam) {
        this.username_verifpinjam = username_verifpinjam;
    }

    public String getUsername_verifkembali() {
        return username_verifkembali;
    }

    public void setUsername_verifkembali(String username_verifkembali) {
        this.username_verifkembali = username_verifkembali;
    }
}
