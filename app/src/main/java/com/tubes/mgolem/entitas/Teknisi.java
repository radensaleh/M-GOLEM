package com.tubes.mgolem.entitas;

public class Teknisi {
    private String idTeknisi;
    private String nama;
    private String password;

    public void login (String id_teknisi, String password){

    }

    public void verifikasipeminjaman(Peminjaman peminjaman){
        peminjaman.setStatus("Dipinjamkan");
    }

    public void verifikasiPengembalian(Peminjaman peminjaman){
        peminjaman.setStatus("Dikembalikan");
    }

    public void ubahPassword(String password){
        this.password = password;
    }
}
