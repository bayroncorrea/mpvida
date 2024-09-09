package com.example.m_p_v.Controlador_tabItems;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import com.example.cookie.R;
import com.example.m_p_v.Controlador_tabItems.inicioIconos.Calendario;
import com.example.m_p_v.Controlador_tabItems.inicioIconos.Estudio;
import com.example.m_p_v.Controlador_tabItems.inicioIconos.Himnario;
import com.example.m_p_v.Controlador_tabItems.inicioIconos.Iglesias;
import com.example.m_p_v.Controlador_tabItems.inicioIconos.Predicas;
import com.example.m_p_v.Controlador_tabItems.inicioIconos.Seminarios;

public class Inicio extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Inicio() {

    }

    public static Inicio newInstance(String param1, String param2) {
        Inicio fragment = new Inicio();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        ImageButton buttonHimnario = view.findViewById(R.id.buttonHimnario);
        buttonHimnario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Himnario.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(), buttonHimnario, "sharedIcon");

                startActivity(intent, options.toBundle());
            }
        });

        ImageButton buttonPredicas = view.findViewById(R.id.buttonPredicas);
        buttonPredicas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Predicas.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(), buttonPredicas, "sharedIcon");

                startActivity(intent, options.toBundle());
            }
        });
        ImageButton buttonEstudio = view.findViewById(R.id.buttonEstudio);
        buttonEstudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Estudio.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(), buttonEstudio, "sharedIcon");

                startActivity(intent, options.toBundle());
            }
        });
        ImageButton buttonCalendario = view.findViewById(R.id.buttonCalendario);
        buttonCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Calendario.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(), buttonCalendario, "sharedIcon");

                startActivity(intent, options.toBundle());
            }
        });
        ImageButton buttonIglesias = view.findViewById(R.id.buttonIglesias);
        buttonIglesias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Iglesias.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(), buttonIglesias, "sharedIcon");

                startActivity(intent, options.toBundle());
            }
        });
        ImageButton buttonSeminarios = view.findViewById(R.id.buttonSeminarios);
        buttonSeminarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Seminarios.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(), buttonSeminarios, "sharedIcon");

                startActivity(intent, options.toBundle());
            }
        });
        return view;
    }
}

