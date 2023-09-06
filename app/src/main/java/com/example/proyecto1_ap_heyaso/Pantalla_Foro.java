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
    }

    public void OpenEvaluar() {
        Intent intent = new Intent(this, Evaluar_Evento.class);
        startActivity(intent);
    }

    public void OpenPropuesta() {
        Intent intent = new Intent(this, Propuesta_Evento.class);
        startActivity(intent);
    }
}
