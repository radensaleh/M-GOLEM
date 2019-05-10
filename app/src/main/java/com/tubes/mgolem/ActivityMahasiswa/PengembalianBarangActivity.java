package com.tubes.mgolem.ActivityMahasiswa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tubes.mgolem.Adapter.AdapterPeminjaman;
import com.tubes.mgolem.Adapter.AdapterPengembalian;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengembalian_barang);

        recyclerView=findViewById(R.id.recyclerPeminjaman);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        Call<List<Peminjaman>> call = RetrofitClient.getInstance().baseAPI().getPeminjaman("2");
        call.enqueue(new Callback<List<Peminjaman>>() {
            @Override
            public void onResponse(Call<List<Peminjaman>> call, Response<List<Peminjaman>> response) {
                List<Peminjaman> listPeminjaman = response.body();

                AdapterPengembalian adapterPengembalian = new AdapterPengembalian(listPeminjaman, PengembalianBarangActivity.this);
                recyclerView.setAdapter(adapterPengembalian);
                adapterPengembalian.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Peminjaman>> call, Throwable t) {

            }
        });

    }
}
