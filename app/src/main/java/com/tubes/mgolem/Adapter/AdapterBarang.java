package com.tubes.mgolem.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tubes.mgolem.Api.MahasiswaAPI;
import com.tubes.mgolem.R;
import com.tubes.mgolem.Rest.RetrofitClient;
import com.tubes.mgolem.entitas.Barang;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterBarang extends RecyclerView.Adapter<AdapterBarang.MyViewHolder> {
    Context context;
    List<Barang> barangList;

    public AdapterBarang(List<Barang> barangList, Context context){
        this.barangList=barangList;
        this.context=context;
    }

    @NonNull
    @Override
    public AdapterBarang.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_barang, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterBarang.MyViewHolder myViewHolder, final int i) {
        myViewHolder.tvIdBarang.setText(barangList.get(i).getId_barang());
        myViewHolder.tvKuantitas.setText(String.valueOf(barangList.get(i).getKuantitas()));

        myViewHolder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new android.app.AlertDialog.Builder(context)
                            .setTitle("Peringatan")
                            .setMessage("Hapus data barang?")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    barangList.remove(i);
                                    notifyItemRemoved(i);
                                    notifyItemRangeChanged(i,barangList.size());
                                    myViewHolder.itemView.setVisibility(View.GONE);
                                }
                            }).setNegativeButton("Kembali", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }
        });

        myViewHolder.btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Barang> call = RetrofitClient.getInstance().baseAPI().getBarang(barangList.get(i).getId_barang());
                call.enqueue(new Callback<Barang>() {
                    @Override
                    public void onResponse(Call<Barang> call, Response<Barang> response) {
                        StringBuffer data = new StringBuffer();
                        data.append("Kategori : "+response.body().getKategori()+"\n");
                        data.append("Merk : "+response.body().getMerk()+"\n");
                        data.append("Tipe : "+response.body().getTipe());

                        new AlertDialog.Builder(context).setTitle("Data Barang").setMessage(data).setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
                    }

                    @Override
                    public void onFailure(Call<Barang> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return barangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdBarang, tvTipe, tvKategori, tvMerk, tvKuantitas;
        Button btnHapus, btnInfo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIdBarang=itemView.findViewById(R.id.tvDataIdBarang);
            tvKuantitas=itemView.findViewById(R.id.tvDataKuantitas);
            btnHapus=itemView.findViewById(R.id.btnHapus);
            btnInfo=itemView.findViewById(R.id.btnInfo);

        }
    }
}
