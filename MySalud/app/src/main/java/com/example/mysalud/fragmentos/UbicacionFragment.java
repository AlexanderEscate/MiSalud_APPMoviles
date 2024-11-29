package com.example.mysalud.fragmentos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log; // Agrega esto para usar logs
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mysalud.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UbicacionFragment extends Fragment {

    // Callback del mapa
    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            // Log para depuración
            Log.d("UbicacionFragment", "El mapa está listo");

            // Configuración de la ubicación y el marcador
            LatLng lima = new LatLng(-12.0464, -77.0428); // Coordenadas de Lima
            googleMap.addMarker(new MarkerOptions().position(lima).title("Marker in Lima"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lima, 12)); // Nivel de zoom
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ubicacion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configuración del mapa
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.main);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        // Verificación de permisos (si aplica)
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }
}
