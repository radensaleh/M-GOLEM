package com.tubes.mgolem.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tubes.mgolem.Api.MahasiswaAPI;
import com.tubes.mgolem.MenuTeknisiActivity;
import com.tubes.mgolem.R;
import com.tubes.mgolem.Rest.RetrofitClient;
import com.tubes.mgolem.entitas.Barang;
import com.tubes.mgolem.entitas.Peminjaman;
import com.tubes.mgolem.entitas.Teknisi;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void onBindViewHolder(@NonNull AdapterPeminjaman.MyViewHolder myViewHolder, final int i) {
        Date date = peminjamanList.get(i).getTgl_pinjam();
        String tglpinjam = new SimpleDateFormat("dd-MM-yyyy").format(date);
        Date dateKembali = peminjamanList.get(i).getTgl_pinjam();
        String tglKembali = new SimpleDateFormat("dd-MM-yyyy").format(dateKembali);

        myViewHolder.tvIdPeminjaman.setText(peminjamanList.get(i).getId_pinjam());
        myViewHolder.tvNim.setText(peminjamanList.get(i).getNim());
        myViewHolder.tvTanggalPinjam.setText(tglpinjam);
        myViewHolder.tvTanggalKembali.setText(tglKembali);
        myViewHolder.tvKegiatan.setText(peminjamanList.get(i).getNama_kegiatan());

        String status=peminjamanList.get(i).getStatus();

        if(status.equals("1")){
            //Verifikasi Peminjaman
            myViewHolder.layoutTeknisiPinjam.setVisibility(View.GONE);
            myViewHolder.layoutTeknisiKembali.setVisibility(View.GONE);
            myViewHolder.btnVerif.setText("Verifikasi Peminjaman");

            myViewHolder.btnVerif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Teknisi teknisi = Teknisi.getInstance();
                    teknisi.verifikasiPeminjaman(peminjamanList.get(i), context);
                }
            });
        }else if(status.equals("3")){
            //Verifikasi Pengembalian
            myViewHolder.layoutTeknisiKembali.setVisibility(View.GONE);
            myViewHolder.btnVerif.setText("Verifikasi Pengembalian");
            myViewHolder.tvUserTeknisiPinjam.setText(peminjamanList.get(i).getUsername_verifpinjam());

            myViewHolder.btnVerif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Teknisi teknisi = Teknisi.getInstance();
                    teknisi.verifikasiPeminjaman(peminjamanList.get(i), context);
                }
            });
        }else if(status.equals("2")){
            //Data Peminjaman
            myViewHolder.layoutTeknisiKembali.setVisibility(View.GONE);
            myViewHolder.btnVerif.setVisibility(View.GONE);
            myViewHolder.tvUserTeknisiPinjam.setText(peminjamanList.get(i).getUsername_verifpinjam());
        }else{
            //Data Pengembalian
            myViewHolder.btnVerif.setVisibility(View.GONE);
            myViewHolder.tvUserTeknisiPinjam.setText(peminjamanList.get(i).getUsername_verifpinjam());
            myViewHolder.tvUserTeknisiKembali.setText(peminjamanList.get(i).getUsername_verifkembali());
        }

        //show data mahasiswa

        myViewHolder.btnDataMhs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<MahasiswaAPI> call = RetrofitClient.getInstance().baseAPI().getMahasiswa(peminjamanList.get(i).getNim());
                call.enqueue(new Callback<MahasiswaAPI>() {
                    @Override
                    public void onResponse(Call<MahasiswaAPI> call, Response<MahasiswaAPI> response) {
                        StringBuffer data = new StringBuffer();
                        data.append("NIM : "+response.body().getNim()+"\n");
                        data.append("Nama : "+response.body().getNama_mhs()+"\n");
                        data.append("Kelas : "+response.body().getId_kelas());

                        new AlertDialog.Builder(context).setTitle("Data Mahasiswa").setMessage(data).setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
                    }

                    @Override
                    public void onFailure(Call<MahasiswaAPI> call, Throwable t) {

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return peminjamanList.size() ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdPeminjaman, tvNim, tvUserTeknisiPinjam, tvUserTeknisiKembali, tvTanggalPinjam, tvTanggalKembali, tvKegiatan;
        LinearLayout layoutTeknisiPinjam, layoutTeknisiKembali, layoutTanggalPinjam, layoutTanggalKembali;
        Button btnVerif, btnDataMhs, btnInfoBarang;
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
            btnDataMhs=itemView.findViewById(R.id.btnDataSiswa);
            btnInfoBarang=itemView.findViewById(R.id.btnInfoBarang);

        }
    }
}
