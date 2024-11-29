package com.example.mysalud.actividades;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mysalud.R;
import com.example.mysalud.fragmentos.CitaFragment;
import com.example.mysalud.fragmentos.ConfigFragment;
import com.example.mysalud.fragmentos.CuentaFragment;
import com.example.mysalud.fragmentos.MenuFragment;
import com.example.mysalud.fragmentos.PerfilFragment;
import com.example.mysalud.fragmentos.SugerenciasFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BienvenidaActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; // Declaración de la vista de navegación

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bienvenida);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Configura el BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.btnNavView);

        // Selecciona el fragmento inicial
        if (savedInstanceState == null) {
            loadFragment(new MenuFragment()); // Carga el fragmento de menú al inicio
            bottomNavigationView.setSelectedItemId(R.id.menu); // Selecciona el botón de menú
        }

        // Listener para cambiar de fragmento al seleccionar un item del BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            //redireccionamiento de fragmentos
            if (item.getItemId() == R.id.menu) {
                selectedFragment = new MenuFragment();

            } else if (item.getItemId() == R.id.cuenta) {
                selectedFragment = new PerfilFragment();

            } else if (item.getItemId() == R.id.sugerencias) {
                selectedFragment = new SugerenciasFragment();

            } else if (item.getItemId() == R.id.cita) {
                selectedFragment = new CitaFragment();

            } else if (item.getItemId() == R.id.config) {
                selectedFragment = new ConfigFragment();
            }

            return loadFragment(selectedFragment);
        });
    }


    // Método para cargar el fragmento seleccionado
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, fragment); // Asegúrate de que estás usando el ID correcto
            transaction.addToBackStack(null); // Agrega a la pila de retroceso
            transaction.commit();
            return true;
        }
        return false;
    }
}