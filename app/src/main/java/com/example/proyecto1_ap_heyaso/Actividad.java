package com.example.proyecto1_ap_heyaso;

public class Actividad {
    private String idActividad;
    private String idEvento;
    private String lugar;
    private String recursos;
    private String descripcion;
    private String duracion;
    private String encargado;

    private String titulo;




    public Actividad() {
        // Default constructor required for Firebase
    }

    public Actividad(String descripcion, String duracion, String encargado, String idActividad, String idEvento, String lugar, String recursos, String titulo) {
        this.idActividad= idActividad;
        this.idEvento = idEvento;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.encargado = encargado;
        this.lugar= lugar;
        this.recursos=recursos;
        this.titulo = titulo;
    }

    public String getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(String IdActividad) {
        this.idActividad = idActividad;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String IdEvento) {
        this.idEvento = idEvento;
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

    public String getRecursos() {
        return recursos;
    }

    public void setRecursos(String recursos) {
        this.recursos = recursos;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
}
