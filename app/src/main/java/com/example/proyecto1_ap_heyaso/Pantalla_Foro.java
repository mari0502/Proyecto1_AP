package com.example.proyecto1_ap_heyaso;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class Pantalla_Foro extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_foro);

        Button btnEvaluar = (Button) findViewById(R.id.evaluarEvento);
        btnEvaluar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenEvaluar();
            }
        });

        Button btnPropuesta = (Button) findViewById(R.id.propuesta);
        btnPropuesta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenPropuesta();
            }
        });

        Button btnPreguntas = (Button) findViewById(R.id.preguntas);
        btnPreguntas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenPreguntas();
            }
        });

        Button btnAprobarPropuesta = (Button) findViewById(R.id.aprobarPropuesta);
        btnAprobarPropuesta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenAprobar();
            }
        });

        Button btnVolver = (Button) findViewById(R.id.btn_volver7);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { reOpenMenu(); }
        });
    }

    public void OpenAprobar() {
        Intent intent = new Intent(this, Procesar_propuestas.class);
        startActivity(intent);
    }

    public void OpenEvaluar() {
        Intent intent = new Intent(this, Evaluar_Evento.class);
        startActivity(intent);
    }

    public void OpenPropuesta() {
        Intent intent = new Intent(this, Propuesta_Evento.class);
        startActivity(intent);
    }

    public void OpenPreguntas() {
        Intent intent = new Intent(this, Preguntas_Foro.class);
        startActivity(intent);
    }

    public void reOpenMenu() {
        //Falta código
    }


}
