package com.example.proyecto1_ap_heyaso;

public class Usuario_Global {
    private static Usuario_Global instance;
    private String idUsuario;
    private String idTipo;

    private Usuario_Global() {}

    public static synchronized Usuario_Global getInstance() {
        if (instance == null) {
            instance = new Usuario_Global();
        }
        return instance;
    }

    //Getters and setters
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(String idTipo) {
        this.idTipo = idTipo;
    }
}
