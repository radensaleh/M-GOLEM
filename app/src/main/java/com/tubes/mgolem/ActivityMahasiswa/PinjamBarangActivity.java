package com.tubes.mgolem.ActivityMahasiswa;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.tubes.mgolem.R;
import com.tubes.mgolem.entitas.Mahasiswa;
import com.tubes.mgolem.entitas.Peminjaman;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PinjamBarangActivity extends AppCompatActivity {
    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View dialogView;
    Button btnTglPinjam, btnTglKembali, btnTambahBarang;
    TextView tvTglPinjamDialog, tvTglKembaliDialog, tvNim, tvTglPinjam, tvTglKembali, tvNamaKegiatan;
    EditText etKegiatan;
    private int mYear, mMonth, mDay;
    Peminjaman peminjaman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinjam_barang);
        peminjaman=new Peminjaman();
        Mahasiswa mhs = Mahasiswa.getInstance();
        peminjaman.setNim(mhs.getNim());

        tvNim=findViewById(R.id.tvNimPeminjam);
        tvTglKembali=findViewById(R.id.tvTglKembali);
        tvTglPinjam=findViewById(R.id.tvTglPinjam);
        tvNamaKegiatan=findViewById(R.id.tvNamaKegiatan);
        btnTambahBarang=findViewById(R.id.btnTambahBarang);

        dialog = new AlertDialog.Builder(PinjamBarangActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_form_pinjam_barang, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);
        dialog.setTitle("Form Pinjam Barang");

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
                                Date tglPinjam= new Date(year,monthOfYear+1,dayOfMonth);
                                tvTglPinjamDialog.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                peminjaman.setTgl_pinjam(tglPinjam);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btnTglKembali.setOnClickListener(new View.OnClickListener() {
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
                                Date tglKembali= new Date(year,monthOfYear+1,dayOfMonth);
                                tvTglKembaliDialog.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                peminjaman.setTgl_kembali(tglKembali);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        dialog.setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvNim.setText(peminjaman.getNim());
                Date datePinjam = peminjaman.getTgl_pinjam();
                String tglpinjam = new SimpleDateFormat("dd-MM-yyyy").format(datePinjam);
                tvTglPinjam.setText(tglpinjam);
                Date dateKembali = peminjaman.getTgl_kembali();
                String tglKembali = new SimpleDateFormat("dd-MM-yyyy").format(dateKembali);
                tvTglKembali.setText(tglKembali);
                String namaKegiatan = etKegiatan.getText().toString();
                peminjaman.setNama_kegiatan(namaKegiatan);
                tvNamaKegiatan.setText(peminjaman.getNama_kegiatan());
            }
        });
        dialog.setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final AlertDialog alert = dialog.create();
        alert.show();

        btnTambahBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }
}
