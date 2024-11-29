package com.example.mysalud.fragmentos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mysalud.R;
import com.example.mysalud.actividades.SesionActivity;
import com.example.mysalud.models.Usuario;
import com.example.mysalud.network.ApiService;
import com.example.mysalud.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CuentaFragment extends Fragment {

    private EditText editNombre, editApellido, editCorreo, editContrasena, editConfirmarContrasena;
    private Button btnActualizar, btnEliminar;
    private ApiService apiService;
    private SharedPreferences prefs;
    private int userId; // ID del usuario para cargar sus datos




    public CuentaFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cuenta, container, false);

        // Inicializar los campos EditText y botones
        editNombre = view.findViewById(R.id.regTxtNombre);
        editApellido = view.findViewById(R.id.regTxtApellido);
        editCorreo = view.findViewById(R.id.regTxtCorreo);
        editContrasena = view.findViewById(R.id.regTxtPass);
        editConfirmarContrasena = view.findViewById(R.id.regTxtConfPass);
        btnActualizar = view.findViewById(R.id.fragCuentabtnActualizar);
        btnEliminar = view.findViewById(R.id.fragCuentaBtnEliminar);

        // Inicializar Retrofit y ApiService
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Inicializar SharedPreferences para obtener los datos del usuario (ID y nombre)
        prefs = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

        // Obtener el ID del usuario desde SharedPreferences
        userId = prefs.getInt("id_usuario", -1); // Si no tienes el ID, necesitarás obtenerlo de otra manera

        // Verificar si el userId es válido
        if (userId == -1) {
            Toast.makeText(getContext(), "Error: No se encontró el ID de usuario", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("CuentaFragment", "ID de usuario: " + userId);  // Log para verificar si el ID es correcto
            obtenerUsuario(); // Si el ID es válido, obtener los datos del usuario
        }



        // Acción para actualizar los datos del usuario
        btnActualizar.setOnClickListener(v -> {
            actualizarUsuario();
        });

        // Acción para eliminar el usuario
        btnEliminar.setOnClickListener(v -> {
            eliminarUsuario();
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
                    editNombre.setText(usuario.getNombres());
                    editApellido.setText(usuario.getApellidos());
                    editCorreo.setText(usuario.getCorreo());
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

    // Actualizar usuario
    private void actualizarUsuario() {
        String nombre = editNombre.getText().toString();
        String apellido = editApellido.getText().toString();
        String correo = editCorreo.getText().toString();
        String contrasena = editContrasena.getText().toString();
        String confirmarContrasena = editConfirmarContrasena.getText().toString();

        // Validar que los campos no estén vacíos
        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar contraseñas
        if (!contrasena.equals(confirmarContrasena)) {
            Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }
        // Crear el objeto Usuario con los datos actualizados
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombres(nombre);
        usuarioActualizado.setApellidos(apellido);
        usuarioActualizado.setCorreo(correo);
        usuarioActualizado.setContrasenia(contrasena);

        // Hacer la llamada a Retrofit para actualizar el usuario
        Call<Usuario> call = apiService.actualizarUsuario(userId, usuarioActualizado);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
                    // Redirigir al PerfilFragment
                    Fragment perfilFragment = new PerfilFragment();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, perfilFragment)
                            .commit();
                } else {
                    Toast.makeText(getContext(), "Error al actualizar el usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("CuentaFragment", "Error de conexión: " + t.getMessage());
                Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Eliminar usuario
    private void eliminarUsuario() {
        new AlertDialog.Builder(getContext())
                .setTitle("Eliminar Cuenta")
                .setMessage("¿Estás seguro de que quieres eliminar tu cuenta?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    // Llamada a la API para eliminar el usuario
                    Call<Void> call = apiService.eliminarUsuario(userId);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();
                                // Limpiar SharedPreferences
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.clear();
                                editor.apply();

                                // Redirigir al login
                                Intent intent = new Intent(getContext(), SesionActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            } else {
                                Toast.makeText(getContext(), "Error al eliminar el usuario", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("CuentaFragment", "Error de conexión: " + t.getMessage());
                            Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("No", null)
                .show();
    }


   /* // Actualizar usuario
    private void actualizarUsuario() {
        String nombre = editNombre.getText().toString();
        String apellido = editApellido.getText().toString();
        String correo = editCorreo.getText().toString();
        String contrasena = editContrasena.getText().toString();
        String confirmarContrasena = editConfirmarContrasena.getText().toString();

        // Validar contraseñas
        if (!contrasena.equals(confirmarContrasena)) {
            Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombres(nombre);
        usuarioActualizado.setApellidos(apellido);
        usuarioActualizado.setCorreo(correo);
        usuarioActualizado.setContrasenia(contrasena);

        // Hacer la llamada a Retrofit para actualizar el usuario
        Call<Usuario> call = apiService.actualizarUsuario(userId, usuarioActualizado);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error al actualizar el usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("CuentaFragment", "Error de conexión: " + t.getMessage());
                Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Eliminar usuario
    private void eliminarUsuario() {
        new AlertDialog.Builder(getContext())
                .setTitle("Eliminar Cuenta")
                .setMessage("¿Estás seguro de que quieres eliminar tu cuenta?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    // Llamada a la API para eliminar el usuario
                    Call<Void> call = apiService.eliminarUsuario(userId);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();
                                // Limpiar SharedPreferences
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.clear();
                                editor.apply();

                                // Redirigir al login
                                Intent intent = new Intent(getContext(), SesionActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            } else {
                                Toast.makeText(getContext(), "Error al eliminar el usuario", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e("CuentaFragment", "Error de conexión: " + t.getMessage());
                            Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("No", null)
                .show();
    }*/
}





