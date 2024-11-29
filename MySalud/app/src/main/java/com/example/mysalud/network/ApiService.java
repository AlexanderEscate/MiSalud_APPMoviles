package com.example.mysalud.network;


import com.example.mysalud.models.LoginRequest;
import com.example.mysalud.models.Servicio;
import com.example.mysalud.models.Usuario;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    // Obtener todos los usuarios
    @GET("usuarios")
    Call<List<Usuario>> obtenerUsuarios();

    // Obtener todos los servicios
   @GET("servicios")
    Call<List<Servicio>> obtenerServicios();
    //@GET("servicios")
    //Call<Map<String, List<Servicio>>> obtenerServicios();

    // Obtener un usuario por ID
    @GET("usuario/{id}")
    Call<com.example.mysalud.models.Usuario> obtenerUsuario(@Path("id") int id);

    // Registrar un nuevo usuario
    @POST("usuario/add")
    Call<Usuario> registrarUsuario(@Body Usuario usuario);

    // Actualizar un usuario
    @PUT("usuario/update/{id}")
    Call<Usuario> actualizarUsuario(@Path("id") int id, @Body Usuario usuario);

    // Eliminar un usuario
    @DELETE("usuario/delete/{id}")
    Call<Void> eliminarUsuario(@Path("id") int id);

    // Verificar usuario por correo y contraseña
    //@GET("usuario/login/{correo}/{contrasenia}")
    //Call<Usuario> obtenerUsuarioPorCredenciales(@Path("correo") String correo, @Path("contrasenia") String contrasenia);
    //@GET("usuario/login")
    //Call<Usuario> obtenerUsuarioPorCredenciales(@Query("correo") String correo, @Query("contrasenia") String contrasenia);

    @POST("usuario/login")
    Call<Usuario> obtenerUsuarioPorCredenciales(@Body LoginRequest loginRequest);
}
