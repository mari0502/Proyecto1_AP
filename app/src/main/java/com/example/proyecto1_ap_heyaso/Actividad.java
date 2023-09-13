package com.example.proyecto1_ap_heyaso;

public class Actividad {
    private String IdActividad;
    private String IdEvento;
    private String capacidad;
    private String descripcion;
    private String duracion;
    private String encargado;

    private String titulo;




    public Actividad() {
        // Default constructor required for Firebase
    }

    public Actividad(String IdActividad, String IdEvento, String capacidad, String descripcion, String duracion, String encargado,  String titulo) {
        this.IdActividad= IdActividad;
        this.IdEvento = IdEvento;
        this.capacidad = capacidad;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.encargado = encargado;
        this.titulo = titulo;
    }

    public String getIdActividad() {
        return IdActividad;
    }

    public void setIdActividad(String IdActividad) {
        this.IdActividad = IdActividad;
    }

    public String getIdEvento() {
        return IdEvento;
    }

    public void setIdEvento(String IdEvento) {
        this.IdEvento = IdEvento;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
