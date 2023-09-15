package com.example.proyecto1_ap_heyaso;

import java.io.Serializable;

public class Usuarios implements Serializable {
    private String carnetUsuario;
    private String nombreEstudiante;
    private String idAsociacionUsuario;
    private String idTipoUsuario;
    private String correoUsuario;
    private String contrasennaUsuario;

    public Usuarios() {
        //Default
    }

    public Usuarios(String carnetUsuario, String nombreEstudiante, String idAsociacionUsuario, String idTipoUsuario, String correoUsuario, String contrasennaUsuario) {
        this.carnetUsuario = carnetUsuario;
        this.nombreEstudiante = nombreEstudiante;
        this.idAsociacionUsuario = idAsociacionUsuario;
        this.idTipoUsuario = idTipoUsuario;
        this.correoUsuario = correoUsuario;
        this.contrasennaUsuario = contrasennaUsuario;
    }

    //Getters and Setters
    public String getCarnetUsuario() {
        return carnetUsuario;
    }

    public void setCarnetUsuario(String carnetUsuario) {
        this.carnetUsuario = carnetUsuario;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getIdAsociacionUsuario() {
        return idAsociacionUsuario;
    }

    public void setIdAsociacionUsuario(String idAsociacionUsuario) {
        this.idAsociacionUsuario = idAsociacionUsuario;
    }

    public String getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(String idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getContrasennaUsuario() {
        return contrasennaUsuario;
    }

    public void setContrasennaUsuario(String contrasennaUsuario) {
        this.contrasennaUsuario = contrasennaUsuario;
    }
}
