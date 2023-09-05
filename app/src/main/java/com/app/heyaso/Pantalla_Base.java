package com.app.heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Pantalla_Base extends AppCompatActivity {

    Button btn_registrarAso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_base);

        btn_registrarAso = findViewById(R.id.btn_registrarEst);

        btn_registrarAso.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Pantalla_Base.this, Crear_Evento.class));
            }
        });
    }
}