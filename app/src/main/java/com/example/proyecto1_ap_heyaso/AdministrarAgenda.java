package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdministrarAgenda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_agenda);

        Button button = (Button) findViewById(R.id.btn_volver);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                reOpenOpciones_evento();
            }
        });

        Button button2 = (Button) findViewById(R.id.btn_crearActividad);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenCrearActividad();
            }
        });

        Button button3 = (Button) findViewById(R.id.btn_buscarActividad);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenBuscarActividad();
            }
        });

        Button button4 = (Button) findViewById(R.id.btn_modificarActividad);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenModificarActividad();
            }
        });

        Button button5 = (Button) findViewById(R.id.btn_eliminarActividad);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenEliminarActividad();
            }
        });
    }


    public void reOpenOpciones_evento() {
        Intent intent = new Intent(this, Opciones_Evento.class);
        startActivity(intent);
    }

    public void OpenCrearActividad() {
        Intent intent = new Intent(this, Crear_Actividad.class);
        startActivity(intent);
    }

    public void OpenBuscarActividad() {
        Intent intent = new Intent(this, Buscar_Actividad.class);
        startActivity(intent);
    }

    public void OpenModificarActividad() {
        Intent intent = new Intent(this, modificar_actividad.class);
        startActivity(intent);
    }

    public void OpenEliminarActividad() {
        Intent intent = new Intent(this, Eliminar_Actividad.class);
        startActivity(intent);
    }
}