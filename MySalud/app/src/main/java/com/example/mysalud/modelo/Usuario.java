package com.example.mysalud.modelo;

public class Usuario {
    private int id_usuario;
    private String nombres;
    private String apellidos;
    private String correo;
    private String contrasenia;

    public Usuario(int id_usuario, String nombres, String apellidos, String correo, String contrasenia) {
        this.id_usuario = id_usuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    public Usuario() {
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
