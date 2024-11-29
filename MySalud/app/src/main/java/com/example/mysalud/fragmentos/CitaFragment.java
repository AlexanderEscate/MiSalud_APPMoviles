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


public class CitaFragment extends Fragment {

    private Button btnRegresar, btnSalir;

    public CitaFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cita, container, false);

        btnRegresar = view.findViewById(R.id.btnRegresar);

        btnRegresar.setOnClickListener(v -> {
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