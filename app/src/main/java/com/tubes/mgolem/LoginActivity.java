package com.tubes.mgolem;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.tubes.mgolem.entitas.Mahasiswa;
import com.tubes.mgolem.entitas.Teknisi;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private TextView tvDaftar;
    private Context mContext;
    private Dialog alertDialog;
    private Button btnYa, btnTidak, btnMasuk;
    private Spinner spLogin;
    private EditText etNim, etPassword;
    private String[] aktor = {"Mahasiswa", "Teknisi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        alertDialog = new Dialog(mContext);

        etNim      = findViewById(R.id.etNim);
        etPassword = findViewById(R.id.etPassword);
        btnMasuk   = findViewById(R.id.btnMasuk);
        spLogin    = findViewById(R.id.spinnerLogin);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, aktor);
        spLogin.setAdapter(adapter);

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nim = etNim.getText().toString();
                String pass = etPassword.getText().toString();

                if(nim.isEmpty()){
                    etNim.setError("Nim Kosong");
                }else if(nim.length() < 7){
                    etNim.setError("7 Character Minimun");
                }else if(pass.isEmpty()){
                    etPassword.setError("Password Kosong");
                }else{
                    if(spLogin.getSelectedItemPosition()==0){
                        Mahasiswa mhs = new Mahasiswa();
                        mhs.login(nim, pass);
                    }else {
                        Teknisi teknisi = new Teknisi();
                        teknisi.login(nim, pass);
                    }

                }


            }
        });

        tvDaftar = findViewById(R.id.tvDaftar);
        tvDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, DaftarActivity.class);
                startActivity(i);
                finish();
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
