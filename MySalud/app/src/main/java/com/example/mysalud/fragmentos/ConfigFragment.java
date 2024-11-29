package com.example.mysalud.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.mysalud.R;
import com.example.mysalud.actividades.SesionActivity;


public class ConfigFragment extends Fragment {

    private SeekBar seekBarBrillo, seekBarSonido;
    private TextView cerrarSesion;
    private AudioManager audioManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_config, container, false);

        seekBarBrillo = view.findViewById(R.id.seekBarBrillo);
        seekBarSonido = view.findViewById(R.id.seekBarSonido);
        cerrarSesion = view.findViewById(R.id.btnCerrarSesion);

        // Obtener el servicio de Audio para el control del sonido
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Comprobar permisos al iniciar el fragmento
        verificarPermisoEscritura();

        // Cargar ajustes previos (si existen)
        cargarAjustes();

        // Configurar el comportamiento del botón de cerrar sesión
        cerrarSesion.setOnClickListener(v -> {
            cerrarSesion();
        });

        // Ajustar el brillo del sistema según la posición del SeekBar
        seekBarBrillo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (Settings.System.canWrite(getContext())) {
                    ajustarBrillo(progress);
                } else {
                    Toast.makeText(getContext(), "Habilita los permisos para ajustar el brillo.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // No implementado
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Guardar el valor de brillo en SharedPreferences
                guardarAjuste("brillo", seekBar.getProgress());
            }
        });

        // Ajustar el volumen del sistema según la posición del SeekBar
        seekBarSonido.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // No implementado
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Guardar el valor de sonido en SharedPreferences
                guardarAjuste("sonido", seekBar.getProgress());
            }
        });

        return view;
    }

    // Método para verificar y solicitar el permiso WRITE_SETTINGS
    private void verificarPermisoEscritura() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(getContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
                startActivity(intent);
            }
        }
    }

    // Método para ajustar el brillo
    private void ajustarBrillo(int brillo) {
        int valorBrillo = brillo * 255 / 100;
        Settings.System.putInt(getActivity().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, valorBrillo);
    }

    // Método para cargar los ajustes previos (si existen)
    private void cargarAjustes() {
        // Recuperar brillo
        SharedPreferences prefs = getActivity().getSharedPreferences("config_prefs", Context.MODE_PRIVATE);
        int brilloGuardado = prefs.getInt("brillo", 50);  // 50 es el valor predeterminado
        seekBarBrillo.setProgress(brilloGuardado);

        // Recuperar sonido
        int sonidoGuardado = prefs.getInt("sonido", audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION));
        seekBarSonido.setProgress(sonidoGuardado);
    }

    // Método para guardar los ajustes
    private void guardarAjuste(String key, int value) {
        SharedPreferences prefs = getActivity().getSharedPreferences("config_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    // Método para cerrar sesión
    private void cerrarSesion() {
        // Eliminar las credenciales del usuario
        SharedPreferences userPrefs = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPrefs.edit();
        editor.clear(); // Elimina todas las preferencias relacionadas con el usuario, incluyendo "recordar"
        editor.apply();

        // Mostrar mensaje de cierre de sesión exitoso
        Toast.makeText(getActivity(), "Cierre de sesión exitosamente", Toast.LENGTH_SHORT).show();

        // Redirigir a la pantalla de inicio de sesión
        Intent intent = new Intent(getActivity(), SesionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}
