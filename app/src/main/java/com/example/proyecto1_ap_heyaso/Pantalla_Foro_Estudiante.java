package com.example.proyecto1_ap_heyaso;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Pantalla_Foro_Estudiante extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_foro);

        Button btnEvaluar = (Button) findViewById(R.id.evaluarEventoEstudiantes);
        btnEvaluar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenEvaluar();
            }
        });

        Button btnPropuesta = (Button) findViewById(R.id.propuestaEstudiante);
        btnPropuesta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenPropuesta();
            }
        });

        Button btnPreguntas = (Button) findViewById(R.id.preguntasEstudiantes);
        btnPreguntas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenPreguntas();
            }
        });

        Button btnVolver = (Button) findViewById(R.id.btn_volverEstudiantesForo);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { reOpenMenu(); }
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
