package com.tubes.mgolem.entitas;

public class Teknisi {
    private String username;
    private String nama;
    private String password;

    public void login (String username, String password){

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
