package com.example.proyecto1_ap_heyaso;

public class Inscripcion {

    private String idEvento;
    private String nombreusuario;
    private String contactousuario;

    public Inscripcion() {
        // Default constructor required for Firebase
    }

    public Inscripcion(String idEvento, String nombreusuario, String contactousuario) {

        this.idEvento = idEvento;
        this.nombreusuario = nombreusuario;
        this.contactousuario = contactousuario;

    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getContactousuario() {
        return contactousuario;
    }

    public void setContactousuario(String contactousuario) {
        this.contactousuario = contactousuario;
    }
}
