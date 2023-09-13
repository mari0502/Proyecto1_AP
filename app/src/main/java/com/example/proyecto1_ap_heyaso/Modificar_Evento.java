package com.example.proyecto1_ap_heyaso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Modificar_Evento extends AppCompatActivity {
    private FirebaseFirestore db;
    private List<String> eventos;
    private Button btn_buscar;
    private Button btn_volver;
    private Spinner inputId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_evento);
        db = FirebaseFirestore.getInstance();

        eventos = new ArrayList<>();
        inputId = findViewById(R.id.evento3);
        btn_buscar = findViewById(R.id.btn_buscar);
        btn_volver = findViewById(R.id.btn_volver6);

        getEventos();

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //que pase a modificar 2 y lleve el id del evento
                Intent intent = new Intent(Modificar_Evento.this, Modificar_Evento2.class);
                intent.putExtra("id",inputId.getSelectedItem().toString());
                startActivity(intent);
            }
        });

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Administrar_Evento.class);
                startActivity(intent);
            }
        });
    }

    private void getEventos(){
        eventos.add("Seleccione un evento");
        db.collection("Evento").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @Override
            public void onComplete(@com.example.proyecto1_ap_heyaso.NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documento : task.getResult()){
                        eventos.add(documento.getString("idEvento"));
                    }
                    inputId.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, eventos));
                }
                else {
                    Toast.makeText(Modificar_Evento.this, "Error al cargar pagina", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}