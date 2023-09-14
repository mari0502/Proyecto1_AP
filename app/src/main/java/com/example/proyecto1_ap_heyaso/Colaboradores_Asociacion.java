package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Colaboradores_Asociacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colaboradores_asociacion);

        // Boton | Accion para ir pantallas correspondientes

        Button button = (Button) findViewById(R.id.btn_annadirColab);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { OpenRegistroColabAso(); }
        });

        Button buttonDos = (Button) findViewById(R.id.btn_volver11);
        buttonDos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { OpenVolverPantallaPrincipal(); }
        });
    }

    //Funciones ir pantallas
    public void OpenRegistroColabAso() {
        //Hacer el registro colab asociacion en DB

        //Registra colaboradores y refreca pagina
        Intent intent = new Intent(this, Colaboradores_Asociacion.class);
        startActivity(intent);
    }

    public void OpenVolverPantallaPrincipal() {
        Intent intent = new Intent(this, Pagina_Principal.class);
        startActivity(intent);
    }


}