package com.tubes.mgolem.entitas;

import java.util.ArrayList;

public class Mahasiswa {
    private String nim;
    private String nama;
    private String kelas;
    private String password;
    private Peminjaman peminjaman;
    private ArrayList<Barang> listBarang;

    public void login(String nim, String password){

    }

    public void pinjamBarang(){
        peminjaman = new Peminjaman();
    }

    public void ubahPassword(String password){

    }

    public void tambahBarang(Barang barang){
        listBarang.add(barang);
    }
}
