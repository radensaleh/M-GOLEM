package com.tubes.mgolem.entitas;

import java.util.ArrayList;

public class ListKelas {
    private static ListKelas kelas;

    private ArrayList<Kelas> listKelas;

    private ListKelas(){
        listKelas = new ArrayList<>();
    }

    public static ListKelas getInstance(){
        if(kelas==null){
            kelas=new ListKelas();
        }
        return kelas;
    }

    public static ListKelas getKelas() {
        return kelas;
    }

    public static void setKelas(ListKelas kelas) {
        ListKelas.kelas = kelas;
    }

    public ArrayList<Kelas> getListKelas() {
        return listKelas;
    }

    public void setListKelas(ArrayList<Kelas> listKelas) {
        this.listKelas = listKelas;
    }

    public void setKelas (Kelas kelas){
        this.listKelas.add(kelas);
    }
}
