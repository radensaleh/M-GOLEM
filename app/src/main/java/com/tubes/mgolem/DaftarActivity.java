package com.tubes.mgolem;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.tubes.mgolem.Api.KelasAPI;
import com.tubes.mgolem.Rest.RetrofitClient;
import com.tubes.mgolem.entitas.Kelas;
import com.tubes.mgolem.entitas.ListKelas;
import com.tubes.mgolem.entitas.Mahasiswa;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarActivity extends AppCompatActivity {

    private Context mContext;
    private TextView tvMasuk;
    private Dialog alertDialog;
    private Spinner spinnerKelas;
    private Button btnYa, btnTidak, btnDaftar ;
    private EditText etNim, etNama, etPassword;
    private int kelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        mContext = this;
        tvMasuk = findViewById(R.id.tvMasuk);
        alertDialog = new Dialog(mContext);

        etNim       = findViewById(R.id.etNim);
        etNama      = findViewById(R.id.etNama);
        etPassword  = findViewById(R.id.etPassword);
        spinnerKelas = findViewById(R.id.spinnerKelas);
        btnDaftar = findViewById(R.id.btnDaftar);

        tvMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        final ListKelas listKelas = ListKelas.getInstance();
        ArrayAdapter<Kelas> adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, listKelas.getListKelas());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKelas.setAdapter(adapter);

        spinnerKelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                kelas = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mahasiswa mhs = Mahasiswa.getInstance();
                String nama = etNama.getText().toString();
                String nim = etNim.getText().toString();
                String password = etPassword.getText().toString();
                String id_kelas = listKelas.getListKelas().get(kelas).getId_kelas();
                mhs.registrasi(nama, nim, password, id_kelas, mContext);

                if(nama.isEmpty()){
                    etNama.setError("Nama Kosong");
                }else if(nim.isEmpty()){
                    etNim.setError("Nim Kosong");
                }else if(password.isEmpty()){
                    etPassword.setError("Password Kosong");
                }
            }
        });

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

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
