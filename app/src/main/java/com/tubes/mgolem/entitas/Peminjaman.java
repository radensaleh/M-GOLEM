package com.tubes.mgolem.entitas;

import java.util.ArrayList;
import java.util.Date;

public class Peminjaman {
    private String idPeminjaman;
    private Date tglPinjam;
    private Date tglKembali;
    private String status;
    private ArrayList<Barang> barangList = new ArrayList<>();

    public void addBarang(Barang barang){
        this.barangList.add(barang);

    }

    public ArrayList<Barang> showBarang(){
        return barangList;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }



    public static void main(String[] args) {
        Peminjaman peminjaman = new Peminjaman();
        Barang barang = new Barang("id", "kategori");

        peminjaman.addBarang(barang);

        System.out.println(peminjaman.showBarang().get(0).getIdBarang());

        peminjaman.setStatus("Dikembalikan");

        System.out.println(peminjaman.getStatus());
    }
}
