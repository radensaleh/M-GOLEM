package com.tubes.mgolem.entitas;

public class Kelas {
    private String id_kelas;
    private String nama_kelas;

    public String getId_kelas() {
        return id_kelas;
    }

    public void setId_kelas(String id_kelas) {
        this.id_kelas = id_kelas;
    }

    public String getNama_kelas() {
        return nama_kelas;
    }

    public void setNama_kelas(String nama_kelas) {
        this.nama_kelas = nama_kelas;
    }

    @Override
    public String toString(){
        return nama_kelas;
    }
}
