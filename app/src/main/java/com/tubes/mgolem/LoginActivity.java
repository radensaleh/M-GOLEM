package com.tubes.mgolem;

import android.app.Dialog;
import android.app.ProgressDialog;
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


public class LoginActivity extends AppCompatActivity {

    private TextView tvDaftar;
    private Context mContext;
    private Dialog alertDialog;
    private Button btnYa, btnTidak, btnMasuk;
    private EditText etNim, etPassword;
    private String[] aktor = {"Mahasiswa", "Teknisi"};

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        alertDialog = new Dialog(mContext);
        pd = new ProgressDialog(mContext);

        etNim      = findViewById(R.id.etNim);
        etPassword = findViewById(R.id.etPassword);
        btnMasuk   = findViewById(R.id.btnMasuk);

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nim = etNim.getText().toString();
                String pass = etPassword.getText().toString();

                if(nim.isEmpty()){
                    etNim.setError("Username Kosong");
                }else if(nim.length() < 7){
                    etNim.setError("Minimal 8 karakter");
                }else if(pass.isEmpty()){
                    etPassword.setError("Password Kosong");
                }else if(pass.length()< 8){
                    etPassword.setError("Minimal 8 karakter");
                }else{
                    Mahasiswa mhs = Mahasiswa.getInstance();
                    mhs.login(nim, pass, mContext);
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
    public void onBackPressed(){
        Intent intent = new Intent(mContext, PilihLogin.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
