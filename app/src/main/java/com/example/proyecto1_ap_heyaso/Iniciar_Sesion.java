package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Iniciar_Sesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        // Boton | Accion para ir pantallas correspondientes

        Button button = (Button) findViewById(R.id.btn_ingresar);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenIngresar();
            }
        });

        Button buttonDos = (Button) findViewById(R.id.btn_volver9);
        buttonDos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                OpenVolver();
            }
        });
    }

    //Funciones ir pantallas
    public void OpenIngresar() {
        Intent intent = new Intent(this, Iniciar_Sesion.class);
        startActivity(intent);
    }

    public void OpenVolver() {
        Intent intent = new Intent(this, Pagina_Principal.class);
        startActivity(intent);
    }
}