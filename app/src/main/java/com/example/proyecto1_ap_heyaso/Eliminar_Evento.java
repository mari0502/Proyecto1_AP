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

public class Eliminar_Evento extends AppCompatActivity {
    private FirebaseFirestore db;
    private Button btn_eliminar;
    private Button btn_volver;
    private Spinner spinner_eventos;
    private List<String> eventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_evento);

        db = FirebaseFirestore.getInstance();
        btn_eliminar = findViewById(R.id.btn_eliminar2);
        btn_volver = findViewById(R.id.btn_volver12);
        spinner_eventos = findViewById(R.id.evento10);
        eventos = new ArrayList<String>();

        getEventos();

        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarEvento();
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
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documento : task.getResult()){
                        eventos.add(documento.getString("idEvento"));
                    }
                    spinner_eventos.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, eventos));
                }
                else {
                    Toast.makeText(Eliminar_Evento.this, "Error al cargar pagina", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void eliminarEvento(){
        //eliminar en cascada
        Toast.makeText(Eliminar_Evento.this, "Evento eliminado correctamente", Toast.LENGTH_SHORT).show();
    }
}