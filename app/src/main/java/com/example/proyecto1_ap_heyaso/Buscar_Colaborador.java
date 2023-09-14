package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Buscar_Colaborador extends AppCompatActivity {
    private FirebaseFirestore db;
    private TextView viewInfoColaborador;
    private List<String> colaboradores;
    private Spinner spinner;
    private Button btnVolver;
    private Button btnBuscar;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_colaborador);

        db = FirebaseFirestore.getInstance();
        viewInfoColaborador = findViewById(R.id.viewInfoColaborador);
        colaboradores = new ArrayList<>();
        spinner = findViewById(R.id.spinner);
        btnVolver = findViewById(R.id.btn_volver13);
        btnBuscar = findViewById(R.id.btn_buscar4);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInfoColaborador(spinner.getSelectedItem().toString());
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Administrar_Colaboradores.class);
                startActivity(intent);
            }
        });
    }

    private void getColaboradores(){

    }

    private void getInfoColaborador(String id){

    }
}