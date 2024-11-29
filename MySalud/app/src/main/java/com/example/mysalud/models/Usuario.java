package com.example.mysalud.models;


import java.io.Serializable;

public class Usuario {

    private int id_usuario;       // id del usuario
    private String nombres;       // Nombres del usuario
    private String apellidos;     // Apellidos del usuario
    private String correo;        // Correo electrónico del usuario
    private String contrasenia;   // Contraseña del usuario

    public Usuario() {
    }


    public Usuario(int id_usuario, String nombres, String apellidos, String correo, String contrasenia) {
        this.id_usuario = id_usuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    public Usuario(String nombres, String apellidos, String correo, String contrasenia) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
