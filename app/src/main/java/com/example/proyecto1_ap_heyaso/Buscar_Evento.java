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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Buscar_Evento extends AppCompatActivity{
    private FirebaseFirestore db;
    private TextView viewInfoEvento;
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
        eventos = new ArrayList<>();
        spinner = findViewById(R.id.spinner4);
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
                getInfoEvento(spinner.getSelectedItem().toString());
            }
        });
    }

    private void getEventos(){
        eventos.add("Seleccione un evento");
        db.collection("Evento").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documento : task.getResult()){
                        eventos.add(documento.getString("idEvento"));
                    }
                    spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, eventos));
                }
                else {
                    Toast.makeText(Buscar_Evento.this, "Error al cargar pagina", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getInfoEvento(String id){
        if(id != "Seleccione un evento"){
            db.collection("Evento").whereEqualTo("idEvento", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for(QueryDocumentSnapshot documento : task.getResult()) {
                            String resultado = "";
                            if(Boolean.TRUE.equals(documento.getBoolean("encuesta"))){ resultado = "habilitada"; } else {resultado = "deshabilitada";};
                            String info = "Id: " + id + "\nTítulo: " + documento.getString("titulo") + "\nCategoría: " + documento.getString("categoria") +
                                    "\nAsociacion: " + documento.getString("asociacion")/*.substring(10)*/ +
                                    "\nDescripción: " + documento.getString("descripcion") + "\nFecha: " + documento.getString("fecha") + "\nDuración: " +
                                    documento.getString("duracion") + "\nLugar: " + documento.getString("lugar") + "\nRequisitos: " + documento.getString("requisitos") +
                                    "\nEncuesta: " + resultado;
                            viewInfoEvento.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                            viewInfoEvento.setText(info);
                        }
                    }
                }
            });
        }
        else{
            viewInfoEvento.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            viewInfoEvento.setText("*Acá se muestra la información del evento buscado*");
            Toast.makeText(Buscar_Evento.this, "No se encontró ningún evento para buscar", Toast.LENGTH_SHORT).show();
        }
    }
}