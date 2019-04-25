package com.tubes.mgolem.slider;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tubes.mgolem.LoginActivity;
import com.tubes.mgolem.PilihLogin;
import com.tubes.mgolem.R;

import agency.tango.materialintroscreen.SlideFragment;

public class SlideBarang extends SlideFragment {

    Button btnLewati;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.slider_barang, container, false);

        btnLewati = view.findViewById(R.id.btnLewati);

        btnLewati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(view.getContext(), PilihLogin.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        return view;
    }

    @Override
    public int backgroundColor() {
        return R.color.colorBlue;
    }

    @Override
    public int buttonsColor() {
        return R.color.colorBlueDark;
    }
}
