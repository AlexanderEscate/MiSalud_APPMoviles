package com.example.mysalud.fragmentos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mysalud.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MenuFragment extends Fragment {

    private ConstraintLayout btnServicios,btnUbicanos;

    private TextView txtSaludo;
    private SharedPreferences prefs;

    public MenuFragment() {
        // Constructor vacío requerido
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        // Referencias a las vistas
        btnServicios = view.findViewById(R.id.constFragMenuServicios);
        btnUbicanos = view.findViewById(R.id.constFragMenuUbicanos);


        // Referencia al TextView para el saludo
        txtSaludo = view.findViewById(R.id.textViewMenu);

        // Configuración de SharedPreferences para obtener el nombre del usuario
        prefs = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        // Obtener el nombre del usuario desde SharedPreferences
        String nombreUsuario = prefs.getString("nombres", "Usuario");

        // Mostrar el saludo con el nombre del usuario
        txtSaludo.setText("¡Hola, " + nombreUsuario + "!");

        btnServicios.setOnClickListener(v -> {
            Fragment menuFragment = new ServicioFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, menuFragment);
            fragmentTransaction.commit();
        });
        btnUbicanos.setOnClickListener(v -> {
            Fragment menuFragment = new UbicacionFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, menuFragment);
            fragmentTransaction.commit();
        });




        return view;
    }
    private void configurarBotones() {
        btnServicios.setOnClickListener(v -> cambiarFragmento(new ServicioFragment(), R.id.servicio));
       btnUbicanos.setOnClickListener(v -> cambiarFragmento(new UbicacionFragment(), R.id.frameLayout));
       // btnCita.setOnClickListener(v -> cambiarFragmento(new CitaFragment(), R.id.cita));
      //  btnBarberos.setOnClickListener(v -> cambiarFragmento(new BarberosFragment(), R.id.frameLayout));
    }

    private void cambiarFragmento(Fragment fragment, int navId) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.btnNavView);
        bottomNavigationView.setSelectedItemId(navId);
    }
}



