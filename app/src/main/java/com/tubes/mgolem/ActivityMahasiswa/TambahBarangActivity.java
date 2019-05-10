package com.tubes.mgolem.ActivityMahasiswa;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.tubes.mgolem.R;

public class TambahBarangActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_barang);

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
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent(TambahBarangActivity.this, ScanQRActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(TambahBarangActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }
}
