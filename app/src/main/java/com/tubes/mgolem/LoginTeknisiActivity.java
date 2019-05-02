package com.tubes.mgolem;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tubes.mgolem.entitas.Teknisi;


public class LoginTeknisiActivity extends AppCompatActivity {
    private Dialog alertDialog;
    private Button btnYa, btnTidak, btnMasuk;
    private EditText etUsername, etPass;
    private Context context;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_teknisi);
        context = LoginTeknisiActivity.this;

        pd = new ProgressDialog(context);

        etUsername=findViewById(R.id.etUsername);
        etPass=findViewById(R.id.etPasswordTeknisi);
        btnMasuk=findViewById(R.id.btnMasukTeknisi);

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String pass = etPass.getText().toString();

                if(username.isEmpty()){
                    etUsername.setError("Username Kosong");
                }else if(username.length() < 8){
                    etUsername.setError("Minimal 8 karakter");
                }else if(pass.isEmpty()){
                    etPass.setError("Password Kosong");
                }else if(pass.length()< 8){
                    etPass.setError("Minimal 8 karakter");
                }else{
                    Teknisi teknisi = Teknisi.getInstance();
                    teknisi.login(username, pass, context);
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(context, PilihLogin.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
