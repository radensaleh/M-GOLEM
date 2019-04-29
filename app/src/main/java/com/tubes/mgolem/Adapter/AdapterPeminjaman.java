package com.tubes.mgolem.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tubes.mgolem.R;
import com.tubes.mgolem.entitas.Barang;
import com.tubes.mgolem.entitas.Peminjaman;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterPeminjaman extends RecyclerView.Adapter<AdapterPeminjaman.MyViewHolder> {
    private List<Peminjaman> peminjamanList;
    private Context context;

    public AdapterPeminjaman(List<Peminjaman> peminjamanList, Context context){
        this.peminjamanList = peminjamanList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterPeminjaman.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_peminjaman, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPeminjaman.MyViewHolder myViewHolder, int i) {
        Date date = peminjamanList.get(i).getTgl_pinjam();
        String tglpinjam = new SimpleDateFormat("dd-MM-yyyy").format(date);
        myViewHolder.tvIdPeminjaman.setText(peminjamanList.get(i).getId_pinjam());
        myViewHolder.tvNim.setText(peminjamanList.get(i).getNim());
        myViewHolder.tvTanggalPinjam.setText(tglpinjam);
        myViewHolder.tvKegiatan.setText(peminjamanList.get(i).getNama_kegiatan());

        String status=peminjamanList.get(i).getStatus();
        if(status.equals("1")){
            //Verifikasi Peminjaman
            myViewHolder.layoutTeknisiPinjam.setVisibility(View.GONE);
            myViewHolder.layoutTeknisiKembali.setVisibility(View.GONE);
        }else if(status.equals("3")){
            //Verifikasi Pengembalian
            myViewHolder.layoutTeknisiKembali.setVisibility(View.GONE);
        }else if(status.equals("2")){
            //Data Peminjaman
            myViewHolder.layoutTeknisiKembali.setVisibility(View.GONE);
            myViewHolder.btnVerif.setVisibility(View.GONE);
        }else{
            //Data Pengembalian
            myViewHolder.btnVerif.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return peminjamanList.size() ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdPeminjaman, tvNim, tvUserTeknisiPinjam, tvUserTeknisiKembali, tvTanggalPinjam, tvTanggalKembali, tvKegiatan;
        LinearLayout layoutTeknisiPinjam, layoutTeknisiKembali, layoutTanggalPinjam, layoutTanggalKembali;
        Button btnVerif;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIdPeminjaman = itemView.findViewById(R.id.tvIdPeminjaman);
            tvNim=itemView.findViewById(R.id.tvNimMhs);
            tvUserTeknisiPinjam=itemView.findViewById(R.id.tvUserTeknisiPinjam);
            tvUserTeknisiKembali=itemView.findViewById(R.id.tvUserTeknisiKembali);
            tvKegiatan=itemView.findViewById(R.id.tvNamaKegiatan);
            tvTanggalPinjam=itemView.findViewById(R.id.tvTanggalPinjam);
            tvTanggalKembali = itemView.findViewById(R.id.tvTanggalKembali);

            layoutTeknisiPinjam=itemView.findViewById(R.id.layoutTeknisiPinjam);
            layoutTeknisiKembali=itemView.findViewById(R.id.layoutTeknisiKembali);
            layoutTanggalPinjam=itemView.findViewById(R.id.layoutTanggalPinjam);
            layoutTanggalKembali=itemView.findViewById(R.id.layoutTanggalKembali);

            btnVerif=itemView.findViewById(R.id.btnVerifikasi);
        }
    }
}
