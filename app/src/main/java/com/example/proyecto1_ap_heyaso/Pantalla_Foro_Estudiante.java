package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.checkerframework.checker.units.qual.C;

public class Pantalla_Foro_Estudiante extends AppCompatActivity {

    private Button btnPreguntas, btnPropuesta, btnEvaluar, btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_foro_estudiante);

        btnEvaluar = findViewById(R.id.btnEvaluarEventEst);
        btnEvaluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenEvaluar();
            }
        });

        btnPreguntas = findViewById(R.id.btnPreguntasEst);
        btnPreguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenPreguntas();
            }
        });

        btnPropuesta = findViewById(R.id.btnPropuestaEst);
        btnPropuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenPropuesta();
            }
        });

        btnVolver = findViewById(R.id.btn_volver18);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reOpenMenu();
            }
        });
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
        Intent intent = new Intent(this, menuPrincipalEstudiante.class);
        startActivity(intent);
    }
}