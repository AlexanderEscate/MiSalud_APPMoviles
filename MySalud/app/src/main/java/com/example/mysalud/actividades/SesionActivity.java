package com.example.mysalud.actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mysalud.R;
import com.example.mysalud.models.LoginRequest;
import com.example.mysalud.models.Usuario;
import com.example.mysalud.network.ApiService;
import com.example.mysalud.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SesionActivity extends AppCompatActivity implements View.OnClickListener {
    EditText txtCorreo, txtPass;
    Button btnIngresar, btnRegistrar;
    CheckBox chkRecordar;

    // Retrofit service
   // private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sesion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Mapear los elementos de la interfaz
        txtCorreo = findViewById(R.id.logTxtCorreo);
        txtPass = findViewById(R.id.logTxtClave);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        chkRecordar = findViewById(R.id.ChkSesionRecordar);

        // Configurar los clics de los botones
        btnRegistrar.setOnClickListener(this);
        btnIngresar.setOnClickListener(this);

        //btnRegistrar = findViewById(R.id.btnRegistrar);
        // Configura el listener para el botón
        //btnRegistrar.setOnClickListener(view -> registrar());
        //btnRegistrar.setOnClickListener(this);


        // Verifica si el usuario tiene sesión guardada
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean recordar = sharedPreferences.getBoolean("recordar", false);
        String correoGuardado = sharedPreferences.getString("correo", null);
        if (recordar && correoGuardado != null) {
            Intent bienvenida = new Intent(SesionActivity.this, BienvenidaActivity.class);
            bienvenida.putExtra("correo", correoGuardado);
            startActivity(bienvenida);
            finish();
        }
    }



//por las
    private void registrar() {
        Intent registro = new Intent(this, RegistroActivity.class);
        startActivity(registro);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnIngresar) {
            String correo = txtCorreo.getText().toString();
            String clave = txtPass.getText().toString();
            iniciarSession(correo, clave);
        } else if (v.getId() == R.id.btnRegistrar) {
            //pasa a registro activity
            Intent registro = new Intent(this, RegistroActivity.class);
            startActivity(registro);
            finish();
        }
    }

    // Método para iniciar sesión
    private void iniciarSession(String correo, String clave) {
        if (correo.isEmpty() || clave.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese todas las credenciales.", Toast.LENGTH_LONG).show();
            return;
        }

        // Crear la solicitud de login con las credenciales
        LoginRequest loginRequest = new LoginRequest(correo, clave);

        // Configurar Retrofit
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        // Llamar al servicio de login
        Call<Usuario> call = apiService.obtenerUsuarioPorCredenciales(loginRequest);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Si la respuesta es exitosa, inicia la sesión
                    Usuario usuario = response.body();

                    // Guardar los datos del usuario en SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("correo", correo);
                    editor.putBoolean("recordar", chkRecordar.isChecked());
                    editor.putInt("id_usuario", usuario.getId_usuario());  // Guardar el ID de usuario
                    editor.putString("nombres", usuario.getNombres());    // Guardar el nombre del usuario
                    editor.putString("apellidos", usuario.getApellidos()); // Guardar los apellidos
                    editor.apply();

                    // Iniciar la actividad de bienvenida
                    Intent bienvenida = new Intent(SesionActivity.this, BienvenidaActivity.class);
                    bienvenida.putExtra("nombre", usuario.getNombres()); // O el campo que desees mostrar
                    startActivity(bienvenida);
                    finish();
                } else {
                    Toast.makeText(SesionActivity.this, "Credenciales incorrectas", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(SesionActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Método para guardar las credenciales en SharedPreferences
    private void guardarCredenciales(String correo, String contrasenia) {
        SharedPreferences preferences = getSharedPreferences("Sesion", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("correo", correo);
        editor.putString("contrasenia", contrasenia);
        editor.apply();
    }

}


