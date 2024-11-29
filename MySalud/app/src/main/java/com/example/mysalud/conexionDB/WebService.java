package com.example.mysalud.conexionDB;

import com.example.mysalud.modelo.Usuario;
import com.example.mysalud.modelo.UsuarioResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface WebService {

    @GET("/usuarios")
    Call<UsuarioResponse> obtenerUsuarios();

    @GET("/usuario/{idUsuario}")
    Call<String> obtenerUsuario(
            @Path("idUsuario") int idUsuario
    );

    @POST("/usuario/add")
    Call<String> agregarUsuario(
            @Body Usuario usuario
    );

    @PUT("/usuario/update/{idUsuario}")
    Call<String> actualizarUsuario(
            @Path("idUsuario") int idUsuario,
            @Body Usuario usuario
    );

    @DELETE("/usuario/delete/{idUsuario}")
    Call<String> borrarUsuario(
            @Path("idUsuario") int idUsuario
    );
}