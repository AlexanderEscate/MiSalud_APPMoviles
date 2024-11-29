package com.example.mysalud.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mysalud.R;
import com.example.mysalud.models.Usuario;
import com.example.mysalud.network.ApiService;
import com.example.mysalud.network.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtNombre, txtApellido, txtCorreo, txtPass, txtConPass;
    Button btnRegistrar,btnIniciar;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtNombre = findViewById(R.id.regTxtNombre);
        txtApellido = findViewById(R.id.regTxtApellido);
        txtCorreo = findViewById(R.id.regTxtCorreo);
        txtPass = findViewById(R.id.regTxtPass);
        txtConPass = findViewById(R.id.regTxtConfPass);
        btnRegistrar = findViewById(R.id.regBtnRegistrar);
        btnIniciar = findViewById(R.id.regBtnIniciarSesion);

        btnRegistrar.setOnClickListener(this);
        btnIniciar.setOnClickListener(this);


        // Inicializar Retrofit
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        apiService = retrofit.create(ApiService.class);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.regBtnRegistrar)
            registrarUsuario();
        else if (v.getId() == R.id.regBtnIniciarSesion)
            iniciarSesion();
    }
    private void iniciarSesion() {
        Intent intent = new Intent(this, SesionActivity.class);
        startActivity(intent);
        finish();
    }

    // Método para registrar un nuevo usuario
    private void registrarUsuario() {
        String nombre = txtNombre.getText().toString();
        String apellido = txtApellido.getText().toString();
        String correo = txtCorreo.getText().toString();
        String pass = txtPass.getText().toString();
        String confPass = txtConPass.getText().toString();

        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || pass.isEmpty() || confPass.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_LONG).show();
            return;
        }

        if (!pass.equals(confPass)) {
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_LONG).show();
            return;
        }

        Usuario usuario = new Usuario(-1,nombre,apellido,correo,pass);



        // Realizar la llamada al servicio para registrar el usuario
        Call<Usuario> call = apiService.registrarUsuario(usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(RegistroActivity.this, "Usuario registrado con éxito", Toast.LENGTH_LONG).show();
                    // Redirigir a la pantalla de inicio de sesión o realizar otras acciones
                    Intent intent = new Intent(RegistroActivity.this, SesionActivity.class); // Intent para ir a SesionActivity
                    startActivity(intent);  // Iniciar la actividad
                    finish();  // Finalizar la actividad actual para evitar que el usuario regrese a esta pantalla
                } else {
                    // Si el correo ya está registrado (código 400), mostramos el error
                    if (response.code() == 400) {
                        // Aquí extraemos el mensaje de error del cuerpo de la respuesta
                        try {
                            String errorMessage = response.errorBody().string(); // Obtener el mensaje de error
                            Toast.makeText(RegistroActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(RegistroActivity.this, "Error al registrar el usuario", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(RegistroActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
