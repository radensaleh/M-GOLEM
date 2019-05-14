package com.tubes.mgolem.ActivityMahasiswa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.tubes.mgolem.R;
import com.tubes.mgolem.entitas.Mahasiswa;

public class DataPeminjamanVerifikasiActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public static String MENU;
    Mahasiswa mhs = Mahasiswa.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_peminjaman_verifikasi);
        String status = getIntent().getStringExtra(MENU);

        recyclerView=findViewById(R.id.recyclerPeminjaman);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        Toast.makeText(DataPeminjamanVerifikasiActivity.this, status, Toast.LENGTH_SHORT).show();


        if(status.equals("1")){
            mhs.lihatPeminjaman("1", DataPeminjamanVerifikasiActivity.this, recyclerView );
        }else if(status.equals("0")){
            mhs.lihatPeminjaman("0", DataPeminjamanVerifikasiActivity.this, recyclerView);
        }else{
            mhs.lihatPeminjaman("3", DataPeminjamanVerifikasiActivity.this, recyclerView );
        }

    }
}
