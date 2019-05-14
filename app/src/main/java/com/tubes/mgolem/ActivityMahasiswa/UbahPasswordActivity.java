package com.tubes.mgolem.ActivityMahasiswa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tubes.mgolem.R;
import com.tubes.mgolem.entitas.Mahasiswa;
import com.tubes.mgolem.entitas.Teknisi;

public class UbahPasswordActivity extends AppCompatActivity {
    EditText etPassLama, etPassBaru, etVerifPass;
    Button btnUbahPass;
    public static String status;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);

        final String st=getIntent().getStringExtra(status);
        mContext = this;

        etPassLama=findViewById(R.id.etPasswordLama);
        etPassBaru=findViewById(R.id.etPassBaru);
        etVerifPass=findViewById(R.id.etVerifPass);
        btnUbahPass=findViewById(R.id.btnUbahPassword);

        btnUbahPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passLama = etPassLama.getText().toString();
                String passBaru = etPassBaru.getText().toString();
                String verifPass = etVerifPass.getText().toString();

                if(passLama.isEmpty()){
                    //etPassLama.setError("Tidak boleh kosong!");
                    new android.support.v7.app.AlertDialog.Builder(mContext)
                            .setIcon(R.drawable.warning)
                            .setTitle("Peringatan")
                            .setMessage("Password Lama Tidak Boleh Kosong")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }else if(passBaru.isEmpty()){
                    //etPassBaru.setError("Tidak boleh kosong!");
                    new android.support.v7.app.AlertDialog.Builder(mContext)
                            .setIcon(R.drawable.warning)
                            .setTitle("Peringatan")
                            .setMessage("Password Baru Tidak Boleh Kosong")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }else if(verifPass.isEmpty()){
                    //etVerifPass.setError("Tidak boleh kosong!");
                    new android.support.v7.app.AlertDialog.Builder(mContext)
                            .setIcon(R.drawable.warning)
                            .setTitle("Peringatan")
                            .setMessage("Password Verifikasi Tidak Boleh Kosong")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }else if(!verifPass.equals(passBaru)){
                    //etVerifPass.setError("Password tidak sesuai!");
                    new android.support.v7.app.AlertDialog.Builder(mContext)
                            .setIcon(R.drawable.warning)
                            .setTitle("Peringatan")
                            .setMessage("Password Tidak Sesuai")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }else{
                    //Toast.makeText(UbahPasswordActivity.this, st, Toast.LENGTH_SHORT).show();
                    if(st.equals("teknisi")){
                        Teknisi teknisi = Teknisi.getInstance();
                        if(!passLama.equals(teknisi.getPassword())){
                            new AlertDialog.Builder(UbahPasswordActivity.this).setTitle("Gagal").setMessage("Password Lama Salah!").setCancelable(false).setIcon(R.drawable.failed)
                                    .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }).show();
                        }else{
                            teknisi.ubahPassword(passBaru, UbahPasswordActivity.this);
                        }
                    }else{
                        Mahasiswa mhs = Mahasiswa.getInstance();
                        if(!passLama.equals(mhs.getPassword())){
                            new AlertDialog.Builder(UbahPasswordActivity.this).setTitle("Gagal").setMessage("Password Lama Salah!").setCancelable(false).setIcon(R.drawable.failed)
                                    .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }).show();
                        }else{
                            mhs.ubahPassword(passLama, passBaru, UbahPasswordActivity.this);
                        }
                    }

                }
            }
        });
    }
}
