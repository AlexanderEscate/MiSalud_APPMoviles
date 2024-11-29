package com.example.mysalud.conexionDB;

import com.example.mysalud.modelo.Usuario;
import com.example.mysalud.modelo.UsuarioResponse;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class RetrofitClient {

    static final String BASE_URL = "http://10.0.2.2:5000";

    Retrofit retrofit;

    public RetrofitClient() { }

    // Método para obtener una instancia de Retrofit configurada
    public Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)  // Base URL de la API
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create())) // Converter para JSON
                    .build();
        }
        return retrofit;
    }

    // Definir los métodos de la API (endpoints)
    public interface WebService {

        // Obtener lista de usuarios
        @GET("usuarios")
        Call<UsuarioResponse> obtenerUsuarios();

        // Obtener un usuario por su ID
        @GET("usuario/{idUsuario}")
        Call<Usuario> obtenerUsuario(@Path("idUsuario") int idUsuario);

        // Agregar un nuevo usuario
        @POST("usuario/add")
        Call<String> agregarUsuario(@Body Usuario usuario);

        // Actualizar un usuario existente
        @PUT("usuario/update/{idUsuario}")
        Call<String> actualizarUsuario(@Path("idUsuario") int idUsuario, @Body Usuario usuario);

        // Eliminar un usuario
        @DELETE("usuario/delete/{idUsuario}")
        Call<String> borrarUsuario(@Path("idUsuario") int idUsuario);
    }
}