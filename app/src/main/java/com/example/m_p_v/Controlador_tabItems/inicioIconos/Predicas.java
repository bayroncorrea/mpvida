package com.example.m_p_v.Controlador_tabItems.inicioIconos;

import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookie.R;

public class Predicas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acti_predicas);

        // Configurar la transición compartida
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.anim_entrada));
        }
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition(); // Llama a la animación de regreso
    }
}