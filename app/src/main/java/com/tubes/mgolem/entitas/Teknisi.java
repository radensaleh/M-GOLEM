package com.tubes.mgolem.entitas;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.tubes.mgolem.Adapter.AdapterPeminjaman;
import com.tubes.mgolem.MenuMhsActivity;
import com.tubes.mgolem.MenuTeknisiActivity;
import com.tubes.mgolem.Rest.Response;
import com.tubes.mgolem.Rest.RetrofitClient;
import com.tubes.mgolem.SQLite.UserDAO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Teknisi {
    private static Teknisi teknisi ;
    private Teknisi(){}
    private String username;
    private String nama;
    private String password;

    public static Teknisi getInstance(){
        if(teknisi==null){
            teknisi=new Teknisi();
        }
        return teknisi;
    }
    
    public void login (String username, String password, final Context context){
        this.username=username;
        this.password=password;

        Call<Response> call = RetrofitClient.getInstance().baseAPI().login(username,password);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, final retrofit2.Response<Response> response) {
                if(response.body().getErrorRes().equals("0")){

                    Teknisi teknisi=Teknisi.getInstance();
                    teknisi.setNama(response.body().getNama());

                    UserDAO user = new UserDAO(context);
                    if(user.getUser()==null){
                        user.setUser(teknisi.getUsername(), teknisi.getPassword(), "1");

                        new AlertDialog.Builder(context).setTitle("Login berhasil").setMessage("Selamat datang "+response.body().getUsername()).setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(context, MenuTeknisiActivity.class);
                                        context.startActivity(intent);
                                    }
                                }).show();
                    }else{
                        Intent intent = new Intent(context, MenuTeknisiActivity.class);
                        context.startActivity(intent);
                    }

                }else if(response.body().getErrorRes().equals("1")) {
                    Toast.makeText(context, response.body().getMessageRes().getUsername()[0], Toast.LENGTH_SHORT).show();
                }else if(response.body().getErrorRes().equals("2")){
                    Toast.makeText(context,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(context,"Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void lihatPeminjaman(String status, final Context context, final RecyclerView recyclerView){
        Call<List<Peminjaman>> call = RetrofitClient.getInstance().baseAPI().getPeminjaman(status);

        call.enqueue(new Callback<List<Peminjaman>>() {
            @Override
            public void onResponse(Call<List<Peminjaman>> call, retrofit2.Response<List<Peminjaman>> response) {
                List<Peminjaman> listPeminjaman = response.body();


//                if(listPeminjaman==null) {
//                    Toast.makeText(context, "data null", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(context, "sukses", Toast.LENGTH_SHORT).show();
//                }

                AdapterPeminjaman adapterPeminjaman = new AdapterPeminjaman(listPeminjaman,context);
                recyclerView.setAdapter(adapterPeminjaman);
                adapterPeminjaman.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Peminjaman>> call, Throwable t) {
                Toast.makeText(context, "Gagal" , Toast.LENGTH_SHORT).show();
            }
        });
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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
}
