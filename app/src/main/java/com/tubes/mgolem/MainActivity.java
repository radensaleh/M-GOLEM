package com.tubes.mgolem;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.tubes.mgolem.Api.KelasAPI;
import com.tubes.mgolem.Rest.RetrofitClient;
import com.tubes.mgolem.SQLite.SlideDAO;
import com.tubes.mgolem.SQLite.UserDAO;
import com.tubes.mgolem.entitas.Kelas;
import com.tubes.mgolem.entitas.ListKelas;
import com.tubes.mgolem.entitas.Mahasiswa;
import com.tubes.mgolem.entitas.Teknisi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    SlideDAO slide;
    UserDAO user;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Runnable rn;
        mContext=this;

        user=new UserDAO(this);

        slide = new SlideDAO(this);

        Call<List<KelasAPI>> call = RetrofitClient.getInstance().baseAPI().kelas();

        call.enqueue(new Callback<List<KelasAPI>>() {
            @Override
            public void onResponse(Call<List<KelasAPI>> call, Response<List<KelasAPI>> response) {
                ListKelas listKelas = ListKelas.getInstance();

                List<KelasAPI> kelasAPIList = response.body();

                for(int i=0; i<kelasAPIList.size(); i++){
                    Kelas kelas = new Kelas();
                    kelas.setId_kelas(kelasAPIList.get(i).getId_kelas());
                    kelas.setNama_kelas(kelasAPIList.get(i).getNama_kelas());
                    listKelas.setKelas(kelas);
                }
            }
            @Override
            public void onFailure(Call<List<KelasAPI>> call, Throwable t) { }
        });

        if(slide.getSlide()==0){
            rn = new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(MainActivity.this, Slider.class);
                    startActivity(i);
                    finish();
                }


            };
        }else{
            rn = new Runnable() {
                @Override
                public void run() {
                    if(user.getUser()!=null){
                        if(user.getUser().get(2).equals("1")){
                            Teknisi teknisi = Teknisi.getInstance();
                            teknisi.login(user.getUser().get(0), user.getUser().get(1), mContext);
                        }else{
                            Mahasiswa mhs = Mahasiswa.getInstance();
                            mhs.login(user.getUser().get(0), user.getUser().get(1), mContext);
                        }

                    }else{
                        Intent i = new Intent(MainActivity.this, PilihLogin.class);
                        startActivity(i);
                        finish();
                    }

                }
            };
        }

        Handler handler = new Handler();
        handler.postDelayed(rn, 2000);
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
