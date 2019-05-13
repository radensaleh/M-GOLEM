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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tubes.mgolem.R;
import com.tubes.mgolem.Rest.RetrofitClient;
import com.tubes.mgolem.entitas.Barang;
import com.tubes.mgolem.entitas.Mahasiswa;
import com.tubes.mgolem.entitas.Peminjaman;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPeminjamanMhs extends RecyclerView.Adapter<AdapterPeminjamanMhs.MyViewHolder> {
    private List<Peminjaman> peminjamanList;
    private Context context;

    public AdapterPeminjamanMhs(List<Peminjaman> peminjamanList, Context context){
        this.peminjamanList = peminjamanList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterPeminjamanMhs.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.data_peminjaman, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPeminjamanMhs.MyViewHolder myViewHolder, final int i) {
        Date date = peminjamanList.get(i).getTgl_pinjam();
        String tglpinjam = new SimpleDateFormat("dd-MM-yyyy").format(date);
        Date dateKembali = peminjamanList.get(i).getTgl_pinjam();
        String tglKembali = new SimpleDateFormat("dd-MM-yyyy").format(dateKembali);

        myViewHolder.tvIdPeminjaman.setText(peminjamanList.get(i).getId_pinjam());
        myViewHolder.tvNim.setText(peminjamanList.get(i).getNim());
        myViewHolder.tvTanggalPinjam.setText(tglpinjam);
        myViewHolder.tvTanggalKembali.setText(tglKembali);
        myViewHolder.tvKegiatan.setText(peminjamanList.get(i).getNama_kegiatan());


        if(peminjamanList.get(i).getStatus().equals("1")){
            myViewHolder.layoutTeknisiPinjam.setVisibility(View.GONE);
            myViewHolder.btnVerif.setText("Menunggu verifikasi peminjaman");
            myViewHolder.btnVerif.setEnabled(false);
            myViewHolder.btnDataMhs.setVisibility(View.GONE);
            myViewHolder.layoutTeknisiKembali.setVisibility(View.GONE);
        }else if(peminjamanList.get(i).getStatus().equals("3")){
            myViewHolder.tvUserTeknisiPinjam.setText(peminjamanList.get(i).getUsername_verifpinjam());
            myViewHolder.btnDataMhs.setVisibility(View.GONE);
            myViewHolder.btnVerif.setText("Menunggu verifikasi pengembalian");
            myViewHolder.btnVerif.setEnabled(false);
            myViewHolder.layoutTeknisiKembali.setVisibility(View.GONE);
        }else if(peminjamanList.get(i).getStatus().equals("2")){
            myViewHolder.btnVerif.setText("Kembalikan Peminjaman");
            myViewHolder.layoutTeknisiKembali.setVisibility(View.GONE);
            myViewHolder.btnDataMhs.setVisibility(View.GONE);
            myViewHolder.tvUserTeknisiPinjam.setText(peminjamanList.get(i).getUsername_verifpinjam());
            myViewHolder.btnVerif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Mahasiswa mhs = Mahasiswa.getInstance();
                    mhs.pengembalianPinjam(peminjamanList.get(i).getId_pinjam(), context);
                }
            });
        }else{
            myViewHolder.tvUserTeknisiKembali.setText(peminjamanList.get(i).getUsername_verifkembali());
            myViewHolder.tvUserTeknisiPinjam.setText(peminjamanList.get(i).getUsername_verifpinjam());
        }

        myViewHolder.btnInfoBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<List<Barang>> call = RetrofitClient.getInstance().baseAPI().getDaftarBarang(peminjamanList.get(i).getId_pinjam());
                call.enqueue(new Callback<List<Barang>>() {
                    @Override
                    public void onResponse(Call<List<Barang>> call, Response<List<Barang>> response) {
                        StringBuffer data = new StringBuffer();
                        for(int i=0;i<response.body().size();i++){
                            data.append("ID Barang : "+response.body().get(i).getId_barang()+"\n");
                            data.append("Tipe : "+response.body().get(i).getTipe()+"\n");
                            data.append("Merk : "+response.body().get(i).getMerk()+"\n");
                            if(i==response.body().size()-1){
                                data.append("Kuantitas : "+response.body().get(i).getKuantitas());
                            }else{
                                data.append("Kuantitas : "+response.body().get(i).getKuantitas()+"\n\n");
                            }

                        }

                        new AlertDialog.Builder(context).setTitle("Daftar Barang").setMessage(data).setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
                    }

                    @Override
                    public void onFailure(Call<List<Barang>> call, Throwable t) {
                        Toast.makeText(context,"gagal", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return peminjamanList.size();
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
