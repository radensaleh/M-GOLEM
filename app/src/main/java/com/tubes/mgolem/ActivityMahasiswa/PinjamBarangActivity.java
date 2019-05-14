package com.tubes.mgolem.ActivityMahasiswa;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tubes.mgolem.Adapter.AdapterBarang;
import com.tubes.mgolem.R;
import com.tubes.mgolem.Rest.Response;
import com.tubes.mgolem.Rest.RetrofitClient;
import com.tubes.mgolem.entitas.Barang;
import com.tubes.mgolem.entitas.Mahasiswa;
import com.tubes.mgolem.entitas.Peminjaman;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class PinjamBarangActivity extends AppCompatActivity {
    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View dialogView;
    Button btnTglPinjam, btnTglKembali, btnTambahBarang, btnPostBarang;
    TextView tvTglPinjamDialog, tvTglKembaliDialog, tvNim, tvNamaPeminjam, tvKelasPeminjam, tvTglPinjam, tvTglKembali, tvNamaKegiatan;
    EditText etKegiatan;
    private int mYear, mMonth, mDay;
    Peminjaman peminjaman;
    LinearLayout linearLayout;
    RecyclerView rvBarang;
    RecyclerView.LayoutManager layoutManager;
    LayoutInflater layoutInflater;

    Mahasiswa mhs = Mahasiswa.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinjam_barang);
        peminjaman=new Peminjaman();
        peminjaman.setNim(mhs.getNim());
        mhs.pinjamBarang(peminjaman);


        rvBarang=findViewById(R.id.rvBarang);
        layoutManager = new LinearLayoutManager(this);
        rvBarang.setLayoutManager(layoutManager);
        rvBarang.setHasFixedSize(true);

        AdapterBarang adapterBarang = new AdapterBarang(mhs.getPeminjaman().getBarangList() ,PinjamBarangActivity.this);
        rvBarang.setAdapter(adapterBarang);
        adapterBarang.notifyDataSetChanged();

        tvNim=findViewById(R.id.tvNimPeminjam);
        tvTglKembali=findViewById(R.id.tvTglKembali);
        tvTglPinjam=findViewById(R.id.tvTglPinjam);
        tvNamaPeminjam=findViewById(R.id.tvNamaPeminjam);
        tvKelasPeminjam=findViewById(R.id.tvKelasPeminjam);
        tvNamaKegiatan=findViewById(R.id.tvNamaKegiatan);
        btnTambahBarang=findViewById(R.id.btnTambahBarang);
        btnPostBarang=findViewById(R.id.btnPostPeminjaman);
        linearLayout=findViewById(R.id.linear);
        linearLayout.setVisibility(View.INVISIBLE);

        tvNim.setText(mhs.getNim());
        tvNamaPeminjam.setText(mhs.getNama());
        tvKelasPeminjam.setText(mhs.getKelas());

        dialog = new AlertDialog.Builder(PinjamBarangActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_form_pinjam_barang, null);
        dialog.setView(dialogView);
        //dialog.setCancelable(false);
        dialog.setTitle("Form Pinjam Barang");
        dialog.setCancelable(false);
        dialog.setIcon(R.drawable.pinjam);

            btnTglPinjam=dialogView.findViewById(R.id.btnTanggalPinjam);
            btnTglKembali=dialogView.findViewById(R.id.btnTanggalKembali);
            tvTglPinjamDialog=dialogView.findViewById(R.id.tvTAnggalPeminjaman);
            tvTglKembaliDialog=dialogView.findViewById(R.id.tvTanggalPengembalian);
            etKegiatan=dialogView.findViewById(R.id.etNamaKegiatan);

            btnTglPinjam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(PinjamBarangActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    Date tglPinjam= new Date(year-1900,monthOfYear,dayOfMonth);
                                    tvTglPinjamDialog.setText(dayOfMonth +"-"+ (monthOfYear+1) +"-"+year);
                                    peminjaman.setTgl_pinjam(tglPinjam);
                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                    datePickerDialog.show();
                }
            });

            btnTglKembali.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(peminjaman.getTgl_pinjam()==null){
                        Toast.makeText(PinjamBarangActivity.this, "Tanggal pinjam harus diisi!", Toast.LENGTH_LONG).show();
                    }else{
                        final Calendar c = Calendar.getInstance();
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(PinjamBarangActivity.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        Date tglKembali = new Date(year-1900,monthOfYear,dayOfMonth);
                                        tvTglKembaliDialog.setText(dayOfMonth +"-"+ (monthOfYear+1) +"-"+year);
                                        peminjaman.setTgl_kembali(tglKembali);
                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.getDatePicker().setMinDate(peminjaman.getTgl_pinjam().getTime());
                        datePickerDialog.show();
                    }

                }
            });

            dialog.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            dialog.setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            final AlertDialog alertDialog = dialog.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String namaKegiatan = etKegiatan.getText().toString();
                    if(namaKegiatan.isEmpty()){
                        etKegiatan.setError("Nama kegiatan harus diisi");
                    }else if(peminjaman.getTgl_pinjam()==null){
                        Toast.makeText(PinjamBarangActivity.this, "Tanggal pinjam harus diisi!", Toast.LENGTH_LONG).show();
                    }else if(peminjaman.getTgl_kembali()==null){
                        Toast.makeText(PinjamBarangActivity.this, "Tanggal kembali harus diisi!", Toast.LENGTH_LONG).show();
                    }else{
                        peminjaman.setNama_kegiatan(namaKegiatan);
                        mhs.pinjamBarang(peminjaman);

                        Date datePinjam = mhs.getPeminjaman().getTgl_pinjam();
                        String tglpinjam = new SimpleDateFormat("dd-MM-yyyy").format(datePinjam);
                        tvTglPinjam.setText(tglpinjam);
                        Date dateKembali = mhs.getPeminjaman().getTgl_kembali();
                        String tglKembali = new SimpleDateFormat("dd-MM-yyyy").format(dateKembali);
                        tvTglKembali.setText(tglKembali);
                        tvNamaKegiatan.setText(peminjaman.getNama_kegiatan());
                        linearLayout.setVisibility(View.VISIBLE);
                        alertDialog.dismiss();
                    }
                }
            });

        btnTambahBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(PinjamBarangActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED){
                    getPermissionCamera();
                }else{
                    Intent intent = new Intent(PinjamBarangActivity.this, ScanQRActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnPostBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date datePinjam = mhs.getPeminjaman().getTgl_pinjam();
                String tglpinjam = new SimpleDateFormat("yyyy/MM/dd").format(datePinjam);
                Date dateKembali = mhs.getPeminjaman().getTgl_kembali();
                String tglKembali = new SimpleDateFormat("yyyy/MM/dd").format(dateKembali);


//                final List<String> id_barang= new ArrayList<>();
//                List<Integer> kuantitas = new ArrayList<>();

                int n = mhs.getPeminjaman().getBarangList().size();
                final String[] id_barang = new String[n];
                Integer[] kuantitas = new Integer[n];

                for(int i=0;i<mhs.getPeminjaman().getBarangList().size();i++){
                    id_barang[i]=mhs.getPeminjaman().getBarangList().get(i).getId_barang();

                    kuantitas[i]=(mhs.getPeminjaman().getBarangList().get(i).getKuantitas());
                }

                Call<Response> call = RetrofitClient.getInstance().baseAPI().pinjamBarang(mhs.getNim(),
                        mhs.getPeminjaman().getNama_kegiatan(), tglpinjam , tglKembali, id_barang, kuantitas);

                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        new android.app.AlertDialog.Builder(PinjamBarangActivity.this).setTitle("Info").setMessage("Peminjaman berhasil").setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }).show();
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        new android.app.AlertDialog.Builder(PinjamBarangActivity.this).setTitle("Info").setMessage("Peminjaman barang gagal").setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                    }
                });

//
            }
        });

    }

    private void canCloseDialog(DialogInterface dialogInterface, boolean close) {
        try {
            Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");

            field.setAccessible(true);
            field.set(dialogInterface, close);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed(){
        finish();
    }


    private static final int CAMERA_PERMISSIONS_REQUEST = 1888;
    public void getPermissionCamera(){
        // 1) Pastikan ContextCompat.checkSelfPermission(...) menggunakan versi support ]
        // library karena Context.checkSelfPermission(...) hanya tersedia di Marshmallow.
        // 2) Selalu lakukan pemeriksaan meskipun permission sudah pernah diberikan
        // karena pengguna dapat mencabut permission kapan saja lewat Settings


            // Bila masuk ke blok ini artinya permission belum diberikan oleh user.
            // Periksa apakah pengguna pernah diminta permission ini dan menolaknya.
            // Jika pernah, kita akan memberikan penjelasan mengapa permission ini dibutuhkan.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.CAMERA)) {
                    // Tampilkan penjelasan mengapa aplikasi ini perlu membaca kontak disini
                    // sebelum akhirnya me-request permission dan menampilkan hasilnya
                }
            }

            // Lakukan request untuk meminta permission (menampilkan jendelanya)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        CAMERA_PERMISSIONS_REQUEST);
            }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent(PinjamBarangActivity.this, ScanQRActivity.class);
                    startActivity(intent);

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(PinjamBarangActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}


