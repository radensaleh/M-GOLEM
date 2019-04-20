package com.tubes.mgolem.entitas;

import java.util.ArrayList;

public class Mahasiswa {
    private String nim;
    private String nama;
    private String kelas;
    private String password;
    private ArrayList<Peminjaman> peminjamanList;
    private ArrayList<Barang> listBarang;

    public void login(String nim, String password){

    }

    public void pinjamBarang(){
        Peminjaman peminjaman = new Peminjaman();
        peminjamanList.add(peminjaman);
    }

    public void ubahPassword(String password){

    }

}
