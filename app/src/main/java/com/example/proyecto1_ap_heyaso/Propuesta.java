package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.widget.TintTypedArray;

public class Propuesta {
    private String idPropuesta, titulo, descripcion, categoria, objetivos, actividades, estado;

    public Propuesta() {
        //Default constructor required for Firebase
    }

    public Propuesta(String titulo, String descripcion, String objetivos, String categoria, String actividades, String estado) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.objetivos = objetivos;
        this.categoria = categoria;
        this.actividades = actividades;
        this.estado = estado;
    }

    //Getters and setters

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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    public String getActividades() {
        return actividades;
    }

    public void setActividades(String actividades) {
        this.actividades = actividades;
    }


    public String getIdPropuesta() {
        return idPropuesta;
    }

    public void setIdPropuesta(String idPropuesta) {
        this.idPropuesta = idPropuesta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
