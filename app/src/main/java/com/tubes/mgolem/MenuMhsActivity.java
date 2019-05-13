package com.tubes.mgolem;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tubes.mgolem.ActivityMahasiswa.DataPeminjamanVerifikasiActivity;
import com.tubes.mgolem.ActivityMahasiswa.PengembalianBarangActivity;
import com.tubes.mgolem.ActivityMahasiswa.PinjamBarangActivity;
import com.tubes.mgolem.ActivityMahasiswa.UbahPasswordActivity;
import com.tubes.mgolem.ActivityTeknisi.DataPengembalianActivity;
import com.tubes.mgolem.SQLite.UserDAO;
import com.tubes.mgolem.entitas.Mahasiswa;

public class MenuMhsActivity extends AppCompatActivity {
    Context context;
    Dialog alertDialog;
    private Button btnYa, btnTidak, btnLogout, btnEdit;
    private CardView cvPinjamBarang, cvPengembalian, cvPeminjaman, cvKembaliBarang, cvDataPengembalian;
    private UserDAO userDAO;
    private TextView tvNama, tvNim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_mhs);
        context=MenuMhsActivity.this;
        userDAO = new UserDAO(context);

        alertDialog = new Dialog(context, R.style.MyDialogTheme);

        //Activity Peminjaman
        cvPinjamBarang=findViewById(R.id.cvPinjam);
        cvPinjamBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuMhsActivity.this, PinjamBarangActivity.class);
                startActivity(intent);
            }
        });

        cvPengembalian=findViewById(R.id. cvDataKembali);
        cvPengembalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuMhsActivity.this, DataPeminjamanVerifikasiActivity.class);
                intent.putExtra(PengembalianBarangActivity.MENU, "3");
                startActivity(intent);
            }
        });

        cvPeminjaman=findViewById(R.id.cvDataPinjam);
        cvPeminjaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuMhsActivity.this, DataPeminjamanVerifikasiActivity.class);
                intent.putExtra(PengembalianBarangActivity.MENU, "1");
                startActivity(intent);
            }
        });

        cvKembaliBarang=findViewById(R.id.cvKembali);
        cvKembaliBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuMhsActivity.this, PengembalianBarangActivity.class);
                startActivity(intent);
            }
        });

        btnEdit=findViewById(R.id.btnEditInfo);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuMhsActivity.this, UbahPasswordActivity.class);
                intent.putExtra(UbahPasswordActivity.status, "mhs");
                startActivity(intent);
            }
        });

        cvDataPengembalian=findViewById(R.id.cvDataPengembalian);
        cvDataPengembalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MenuMhsActivity.this, DataPeminjamanVerifikasiActivity.class);
                intent.putExtra(PengembalianBarangActivity.MENU, "0");
                startActivity(intent);
            }
        });

        tvNim = findViewById(R.id.txtnim);
        tvNama= findViewById(R.id.txtnama);

        Mahasiswa mahasiswa = Mahasiswa.getInstance();
        tvNama.setText(mahasiswa.getNama());
        tvNim.setText("NIM. " + mahasiswa.getNim());

        btnLogout=findViewById(R.id.btnLogoutMhs);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


    }
    public void logout(){
        alertDialog.setContentView(R.layout.alert_keluar);
        btnTidak = alertDialog.findViewById(R.id.btnTidak);
        btnYa    = alertDialog.findViewById(R.id.btnYa);

        btnYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDAO.deleteUser();
                Intent intent = new Intent(context, LoginActivity.class);
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
