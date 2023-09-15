package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Administrar_Colaboradores extends AppCompatActivity {
    private Button btnBuscarColaborador;
    private Button btnModificarColaborador;
    private Button btnEliminarColaborador;
    private Button btnSalir;
    private Button btnAgregarColab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_colaboradores);

        //Obtiene el objeto
        Intent intent = getIntent();

        btnBuscarColaborador = findViewById(R.id.btn_buscarcolaborador);
        btnModificarColaborador = findViewById(R.id.btn_modificarcolaborador);
        btnEliminarColaborador = findViewById(R.id.btn_eliminarcolaborador);
        btnSalir = findViewById(R.id.btn_salir3);
        btnAgregarColab = findViewById(R.id.btn_agregarColab);

        btnBuscarColaborador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Buscar_Colaborador.class);
                startActivity(intent);
            }
        });

        btnModificarColaborador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Modificar_Colaborador.class);
                startActivity(intent);
            }
        });

        btnEliminarColaborador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Eliminar_Colaborador.class);
                startActivity(intent);
            }
        });

        btnAgregarColab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Colaboradores_Asociacion.class);
                startActivity(intent);
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}