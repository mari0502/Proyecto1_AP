package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Buscar_Evento extends AppCompatActivity{
    private FirebaseFirestore db;
    private TextView viewInfoEvento;
    private List<String> eventoInfo;
    private List<String> eventos;
    private Spinner spinner;
    private Button btnVolver;
    private Button btnBuscar;
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_evento);

        db = FirebaseFirestore.getInstance();
        viewInfoEvento = findViewById(R.id.viewInfoEvento);
        eventoInfo = new ArrayList<>();
        eventos = new ArrayList<>();
        spinner = findViewById(R.id.spinner2);
        btnVolver = findViewById(R.id.btn_volver3);
        btnBuscar = findViewById(R.id.btn_buscar2);

        getEventos();

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Administrar_Evento.class);
                startActivity(intent);
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(spinner.getSelectedItem().toString());
                getInfoEvento(spinner.getSelectedItem().toString());
            }
        });
    }

    private void getEventos(){
        eventos.add("Seleccione una opción");
        db.collection("Evento").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documento : queryDocumentSnapshots){
                    eventos.add(documento.getString("idEvento"));
                }
                spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, eventos));
            }
        });
    }

    private void getInfoEvento(String id){
        if(id != "Seleccione una opción"){
            Query query = db.collection("Evento");
            query = query.whereEqualTo("idEvento", "id");
            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (QueryDocumentSnapshot documento : queryDocumentSnapshots) {
                        /*String info = "Id: " + id + "Título: " + documentSnapshot.getString("titulo") + "Categoría: " + documentSnapshot.getString("categoria") +
                                "Descripción: " + documentSnapshot.getString("descripcion") + "Fecha: " + documentSnapshot.getString("fecha") + "Duración: " +
                                documentSnapshot.getString("duracion") +  "Lugar: " + documentSnapshot.getString("lugar") + "Requisitos: "  + documentSnapshot.getString("requisitos");*/
                        System.out.print(documento.getString("idEvento"));
                        //viewInfoEvento.setText(info);
                    }
                }
            });
        }
        else{
            Toast.makeText(Buscar_Evento.this, "No se encontró ningún evento para buscar", Toast.LENGTH_SHORT).show();
        }
    }
}