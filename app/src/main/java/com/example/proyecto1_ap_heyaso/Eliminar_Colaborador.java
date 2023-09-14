package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Eliminar_Colaborador extends AppCompatActivity {
    private FirebaseFirestore db;
    private Button btnEliminar;
    private Button btnVolver;
    private Spinner spinner;
    private List<String> colaboradores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_colaborador);

        db = FirebaseFirestore.getInstance();
        colaboradores = new ArrayList<>();
        btnEliminar = findViewById(R.id.btn_eliminar3);
        btnVolver = findViewById(R.id.btn_volver14);
        spinner = findViewById(R.id.colaborador2);

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }
}