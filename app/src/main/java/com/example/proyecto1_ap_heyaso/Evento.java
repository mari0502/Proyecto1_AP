package com.example.proyecto1_ap_heyaso;

public class Evento {
    private String titulo;
    private String IdEvento;
    private String descripcion;
    private String lugar;
    private String duración;
    private String fecha;
    private String categoria;
    private String requisitos;




    public Evento(String idEvento, String titulo, String descripcion, String lugar, String duración, String fecha, String categoria, String requisitos) {
        this.IdEvento= idEvento;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.duración = duración;
        this.fecha = fecha;
        this.categoria = categoria;
        this.requisitos = requisitos;
}

    public String getIdEvento() {
        return IdEvento;
    }

    public void setIdEvento(String idEvento) {
        IdEvento = idEvento;
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
        this.duración = duración;
    }

    public String getDuración() {
        return duración;
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
