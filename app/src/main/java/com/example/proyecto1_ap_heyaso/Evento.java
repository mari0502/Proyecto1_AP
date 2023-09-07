package com.example.proyecto1_ap_heyaso;

public class Evento {
    private String IdEvento;
    private String categoria;
    private String descripcion;
    private String duracion;
    private String fecha;
    private String lugar;

    private String requisitos;
    private String titulo;



    public Evento() {
        // Default constructor required for Firebase
    }

    public Evento(String IdEvento, String categoria, String descripcion, String duracion, String fecha, String lugar, String requisitos, String titulo) {
        this.IdEvento= IdEvento;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.duracion = duracion;
        this.fecha = fecha;
        this.categoria = categoria;
        this.requisitos = requisitos;
}

    public String getIdEvento() {
        return IdEvento;
    }

    public void setIdEvento(String IdEvento) {
        this.IdEvento = IdEvento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDuración(String duración) {
        this.duracion = duración;
    }

    public String getDuración() {
        return duracion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }
}
