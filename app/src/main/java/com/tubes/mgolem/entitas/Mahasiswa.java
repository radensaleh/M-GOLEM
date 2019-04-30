package com.tubes.mgolem.entitas;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.tubes.mgolem.MenuMhsActivity;
import com.tubes.mgolem.MenuTeknisiActivity;
import com.tubes.mgolem.R;
import com.tubes.mgolem.Rest.Response;
import com.tubes.mgolem.Rest.RetrofitClient;
import com.tubes.mgolem.SQLite.UserDAO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class Mahasiswa {
    private static Mahasiswa mhs;
    private Mahasiswa(){}
    private String nim;
    private String nama;
    private String kelas;
    private String password;
    private ArrayList<Peminjaman> peminjamanList;
    private ArrayList<Barang> listBarang;

    public static Mahasiswa getInstance(){
        if(mhs==null){
            mhs = new Mahasiswa();
        }
        return mhs;
    }

    public void login(String nim, String password, final Context context){
        this.nim = nim;
        this.password=password;

        Call<Response> call = RetrofitClient.getInstance().baseAPI().loginMhs(nim,password);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.body().getErrorRes().equals("0")){
                    Mahasiswa mhs = Mahasiswa.getInstance();
                    mhs.setNama(response.body().getNama());
                    mhs.setKelas(response.body().getKelas());

                    UserDAO userDAO = new UserDAO(context);
                    if(userDAO.getUser()==null){
                        userDAO.setUser(mhs.getNim(), mhs.getPassword(), "2");
                        new AlertDialog.Builder(context).setTitle("Login berhasil").setMessage("Selamat datang "+response.body().getNama()).setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(context, MenuMhsActivity.class);
                                        context.startActivity(intent);
                                    }
                                }).show();
                    }else{
                        Intent intent = new Intent(context, MenuMhsActivity.class);
                        context.startActivity(intent);
                    }
                }else if(response.body().getErrorRes().equals("1")){
                    Toast.makeText(context, response.body().getMessageRes().getNim()[0], Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(context,"Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void login(final String nim, final String password, final Context context, final ProgressDialog pd){
        pd.setIcon(R.drawable.login);
        pd.setTitle("Masuk");
        pd.setMessage("Harap Menunggu. . .");
        pd.setCancelable(false);
        pd.show();

        this.nim = nim;
        this.password=password;

        Runnable rn = new Runnable() {
            @Override
            public void run() {
                Call<Response> call = RetrofitClient.getInstance().baseAPI().loginMhs(nim,password);
                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if(response.body().getErrorRes().equals("0")){
                            pd.dismiss();
                            Mahasiswa mhs = Mahasiswa.getInstance();
                            mhs.setNama(response.body().getNama());
                            mhs.setKelas(response.body().getKelas());

                            UserDAO userDAO = new UserDAO(context);
                            if(userDAO.getUser()==null){
                                pd.dismiss();
                                userDAO.setUser(mhs.getNim(), mhs.getPassword(), "2");
                                new AlertDialog.Builder(context).setIcon(R.drawable.success).setTitle("Login Berhasil").setMessage("Selamat Datang "+response.body().getNama()).setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(context, MenuMhsActivity.class);
                                                context.startActivity(intent);
                                            }
                                        }).show();
                            }else{
                                pd.dismiss();
                                Intent intent = new Intent(context, MenuMhsActivity.class);
                                context.startActivity(intent);
                            }
                        }else if(response.body().getErrorRes().equals("1")){
                            pd.dismiss();
//                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            new AlertDialog.Builder(context).setIcon(R.drawable.failed).setTitle("Login Gagal").setMessage(response.body().getMessage()).setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();
                        }else{
                            pd.dismiss();
//                            Toast.makeText(context,response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(context,"Gagal", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(rn, 2000);


    }

    public void registrasi(String nama, String nim, String password, String kelas, final Context context){
        Mahasiswa mhs = Mahasiswa.getInstance();
        mhs.setNim(nim);
        mhs.setKelas(kelas);
        mhs.setPassword(password);
        mhs.setNama(nama);

        Call<Response> call = RetrofitClient.getInstance().baseAPI().registrasi(nama, nim, password, kelas);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.body().getErrorRes().equals("0")){
                    Mahasiswa mhs = Mahasiswa.getInstance();

                    UserDAO userDAO = new UserDAO(context);
                    if(userDAO.getUser()==null){
                        userDAO.setUser(mhs.getNim(), mhs.getPassword(), "2");
                        new AlertDialog.Builder(context).setTitle("Registrasi Berhasil").setMessage("Selamat Datang "+response.body().getNama()).setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(context, MenuMhsActivity.class);
                                        context.startActivity(intent);
                                    }
                                }).show();
                    }else{
                        Intent intent = new Intent(context, MenuMhsActivity.class);
                        context.startActivity(intent);
                    }
                }else if(response.body().getErrorRes().equals("1")){
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    public void pinjamBarang(){
        Peminjaman peminjaman = new Peminjaman();
        peminjamanList.add(peminjaman);
    }

    public void ubahPassword(String password){

    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
