package com.example.mysalud.modelo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UsuarioResponse {
    @SerializedName("listaUsuarios")
    ArrayList<Usuario> listaUsuarios;
}
