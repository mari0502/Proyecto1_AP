package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Pagina_Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_principal);

        // Boton | Accion para ir pantallas correspondientes

        Button button = (Button) findViewById(R.id.btn_iniciarSesion);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenIniciarSesion();
            }
        });

        Button buttonDos = (Button) findViewById(R.id.btn_registrarEstudiante);
        buttonDos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenRegistrarEstudiante();
            }
        });

        Button buttonTres = (Button) findViewById(R.id.btn_registrarAso2);
        buttonTres.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenRegistrarAsociacion();
            }
        });
    }

    //Funciones ir pantallas
    public void OpenIniciarSesion() {
        Intent intent = new Intent(this, Iniciar_Sesion.class);
        startActivity(intent);
    }

    public void OpenRegistrarEstudiante() {
        Intent intent = new Intent(this, Registrar_Estudiante.class);
        startActivity(intent);
    }

    public void OpenRegistrarAsociacion() {
        Intent intent = new Intent(this, Registrar_Asociacion.class);
        startActivity(intent);
    }
}