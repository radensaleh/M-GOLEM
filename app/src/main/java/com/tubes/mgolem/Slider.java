package com.tubes.mgolem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tubes.mgolem.slider.SlideBarang;
import com.tubes.mgolem.slider.SlideMahasiswa;
import com.tubes.mgolem.slider.SlidePinjam;

import agency.tango.materialintroscreen.MaterialIntroActivity;

public class Slider extends MaterialIntroActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(new SlideMahasiswa());
        addSlide(new SlideBarang());
        addSlide(new SlidePinjam());

    }

    @Override
    public void onFinish() {
        super.onFinish();
        Intent i = new Intent(Slider.this, LoginActivity.class);
        startActivity(i);
    }
}
