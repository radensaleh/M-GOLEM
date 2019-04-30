package com.tubes.mgolem.entitas;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tubes.mgolem.Adapter.AdapterPeminjaman;
import com.tubes.mgolem.MenuMhsActivity;
import com.tubes.mgolem.MenuTeknisiActivity;
import com.tubes.mgolem.R;
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
    
    public void login (final String username, final String password, final Context context){
        this.username=username;
        this.password=password;

        Runnable rn = new Runnable() {
            @Override
            public void run() {
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

                                new AlertDialog.Builder(context).setTitle("Login berhasil").setMessage("Selamat Datang "+response.body().getNama()).setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(context, MenuTeknisiActivity.class);
                                                intent.putExtra("username", response.body().getUsername());
                                                intent.putExtra("nama", response.body().getNama());
                                                context.startActivity(intent);
                                            }
                                        }).show();
                            }else{
                                Intent intent = new Intent(context, MenuTeknisiActivity.class);
                                context.startActivity(intent);
                            }

                }else if(response.body().getErrorRes().equals("1")) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
        };

        Handler handler = new Handler();
        handler.postDelayed(rn, 2000);

    }

    public void login (final String username, final String password, final Context context, final ProgressDialog pd){
        pd.setIcon(R.drawable.login);
        pd.setTitle("Masuk");
        pd.setMessage("Harap Menunggu. . .");
        pd.setCancelable(false);
        pd.show();

        this.username=username;
        this.password=password;

        Runnable rn = new Runnable() {
            @Override
            public void run() {
                Call<Response> call = RetrofitClient.getInstance().baseAPI().login(username,password);
                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, final retrofit2.Response<Response> response) {
                        if(response.body().getErrorRes().equals("0")){
                            pd.dismiss();
                            Teknisi teknisi=Teknisi.getInstance();
                            teknisi.setNama(response.body().getNama());

                            UserDAO user = new UserDAO(context);
                            if(user.getUser()==null){
                                pd.dismiss();
                                user.setUser(teknisi.getUsername(), teknisi.getPassword(), "1");

                                new AlertDialog.Builder(context).setIcon(R.drawable.success).setTitle("Login berhasil").setMessage("Selamat datang "+response.body().getUsername()).setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(context, MenuTeknisiActivity.class);
                                                context.startActivity(intent);
                                            }
                                        }).show();
                            }else{
                                pd.dismiss();
                                Intent intent = new Intent(context, MenuTeknisiActivity.class);
                                context.startActivity(intent);
                            }

                        }else if(response.body().getErrorRes().equals("1")) {
                            pd.dismiss();
                            //Toast.makeText(context, response.body().getMessageRes().getUsername()[0], Toast.LENGTH_SHORT).show();
                                new AlertDialog.Builder(context).setIcon(R.drawable.failed).setTitle("Login Gagal").setMessage(response.body().getMessage()).setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        }).show();
                        }else if(response.body().getErrorRes().equals("2")){
                            pd.dismiss();
                            new AlertDialog.Builder(context).setIcon(R.drawable.failed).setTitle("Login Gagal").setMessage(response.body().getMessage()).setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        pd.dismiss();
                        Toast.makeText(context,t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(rn, 2000);

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

    public void verifikasiPeminjaman(Peminjaman peminjaman, final Context context){
        Call<Response> call = RetrofitClient.getInstance().baseAPI().verifikasi(peminjaman.getId_pinjam(), this.username);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.body().getErrorRes().equals("0")){
                    new AlertDialog.Builder(context).setTitle("Verifikasi Berhasil").setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(context, MenuTeknisiActivity.class );
                                    context.startActivity(intent);
                                    ((Activity) context).finish();
                                }
                            }).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });

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
