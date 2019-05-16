package com.tubes.mgolem.ActivityMahasiswa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.tubes.mgolem.MenuMhsActivity;
import com.tubes.mgolem.R;
import com.tubes.mgolem.entitas.Mahasiswa;

public class DataPeminjamanVerifikasiActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public static String MENU;
    Mahasiswa mhs = Mahasiswa.getInstance();

    private ProgressBar pb;
    private TextView tvKosong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_peminjaman_verifikasi);
        String status = getIntent().getStringExtra(MENU);

        recyclerView=findViewById(R.id.recyclerPeminjaman);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        pb = findViewById(R.id.pb);
        tvKosong = findViewById(R.id.tvKosong);

        Sprite sp = new CubeGrid();
        pb.setIndeterminateDrawable(sp);

        Intent i = getIntent();
        Bundle b = i.getExtras();

        String ver = (String) b.get("Verifikasi");

        if(ver.equals("0")){
            setTitle("Data Pengembalian"); //cardview Data Pengembalian
        }else if(ver.equals("1")){
            setTitle("Verifikasi Peminjaman"); //cardview Verifikasi Peminjaman
        }else if(ver.equals("3")){
            setTitle("Verifikasi Pengembalian");//cardview Pengembalian
        }

        // lah ini ko ?
        mhs.lihatPeminjaman(status, DataPeminjamanVerifikasiActivity.this, recyclerView, pb , tvKosong);

    }
}
