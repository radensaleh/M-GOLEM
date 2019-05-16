package com.tubes.mgolem.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.tubes.mgolem.R;
import com.tubes.mgolem.entitas.Barang;

import java.util.ArrayList;
import java.util.List;

public class AdapterBarangTeknisi extends RecyclerView.Adapter<AdapterBarangTeknisi.MyViewHolder> {
    public List<Barang> listBarang;
    public Context context;

    public AdapterBarangTeknisi(List<Barang> listBarang, Context context){
        this.context=context;
        this.listBarang=listBarang;
    }

    @NonNull
    @Override
    public AdapterBarangTeknisi.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_barang_teknisi,viewGroup, false );
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBarangTeknisi.MyViewHolder myViewHolder, int i) {
        myViewHolder.tvIdBarang.setText(listBarang.get(i).getId_barang());
        myViewHolder.tvTipe.setText(listBarang.get(i).getTipe());
        myViewHolder.tvMerk.setText(listBarang.get(i).getMerk());
        myViewHolder.tvKategori.setText(listBarang.get(i).getKategori());
        myViewHolder.tvKuantitas.setText(String.valueOf(listBarang.get(i).getKuantitas()));
    }

    @Override
    public int getItemCount() {
        return listBarang.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdBarang, tvTipe, tvKategori, tvMerk, tvKuantitas;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIdBarang=itemView.findViewById(R.id.tvIdBarang);
            tvKategori=itemView.findViewById(R.id.tvKategori);
            tvMerk=itemView.findViewById(R.id.tvMerkBarangTeknisi);
            tvTipe=itemView.findViewById(R.id.tvTipe);
            tvKuantitas=itemView.findViewById(R.id.tvKuantitasTeknisi);
        }
    }
}
