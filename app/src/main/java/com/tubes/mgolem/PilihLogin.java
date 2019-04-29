package com.tubes.mgolem;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tubes.mgolem.SQLite.SlideDAO;

public class PilihLogin extends AppCompatActivity {
    Button btnLoginMhs, btnLoginTeknisi;
    private Dialog alertDialog;
    private Context mContext;
    private Button btnYa, btnTidak;
    SlideDAO slide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_login);
        mContext = this;

        slide=new SlideDAO(this);
        slide.ubahSlide();
        btnLoginMhs=findViewById(R.id.btnLoginMhs);
        btnLoginTeknisi=findViewById(R.id.btnLoginTeknisi);

        alertDialog = new Dialog(mContext);

        btnLoginMhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(PilihLogin.this, LoginActivity.class);
                startActivity(intentLogin);
            }
        });

        btnLoginTeknisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(PilihLogin.this, LoginTeknisiActivity.class);
                startActivity(intentLogin);
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
