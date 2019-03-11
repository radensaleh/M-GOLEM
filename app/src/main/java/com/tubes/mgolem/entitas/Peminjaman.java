package com.tubes.mgolem.entitas;

import java.util.Date;

public class Peminjaman {
    private String idPeminjaman;
    private Date tglPinjam;
    private Date tglKembali;
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }
}
