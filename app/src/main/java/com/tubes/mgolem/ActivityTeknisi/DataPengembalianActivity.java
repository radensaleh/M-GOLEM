package com.tubes.mgolem.ActivityTeknisi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.tubes.mgolem.R;
import com.tubes.mgolem.entitas.Teknisi;

public class DataPengembalianActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private LayoutInflater inflater;
    public static String MENU;

    private ProgressBar pb;
    private TextView tvKosong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pengembalian);

        recyclerView = findViewById(R.id.recyclerPeminjaman);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        tvKosong = findViewById(R.id.tvKosong);
        pb = findViewById(R.id.pb);

        Sprite spkit = new CubeGrid();
        pb.setIndeterminateDrawable(spkit);

        Teknisi teknisi = Teknisi.getInstance();

        String menu = getIntent().getStringExtra(MENU);

        if(menu.equals("VerifPengembalian")){
            setTitle("Verifikasi Pengembalian");
            teknisi.lihatPeminjaman("3", this, recyclerView, pb, tvKosong);
        }else if(menu.equals("VerifPeminjaman")){
            setTitle("Verifikasi Peminjaman");
            teknisi.lihatPeminjaman("1", this ,recyclerView, pb, tvKosong);
        }else if(menu.equals("DataPeminjaman")){
            setTitle("Data Peminjaman");
            teknisi.lihatPeminjaman("2", this, recyclerView, pb, tvKosong);
        }else{
            setTitle("Data Pengembalian");
            teknisi.lihatPeminjaman("0", this, recyclerView, pb, tvKosong);
        }


    }
}
