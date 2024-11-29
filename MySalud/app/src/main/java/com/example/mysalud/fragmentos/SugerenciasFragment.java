package com.example.mysalud.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mysalud.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class SugerenciasFragment extends Fragment {
    private Button btnEnviar, btnSalir;



    public SugerenciasFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sugerencias, container, false);


        btnEnviar = view.findViewById(R.id.fragPerfilBtnEditar);
        btnSalir = view.findViewById(R.id.fragPerfilBtnRegresar);

        btnSalir.setOnClickListener(v -> {
            Fragment citaFragment = new MenuFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, citaFragment);
            fragmentTransaction.commit();

            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.btnNavView);
            bottomNavigationView.setSelectedItemId(R.id.menu);
        });


        return view;
    }
}