package com.tubes.mgolem.ActivityTeknisi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;

import com.tubes.mgolem.R;
import com.tubes.mgolem.entitas.Teknisi;

public class DataPengembalianActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private LayoutInflater inflater;
    public static String MENU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pengembalian);

        recyclerView = findViewById(R.id.recyclerPeminjaman);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        Teknisi teknisi = Teknisi.getInstance();

        String menu = getIntent().getStringExtra(MENU);

        if(menu.equals("VerifPengembalian")){
            teknisi.lihatPeminjaman("3", this, recyclerView);
        }else if(menu.equals("VerifPeminjaman")){
            teknisi.lihatPeminjaman("1", this ,recyclerView);
        }else if(menu.equals("DataPeminjaman")){
            teknisi.lihatPeminjaman("2", this, recyclerView);
        }else{
            teknisi.lihatPeminjaman("0", this, recyclerView);
        }


    }
}
