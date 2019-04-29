package com.tubes.mgolem;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tubes.mgolem.ActivityTeknisi.DataPengembalianActivity;
import com.tubes.mgolem.SQLite.UserDAO;
import com.tubes.mgolem.entitas.Teknisi;

public class MenuTeknisiActivity extends AppCompatActivity {
    Context context;
    Dialog alertDialog;
    private Button btnYa, btnTidak, btnLogout, btnVerifPeminjaman, btnVerifPengembalian, btnDataPeminjaman, btnDataPengembalian;
    private UserDAO  userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_teknisi);
        context=this;
        alertDialog = new Dialog(context);

        userDAO=new UserDAO(context);

        btnLogout=findViewById(R.id.btnLogoutTeknisi);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        Teknisi teknisi = Teknisi.getInstance();

        btnVerifPeminjaman  = findViewById(R.id.btnVerifPeminjaman);
        btnVerifPengembalian = findViewById(R.id.btnVerifikasiPengembalian);
        btnDataPeminjaman = findViewById(R.id.btnDataPeminjaman);
        btnDataPengembalian = findViewById(R.id.btnDataPengembalian);

        btnVerifPengembalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuTeknisiActivity.this, DataPengembalianActivity.class);
                intent.putExtra(DataPengembalianActivity.MENU, "VerifPengembalian");
                startActivity(intent);
            }
        });

        btnVerifPeminjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuTeknisiActivity.this, DataPengembalianActivity.class);
                intent.putExtra(DataPengembalianActivity.MENU, "VerifPeminjaman");
                startActivity(intent);
            }
        });

        btnDataPeminjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuTeknisiActivity.this, DataPengembalianActivity.class);
                intent.putExtra(DataPengembalianActivity.MENU, "DataPeminjaman");
                startActivity(intent);
            }
        });

        btnDataPengembalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuTeknisiActivity.this, DataPengembalianActivity.class);
                intent.putExtra(DataPengembalianActivity.MENU, "Data Pengembalian");
                startActivity(intent);
            }
        });

        Toast.makeText(context, teknisi.getNama()+" "+teknisi.getUsername(), Toast.LENGTH_SHORT).show();
    }


    public void logout(){
        alertDialog.setContentView(R.layout.alert_keluar);
        btnTidak = alertDialog.findViewById(R.id.btnTidak);
        btnYa    = alertDialog.findViewById(R.id.btnYa);

        btnYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDAO.deleteUser();
                Intent intent = new Intent(context, LoginTeknisiActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        alertDialog.setContentView(R.layout.alert_keluar);
        btnTidak = alertDialog.findViewById(R.id.btnTidak);
        btnYa    = alertDialog.findViewById(R.id.btnYa);

        btnYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                finish();
            }
        });

        btnTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}
