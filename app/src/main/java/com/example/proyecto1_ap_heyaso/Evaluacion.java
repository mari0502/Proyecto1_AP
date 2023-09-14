package com.example.proyecto1_ap_heyaso;

public class Evaluacion{
    private String calificacion;
    private String idEvaluacion;
    private String comentario;
    private String idEvento;
    private String carnet;

    public Evaluacion() {
        // constructor
    }

    public Evaluacion(String idEvaluacion, String idEvento, String carnet, String comentario, String calificacion) {
        this.idEvaluacion = idEvaluacion;
        this.idEvento = idEvento;
        this.carnet = carnet;
        this.comentario = comentario;
        this.calificacion = calificacion;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public String getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(String idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }
}
