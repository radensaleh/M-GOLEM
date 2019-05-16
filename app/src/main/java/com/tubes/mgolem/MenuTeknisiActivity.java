package com.tubes.mgolem;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tubes.mgolem.ActivityMahasiswa.UbahPasswordActivity;
import com.tubes.mgolem.ActivityTeknisi.DataBarangTeknisiActivity;
import com.tubes.mgolem.ActivityTeknisi.DataPengembalianActivity;
import com.tubes.mgolem.Adapter.AdapterBarangTeknisi;
import com.tubes.mgolem.SQLite.UserDAO;
import com.tubes.mgolem.entitas.Teknisi;

public class MenuTeknisiActivity extends AppCompatActivity {
    Context context;
    Dialog alertDialog;
    private Button btnYa, btnTidak, btnLogout, btnUbahPass;
    private CardView cvPinjam, cvKembali, cvVerifPinjam, cvVerifKembali, cvDataBarang;
    private UserDAO  userDAO;
    private TextView tvUsername, tvNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_teknisi);
        context=this;
        alertDialog = new Dialog(context);

        userDAO=new UserDAO(context);

        btnLogout=findViewById(R.id.btnLogout);

        btnUbahPass=findViewById(R.id.btnEditInfo);
        btnUbahPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuTeknisiActivity.this, UbahPasswordActivity.class);
                intent.putExtra(UbahPasswordActivity.status, "teknisi");
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        Teknisi teknisi = Teknisi.getInstance();

        tvNama = findViewById(R.id.txtnama);
        tvUsername = findViewById(R.id.txtusername);
        cvPinjam = findViewById(R.id.cvPinjam);
        cvKembali= findViewById(R.id.cvKembali);
        cvVerifPinjam = findViewById(R.id.cvVerifPinjam);
        cvVerifKembali= findViewById(R.id.cvVerifKembali);
        cvDataBarang=findViewById(R.id.cvDataBarang);


        tvUsername.setText("Username : " + teknisi.getUsername());
        tvNama.setText(teknisi.getNama());

        cvPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuTeknisiActivity.this, DataPengembalianActivity.class);
                intent.putExtra(DataPengembalianActivity.MENU, "DataPeminjaman");
                startActivity(intent);
            }
        });

        cvKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuTeknisiActivity.this, DataPengembalianActivity.class);
                intent.putExtra(DataPengembalianActivity.MENU, "Data Pengembalian");
                startActivity(intent);
            }
        });

        cvVerifPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuTeknisiActivity.this, DataPengembalianActivity.class);
                intent.putExtra(DataPengembalianActivity.MENU, "VerifPeminjaman");
                startActivity(intent);
            }
        });

        cvVerifKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuTeknisiActivity.this, DataPengembalianActivity.class);
                intent.putExtra(DataPengembalianActivity.MENU, "VerifPengembalian");
                startActivity(intent);
            }
        });

        cvDataBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuTeknisiActivity.this, DataBarangTeknisiActivity.class);
                startActivity(intent);
            }
        });

        //Toast.makeText(context, teknisi.getNama()+" "+teknisi.getUsername(), Toast.LENGTH_SHORT).show();
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
