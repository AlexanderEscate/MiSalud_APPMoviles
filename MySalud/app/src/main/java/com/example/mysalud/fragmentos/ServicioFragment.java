package com.example.mysalud.fragmentos;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mysalud.R;
import com.example.mysalud.models.Servicio;
import com.example.mysalud.models.ServicioAdapter;
import com.example.mysalud.network.ApiService;
import com.example.mysalud.network.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServicioFragment extends Fragment {

    private LinearLayout servicesLayout;
    private ArrayList<Servicio> serviciosList = new ArrayList<>();



    public ServicioFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_servicio, container, false);

        servicesLayout = view.findViewById(R.id.servicesContainer);
        Button btnRegresar = view.findViewById(R.id.fragservicioBack);

        // Configuración del botón de regresar
        btnRegresar.setOnClickListener(v -> regresarAlMenu());

        // Carga de servicios desde la API
        loadServiciosFromAPI();


        return view;
    }

    private void loadServiciosFromAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.40:5000/") // URL base de la API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Servicio>> call = apiService.obtenerServicios();

        call.enqueue(new Callback<List<Servicio>>() {
            @Override
            public void onResponse(Call<List<Servicio>> call, Response<List<Servicio>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    serviciosList.clear();
                    serviciosList.addAll(response.body());
                    displayServicios();
                } else {
                    // Muestra un mensaje si la respuesta no fue exitosa
                    Toast.makeText(getActivity(), "Error al cargar los servicios, código de respuesta: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Error: " + response.message());}
            }

            @Override
            public void onFailure(Call<List<Servicio>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "Error al conectar con la API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayServicios() {
        servicesLayout.removeAllViews();

        for (Servicio servicio : serviciosList) {
            View serviceView = getLayoutInflater().inflate(R.layout.service_item, servicesLayout, false);

            TextView serviceName = serviceView.findViewById(R.id.serviceName);
            TextView serviceDescription = serviceView.findViewById(R.id.serviceDescription);
            ImageView serviceImage = serviceView.findViewById(R.id.serviceImage);
            ImageButton btnNext = serviceView.findViewById(R.id.btnNext);

            serviceName.setText(servicio.getNombre_servicio());
            serviceDescription.setText(servicio.getDescripcion());

            // Asignar la imagen basada en el nombre del servicio sin Glide
            int imageResource = obtenerImagenPorNombre(servicio.getNombre_servicio());
            serviceImage.setImageResource(imageResource);

            // Configurar el listener para el botón "Siguiente"
            //btnNext.setOnClickListener(v -> abrirFragmentoCorrespondiente(servicio.getNombre_servicio()));

            servicesLayout.addView(serviceView);
        }
    }

    private int obtenerImagenPorNombre(String nombreServicio) {
        if (nombreServicio.equalsIgnoreCase("Consulta Pediátrica")) {
            return R.drawable.img;
        } else if (nombreServicio.equalsIgnoreCase("Examen de Laboratorio")) {
            return R.drawable.baseline_ads_click_24;
        } else if (nombreServicio.equalsIgnoreCase("Terapia Física")) {
            return R.drawable.baseline_back_hand_24;
        } else if (nombreServicio.equalsIgnoreCase("Atención de Emergencias")) {
            return R.drawable.baseline_credit_card_24;
        } else if (nombreServicio.equalsIgnoreCase("Chequeo Cardiológico")) {
            return R.drawable.baseline_calendar_month_24;
        }else if (nombreServicio.equalsIgnoreCase("Odontoligía")) {
            return R.drawable.baseline_home_24;
        }else if (nombreServicio.equalsIgnoreCase("Consulta Ginecológica")) {
            return R.drawable.baseline_lock_24;
        } else if (nombreServicio.equalsIgnoreCase("Cirugía Ambulatoria")) {
            return R.drawable.cita;
        } else if (nombreServicio.equalsIgnoreCase("Ultrasonido")) {
            return R.drawable.baseline_time_24;
        }else if (nombreServicio.equalsIgnoreCase("Nutrición y Dietética")) {
            return R.drawable.enviar_icon_read_24;
        } else if (nombreServicio.equalsIgnoreCase("Medicina Interna")) {
            return R.drawable.config_icon;
        } else if (nombreServicio.equalsIgnoreCase("Psicología Clínica")) {
            return R.drawable.baseline_ver_24;
        }else if (nombreServicio.equalsIgnoreCase("Chequeo Prenatal")) {
            return R.drawable.ubicanos;
        }


        // Imagen por defecto si no coincide ningún nombre
        return R.drawable.delete_icon_24;
    }

    private void regresarAlMenu() {
        Fragment menuFragment = new MenuFragment();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, menuFragment);
        fragmentTransaction.commit();

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.btnNavView);
        bottomNavigationView.setSelectedItemId(R.id.menu);
    }




}