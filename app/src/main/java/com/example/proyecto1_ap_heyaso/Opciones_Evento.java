package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;


public class Opciones_Evento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_evento);

        Button button = (Button) findViewById(R.id.btn_compartir);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenCompartirEnRedes();
            }
        });

        Button button2 = (Button) findViewById(R.id.btn_administrar);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenAdministrarAgenda();
            }
        });

    }



    public void OpenCompartirEnRedes() {
        Intent intent = new Intent(this, Compartir_en_redes.class);
        startActivity(intent);
    }
    public void OpenAdministrarAgenda() {
        Intent intent = new Intent(this, AdministrarAgenda.class);
        startActivity(intent);
    }



}