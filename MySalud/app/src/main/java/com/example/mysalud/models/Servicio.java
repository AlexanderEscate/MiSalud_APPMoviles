package com.example.mysalud.models;

import com.google.gson.annotations.SerializedName;

public class Servicio {
    @SerializedName("id_servicio")
    private int id_servicio;
    @SerializedName("nombre_servicio")
    private String nombre_servicio;
    @SerializedName("descripcion")
    private String descripcion;

    public Servicio() {
    }

    public Servicio(int id_servicio, String nombre_servicio, String descripcion) {

        this.id_servicio = id_servicio;
        this.nombre_servicio = nombre_servicio;
        this.descripcion = descripcion;
    }

    public Servicio(String nombre_servicio, String descripcion) {
        this.nombre_servicio = nombre_servicio;
        this.descripcion = descripcion;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public String getNombre_servicio() {
        return nombre_servicio;
    }

    public void setNombre_servicio(String nombre_servicio) {
        this.nombre_servicio = nombre_servicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
