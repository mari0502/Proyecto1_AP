package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Administrar_Evento extends AppCompatActivity {

    Button btn_crearEvento;
    Button btn_buscarEvento;
    Button btn_modificarEvento;
    Button btn_eliminarEvento;
    Button btn_salir;

    Button btn_OpcionesEvento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_evento);

        btn_crearEvento = findViewById(R.id.btn_crearevento);
        btn_buscarEvento = findViewById(R.id.btn_buscarevento);
        btn_modificarEvento = findViewById(R.id.btn_modificarevento);
        btn_eliminarEvento = findViewById(R.id.btn_eliminarevento);
        btn_salir = findViewById(R.id.btn_salir2);

        btn_crearEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Crear_Evento.class);
                startActivity(intent);
            }
        });

        btn_buscarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Buscar_Evento.class);
                startActivity(intent);
            }
        });

        btn_modificarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Modificar_Evento.class);
                startActivity(intent);
            }
        });

        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), menuPrincipalColaborador.class);
                startActivity(intent);
            }
        });


        /*btn_OpcionesEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Opciones_Evento.class);
                startActivity(intent);
            }
        });*/
    }
}