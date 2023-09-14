package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

public class Modificar_Colaborador extends AppCompatActivity {
    private FirebaseFirestore db;
    private Button btnModificar;
    private Button btnVolver;
    private TextInputEditText nombre;
    private TextInputEditText carrera;
    private TextInputEditText puesto;
    private TextInputEditText correo;
    private TextInputEditText contrasenna;
    private TextInputEditText contacto;
    private TextInputEditText descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_colaborador);

        db = FirebaseFirestore.getInstance();
        nombre = findViewById(R.id.nombre);
        carrera = findViewById(R.id.carrera2);
        puesto = findViewById(R.id.puesto2);
        correo = findViewById(R.id.correo4);
        nombre = findViewById(R.id.nombre);
        contrasenna = findViewById(R.id.contrasenna3);
        contacto = findViewById(R.id.contacto4);
        descripcion = findViewById(R.id.descripcion5);
        btnModificar = findViewById(R.id.btn_modificar9);
        btnVolver = findViewById(R.id.btn_volver10);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}