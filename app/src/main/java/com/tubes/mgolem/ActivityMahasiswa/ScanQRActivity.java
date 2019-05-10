package com.tubes.mgolem.ActivityMahasiswa;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.tubes.mgolem.R;
import com.tubes.mgolem.Rest.RetrofitClient;
import com.tubes.mgolem.entitas.Barang;
import com.tubes.mgolem.entitas.Mahasiswa;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanQRActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(final Result rawResult) {
        Log.v("TAG", rawResult.getText()); // Prints scan results
        Log.v("TAG", rawResult.getBarcodeFormat().toString());


        mScannerView.stopCamera();
        setContentView(R.layout.activity_scan_qr);
        final android.support.v7.app.AlertDialog.Builder dialog;
        LayoutInflater inflater;
        View dialogView;

        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView=inflater.inflate(R.layout.activity_data_barang, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Data Barang");

        final TextView tvIdBarang, tvTipe, tvKategori, tvMerk;
        final EditText etKuantitas;

        tvIdBarang=dialogView.findViewById(R.id.tvIdBarang);
        tvKategori=dialogView.findViewById(R.id.tvKategoriBarang);
        tvMerk=dialogView.findViewById(R.id.tvMerkBarang);
        tvTipe=dialogView.findViewById(R.id.tvTipe);
        etKuantitas=dialogView.findViewById(R.id.etKuantitas);

        Mahasiswa mhs = Mahasiswa.getInstance();
        boolean eq =false;
        for(int i=0;i<mhs.getPeminjaman().getBarangList().size();i++){
            if(rawResult.getText().equals(mhs.getPeminjaman().getBarangList().get(i).getId_barang())){
                eq =true;
                break;
            }
        }

        if(eq){
            new android.app.AlertDialog.Builder(ScanQRActivity.this)
                    .setTitle("Gagal")
                    .setMessage("Data barang sudah dimasukan")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
        }else {
            Call<Barang> call = RetrofitClient.getInstance().baseAPI().getBarang(rawResult.getText());
            call.enqueue(new Callback<Barang>() {
                @Override
                public void onResponse(Call<Barang> call, final Response<Barang> response) {
                    if (response.body().getTipe() != null) {
                        tvIdBarang.setText(rawResult.getText());
                        tvTipe.setText(response.body().getTipe());
                        tvKategori.setText(response.body().getKategori());
                        tvMerk.setText(response.body().getMerk());

                        dialog.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Mahasiswa mhs = Mahasiswa.getInstance();
                                Barang barang = new Barang();
                                String kuantitas = etKuantitas.getText().toString();
                                if (kuantitas.isEmpty()) {
                                    etKuantitas.setError("Kuantitas tidak boleh kosong");
                                }
                                barang.setKuantitas(Integer.parseInt(kuantitas));
                                barang.setId_barang(rawResult.getText());
                                barang.setKategori(response.body().getKategori());
                                barang.setTipe(response.body().getTipe());
                                barang.setMerk(response.body().getTipe());
                                mhs.tambahBarang(barang);
                                finish();
                            }
                        });

                        dialog.setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        dialog.show();
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<Barang> call, Throwable t) {
                    Toast.makeText(ScanQRActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
            });
        }

        //finish();


    }
}
