package com.tubes.mgolem.Rest;

import com.tubes.mgolem.entitas.Peminjaman;

import java.util.List;

public class Response {

    String errorRes;
    Message messageRes;
    String message;
    String username;
    String nama;
    String kelas;
    List<Peminjaman> peminjaman;

    public List<Peminjaman> getPeminjaman() {
        return peminjaman;
    }

    public void setPeminjaman(List<Peminjaman> peminjaman) {
        this.peminjaman = peminjaman;
    }

    public String getErrorRes() {
        return errorRes;
    }

    public void setErrorRes(String errorRes) {
        this.errorRes = errorRes;
    }

    public Message getMessageRes() {
        return messageRes;
    }

    public void setMessageRes(Message messageRes) {
        this.messageRes = messageRes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }
}
