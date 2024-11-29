package com.example.mysalud.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mysalud.R;

public class InicioActivity extends AppCompatActivity {
    ProgressBar barCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        barCarga = findViewById(R.id.progressBarInicio);
        Thread tCarga = new Thread(new Runnable() {//crea metodo sobrescrito
            @Override
            public void run() {
                //validar y pintar barra de progreso-------
                for (int i=0;i < barCarga.getMax();i++){
                    barCarga.setProgress(i);
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                //llamar  otra actividad despues d comletar barra de progreso------

                Intent sesion = new Intent(getApplicationContext(), SesionActivity.class);
                startActivity(sesion);
                finish();//finalizar activity

            }
        });
        tCarga.start();
    }
}