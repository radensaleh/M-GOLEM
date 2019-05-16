package com.tubes.mgolem.ActivityTeknisi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.tubes.mgolem.R;
import com.tubes.mgolem.entitas.Teknisi;

public class DataBarangTeknisiActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_barang_teknisi);

        recyclerView = findViewById(R.id.recyclerDataBarang);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        Teknisi teknisi = Teknisi.getInstance();
        teknisi.lihatDataBarang(DataBarangTeknisiActivity.this, recyclerView);

    }
}
