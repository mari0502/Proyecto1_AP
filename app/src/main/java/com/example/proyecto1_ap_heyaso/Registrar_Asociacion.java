package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registrar_Asociacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_asociacion);

        // Boton | Accion para ir pantallas correspondientes

        Button button = (Button) findViewById(R.id.btn_continuar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenContinuarRegistroAso();
            }
        });

        Button buttonDos = (Button) findViewById(R.id.btn_volver12);
        buttonDos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenVolverPantallaPrincipal();
            }
        });

    }

    //Funciones ir pantallas
    public void OpenContinuarRegistroAso() {
        //Hacer el registro asociacion en DB

        //Pasar a registrar colaboradores
        Intent intent = new Intent(this, Colaboradores_Asociacion.class);
        startActivity(intent);
    }

    public void OpenVolverPantallaPrincipal() {
        Intent intent = new Intent(this, Pagina_Principal.class);
        startActivity(intent);
    }
}