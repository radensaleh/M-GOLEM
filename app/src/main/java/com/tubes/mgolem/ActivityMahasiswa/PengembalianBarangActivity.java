package com.tubes.mgolem.ActivityMahasiswa;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.tubes.mgolem.Adapter.AdapterPeminjamanMhs;
import com.tubes.mgolem.R;
import com.tubes.mgolem.Rest.RetrofitClient;
import com.tubes.mgolem.entitas.Mahasiswa;
import com.tubes.mgolem.entitas.Peminjaman;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengembalianBarangActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public static String MENU;
    Mahasiswa mhs = Mahasiswa.getInstance();

    private ProgressBar pb;
    private TextView tvKosong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengembalian_barang);

        pb = findViewById(R.id.pb);
        tvKosong = findViewById(R.id.tvKosong);

        Sprite sp = new CubeGrid();
        pb.setIndeterminateDrawable(sp);

        recyclerView=findViewById(R.id.recyclerPeminjaman);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        pengembalianBarang();

    }

    public void pengembalianBarang(){
        pb.setVisibility(View.VISIBLE);
        tvKosong.setVisibility(View.INVISIBLE);

        Runnable rn = new Runnable() {
            @Override
            public void run() {
                Call<List<Peminjaman>> call = RetrofitClient.getInstance().baseAPI().getPeminjamanMhs(mhs.getNim(), "2");
                call.enqueue(new Callback<List<Peminjaman>>() {
                    @Override
                    public void onResponse(Call<List<Peminjaman>> call, Response<List<Peminjaman>> response) {
                        if(response.body().size() == 0){
                            pb.setVisibility(View.GONE);
                            tvKosong.setVisibility(View.VISIBLE);
                        }else{
                            pb.setVisibility(View.GONE);
                            tvKosong.setVisibility(View.INVISIBLE);

                            List<Peminjaman> listPeminjaman = response.body();

                            AdapterPeminjamanMhs adapterPengembalian = new AdapterPeminjamanMhs(listPeminjaman, PengembalianBarangActivity.this);
                            recyclerView.setAdapter(adapterPengembalian);
                            adapterPengembalian.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Peminjaman>> call, Throwable t) {

                    }
                });
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(rn, 2000);
    }
}
