package com.example.mysalud.fragmentos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysalud.R;
import com.example.mysalud.models.Usuario;
import com.example.mysalud.network.ApiService;
import com.example.mysalud.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PerfilFragment extends Fragment {

    private TextView txtNombre, txtApellido, txtCorreo;
    private Button btnRegresar, btnEditar;
    private SharedPreferences prefs;// SharedPreferences para obtener datos del usuario
    private ApiService apiService;
    private int userId; // ID del usuario para cargar sus datos

    public PerfilFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        // Inicializar los TextViews y botones
        txtNombre = view.findViewById(R.id.regTxtViewNombre);
        txtApellido = view.findViewById(R.id.regTxtViewApellido);
        txtCorreo = view.findViewById(R.id.regTxtViewCorreo);
        btnRegresar = view.findViewById(R.id.fragPerfilBtnRegresar);
        btnEditar = view.findViewById(R.id.fragPerfilBtnEditar);

        //1 Inicializar Retrofit y ApiService
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Acceder a SharedPreferences para obtener los datos del usuario
        prefs = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

        // Obtener el ID del usuario desde SharedPreferences
        userId = prefs.getInt("id_usuario", -1); // Si no tienes el ID, necesitarás obtenerlo de otra manera

        // Verificar si el userId es válido
        if (userId == -1) {
            Toast.makeText(getContext(), "Error: No se encontró el ID de usuario", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("PerfilFragment", "ID de usuario: " + userId);  // Log para verificar si el ID es correcto
            obtenerUsuario(); // Si el ID es válido, obtener los datos del usuario
        }





        //redireccionar a menu
        btnRegresar.setOnClickListener(v -> {
            Fragment menuFragment = new MenuFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, menuFragment);
            fragmentTransaction.commit();
        });

        //redireccionar a CuentaFragment
        btnEditar.setOnClickListener(v -> {
            Fragment cuentaFragment = new CuentaFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, cuentaFragment);
            fragmentTransaction.commit();
        });


        return view;
    }


    // Método para obtener los datos del usuario desde la API
    private void obtenerUsuario() {
        // Llamada a la API para obtener los datos del usuario
        Call<Usuario> call = apiService.obtenerUsuario(userId);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuario = response.body();
                    // Cargar los datos del usuario en los campos de texto
                    txtNombre.setText(usuario.getNombres());
                    txtApellido.setText(usuario.getApellidos());
                    txtCorreo.setText(usuario.getCorreo());
                } else {
                    Log.e("CuentaFragment", "Error al obtener los datos, código: " + response.code());
                    Toast.makeText(getContext(), "Error al obtener los datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                // Mostrar error de conexión
                Log.e("CuentaFragment", "Error de conexión: " + t.getMessage());
                Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}