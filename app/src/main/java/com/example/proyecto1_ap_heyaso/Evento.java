package com.example.proyecto1_ap_heyaso;

public class Evento {
    private String idEvento;
    private String categoria;
    private String idAsociacion;
    private String descripcion;
    private String duracion;
    private String fecha;
    private String lugar;
    private String requisitos;
    private String titulo;
    private Boolean encuesta;
    private String capacidad;

    public Evento() {
        // Default constructor required for Firebase
    }

    public Evento(String idEvento, String titulo, String descripcion, String categoria, String idAsociacion, String lugar, String duracion, String fecha, String requisitos, Boolean encuesta, String capacidad) {
        this.idEvento = idEvento;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.duracion = duracion;
        this.fecha = fecha;
        this.categoria = categoria;
        this.requisitos = requisitos;
        this.idAsociacion = idAsociacion;
        this.encuesta = encuesta;
        this.capacidad = capacidad;
}

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String IdEvento) {
        this.idEvento = IdEvento;
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

    public void setDuracion(String duración) {
        this.duracion = duración;
    }

    public String getDuracion() {
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

    public String getIdAsociacion() { return idAsociacion; }

    public void setIdAsociacion(String idAsociacion) {
        this.idAsociacion = idAsociacion;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public Boolean getEncuesta() {
        return encuesta;
    }

    public void setEncuesta(Boolean encuesta) {
        this.encuesta = encuesta;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }
}
